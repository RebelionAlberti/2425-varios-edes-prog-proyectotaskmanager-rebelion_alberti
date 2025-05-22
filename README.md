## Identificación de la Actividad
- **ID de la Actividad:** 4.3.2 Aplicación de Code Smells y Patrones de Refactorización
- **Módulo:** EDES
- **Unidad de Trabajo:** Unidad 4: SCV, refactorización y documentación
- **Fecha de Creación:** 22-05-2025
- **Fecha de Entrega:** 22-05-2025
- **Alumno(s):**
    - **Nombre y Apellidos:** Adrián Fernández Garrido
    - **Correo electrónico:** afergar613@g.educaand.es
    - **Iniciales del Alumno/Grupo:** AFG

## Descripción de la Actividad

La actividad consiste en identificar Code Smells sobre el código y usar patrones de refactorización para eliminar los CodeSmells
Usaremos Test unitarios con JUnit para comprobar que no cambie el funcionamiento del CodeSmell ya refactorizado.

## Desarrollo de la Actividad

### Descripción del Desarrollo

- Se identifica 5 Code Smells
- Se usan 5 patrones de refactorización
- Se hacen test unitarios para comprobar su funcionamiento
- Se responden a unas preguntas propuestas por el profesor

### Código Fuente

#### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/ActividadService.kt#L116-L124

#### Después

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/7d7f2bc3d9905392901ffaa70172f4960fffa569/src/main/kotlin/aplicacion/ActividadService.kt#L116-L120

### Prueba Unitaria

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/877b05b0000d3e2347b8c8a28a858a5bbf022303/src/test/kotlin/ActividadServiceTest.kt#L17-L32

## Conclusiones

- Se aprenden a identificar CodeSmells y las partes negativas que pueden llegar a traer
- Se aprende a refactorizar mediante patrones de refactorización y se usan algunas funcionalidades de refactorización del IDE
- Se hacen pruebas unitarias con JUnit para mantener su funcionalidad tras ser refactorizado
