#  3.3 Test unitarios y Mockk

En esta actividad he seleccionado el servicio Actividad (clase **ActividadService.kt**) para realizar tests unitarios con mockk a los métodos que la componen.

- [Enlace a la clase seleccionada]()

## Métodos testeados:

En total he realizado 9 tests, todos ellos probando el correcto funcionamiento de los métodos de dicha clase. Estos métodos son los siguientes:

1. **crearTarea()** --> 2 tests ([link al código]())

2. **obtenerActividades()** --> 1 test ([link al código]())

3. **actualizarEstado()** --> 2 tests ([link al código]())

4. **asignarUsuarioATarea()** --> 2 tests ([link al código]())

5. **eliminarTareaPorId()** --> 2 tests ([link al código]())


## Tabla de casos de prueba:

| Método de servico         | Valores pasados / casos propuestos      | Resultado esperado de métodos mockk                      | Acción realizada                              | Resultado esperado                           |
|---------------------------|-----------------------------------------|----------------------------------------------------------|-----------------------------------------------|-----------------------------------------------|
| crearTarea()              | Descripción válida con datos correctos  | `repositorio.agregarActividad(...)` devuelve `true`      | Llamar a `crearTarea(descripción, etiquetas)` | Tarea creada correctamente                   |
| crearTarea()              | Descripción vacía                       | `repositorio.agregarActividad(...)` devuelve `true`      | Llamar a `crearTarea("", etiquetas)`          | Tarea creada incluso sin descripción         |
| obtenerActividades()      | Repositorio con actividades que mostrar | `repositorio.recuperarTodas()` devuelve lista de actividades | Llamar a `obtenerActividades()`               | Devuelve lista con actividades                |
| actualizarEstadoTarea()   | Id válido y tarea encontrada            | `repositorio.recuperarPorId(id)` devuelve `Tarea`<br>`repositorio.actualizarActividad(tarea)` devuelve `true` | Llamar a `actualizarEstadoTarea(id, estado)`  | Devuelve `true`, actualiza estado y registra |
| actualizarEstadoTarea()   | Id inválido / inexistente               | `repositorio.recuperarPorId(id)` devuelve `null`         | Llamar a `actualizarEstadoTarea(id, estado)`  | Devuelve `false`, no se actualiza nada       |
| asignarUsuarioATarea()    | Usuario válido y repositorio acepta     | `repositorio.asignarUsuarioATarea(idTarea, usuario)` devuelve `true`<br>`repositorio.recuperarPorId(idTarea)` devuelve `Tarea` | Llamar a `asignarUsuarioATarea(idTarea, usuario)` | Devuelve `true`, se registra "Tarea asignada a: nombre" |
| asignarUsuarioATarea()    | Usuario nulo / inexistente              | `repositorio.asignarUsuarioATarea(idTarea, null)` devuelve `false` | Llamar a `asignarUsuarioATarea(idTarea, null)` | Devuelve `false`, no se registra nada         |
| eliminarTareaPorId()      | Id válido                               | `repositorio.borrarPorId(id)` devuelve `Actividad`       | Llamar a `eliminarActividadPorId(id)`         | Devuelve la actividad eliminada              |
| eliminarTareaPorId()      | Id inexistente                          | `repositorio.borrarPorId(id)` devuelve `null`            | Llamar a `eliminarActividadPorId(id)`         | Devuelve `null`                              |


