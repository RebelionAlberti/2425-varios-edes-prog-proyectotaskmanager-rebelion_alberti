# Identificar Code Smells y Patrones de refactorización aplicados

- Vamos a identificar algunos code smells y como podemos

## 1 - Bloater: Primitive Obsession

- Es un code smell que ocurre cuando se usan tipos primitivos como Strings o Int se usan para representar conceptos importantes en lugar de crear tipo o clases dedicadas.
- Esto puedo provocar varios problemas como:
    - Equivocaciones con valores inválidos o errores a la hora de escribir.
    - Hace que el código sea difícil de mantener y sea menos expresivo.
    - Dispersa la lógica relacionada con el tipo por todo el código

### Antes

- En el parámetro recibe un String el cual está mal visto porque es un tipo primitivo para representar un tipo importante como es el tipo de Actividad.

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/ActividadService.kt#L106-L114

### Después

- Para solucionar este error usaremos el patron de refactorización **Replace Data Value with Object** que consiste en remplazar un parámetro primitivo (en nuestro caso una String) por un objeto en este caso creamos un enum class con las opciones de las actividades posibles el cual usaremos como parámetro en el método.
- Esta es la refactorización realizada sobre el método.

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/b5e80c4746f70d728e53e96ad5aaaea86a9ddaa6/src/main/kotlin/aplicacion/ActividadService.kt#L107-L114

- Para ello hemos tenido que crear una enum class con los tipos de actividades y también un par de modificaciones en la UI y en la Interfaz IActividadService, ya que recibían una String y hemos tenido que realizar la modificación para que reciban el enum class.

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/b5e80c4746f70d728e53e96ad5aaaea86a9ddaa6/src/main/kotlin/aplicacion/TipoActividad.kt#L3-L5

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/b5e80c4746f70d728e53e96ad5aaaea86a9ddaa6/src/main/kotlin/aplicacion/IActividadService.kt#L25

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/b5e80c4746f70d728e53e96ad5aaaea86a9ddaa6/src/main/kotlin/presentacion/UI.kt#L316-L325

# Prueba Unitaria



## 2 - Object-Orientation Abusers: Switch Statements

- Este code smell ocurre cuando el código usa condicionales como switch, when, if-else entre otras condicionales se usan para controlar el comportamiento en función de tipos o estados
- Puede provocar lo siguiente:
    - Rompe la orientación de los objetos porque la lógica no está distribuida en las clases que deberían manejarla.
    - Hace difícil extender o modificar el código sin cambiar todos los condicionales.
    - Puede generar código repetitivo y difícil de entender.

### Antes

- Se usa un when que evalúa el estado de cada actividad desde fuera de la clase Tarea rompiendo el principio de encapsulamiento en lugar de delegar esa lógica a la propia clase que maneja los estados de Tarea.

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/ActividadService.kt#L116-L124

### Después

- Para solucionar este Code Smell con el patrón de refactorización **Replace Conditional with Polymorphism** que consiste en sustituir estructuras condicionales por métodos polimórficos definidos en alguna subclase.
- Esta es la refactorización realizada sobre el código:


- Para que funcione hemos tenido que crear el método estaEnEstado en la clase Tarea para que de esta forma cada objeto Tarea puede decir si está en el estado dado y de esta forma evitas repetir it.estado == Status.X varias veces.



# Prueba Unitaria



## 3 - Dispensables: Duplicated Code

- Es cuando un fragmento de código aparece copiado en varios lugares. Esto es problemático porque:
    - Aumenta la dificultad para mantener el código.
    - Sí hay que cambiar algo hay que actualizarla en multiples sitios lo que puede generar errores.
    - Reduce la claridad y concisión del código.

### Antes

- La parte que se duplica es:

```kotlin
val tarea = repositorio.recuperarPorId(id)
if (tarea is Tarea) {
    // Resto del codigo
}
```

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/ActividadService.kt#L33-L44

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/ActividadService.kt#L46-L62

## 4 - Couplers: Inappropriate Intimacy

- Sucede cuando una clase accede a detalles internos de otra clase, acoplando fuertemente las dos violando la encapsulación Por ejemplo:
    - Castear la instancia para acceder a métodos específicos.
    - Manipular datos internos que deberían ser privados.
    - Esto puede dificultar el mantenimiento y la evolución del código.

### Antes

- La parte que se refiere dentro del codigo es:

```kotlin
if (usuario != null) {
    (repositorio as? ActividadRepository)?.guardarActividadesCsv()
}
```

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/ActividadService.kt#L64-L100

## 5 - Couplers: Middle Man

- Ocurre cuando una clase actúa solo como intermediaria para llamar a métodos de otra, sin añadir lógica ni valor este code smell puede llegar a generar:
    - Código innecesariamente complejo y redundante.
    - Capas de abstracción sin sentido que complican el flujo.

### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/e4494c88ca23f905dabd7bed396934d7434b979a/src/main/kotlin/aplicacion/UsuarioService.kt#L53-L55
