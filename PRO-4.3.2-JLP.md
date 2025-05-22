# Code Smells
## CS1 - Método Largo
**Descripción:** En este caso, el método `cargarTareasCsv()` era excesivamente largo y hacía muchas cosas por si mismo, siendo más difícil entender que es lo que hace. Lo más aconsejable sería dividirlo en varios métodos para que tenga una función clara.

**Patrón de refactorización**: El patrón utilizado ha sido **Extracción de Método**, ya que el método `cargarTareasCsv()` ha sido dividido en varios.

**Antes**:
https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/datos/dao/TareaDAO.kt#L19-L81

**Después**:

## CS2 - Renombrar
**Descripción:** En este caso, el método `asingarUsuarioATarea()` estaba mal escrito y quizás la descripción por el nombre no era la más correcta. Para eso he utilizado la herramienta de `Rename`, que lo refactoriza y lo cambia en todo el documento

**Patrón de refactorización**: El patrón utilizado ha sido **Rename Refactoring**, ya que el método ha sido renombrado con las herramientas de IntelliJ IDEA para darle un nombre más descriptivo.

**Antes**:
https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/datos/dao/TareaDAO.kt#L154-L161

**Después**:

## CS3 - Mover Método

**Descripción:** En el `ActividadService` había código que mezclaba la lógica de negocio con la persistencia, porque se llamaba directamente a `repositorio.guardarCsv("tarea")` desde el servicio. Esto rompe el principio de responsabilidad única y hace el código más difícil de mantener. Para solucionarlo, cambié la responsabilidad de guardar los datos al repositorio, dejando el servicio sólo con la lógica de negocio.

**Patrón de refactorización:** El patrón utilizado ha sido **Move Method**, que consiste en trasladar un método o responsabilidad a la clase que debe encargarse de ello, mejorando la separación de responsabilidades y la organización del código.

**Antes**:
https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/aplicacion/ActividadService.kt#L133-L142

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/datos/repository/Repository.kt#L23-L29

**Después**:

## CS4 - Intimidad inapropiada

**Descripción:**  
En el código, la propiedad `estado` de la clase `Tarea` era pública y mutable, lo que permitía que otras clases como `ActividadService` la modificaran directamente. Esto rompe la encapsulación, ya que la lógica para cambiar el estado debe estar dentro de la propia clase `Tarea` para garantizar que se cumplan las reglas de negocio.

Para solucionar esto, se hizo que la propiedad `estado` fuera privada y se creó un método público `cambiarEstado` que gestiona la modificación con las validaciones necesarias. De esta forma, otras clases solo pueden cambiar el estado a través de este método, manteniendo la integridad y la responsabilidad de la clase.

**Patrón de refactorización:**  
Se aplicó el patrón **Encapsulation via public method**, que consiste en restringir el acceso directo a atributos y ofrecer métodos controlados para modificar su valor, asegurando la correcta gestión interna.

**Antes**:
https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/dominio/Tarea.kt#L13

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/aplicacion/ActividadService.kt#L90-L98

**Después**:

## CS5 - Control de bucle excesivo
**Descripción:**  
El código incluye controles innecesarios dentro de bucles que terminan la ejecución antes de tiempo o complican la comprensión del flujo. Esto puede provocar que el bucle no se comporte como se espera y dificulta el mantenimiento.

**Patrón de refactorización:**  
El patrón utilizado ha sido **Simplify Loop Control** que sirve para eliminar controles redundantes o innecesarios y manejar la condición de salida de forma clara y directa para mejorar la legibilidad y funcionalidad.

**Antes**:
https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/0bf353c10e17e71618def32c13502b172f9da7f8/src/main/kotlin/presentacion/UI.kt#L53-L77

**Después**:


## Test Unitarios
He realizado únicamente 2 test unitarios, aunque he intentado hacer más pero algunos errores inesperados me lo han impedido, y eso junto al tiempo demasiado justo me han llevado a solo poder realizar 2 de ellos.    
[TareaDAOTest.kt](src/test/kotlin/datos/dao/TareaDAOTest.kt)  
[TareaDAOUsuarioTest.kt](src/test/kotlin/datos/dao/TareaDAOUsuarioTest.kt)  
![img.png](assets/img.png)

## Respuestas a Preguntas
[1]
- 1.a ¿Qué code smell y patrones de refactorización has aplicado?
    - Método Largo / Extracción de Método
    - Refactorización de renombrado
    - Mezcla de responsabilidades
    - Intimidad inapropiada
    - Control de bucle excesivo


- 1.b Teniendo en cuenta aquella funcionalidad que tiene pruebas unitarias, selecciona un patrón de refactorización de los que has aplicado y que están cubierto por los test unitarios. ¿Porque mejora o no mejora tu código? Asegurate de poner enlaces a tu código
    - Extracción de Método, hace más fácil la comprensión del código y su mantenimiento, ya que al estar dividido se vuelve menos complejo.

[2]
- 2.a Describe el proceso que sigues para asegurarte que la refactorización no afecta a código que ya tenias desarrollado.
    - Crear pruebas antes de la refactorización
    - Refactorizar con las herrramientas del propio IntelliJ IDEA para no caer en errores de refactorización manual
    - Probar las pruebas unitarias
    - Depurar el código

[3]
- 3.a ¿Que funcionalidad del IDE has usado para aplicar la refactorización seleccionada? Si es necesario, añade capturas de pantalla para identificar la funcionalidad.
    - Para Extracción de Método: Refactor > Extract Function![img_1.png](assets/img_1.png)
    - Para Rename Refactoring: Refactor > Rename![img_2.png](assets/img_2.png)
    - Para Mover Método: Refactor > Move![img_3.png](assets/img_3.png)
    - Para Simplificar control del bucle: Ninguna, de manera manual.