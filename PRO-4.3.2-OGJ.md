# Actividad P4.3.2 - Refactorización en TaskManager

## Code Smells detectados y patrones aplicados

### 1. Duplicación de lógica de búsqueda por ID

- **Descripción:** En todos estos métodos se repite la misma lógica para buscar una actividad por ID
- **Code Smell**: Duplicate Code
- **Patrón de refactorización aplicado**: Extract Method
- **Solución aplicada**: Se ha extraído esa lógica a una función privada llamada buscarPorId(id: Int).


- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/datos/ActividadRepository.kt#L25-L30)

- [Refactorizado]()

### 2 Uso innecesario de variable temporal en método eliminar

- **Code Smell:** Temporary Variable
- **Descripción:** Se usa una variable temporal `eliminado` para guardar el resultado de una condición cuando se puede devolver directamente.
- **Patrón aplicado:** Simplify Conditional Expression
- **Solución:** Retornar directamente el resultado de `usuarios.remove(usuario)` si el usuario existe, evitando la variable temporal.


- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/datos/UsuarioRepository.kt#L17-L23)

- [Refactorizado]()

### 3. Uso incorrecto del getter para incrementar contadorId

- **Descripción:** El getter de la variable `contadorId` incrementa el valor cada vez que se accede, causando comportamientos inesperados.
- **Code Smell:** Getter con efectos secundarios 
- **Patrón aplicado:** Getter con efectos secundarios (Misuse of Accessors / Hidden Side Effects)
- **Solución:** Cambiar el getter por un método explícito que incremente y devuelva el contador de manera clara y controlada.

- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/dominio/Actividad.kt#L8-L11)


- [Refactorizado]()

### 4. Duplicación en filtrado de tareas por estado

- **Descripción:** Se filtran las tareas repetidamente para cada estado, lo que genera código duplicado y dificulta la escalabilidad.
- **Code Smell:** Shotgun Surgery / Duplicate Code
- **Patrón aplicado:** Replace Conditional with Polymorphism
- **Solución:** Agrupar tareas por estado usando `groupBy` y luego iterar para imprimir, evitando repetir la misma operación.

- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/dominio/Dashboard.kt#L12-L14)


- [Refactorizado]()

### 5. Uso inconsistente de constructor privado y factory method en clase Evento

- **Descripción:** La clase utiliza un constructor privado junto con un método factory estático (`crearInstancia`) para crear objetos. 

- **Code Smell:** Inconsistent Abstraction / Duplicated Code

- **Patrón de refactorización aplicado:** Replace Constructor with Factory Method, unificado para evitar duplicidad

- **Solución:** Eliminar el constructor privado y utilizar un constructor público con parámetros por defecto para simplificar la creación de instancias y evitar duplicación. 

- [Sin refactorizar](https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/9b6f395aa997ad13775c8f2278d077c23f30d385/src/main/kotlin/dominio/Evento.kt#L3-L22)


- [Refactorizado]()

## Preguntas

