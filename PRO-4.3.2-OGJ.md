# Actividad P4.3.2 - Refactorización en TaskManager

## Code Smells detectados y patrones aplicados

### 1. Duplicación de lógica de búsqueda por ID

- **Descripción:** En todos estos métodos se repite la misma lógica para buscar una actividad por ID
- **Code Smell**: Duplicate Code
- **Patrón de refactorización aplicado**: Extract Method
- **Solución aplicada**: Se ha extraído esa lógica a una función privada llamada buscarPorId(id: Int).


- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/datos/ActividadRepository.kt#L25-L30)

- [Refactorizado](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/1006926f7d1fb7b68cbc8b1eb9808353ed0623a3/src/main/kotlin/datos/ActividadRepository.kt#L21)

### 2 Uso innecesario de variable temporal en método eliminar

- **Code Smell:** Temporary Variable
- **Descripción:** Se usa una variable temporal `eliminado` para guardar el resultado de una condición cuando se puede devolver directamente.
- **Patrón aplicado:** Simplify Conditional Expression
- **Solución:** Retornar directamente el resultado de `usuarios.remove(usuario)` si el usuario existe, evitando la variable temporal.


- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/datos/UsuarioRepository.kt#L17-L23)

- [Refactorizado](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/1006926f7d1fb7b68cbc8b1eb9808353ed0623a3/src/main/kotlin/datos/UsuarioRepository.kt#L17-L24)

### 3. Uso incorrecto del getter para incrementar contadorId

- **Descripción:** El getter de la variable `contadorId` incrementa el valor cada vez que se accede, causando comportamientos inesperados.
- **Code Smell:** Getter con efectos secundarios 
- **Patrón aplicado:** Getter con efectos secundarios (Misuse of Accessors / Hidden Side Effects)
- **Solución:** Cambiar el getter por un método explícito que incremente y devuelva el contador de manera clara y controlada.

- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/dominio/Actividad.kt#L8-L11)


- [Refactorizado](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/1006926f7d1fb7b68cbc8b1eb9808353ed0623a3/src/main/kotlin/dominio/Actividad.kt#L10-L14)

### 4. Duplicación en filtrado de tareas por estado

- **Descripción:** Se filtran las tareas repetidamente para cada estado, lo que genera código duplicado y dificulta la escalabilidad.
- **Code Smell:** Shotgun Surgery / Duplicate Code
- **Patrón aplicado:** Replace Conditional with Polymorphism
- **Solución:** Agrupar tareas por estado usando `groupBy` y luego iterar para imprimir, evitando repetir la misma operación.

- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/dominio/Dashboard.kt#L12-L14)


- [Refactorizado](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/1006926f7d1fb7b68cbc8b1eb9808353ed0623a3/src/main/kotlin/dominio/Dashboard.kt#L18-L32)

### 5. Uso inconsistente de constructor privado y factory method en clase Evento

- **Descripción:** La clase utiliza un constructor privado junto con un método factory estático (`crearInstancia`) para crear objetos. 

- **Code Smell:** Inconsistent Abstraction / Duplicated Code

- **Patrón de refactorización aplicado:** Replace Constructor with Factory Method, unificado para evitar duplicidad

- **Solución:** Eliminar el constructor privado y utilizar un constructor público con parámetros por defecto para simplificar la creación de instancias y evitar duplicación. 

- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/dominio/Evento.kt#L3-L22)


- [Refactorizado](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/1006926f7d1fb7b68cbc8b1eb9808353ed0623a3/src/main/kotlin/dominio/Evento.kt#L3-L19)

## Preguntas

### 1.a ¿Qué code smell y patrones de refactorización has aplicado?

**Code Smells aplicados:**

- Duplicate Code, Temporary Variable, Shotgun Surgery / Duplicate Code (filtrado repetido, Misuse of Accessors (getter con efectos secundarios), Inconsistent Abstraction / Duplicated Code (constructor privado y método factory)

**Patrones de refactorización aplicados:**

- Extract Method, Simplify Conditional Expression, Replace Conditional with Polymorphism / Extract Method, Replace Getter with Method, Replace Constructor with Factory Method

### 1.b Teniendo en cuenta aquella funcionalidad que tiene pruebas unitarias, selecciona un patrón de refactorización de los que has aplicado y que están cubierto por los test unitarios. ¿Por qué mejora o no mejora tu código?

- He sacado la búsqueda repetida en un solo método para que el código sea más limpio y fácil de mantener. 

- Aunque no tengo pruebas unitarias, la refactorización se hizo para asegurar que el comportamiento sigue igual.

### 2.a Describe el proceso que sigues para asegurarte que la refactorización no afecta a código que ya tenías desarrollado.

- Como no tengo tests, voy haciendo los cambios poco a poco y comprobando que todo sigue funcionando igual. También uso git para ver qué cambio hice y poder volver atrás si hace falta.

### 3.a ¿Qué funcionalidad del IDE has usado para aplicar la refactorización seleccionada? Si es necesario, añade capturas de pantalla para identificar la funcionalidad.

Para hacer la refactorización usé la opción Extract Method que trae IntelliJ IDEA. Me permitió seleccionar el bloque de código repetido y crear un método nuevo de forma automática, lo que facilitó que el cambio fuera rápido y sin errores.