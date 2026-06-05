# Contexto del Modelo de Datos - Sistema de Matrícula y Pagos de Estudiantes

## 1. Alcance General del Modelo

Este sistema está enfocado en la gestión de matrículas y pagos de estudiantes.

El modelo de datos debe soportar el siguiente flujo principal:

```text
Student
→ Enrollment
→ Payment Order
→ Payment
→ Payment Receipt
```

El sistema no gestiona cursos, profesores, horarios, aulas, calificaciones, asistencia ni evaluaciones académicas.

Un estudiante no se matricula en cursos individuales. Un estudiante se matricula en un programa académico durante un periodo académico específico.

---

## 2. Modelos Principales

## 2.1 Student

Representa a un estudiante registrado en la institución.

El estudiante es la persona principal que será matriculada y asociada a los pagos.

### Propósito

El modelo `Student` almacena la información personal y de contacto básica del estudiante.

### Información sugerida

- Código del estudiante
- Nombres
- Apellidos
- Tipo de documento
- Número de documento
- Correo electrónico
- Teléfono
- Dirección
- Estado
- Fecha de creación

### Estados sugeridos

```text
ACTIVE
INACTIVE
```

### Relaciones

```text
Student 1 ─── * Enrollment
Student 1 ─── * PaymentOrder
```

Un estudiante puede tener muchas matrículas a lo largo del tiempo, ya que puede matricularse en diferentes periodos académicos.

---

## 2.2 Academic Program

Representa el programa, carrera o servicio educativo ofrecido por la institución.

Ejemplos:

```text
Software Engineering
Business Administration
Accounting
English Program
Pre-university Program
```

### Propósito

El modelo `AcademicProgram` define dónde se está matriculando el estudiante.

El estudiante no se matricula solo en un periodo. El estudiante se matricula en un programa dentro de un periodo.

Ejemplo:

```text
Estudiante: Juan Pérez
Programa Académico: Software Engineering
Periodo Académico: 2026-I
```

### Información sugerida

- Nombre
- Código
- Descripción
- Estado
- Fecha de creación

### Estados sugeridos

```text
ACTIVE
INACTIVE
```

### Relaciones

```text
AcademicProgram 1 ─── * Enrollment
```

Un programa académico puede tener muchas matrículas.

---

## 2.3 Academic Period

Representa el periodo académico en el que un estudiante se matricula.

Ejemplos:

```text
2026-I
2026-II
Summer 2026
March 2026
Regular Cycle 2026
```

### Propósito

El modelo `AcademicPeriod` controla cuándo se realiza la matrícula.

Es importante porque una institución puede abrir o cerrar periodos de matrícula.

### Información sugerida

- Nombre
- Fecha de inicio
- Fecha de fin
- Estado
- Fecha de creación

### Estados sugeridos

```text
OPEN
IN_PROGRESS
CLOSED
CANCELLED
```

### Relaciones

```text
AcademicPeriod 1 ─── * Enrollment
```

Un periodo académico puede tener muchas matrículas.

### Regla de negocio

Un periodo académico cerrado o cancelado no debe permitir nuevas matrículas.

---

## 2.4 Enrollment

Representa la matrícula del estudiante en un programa académico y periodo académico.

Este es uno de los modelos centrales del sistema.

### Propósito

El modelo `Enrollment` conecta:

```text
Student + AcademicProgram + AcademicPeriod
```

Representa que un estudiante específico se está matriculando en un programa específico durante un periodo específico.

Ejemplo:

```text
Juan Pérez está matriculado en Software Engineering para el periodo académico 2026-I.
```

### Información sugerida

- Estudiante
- Programa académico
- Periodo académico
- Fecha de matrícula
- Estado de la matrícula
- Observación
- Fecha de creación

### Estados sugeridos

```text
PENDING_PAYMENT
ENROLLED
CANCELLED
```

### Relaciones

```text
Student 1 ─── * Enrollment
AcademicProgram 1 ─── * Enrollment
AcademicPeriod 1 ─── * Enrollment
Enrollment 1 ─── * PaymentOrder
```

### Reglas de negocio

- Una nueva matrícula debe iniciar como `PENDING_PAYMENT`.
- La matrícula pasa a `ENROLLED` solo cuando el pago requerido es confirmado.
- Una matrícula cancelada no debe permitir nuevos pagos.
- Una matrícula siempre debe pertenecer a un estudiante, un programa académico y un periodo académico.

---

## 2.5 Payment Concept

Representa el motivo o concepto por el cual el estudiante debe pagar.

Ejemplos:

```text
Enrollment Fee
Monthly Fee
Registration Fee
Administrative Fee
```

### Propósito

El modelo `PaymentConcept` evita escribir los motivos de pago como texto plano en cada orden de pago.

En lugar de escribir “Enrollment Fee” manualmente cada vez, el sistema usa un concepto predefinido.

### Información sugerida

- Nombre
- Código
- Descripción
- Estado
- Fecha de creación

### Estados sugeridos

```text
ACTIVE
INACTIVE
```

### Relaciones

```text
PaymentConcept 1 ─── * PaymentOrder
```

Un concepto de pago puede usarse en muchas órdenes de pago.

---

## 2.6 Payment Order

Representa el monto que el estudiante debe pagar.

Este modelo es diferente de `Payment`.

```text
PaymentOrder = Lo que el estudiante debe pagar
Payment = Lo que el estudiante realmente pagó
```

### Propósito

El modelo `PaymentOrder` representa una obligación financiera pendiente generada a partir de una matrícula.

Ejemplo:

```text
Estudiante: Juan Pérez
Concepto: Enrollment Fee
Monto: S/ 350.00
Estado: Pendiente
```

### Información sugerida

- Estudiante
- Matrícula
- Concepto de pago
- Monto
- Fecha de emisión
- Fecha de vencimiento
- Estado
- Fecha de creación

### Estados sugeridos

```text
PENDING
PAID
EXPIRED
CANCELLED
```

### Relaciones

```text
Enrollment 1 ─── * PaymentOrder
PaymentConcept 1 ─── * PaymentOrder
PaymentOrder 1 ─── * Payment
```

### Reglas de negocio

- Cuando se crea una matrícula, el sistema debe generar una orden de pago.
- Una orden de pago inicia como `PENDING`.
- Cuando se confirma un pago válido, la orden de pago pasa a `PAID`.
- Si la matrícula se cancela, la orden de pago pendiente también puede cancelarse.
- Una orden de pago pagada no debe permitir otro pago confirmado, a menos que se admitan pagos parciales.
- Para este sistema inicial, se recomienda soportar un pago completo por orden de pago.

---

## 2.7 Payment Method

Representa el método de pago utilizado por el estudiante.

Ejemplos:

```text
Cash
Bank Transfer
Yape
Plin
Card
```

### Propósito

El modelo `PaymentMethod` define los métodos de pago aceptados por la institución.

Algunos métodos de pago pueden requerir un código de operación.

Ejemplo:

```text
Yape requiere código de operación: true
Cash requiere código de operación: false
```

### Información sugerida

- Nombre
- Código
- Requiere código de operación
- Estado
- Fecha de creación

### Estados sugeridos

```text
ACTIVE
INACTIVE
```

### Relaciones

```text
PaymentMethod 1 ─── * Payment
```

Un método de pago puede usarse en muchos pagos.

---

## 2.8 Payment

Representa el pago real realizado por el estudiante.

### Propósito

El modelo `Payment` registra el intento de pago o la confirmación del pago de una orden de pago.

Ejemplo:

```text
Orden de Pago: Enrollment Fee - S/ 350.00
Método de Pago: Yape
Código de Operación: 123456
Estado: Confirmado
```

### Información sugerida

- Orden de pago
- Método de pago
- Monto pagado
- Fecha de pago
- Código de operación
- Estado
- Observación
- Fecha de creación

### Estados sugeridos

```text
PENDING_VALIDATION
CONFIRMED
REJECTED
CANCELLED
```

### Relaciones

```text
PaymentOrder 1 ─── * Payment
PaymentMethod 1 ─── * Payment
Payment 1 ─── 1 PaymentReceipt
```

### Reglas de negocio

- Un pago debe pertenecer a una orden de pago.
- Un pago puede ser confirmado o rechazado.
- Si el pago es confirmado:
    - El estado del pago pasa a `CONFIRMED`.
    - El estado de la orden de pago pasa a `PAID`.
    - El estado de la matrícula pasa a `ENROLLED`.

- Si el pago es rechazado:
    - El estado del pago pasa a `REJECTED`.
    - La orden de pago permanece en `PENDING`.
    - La matrícula permanece en `PENDING_PAYMENT`.

- Si el método de pago requiere un código de operación, el código de operación debe ser obligatorio.

---

## 2.9 Payment Receipt

Representa el comprobante o confirmación interna generada después de que un pago es confirmado.

Esto no necesita ser facturación electrónica avanzada.

### Propósito

El modelo `PaymentReceipt` almacena el documento interno de confirmación de pago.

Ejemplo:

```text
Comprobante N°: R-000001
Estudiante: Juan Pérez
Programa: Software Engineering
Periodo: 2026-I
Monto: S/ 350.00
Método de Pago: Yape
```

### Información sugerida

- Pago
- Número de comprobante
- Fecha de emisión
- Monto total
- Estado
- Fecha de creación

### Estados sugeridos

```text
ISSUED
CANCELLED
```

### Relaciones

```text
Payment 1 ─── 1 PaymentReceipt
```

### Reglas de negocio

- Un comprobante solo debe generarse después de que un pago es confirmado.
- Un pago rechazado no debe generar un comprobante.
- Un pago cancelado no debe tener un comprobante activo.

---

## 2.10 User

Representa a un usuario del sistema que puede acceder a la aplicación.

Ejemplos:

```text
Administrator
Academic Secretary
Cashier
```

### Propósito

El modelo `User` controla el acceso al sistema.

### Información sugerida

- Nombres
- Apellidos
- Correo electrónico
- Contraseña
- Rol
- Estado
- Fecha de creación

### Estados sugeridos

```text
ACTIVE
INACTIVE
```

### Relaciones

```text
Role 1 ─── * User
```

Un usuario pertenece a un rol.

---

## 2.11 Role

Representa el rol del usuario en el sistema.

Ejemplos:

```text
ADMIN
SECRETARY
CASHIER
```

### Propósito

El modelo `Role` define el tipo de acceso que tiene un usuario.

### Información sugerida

- Nombre
- Descripción
- Estado

### Roles sugeridos

```text
ADMIN
SECRETARY
CASHIER
```

### Permisos sugeridos

```text
ADMIN:
- Gestionar estudiantes
- Gestionar programas académicos
- Gestionar periodos académicos
- Gestionar matrículas
- Gestionar órdenes de pago
- Gestionar pagos
- Gestionar comprobantes
- Ver reportes
- Gestionar usuarios

SECRETARY:
- Gestionar estudiantes
- Gestionar matrículas
- Ver información de pagos
- Ver reportes

CASHIER:
- Ver órdenes de pago
- Registrar pagos
- Confirmar pagos
- Rechazar pagos
- Generar comprobantes
```

---

# 3. Modelos Finales Recomendados

Para este proyecto, los modelos recomendados son:

```text
Student
AcademicProgram
AcademicPeriod
Enrollment
PaymentConcept
PaymentOrder
PaymentMethod
Payment
PaymentReceipt
User
Role
```

---

# 4. Relaciones Principales

```text
Student 1 ─── * Enrollment

AcademicProgram 1 ─── * Enrollment

AcademicPeriod 1 ─── * Enrollment

Enrollment 1 ─── * PaymentOrder

PaymentConcept 1 ─── * PaymentOrder

PaymentOrder 1 ─── * Payment

PaymentMethod 1 ─── * Payment

Payment 1 ─── 1 PaymentReceipt

Role 1 ─── * User
```

---

# 5. Principales Transiciones de Estado

## 5.1 Flujo de Estados de la Matrícula (Enrollment)

```text
PENDING_PAYMENT → ENROLLED
PENDING_PAYMENT → CANCELLED
```

Significado:

- `PENDING_PAYMENT`: el estudiante ha sido registrado para la matrícula, pero el pago aún no está confirmado.
- `ENROLLED`: el pago fue confirmado y la matrícula está activa.
- `CANCELLED`: la matrícula fue cancelada.

---

## 5.2 Flujo de Estados de la Orden de Pago (Payment Order)

```text
PENDING → PAID
PENDING → EXPIRED
PENDING → CANCELLED
```

Significado:

- `PENDING`: el estudiante todavía tiene que pagar.
- `PAID`: la orden fue pagada.
- `EXPIRED`: la fecha de vencimiento ha pasado.
- `CANCELLED`: la orden fue cancelada.

---

## 5.3 Flujo de Estados del Pago (Payment)

```text
PENDING_VALIDATION → CONFIRMED
PENDING_VALIDATION → REJECTED
PENDING_VALIDATION → CANCELLED
```

Significado:

- `PENDING_VALIDATION`: el pago fue registrado pero aún necesita validación.
- `CONFIRMED`: el pago es válido.
- `REJECTED`: el pago no fue aceptado.
- `CANCELLED`: el pago fue cancelado.

---

## 5.4 Flujo de Estados del Comprobante de Pago (Payment Receipt)

```text
ISSUED → CANCELLED
```

Significado:

- `ISSUED`: el comprobante fue generado.
- `CANCELLED`: el comprobante fue cancelado.

---

# 6. Decisiones Importantes de Modelado

## 6.1 No existe un modelo Course

Este sistema no incluye la gestión de cursos individuales.

El estudiante no se matricula en cursos.

El estudiante se matricula en:

```text
AcademicProgram + AcademicPeriod
```

Ejemplo:

```text
Estudiante: Juan Pérez
Programa Académico: Software Engineering
Periodo Académico: 2026-I
```

Esto mantiene el sistema enfocado en la gestión de matrículas y pagos.

---

## 6.2 La Matrícula y el Pago están separados

El modelo `Enrollment` representa el registro administrativo del estudiante en un programa y periodo.

El modelo `PaymentOrder` representa lo que el estudiante debe pagar.

El modelo `Payment` representa lo que el estudiante realmente pagó.

Esta separación evita mezclar los datos académicos de la matrícula con los datos financieros.

---

## 6.3 La Orden de Pago y el Pago son diferentes

Una orden de pago se genera antes del pago.

El pago se registra después de que el estudiante paga.

Ejemplo:

```text
PaymentOrder:
- Monto: S/ 350.00
- Estado: PENDING

Payment:
- Monto pagado: S/ 350.00
- Método: Yape
- Estado: CONFIRMED
```

---

## 6.4 El Comprobante se genera solo después de confirmar el pago

El sistema no debe generar un comprobante para pagos rechazados o pendientes.

El flujo correcto es:

```text
PaymentOrder PENDING
→ Payment PENDING_VALIDATION
→ Payment CONFIRMED
→ PaymentOrder PAID
→ Enrollment ENROLLED
→ PaymentReceipt ISSUED
```

---

# 7. Modelos Fuera de Alcance

Los siguientes modelos no deben incluirse en la versión inicial:

```text
Course
Teacher
Classroom
Schedule
Attendance
Grade
Exam
Scholarship
Discount
Library
VirtualClassroom
```

Estos modelos están fuera del alcance actual del proyecto.

El sistema debe mantenerse enfocado en:

```text
Matrícula de estudiantes
Generación de órdenes de pago
Registro de pagos
Validación de pagos
Generación de comprobantes de pago
Reportes administrativos
```
