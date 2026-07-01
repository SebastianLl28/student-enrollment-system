# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package -DskipTests

# Run locally (requires MySQL via Docker)
docker compose up -d mysql      # start DB only
./mvnw spring-boot:run          # run app against local DB

# Run everything with Docker
docker compose up --build

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Package without tests
./mvnw clean package -DskipTests
```

## Environment variables required

Copy `.env` and fill in secrets before running:

| Variable | Purpose |
|---|---|
| `JWT_SECRET` | HS256 secret for session tokens |
| `PAYPAL_CLIENT_ID` | PayPal sandbox client ID |
| `PAYPAL_CLIENT_SECRET` | PayPal sandbox secret |
| `EMAIL_USERNAME` | Mailtrap SMTP username |
| `EMAIL_PASSWORD` | Mailtrap SMTP password |
| `DB_USERNAME` / `DB_PASSWORD` | MySQL credentials (default: `admin`/`admin`) |

`app.base-url` in `application.properties` must be a publicly reachable URL for PayPal webhooks and return URLs. For local dev, use a tunnel (e.g., Cloudflare Tunnel or ngrok).

## Architecture

**Spring Boot 3.2 + Java 21 MVC web app** backed by MySQL. Thymeleaf with the Layout Dialect renders all views server-side. Spring Security guards `/app/secure/**` with form-based login (session cookie). There is no REST API for the frontend — all controllers return view names or redirects.

### Package layout

```
pe.utp.marcodesarrolloweb
├── configuration/
│   ├── auth/          # SecurityConfig, ApplicationConfig (UserDetailsService, PasswordEncoder)
│   ├── common/        # DataSeeder, AppProperties (@ConfigurationProperties), AppConstants,
│   │                  # CreatedOrder / Link / OrderResponse (PayPal response records)
│   └── paypal/        # PayPalConfig (RestClient bean), PayPalProperties
├── controller/        # @Controller (Thymeleaf) + @RestController (PayPal webhook)
├── dto/               # Auth request/response DTOs
├── model/             # JPA entities + enums/
├── repository/        # Spring Data JPA repositories
└── service/           # Business logic
```

### Key design decisions

**Enrollment → Payment flow (orchestrated, not transactional across boundaries)**

`EnrollmentPaymentOrchestrator` coordinates the three steps that must NOT share a single DB transaction:
1. Persist the enrollment (`EnrollmentService.create`)
2. Call PayPal REST API to create an order (`PayPalOrderService.createOrder`) — external call, no rollback possible
3. Persist PayPal orderId + amount back on the enrollment (`EnrollmentService.attachPayPalOrder`)
4. Send payment-link email (`MailService`)

This means a crash between steps can leave an enrollment without a PayPal order ID. Handle that case explicitly when adding retry/recovery logic.

**PayPal webhook is the source of truth for payment confirmation**

`PayPalWebhookController` (`/payments/paypal/webhook`) listens for `CHECKOUT.ORDER.APPROVED` and `PAYMENT.CAPTURE.COMPLETED`. On capture, it calls `EnrollmentService.markAsPaid` which transitions the enrollment to `PAID`. The `/payments/paypal/return` redirect is the UX path but is not the authoritative confirmation.

CSRF is disabled for `/payments/paypal/**` so PayPal can POST to the webhook.

**Enrollment status lives in `Enrollment` directly** (no separate PaymentOrder entity yet)

The `Enrollment` entity stores `paypalOrderId`, `amount`, `currency`, and `paidAt` directly. The data model docs describe a richer `PaymentOrder` entity as a future goal — do not assume it exists in code today.

**DataSeeder** seeds users, academic programs, periods, and students on every startup using `CommandLineRunner`. It guards against duplicates via `existsByCode`/`existsByName` checks. Default admin user: `u12345678@utp.eud.pe` / `123456`.

**DDL mode** is `create-drop` by default (local) and `update` in Docker. Changing to `validate` or `none` in production requires running migrations manually.

### Business rules enforced in code

- A new enrollment starts as `PENDING_PAYMENT`; transitions to `PAID` only via PayPal webhook
- Duplicate enrollment guard: same `(studentId, programId, periodId)` where status ≠ `CANCELLED` is rejected
- Inactive academic periods reject new enrollments
- `EnrollmentStatus` enum: `PENDING_PAYMENT`, `PAID`, `CANCELLED` (not `ENROLLED` — the docs use that name but the enum value is `PAID`)

### Templates

Thymeleaf templates live in `src/main/resources/templates/` organized by module (`academicPeriod/`, `academicProgram/`, `enrollment/`, `payments/`). The Layout Dialect is used — look for a layout fragment to understand the shared shell. The `activeMenu` `@ModelAttribute` on each controller drives the sidenav highlight.

## Data model

These are the five JPA entities that exist today. No other entities are planned for the MVP.

### Student
`code` (unique), `firstName`, `lastName`, `documentType` (`DNI` | `PASSPORT` | `FOREIGN_CARD`), `documentNumber`, `email`, `phone`, `address`, `status` (`ACTIVE` | `INACTIVE`), `createdAt`.  
`getFullName()` returns `firstName + " " + lastName`.

### AcademicProgram
`code` (unique), `name`, `description`, `active` (Boolean), `price` (BigDecimal), `createdAt`.  
`price` is the enrollment fee sent to PayPal when a student enrolls.

### AcademicPeriod
`name` (unique), `startDate`, `endDate`, `active` (Boolean), `createdAt`.  
`active = false` blocks new enrollments — the only period gate currently enforced.

### Enrollment
`studentId`, `academicProgramId`, `academicPeriodId` (FK columns), plus lazy `@ManyToOne` relations (`student`, `academicProgram`, `academicPeriod`).  
`enrollmentDate`, `status` (`PENDING_PAYMENT` | `PAID` | `CANCELLED`), `observation`.  
PayPal fields embedded directly: `paypalOrderId`, `amount`, `currency`, `paidAt`.

### User
`firstName`, `lastName`, `email`, `password` (BCrypt), `userStatus` (`ACTIVE` | `INACTIVE`), `createdAt`.  
No role field yet — Spring Security currently only checks `authenticated` for `/app/secure/**`.

## MVP enrollment flow

```
1. Admin creates a Student
2. Admin creates an Enrollment (student + program + period)
        └─ EnrollmentService.create() validates:
              • period.active must be true
              • no duplicate (studentId, programId, periodId) where status ≠ CANCELLED
        └─ Enrollment saved with status = PENDING_PAYMENT
3. EnrollmentPaymentOrchestrator calls PayPalOrderService.createOrder()
        └─ PayPal order created with amount = program.price, custom_id = enrollment.id
        └─ enrollmentService.attachPayPalOrder() persists paypalOrderId + amount + currency
4. MailService sends the PayPal approval link to student.email
5. Student pays via PayPal
6. PayPal POSTs to /payments/paypal/webhook
        └─ CHECKOUT.ORDER.APPROVED  → captureOrder() → markAsPaid()
        └─ PAYMENT.CAPTURE.COMPLETED → markAsPaid()  (authoritative source of truth)
        └─ Enrollment.status → PAID, paidAt set
```

## MVP remaining tasks

- Enrollment cancellation (set status → `CANCELLED`; guard: cannot cancel if already `PAID`)
- Student activate/deactivate toggle
- AcademicProgram activate/deactivate toggle
- AcademicPeriod open/close toggle
- Enrollment detail view (show PayPal order ID, amount, paidAt)

Out of scope for MVP: payment methods, payment orders as a separate entity, payment receipts, roles/permissions, reports, user management UI.
