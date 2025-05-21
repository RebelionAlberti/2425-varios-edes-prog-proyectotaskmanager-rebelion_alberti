# Elección de herramienta: Ktlint

- La elección de Ktlint es debido a que considero que es una herramienta más fácil que usar que herramientas como detekt y aunque tiene menos opciones de configuración tienen funciones muy interesantes como el formateo automático sin necesidad de hacer cambios manuales.

# Instalación de Ktlint

- Para instalar Ktlint tendremos que acceder a nuestro build.gradle para agregar el plugin que será el siguiente:
  - Comenzaremos agregando en el bloque de plugins la siguiente línea `id("org.jlleitschuh.gradle.ktlint") version "11.6.0"`
  - A continuación sincronizamos el gradle

![Instalación Ktlint 1](assets/instalación_ktlint.png)

# Uso de Ktlint

- Una vez instalado Ktlint introduciremos en la terminal `./gradlew ktlintCheck`
- Este comando realizará el análisis estático de código y mostrará los errores de estilo y formato.

![Uso de Ktlint 1](assets/usoDeKtlint.png)

- Ahora en el directorio *build/reports/ktlint/ktlintMainSourceSetCheck/ktlintMainSourceSetCheck.txt* [este](build/reports/ktlint/ktlintMainSourceSetCheck) encontramos el txt donde podemos encontrar el reporte de Ktlint con todos los errores de estilo y formato.

![Uso de Ktlint 2](assets/usoDeKtlint2.png)

- Ahora podemos usar el comando en la terminal `./gradlew ktlintFormat` para ejecutar la correción automática que en mi opinion es bastante buena y considero que la configuración que tiene Ktlint por defecto es bastante buena.
- Como podemos ver tras la ejecución exitosa si vamos al txt mencionado antes estará vacío o tendrá los errores que deberán ser solucionados manualmente (en mi caso vacío).

![Uso de Ktlint 3](assets/usoDeKtlint3.png)

# Configuración de Ktlint

- Aunque Ktlint es menos configurable que detekt existen opciones para configurar las reglas que sigue como por ejemplo crear en la raíz del proyecto un archivo llamado [.editorconfig](.editorconfig)
- Podremos agregar alguna regla para probar que funciona (no la voy a mantener porque me gusta la configuración por defecto).
- En el archivo .editorconfig escribiremos la siguiente regla como se puede ver en la imagen:

![editorconfig](assets/editorconfig.png)

- Usamos `root = true` para asegurarnos de que el archivo .editorconfig este en la raíz.
- Escribimos `[*.{kt,kts}]` que serán los archivos que se verán afectados por esta regla.
- Agregamos la regla que queremos probar `max_line_length = 120` esto provocara que todas las líneas del código deban tener un maximo de 120 caracteres.
- Realizaremos un `./gradlew ktlintCheck` en la terminal para ver si hay alguna línea del código que incumple esta nueva regla.

![Configuración 1](assets/configuracion1.png)

- Como hemos podido ver en la imagen hay 2 errores y el mismo Ktlint nos dice que no pueden ser autocorregidos asi que lo solucionaré yo manualmente.
- Ahora mediante imágenes mostraré él antes y él después ten en cuenta que entre imágenes.

## Antes

- Returns muy largos sobrepasando los 120 caracteres tanto que no se ve ni en pantalla.

![CorrecciónAntes1](assets/AntesCorreccion1.png)

![CorrecciónAntes2](assets/AntesCorrecion2.png)

# Después

- Se concatenan el contenido de los returns para acortarlos y que se vea por pantalla.

![CorrecciónDespués1](assets/DespuesCorrecion1.png)

![CorrecciónDespués2](assets/DespuesCorrecion2.png)

- Volvemos a pasar `./gradlew ktlintCheck` para ver si se han solucionado y como podemos ver ya no detecta nada.

![CorrecciónExitosa](assets/correcionExitosa.png)

# Errores encontrados y sus soluciones

## 1- No tener salto de linea al final del código

- En este caso en el archivo Tarea.kt no tenía en un principio salto de línea al final del código y es muy recomendable debido a que puede aumentar la compatibilidad con otras herramientas, mejora la legibilidad del código por consola entre otras cosas.
- El autocorrector de kt link lo soluciono como se puede ver en los permalinks.

### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/7437b72bef4d7274964000857e8ace6d461ff5f3/src/main/kotlin/dominio/Tarea.kt#L86-L90

### Después

- Github después del commit con los cambios no registra el salto de línea final asi que te lo dejo por aquí como quedaría

```kotlin
override fun toString(): String {
        val asignado = asignadoA?.nombre ?: "No asignado"
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, " +
            "Detalle: $detalle, Estado: ${estado.descripcion}, " +
            "Asignado a: $asignado, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}

```

## 2- No tener salto de línea a los parámetros cuando son muchos

- En este caso en ActividadService en el método crearEvento podemos ver que tenía muchos parámetros y es recomendable cuando hay muchos hacer saltos de líneas entre ellos para mejorar su legibilidad.
- El autocorrector de kt link lo soluciono como se puede ver en los permalinks.

### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/7437b72bef4d7274964000857e8ace6d461ff5f3/src/main/kotlin/aplicacion/ActividadService.kt#L19-L22

### Después

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/c9ab5ed6b20087c0a67e5d233d5e69bd9e70f69a/src/main/kotlin/aplicacion/ActividadService.kt#L19-L24

## 3- No tener ordenado los imports por paquetes y alfabéticamente

- En este caso en ActividadService podemos ver que tenía los imports puestos de cualquier manera y Ktlint te los ordenas por paquetes y alfabéticamente para que sean más legibles y poder identificarlos.
- El autocorrector de kt link lo soluciono como se puede ver en los permalinks.

### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/7437b72bef4d7274964000857e8ace6d461ff5f3/src/main/kotlin/aplicacion/ActividadService.kt#L3-L11

### Después

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/c9ab5ed6b20087c0a67e5d233d5e69bd9e70f69a/src/main/kotlin/aplicacion/ActividadService.kt#L3-L11

## 4- No usar llaves en expresiones if-else en una sola linea

- En este caso en ActividadRepository en una parte del método cargarActividadesCsv() se pueden ver expresiones if-else en una sola línea y Ktlint te lo formatea para que estén en líneas diferentes y usando multiples llaves para que sea más legible y se vea a simple vista menos complejo.
- El autocorrector de kt link lo soluciono como se puede ver en los permalinks.

### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/7437b72bef4d7274964000857e8ace6d461ff5f3/src/main/kotlin/datos/ActividadRepository.kt#L112-L115

### Después

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/c9ab5ed6b20087c0a67e5d233d5e69bd9e70f69a/src/main/kotlin/datos/ActividadRepository.kt#L114-L125

## 5- En un If seguido de un return no usar llaves

- En este caso en UsuarioRepository en el método cargarUsuario() tenemos un if seguido de un return sin usar líneas Ktlint te lo formatea y coloca llaves, ya que asi es más fácil identificar fallos en casos de error y se mantiene una estructura en el código.
- El autocorrector de kt link lo soluciono como se puede ver en los permalinks.

### Antes

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/7437b72bef4d7274964000857e8ace6d461ff5f3/src/main/kotlin/datos/UsuarioRepository.kt#L31-L32

### Después

https://github.com/RebelionAlberti/2425-varios-edes-prog-proyectotaskmanager-rebelion_alberti/blob/c9ab5ed6b20087c0a67e5d233d5e69bd9e70f69a/src/main/kotlin/datos/UsuarioRepository.kt#L31-L33

