# Contexto Adicional del Proyecto - Sistema de Matrícula y Pagos de Estudiantes

## 1. Comportamiento Clave del Sistema

El sistema debe seguir un flujo administrativo simple.

Primero se registra a un estudiante en el sistema. Luego, el usuario administrativo crea una matrícula para ese estudiante seleccionando un programa académico y un período académico.

Después de crear la matrícula, el sistema debe generar una orden de pago. La matrícula debe permanecer pendiente hasta que se confirme el pago.

Una vez que se confirma el pago, la orden de pago pasa a estado pagado y la matrícula pasa a estado activo.

El sistema no debe activar una matrícula si el pago aún está pendiente, rechazado, cancelado o no registrado.

---

## 2. Pantallas Principales

El sistema debe incluir estas pantallas principales:

```text
Panel de Control (Dashboard)
Estudiantes
Programas Académicos
Períodos Académicos
Matrículas
Órdenes de Pago
Pagos
Comprobantes de Pago
Reportes
Usuarios
```

---

## 3. Comportamiento del Panel de Control

El panel de control debe mostrar un resumen de la información más importante.

Indicadores sugeridos:

```text
Total de estudiantes
Matrículas activas
Matrículas pendientes
Órdenes de pago pendientes
Pagos confirmados del mes actual
Últimas matrículas
Últimos pagos
```

El panel de control debe ayudar a los usuarios administrativos a entender rápidamente el estado actual de las matrículas y los pagos.

---

## 4. Comportamiento de la Gestión de Estudiantes

El módulo de estudiantes debe permitir a los usuarios:

```text
Crear estudiante
Actualizar estudiante
Listar estudiantes
Buscar estudiantes
Ver detalles del estudiante
Ver historial de matrículas del estudiante
Ver historial de pagos del estudiante
Activar o desactivar estudiante
```

El sistema debe evitar registrar dos estudiantes con el mismo número de documento.

El código del estudiante debe ser único.

---

## 5. Comportamiento de la Gestión de Matrículas

El módulo de matrículas debe permitir a los usuarios:

```text
Crear matrícula
Listar matrículas
Buscar matrículas
Filtrar matrículas por estado
Filtrar matrículas por período académico
Filtrar matrículas por programa académico
Cancelar matrícula
Ver detalles de la matrícula
```

Un estudiante no debe tener matrículas activas duplicadas para el mismo programa académico y período académico.

Ejemplo:

```text
Un estudiante no puede matricularse dos veces en Ingeniería de Software para 2026-I.
```

Una nueva matrícula debe iniciar como:

```text
PENDIENTE_DE_PAGO
```

La matrícula solo debe cambiar a:

```text
MATRICULADO
```

cuando se confirma el pago relacionado.

---

## 6. Comportamiento de la Orden de Pago

El módulo de órdenes de pago debe permitir a los usuarios:

```text
Generar orden de pago
Listar órdenes de pago
Buscar órdenes de pago
Filtrar órdenes de pago por estado
Filtrar órdenes de pago por estudiante
Filtrar órdenes de pago por fecha de vencimiento
Cancelar orden de pago
Ver detalles de la orden de pago
```

Una orden de pago representa el monto que el estudiante debe pagar.

El sistema debe generar una orden de pago cuando se crea una matrícula.

Una orden de pago debe iniciar como:

```text
PENDIENTE
```

Una orden de pago debe pasar a:

```text
PAGADA
```

solo cuando se confirma un pago relacionado.

---

## 7. Comportamiento de la Gestión de Pagos

El módulo de pagos debe permitir a los usuarios:

```text
Registrar pago
Listar pagos
Buscar pagos
Confirmar pago
Rechazar pago
Cancelar pago
Ver detalles del pago
```

Un pago siempre debe pertenecer a una orden de pago.

Si el método de pago seleccionado requiere un código de operación, el código de operación debe ser obligatorio.

Ejemplo:

```text
Yape requiere código de operación.
La transferencia bancaria requiere código de operación.
El efectivo no requiere código de operación.
```

---

## 8. Comportamiento del Comprobante

El sistema debe generar un comprobante de pago solo después de que un pago sea confirmado.

No se debe generar un comprobante para:

```text
Pagos pendientes
Pagos rechazados
Pagos cancelados
```

El comprobante debe incluir:

```text
Número de comprobante
Nombre completo del estudiante
Programa académico
Período académico
Método de pago
Monto pagado
Fecha de pago
Estado del comprobante
```

El comprobante es solo una confirmación interna de pago, no una facturación electrónica avanzada.

---

## 9. Validaciones Principales

El sistema debe validar las siguientes reglas:

```text
El número de documento de un estudiante debe ser único.
El código de un estudiante debe ser único.
Un período académico no puede recibir matrículas si está cerrado o cancelado.
Una matrícula no puede existir sin un estudiante.
Una matrícula no puede existir sin un programa académico.
Una matrícula no puede existir sin un período académico.
Una matrícula cancelada no puede recibir pagos.
Una orden de pago pagada no debe recibir otro pago confirmado.
Un pago no puede confirmarse si el monto pagado es inválido.
Un comprobante no puede generarse si el pago no está confirmado.
```

---

## 10. Búsquedas y Filtros Sugeridos

El sistema debe incluir opciones de búsqueda y filtro en los módulos principales.

### Estudiantes

Buscar por:

```text
Código del estudiante
Nombre completo
Número de documento
Estado
```

### Matrículas

Filtrar por:

```text
Estudiante
Programa académico
Período académico
Estado de la matrícula
Fecha de matrícula
```

### Órdenes de Pago

Filtrar por:

```text
Estudiante
Matrícula
Estado
Fecha de emisión
Fecha de vencimiento
```

### Pagos

Filtrar por:

```text
Estudiante
Método de pago
Estado del pago
Fecha de pago
Código de operación
```

---

## 11. Reportes Sugeridos

El sistema debe incluir reportes administrativos básicos.

Reportes recomendados:

```text
Estudiantes matriculados por período académico
Matrículas pendientes de pago
Matrículas activas por programa académico
Órdenes de pago pendientes
Pagos confirmados por rango de fechas
Historial de pagos por estudiante
Historial de matrículas por estudiante
```

Los reportes deben ser simples y administrativos.

El sistema no debe generar reportes de rendimiento académico porque las calificaciones y los cursos están fuera del alcance actual.

---

## 12. Roles de Usuario y Permisos

El sistema debe gestionar el acceso según los roles de usuario.

### ADMINISTRADOR (ADMIN)

Puede gestionar todos los módulos:

```text
Estudiantes
Programas académicos
Períodos académicos
Matrículas
Órdenes de pago
Pagos
Comprobantes
Reportes
Usuarios
```

### SECRETARIO/A (SECRETARY)

Puede gestionar la información relacionada con las matrículas:

```text
Estudiantes
Programas académicos
Períodos académicos
Matrículas
Ver órdenes de pago
Ver estado de pagos
Reportes
```

### CAJERO/A (CASHIER)

Puede gestionar la información relacionada con los pagos:

```text
Ver órdenes de pago
Registrar pagos
Confirmar pagos
Rechazar pagos
Generar comprobantes
Ver reportes de pagos
```

---

## 13. Fuera del Alcance

El sistema no debe incluir las siguientes funcionalidades en la versión inicial:

```text
Gestión de cursos
Gestión de docentes
Gestión de aulas
Gestión de horarios
Gestión de asistencia
Gestión de calificaciones
Gestión de exámenes
Gestión de becas
Gestión de descuentos
Contabilidad avanzada
Facturación electrónica avanzada
Aula virtual
Módulo de biblioteca
```

El sistema debe mantenerse enfocado únicamente en:

```text
Registro de estudiantes
Gestión de matrículas
Generación de órdenes de pago
Registro de pagos
Validación de pagos
Generación de comprobantes
Reportes administrativos
```

---

## 14. Idea Central Final

El sistema debe diseñarse en torno a este flujo:

```text
Estudiante
→ Matrícula
→ Orden de Pago
→ Pago
→ Comprobante de Pago
```

La regla más importante es:

```text
Una matrícula no está activa hasta que el pago sea confirmado.
```

El sistema es administrativo, no académico.
