#  3.3 Test unitarios y Mockk

Para la entrega de esta actividad, cada integrante del grupo se ha creado una rama independiente al proyecto para
poder entregarla. Enlace a mi rama [aquí](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/tree/3_3_JGD_kotest_mockk).

En esta actividad he seleccionado el servicio Actividad (clase **ActividadService.kt**) para realizar tests unitarios con mockk a los métodos que la componen.

- [Enlace a la clase seleccionada](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/38e0b08a4800f2fe95c74a36cf920a717b97a428/src/main/kotlin/aplicacion/ActividadService.kt#L12-L138)

## Métodos testeados:

En total he realizado 14 tests, todos ellos probando el correcto funcionamiento de los métodos de dicha clase. Estos métodos son los siguientes:

1. **crearTarea()** --> 2 tests ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/38e0b08a4800f2fe95c74a36cf920a717b97a428/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L18-L44))

2. **obtenerActividades()** --> 2 test ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/38e0b08a4800f2fe95c74a36cf920a717b97a428/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L46-L55))

3. **actualizarEstado()** --> 2 tests ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/38e0b08a4800f2fe95c74a36cf920a717b97a428/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L57-L87))

4. **asignarUsuarioATarea()** --> 2 tests ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/38e0b08a4800f2fe95c74a36cf920a717b97a428/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L89-L120))

5. **eliminarTareaPorId()** --> 2 tests ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/38e0b08a4800f2fe95c74a36cf920a717b97a428/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L122-L146))

6. **crearEvento()** --> 2 tests ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/2c7d3a7596580be7dc43bddb8190477a502bdf10/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L159-L202))

7. **actualizarEstadoSubtareas()** --> 2 tests ([link al código](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/1a0d83bb080258d2a6ca3612e221870afb134ea2/src/test/kotlin/aplicacion/ActividadServiceTest.kt#L205-L243))

## Tabla de casos de prueba:

| Método de servico           | Valores pasados / casos propuestos                                         | Resultado esperado de métodos mockk                      | Acción realizada                                                         | Resultado esperado                                      |
|-----------------------------|----------------------------------------------------------------------------|----------------------------------------------------------|--------------------------------------------------------------------------|---------------------------------------------------------|
| crearTarea()                | Descripción válida con datos correctos                                     | `repositorio.agregarActividad(...)` devuelve `true`      | Llamar a `crearTarea(descripción, etiquetas)`                            | Tarea creada correctamente                              |
| crearTarea()                | Descripción vacía                                                          | `repositorio.agregarActividad(...)` devuelve `true`      | Llamar a `crearTarea("", etiquetas)`                                     | Tarea creada incluso sin descripción                    |
| obtenerActividades()        | Repositorio con actividades que mostrar                                    | `repositorio.recuperarTodas()` devuelve lista de actividades | Llamar a `obtenerActividades()`                                          | Devuelve lista con actividades                          |
| actualizarEstadoTarea()     | Id válido y tarea encontrada                                               | `repositorio.recuperarPorId(id)` devuelve `Tarea`<br>`repositorio.actualizarActividad(tarea)` devuelve `true` | Llamar a `actualizarEstadoTarea(id, estado)`                             | Devuelve `true`, actualiza estado y registra            |
| actualizarEstadoTarea()     | Id inválido / inexistente                                                  | `repositorio.recuperarPorId(id)` devuelve `null`         | Llamar a `actualizarEstadoTarea(id, estado)`                             | Devuelve `false`, no se actualiza nada                  |
| asignarUsuarioATarea()      | Usuario válido y repositorio acepta                                        | `repositorio.asignarUsuarioATarea(idTarea, usuario)` devuelve `true`<br>`repositorio.recuperarPorId(idTarea)` devuelve `Tarea` | Llamar a `asignarUsuarioATarea(idTarea, usuario)`                        | Devuelve `true`, se registra "Tarea asignada a: nombre" |
| asignarUsuarioATarea()      | Usuario nulo / inexistente                                                 | `repositorio.asignarUsuarioATarea(idTarea, null)` devuelve `false` | Llamar a `asignarUsuarioATarea(idTarea, null)`                           | Devuelve `false`, no se registra nada                   |
| eliminarTareaPorId()        | Id válido                                                                  | `repositorio.borrarPorId(id)` devuelve `Actividad`       | Llamar a `eliminarActividadPorId(id)`                                    | Devuelve la actividad eliminada                         |
| eliminarTareaPorId()        | Id inexistente                                                             | `repositorio.borrarPorId(id)` devuelve `null`            | Llamar a `eliminarActividadPorId(id)`                                    | Devuelve `null`                                         |
| crearEvento()               | Descripción válida con todos los datos                                     | `repositorio.agregarActividad(...)` devuelve `true`            | Llamar a `crearEvento(descripcion, fechaRealizacion, ubicacion, etiquetas)` | Evento creado correctamente con los datos válidos       |
| crearEvento()               | Descripción vacía                                                          | `repositorio.agregarActividad(...)` devuelve `true`            | Llamar a `crearEvento("", fechaRealizacion, ubicacion, etiquetas)`       | Evento creado correctamente con la descripción vacía    |
| **actualizarEstadoSubtareas()** | Id válido y subtarea puede finalizar correctamente                         | `repositorio.recuperarPorId(idSubtarea)` devuelve `Tarea`<br>`tarea.puedeFinalizar()` devuelve `true`<br>`repositorio.actualizarActividad(tarea)` devuelve `true` | Llamar a `actualizarEstadoSubtareas(idSubtarea, Status.CERRADA)`   | Devuelve `true`, actualiza el estado de la subtarea a `CERRADA` y la registra. |
| **actualizarEstadoSubtareas()** | Id válido y subtarea no puede finalizar por tener otras subtareas abiertas | `repositorio.recuperarPorId(idSubtarea)` devuelve `Tarea`<br>`tarea.puedeFinalizar()` devuelve `false` | Llamar a `actualizarEstadoSubtareas(idSubtarea, Status.CERRADA)`   | Devuelve `false`, no actualiza el estado y no realiza ninguna acción. |


