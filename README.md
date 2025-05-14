# Ejercicio Mock y Kotest Óscar García Jaén 

### Tabla de información de los test:

| Método de servicio           | Caso de prueba                                     | Resultado del mock                            | Acción llamada                           | Resultado esperado                                                      |
|-----------------------------|----------------------------------------------------|------------------------------------------------|-------------------------------------------|-------------------------------------------------------------------------|
| crearTarea(...)             | Datos válidos                                      | `mockRepo.agregarActividad` devuelve `true`   | Llamar a `crearTarea(...)`                | Retorna `true`, la tarea se agrega correctamente                       |
| crearTarea(...)             | Datos válidos pero el repositorio falla            | `mockRepo.agregarActividad` devuelve `false`  | Llamar a `crearTarea(...)`                | Retorna `false`, no se agrega                                          |
| obtenerActividades()       | Actividades existen en el repositorio              | `mockRepo.recuperarTodas` devuelve lista      | Llamar a `obtenerActividades()`           | Devuelve la lista esperada                                             |
| crearEvento(...)           | Datos válidos                                      | `mockRepo.agregarActividad` devuelve `true`   | Llamar a `agregarActividad(evento)`       | Evento agregado correctamente, retorna `true`                          |
| crearEvento(...)           | Fecha inválida                                     | `mockRepo.agregarActividad` devuelve `true`   | Llamar a `agregarActividad(evento)`       | Evento agregado (no se valida la fecha en el test)                    |
| filtrarPorTipo(...)        | Tipo válido (ej. "Tarea")                          | `mockRepo.recuperarTodas` devuelve lista      | Llamar a `filtrarPorTipo("Tarea")`        | Devuelve solo elementos tipo Tarea                                     |
| filtrarPorTipo(...)        | Tipo inválido                                      | `mockRepo.recuperarTodas` devuelve lista      | Llamar a `filtrarPorTipo("Invalido")`     | Devuelve lista vacía                                                   |
| filtrarPorEstado(...)      | Estado válido con coincidencias (ej. ABIERTA)      | `mockRepo.recuperarTodas` devuelve lista      | Llamar a `filtrarPorEstado(Status.ABIERTA)` | Devuelve solo tareas con estado ABIERTA                              |
| filtrarPorEstado(...)      | Estado válido sin coincidencias                    | `mockRepo.recuperarTodas` devuelve lista      | Llamar a `filtrarPorEstado(Status.CERRADA)` | Devuelve lista vacía                                                  |
