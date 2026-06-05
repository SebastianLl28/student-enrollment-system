# Contexto de Requisitos Funcionales - Sistema de Matrícula y Pagos de Estudiantes

## 1. Propósito del Sistema

Este sistema gestiona las matrículas y los pagos de los estudiantes de una institución educativa.

El sistema se enfoca únicamente en los procesos administrativos. No gestiona cursos, calificaciones, asistencia, docentes, aulas ni horarios.

El objetivo principal es permitir que el personal administrativo registre estudiantes, los matricule en un programa académico y en un periodo académico, genere órdenes de pago, registre pagos, valide pagos y emita comprobantes de pago.

---

## 2. Flujo de Negocio Principal

El flujo principal del sistema es:

1. Registrar un estudiante.
2. Seleccionar un programa académico.
3. Seleccionar un periodo académico.
4. Crear la matrícula del estudiante.
5. Generar una orden de pago para la matrícula.
6. Registrar el pago realizado por el estudiante.
7. Confirmar o rechazar el pago.
8. Si el pago es confirmado, marcar la matrícula como activa.
9. Generar un comprobante de pago.
10. Permitir reportes administrativos sobre matrículas y pagos.

---

## 3. Requisitos Funcionales

### RF01 - Inicio de Sesión de Usuario

El sistema debe permitir que los usuarios administrativos inicien sesión con sus credenciales.

Los usuarios deben acceder al sistema según su rol.

Roles sugeridos:

- ADMIN
- SECRETARIA
- CAJERO

---

### RF02 - Gestionar Estudiantes

El sistema debe permitir a los usuarios registrar, actualizar, listar, buscar y consultar estudiantes.

El sistema debe permitir buscar estudiantes por:

- Código de estudiante
- Nombre completo
- Número de documento

El registro del estudiante debe contener información personal y de contacto básica.

---

### RF03 - Gestionar Programas Académicos

El sistema debe permitir a los usuarios registrar, actualizar, listar, buscar y desactivar programas académicos.

Un programa académico representa el programa, la carrera o el servicio educativo en el que se matriculará el estudiante.

Ejemplos:

- Ingeniería de Software
- Administración de Empresas
- Programa de Inglés
- Programa Preuniversitario

---

### RF04 - Gestionar Periodos Académicos

El sistema debe permitir a los usuarios registrar, actualizar, listar, buscar, abrir, cerrar y cancelar periodos académicos.

Un periodo académico representa el periodo en el que se matricula el estudiante.

Ejemplos:

- 2026-I
- 2026-II
- Verano 2026
- Marzo 2026

El sistema no debe permitir nuevas matrículas en periodos cerrados o cancelados.

---

### RF05 - Registrar Matrícula

El sistema debe permitir a los usuarios matricular a un estudiante en un programa académico y un periodo académico.

Para crear una matrícula, el usuario debe seleccionar:

- Estudiante
- Programa académico
- Periodo académico

Una nueva matrícula debe iniciar con el estado:

```text
PENDING_PAYMENT
```

La matrícula se activa únicamente después de que el pago requerido sea confirmado.

---

### RF06 - Consultar Matrículas

El sistema debe permitir a los usuarios listar, buscar y filtrar matrículas.

Los filtros deben incluir:

- Estudiante
- Programa académico
- Periodo académico
- Estado de la matrícula
- Fecha de matrícula

Estados de matrícula sugeridos:

```text
PENDING_PAYMENT
ENROLLED
CANCELLED
```

---

### RF07 - Cancelar Matrícula

El sistema debe permitir a los usuarios cancelar una matrícula.

Si la matrícula tiene una orden de pago pendiente, el sistema también debe cancelar esa orden de pago.

Una matrícula cancelada no debe permitir nuevos pagos.

---

### RF08 - Generar Orden de Pago

El sistema debe generar una orden de pago asociada a una matrícula.

Una orden de pago representa lo que el estudiante debe pagar.

Estados de orden de pago sugeridos:

```text
PENDING
PAID
EXPIRED
CANCELLED
```

Una orden de pago debe incluir:

- Matrícula
- Concepto de pago
- Monto
- Fecha de emisión
- Fecha de vencimiento
- Estado

---

### RF09 - Consultar Órdenes de Pago

El sistema debe permitir a los usuarios listar, buscar y filtrar órdenes de pago.

Los filtros deben incluir:

- Estudiante
- Matrícula
- Estado de la orden de pago
- Fecha de emisión
- Fecha de vencimiento

Esto ayuda a identificar a los estudiantes con pagos pendientes.

---

### RF10 - Gestionar Métodos de Pago

El sistema debe permitir a los usuarios gestionar los métodos de pago aceptados por la institución.

Ejemplos:

- Efectivo
- Transferencia bancaria
- Yape
- Plin
- Tarjeta

Un método de pago puede requerir un código de operación.

Ejemplo:

```text
Yape requiere código de operación: true
Efectivo requiere código de operación: false
```

---

### RF11 - Registrar Pago

El sistema debe permitir al cajero registrar un pago realizado por un estudiante.

El pago debe estar asociado a una orden de pago.

Un pago debe incluir:

- Orden de pago
- Método de pago
- Monto pagado
- Fecha de pago
- Código de operación, si es requerido
- Observación

---

### RF12 - Validar Pago

El sistema debe permitir a los usuarios confirmar o rechazar un pago.

Estados de pago sugeridos:

```text
PENDING_VALIDATION
CONFIRMED
REJECTED
CANCELLED
```

Cuando un pago es confirmado:

- El estado del pago pasa a `CONFIRMED`.
- El estado de la orden de pago pasa a `PAID`.
- El estado de la matrícula pasa a `ENROLLED`.

Cuando un pago es rechazado:

- El estado del pago pasa a `REJECTED`.
- La orden de pago permanece en `PENDING`.
- La matrícula permanece en `PENDING_PAYMENT`.

---

### RF13 - Generar Comprobante de Pago

El sistema debe generar un comprobante de pago después de que un pago sea confirmado.

El comprobante debe incluir:

- Número de comprobante
- Estudiante
- Programa académico
- Periodo académico
- Monto pagado
- Método de pago
- Fecha de pago
- Estado del comprobante

El comprobante es un documento de confirmación interno, no una facturación electrónica avanzada.

---

### RF14 - Consultar Historial Administrativo del Estudiante

El sistema debe permitir a los usuarios consultar el historial administrativo de un estudiante.

El historial debe mostrar:

- Información del estudiante
- Matrículas
- Órdenes de pago
- Pagos
- Comprobantes

---

### RF15 - Generar Reportes Administrativos

El sistema debe permitir a los usuarios generar reportes administrativos básicos.

Reportes sugeridos:

- Estudiantes matriculados por periodo académico
- Matrículas pendientes de pago
- Pagos confirmados por rango de fechas
- Órdenes de pago pendientes
- Matrículas por programa académico
- Historial de pagos por estudiante

---

### RF16 - Gestionar Usuarios del Sistema

El sistema debe permitir a los administradores gestionar usuarios.

El administrador debe poder:

- Registrar usuarios
- Editar usuarios
- Activar o desactivar usuarios
- Asignar roles

---

## 4. Reglas de Negocio Principales

### RN01 - La matrícula debe pertenecer a un estudiante

Una matrícula no puede existir sin un estudiante.

---

### RN02 - La matrícula debe pertenecer a un programa académico

Un estudiante debe matricularse en un programa académico específico.

---

### RN03 - La matrícula debe pertenecer a un periodo académico

Un estudiante debe matricularse en un periodo académico específico.

---

### RN04 - Las nuevas matrículas inician como pendientes de pago

Toda nueva matrícula debe iniciar como:

```text
PENDING_PAYMENT
```

---

### RN05 - La confirmación del pago activa la matrícula

Una matrícula solo puede pasar a:

```text
ENROLLED
```

cuando el pago relacionado es confirmado.

---

### RN06 - La orden de pago y el pago son conceptos diferentes

Una orden de pago representa lo que el estudiante debe pagar.

Un pago representa lo que el estudiante realmente pagó.

---

### RN07 - Los pagos rechazados no activan matrículas

Si un pago es rechazado, la matrícula permanece pendiente de pago.

---

### RN08 - Las matrículas canceladas no pueden recibir pagos

Si una matrícula es cancelada, el sistema no debe permitir nuevos pagos para ella.

---

### RN09 - Los periodos cerrados no pueden recibir nuevas matrículas

Si un periodo académico está cerrado, el sistema no debe permitir nuevas matrículas para ese periodo.

---

## 5. Fuera de Alcance

El sistema no debe incluir estas funcionalidades en la versión inicial:

- Gestión de cursos
- Gestión de docentes
- Gestión de horarios
- Gestión de aulas
- Gestión de asistencia
- Gestión de calificaciones
- Exámenes
- Ranking académico
- Aula virtual
- Módulo de biblioteca
- Contabilidad avanzada
- Descuentos
- Becas
- Facturación electrónica avanzada
