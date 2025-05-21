# Linting

En esta práctica tenemos que instalar y usar un analizador de código estático a elegir
entre detekt y ktlint, y utilizarlo en nuestro proyecto de taskManager. En mi caso he escogido ktlint,
no sé por qué razón detekt me ha dado varios problemas, así que he escogido **ktlint**.

## Ktlint:

Ktlint es un linter que permite comprobar el estilo de código y también puede realizar el formateo de manera
automática. Se guía en base las convenciones de codificación y a la guía de estilo definidas para kotlin con
la finalidad de identificar problemas de formato en nuestro código.

Para poder implementarlo en nuestro proyecto, debemos incluirlo en las dependencias del mismo, en el archivo
build.gradle.kts, de la siguiente manera:

![](/assets/dependenciasKtlint.png)

Una vez añadimos, actualizamos nuestro gradle y ejecutamos el siguiente comando:

```bash
./gradlew ktlintCheck
```

![](/assets/erroresKtlintCheck.png)

Este comando, lo que hace es analizar nuestro código en busca de errores según el criterio de la
sintaxis oficial de kotlin, y una vez los detecta, nos los muestra por consola, informando al usuario
de qué es lo que debe mejorar/cambiar en su proyecto para mejorarlo.

Una vez ejecutado este comando, si queremos arreglar estos errores automáticamente, debemos ejecutar
otro comando, el cual después de ya tener los errores localizados, los arregla automáticamente sin
tener que cambiarlos a mano. El comando es el siguiente:

```bash
./gradlew ktlintFormat
```

![](/assets/ktlintFormat.png)

Una vez ejecutamos esto, volvemos a ejecutar el comando anterior para solo buscar errores, y observamos como
la gran mayoría de ellos, por no decir todos, han desaparecido y han sido solucionados automáticamente por esta
herramienta Ktlint. El resultado sería el siguiente:

![](/assets/erroresSolucionadosKtlintCheck.png)

## Detección y explicación de errores encontrados:

No he hecho capturas antes de ejecutar el comando para arreglar los errores, por lo tanto los ejemplos no serán con
código de este proyecto, pero serán explicados de igual manera. Como los 5 errores seleccionados no son de gran complejidad
no creo que haya problema con poner un ejemplo diferente.

### 1. El archivo debe terminar con una nueva línea (\n)

#### Descripción del error:
Ktlint espera que todos los archivos de código terminen con una línea vacía (salto de línea). Esto mejora la compatibilidad
con herramientas de control de versiones.

#### Solución aplicada:
Añadirle una línea vacía al final de los archivos que no la tuviesen previamente.


- antes:
```kotlin
fun main() {
    println("Hola mundo")
}
```

- después:
```kotlin
fun main() {
println("Hola mundo")
}
// salto de línea.
```

### 2. Líneas en blanco innecesarias

#### Descripción del error:
Ktlint detecta líneas vacías innecesarias, por ejemplo, al principio o al final de un bloque de código. Esto al parecer,
incumple con la sintaxis oficial de kotlin.

#### Solución aplicada:
Eliminar las líneas vacías innecesarias donde sea necesario.

- antes:
```kotlin
fun saludo() {

    println("Hola")

}
```
- después:

```kotlin
fun saludo() {
    println("Hola")
}
```

### 3. Orden incorrecto de imports

#### Descripción del error:
Los imports deben ordenarse alfabéticamente y sin líneas en blanco entre ellos. Ktlint recomienda ordenar primero los
paquetes java, javax, luego kotlin, y al final los necesarios del proyecto.

#### Solución aplicada:
Ordenarlos alfabéticamente como está impuesto en la documentación oficial de la sintaxis de kotlin.

- antes:

```kotlin
import kotlin.collections.List

import java.util.Date
```

- después:
```kotlin
import java.util.Date
import kotlin.collections.List
```

### 4. Espaciado incorrecto alrededor de los dos puntos (:)

#### Descripción del error:
El espaciado alrededor del símbolo `:` en declaraciones de tipo o herencia debe seguir la sintaxis oficial, `perro: Animal`
un solo espacio después y ninguno antes.

#### Solución aplicada:
Corregir las declaraciones de tipo o de herencia en las que se haya encontrado este error y poner los espacios correctamente.

- antes:
```kotlin
val nombre :String = "Juan"
fun saludo(): String {
    return "hola Juan"
}
```
- después:
```kotlin
val nombre: String = "Juan"
fun saludo(): String {
    return "hola Juan"
}
```

### 5. Indentación incorrecta

#### Descripción del error:
Las líneas deben estar indentadas según la estructura del bloque. Ktlint espera 4 espacios por nivel de indentación.

#### Solución aplicada:
Indentar correctamente aquellos bloques de código que no cumpliesen con la norma general de 4 espacios por nivel de indentación.

- antes:
```kotlin
fun ejemplo() {
    if (true) {
      println("Error") // 6 espacios en vez de 8
    }
}
```
- después:
```kotlin
fun ejemplo() {
    if (true) {
        println("Corregido") // 8 espacios
    }
}
```

## Cambio de alguna configuración en ktlint:

Para realizar el cambio en la configuración de ktlint, tenemos que crear un archivo `.editorconfig` en el cual incluiremos
aquellos requisitos que queramos que se cumplan sintácticamente en nuestro proyecto.

En mi caso, he decidido "solucionar" todos los errores detectados anteriormente e intentar que no se vuelvan a repetir,
metiendo en este archivo [ .editorconfig](.editorconfig) lo siguiente:

![](/assets/editconfig.png)

Si por ejemplo en este archivo, cambio el número de espacios parar la indentación, me daría errores debido a que es lo que
he impuesto. Si cambio de 4 a 2 el nº de espacios para la indentación, al ejecutar el comando para buscar errores, me
tomaría todas las indentaciones con 4 espacios como errores, y si ejecuto el comando para corregir errores automáticamente,
todas estas indentaciones pasarían a ser de 2 espacios.

## Preguntas a responder:

### [1]

- 1.a ¿Qué herramienta has usado, y para qué sirve?

He usado ktlint, una herramienta que sirve para tener un código lo más limpio y familiar posible para el resto de la comunidad,
ya que te impone las pautas de la sintaxis oficial de kotlin para tu proyecto, con opciones incluso a modificarlas, de manera automática.

- 1.b ¿Cuáles son sus características principales?

La principal característica de ktlint es el formateo de código automático, pero también es fácil de configurar, tiene integración
con gradle y podríamos decir que es bastante rápido y ligero, exceptuando la primera vez que se implementa en el proyecto, que
sí que puede tardar algo más.

- 1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?

Al usar ktlint nos ahorramos muchísimo tiempo en tener que realizar cambios en nuestro código para que todo nuestro proyecto cumpla los mismos
requisitos. Con esta herramienta, podremos localizar los fallos e incluso arreglarlos de una vez de manera automática con tan solo
ejecutar un par de comandos. Por no hablar sobre la flexibilidad de ajustes que tiene, que podemos cambiar dichos requisitos según nos
convenga.

### [2]
- 2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cuál es el que te ha parecido
que ha mejorado más tu código?

No sabría cuál de todos escoger, ya que ha detectado muchos diferentes. Si tuviese que destacar alguno, eligiría el de los espacios
alrededor de los `:`, ya que tengo la mala costumbre de dejar un espacio tanto delante como detrás, y si tuviese que ir cambiando eso
por cada definición en el proyecto tardaría infinito, y con esto se hace automáticamente.

Realmente todos los cambios realizados por ktlint mejoran el código de igual manera, destacando la indentación y las reglas generales
de sintaxis.

- 2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?

Sí, las soluciones que esta herramienta impone sobre los problemas encontrados no son supercomplejas, y realmente se pueden
entender fácilmente con tan solo leer el mensaje de error al ejecutarla. Si la sintaxis de kotlin y la forma oficial de desarrollar
software ha sido acordada de esa manera, significa que esa es la correcta.

- 2.c ¿Por qué se ha producido ese error/problema?

Porque inicialmente, a la hora de desarrollar nuestro proyecto, mis compañeros y yo desconocíamos la manera acordada
de desarrollar software en kotlin según la convención oficial, por tanto al ejecutar por primera vez ktlint se encontraron
muchísimos errores.

### [3]
- 3.a ¿Qué posibilidades de configuración tiene la herramienta?

No se de cuántas dispondrá exactamente, pero en mi caso y según he buscado, he configurado a modo de prueba:
que añada un salto de línea a final de línea, que todos los archivos finalicen con una línea vacía, que borre los espacios
en blanco al final de línea, que use 4 espacios a la hora de indentar, que la longitud máxima por línea sea de 20 carácteres
y que ordene correctamente las importaciones según se acuerda en la documentación oficial.

- 3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?

Diría que cambiar el orden de los imports es la que me ha parecido más distinta. No sabía que este orden podía afectar a
algo y al parecer lo correcto según la sintaxis oficial de kotlin es ordenarlas alfabéticamente, por lo que lo implementé
en el fichero de `.editorconfig`.

- 3.c Pon un ejemplo de como ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio,

- antes:
```kotlin
import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Status
import dominio.Usuario
import dominio.RangoFecha
import java.text.SimpleDateFormat

class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
}

```

- después:
```kotlin

import datos.IActividadRepository
import dominio.Actividad
import dominio.Evento
import dominio.RangoFecha
import dominio.Status
import dominio.Tarea
import dominio.Usuario
import java.text.SimpleDateFormat

class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
}
```
### [4]
4 ¿Qué conclusiones sacas después del uso de estas herramientas?

La verdad que, aunque llevar a cabo el proceso de implementarla por primera vez pueda ser algo tedioso y aburrido, en el sentido
de no saber cómo hacerlo y tal, a la larga va a ser beneficioso para el proyecto, ya que uno de los beneficios principales de esta
herramienta es el ahorro de tiempo en revisiones sintácticas.

Además, si en un futuro estas normas cambian, sería tan fácil como cambiar lo necesario en el archivo .editorconfig y volver a
ejecutar el comando para que se realicen los cambios automáticamente en el proyecto.
