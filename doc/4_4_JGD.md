# Documentación con KDoc y Dokka en Kotlin

Este documento explica cómo documentar código Kotlin con KDoc y generar documentación automática con Dokka.

##  ¿Qué es KDoc?

KDoc es el sistema de documentación de Kotlin. Se basa en comentarios especiales que describen clases, funciones, propiedades, etc.

## ¿Qué es Dokka?

Dokka es una herramienta oficial de JetBrains para generar documentación en diferentes formatos (HTML, Markdown, Javadoc) a partir de comentarios KDoc.

## Configuración de Dokka

Añadimos a nuestro archivo build.gradle.kts los plugins necesarios junto a las tareas. En mi caso, no leí la práctica 
completa desde el inicio y en lugar de solo realizar la documentación de 3 clases, lo he realizado de todo el proyecto. 
Última vez que no me leo una práctica completa antes de ponerme a hacerla.

```kotlin
plugins {
    id("org.jetbrains.dokka") version "1.9.10"
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka"))
    dokkaSourceSets {
        named("main") {
            documentedVisibilities.set(
                setOf(
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PUBLIC,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.INTERNAL,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PRIVATE,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PROTECTED
                )
            )
        }
    }
}
```

## Pasos a seguir para la generación de la documentación.

### 1. Realizar la documentación con kdoc

Lo primero es documentar tu proyecto lo mejor posible haciendo uso de kdoc, que, como ya hemos visto anteriormente
es el sistema de documentación oficial de kotlin, el cual tiene una sintaxis y unas pautas que se deben cumplir a la hora
de escribir la documentación para que, posteriormente, podamos generar la documentación con dokka sin ningún problema.

Cualquier clase del proyecto está documentada con kdoc, este es un ejemplo de una clase y un par de métodos de la misma
documentados con kdoc:

#### Clase:
![](/assets/ejemploKdocClases.png)

#### Métodos:

![](/assets/ejemploMétodosKdoc.png)


### 2. Generar la documentación con Dokka

Como hemos visto anteriormente, debemos tener dokka implementado en nuestro proyecto para poder realizar este paso. Una
vez tenemos nuestras clases documentadas con kdoc correctamente, podemos utilizar dokka para generar una documentación 
más atractiva visualmente y mucho más familiar.

Para generar la documentación con dokka, debemos ejecutar en terminal el siguiente comando:

```bash
gradle dokkaHTML
```

Una vez ejecutado este comando, si todo está bien configurado y bien implementado, se habrá generado la documentación en
la carpeta build. 

[Enlace a la página con la documentación generada con dokka.](/build/dokka/index.html)

### 3. Abrir la documentación en el navegador

Una vez localizamos los archivos generados en nuestro proyecto por dokka, buscamos el archivo que tenga por nombre 
"index.html" y lo abrimos en nuestro navegador.

Si todo ha salido como estaba previsto, estaremos viendo la documentación que creamos nosotros mismos en kdoc en nuestro
proyecto de manera mucho más visual, en formato web.

## Preguntas a responder:

### 1 ¿Qué comentarios destacas y por qué?

Los comentarios que más destaco son los de las funciones públicas, ya que permiten entender con tan solo echar un vistazo 
qué hace cada una, sin necesidad de ponerse a analizar el cuerpo de la función. También me parecen importantes los de las 
interfaces y clases de servicio, porque ayudan a comprender más fácilmente la lógica general del proyecto.

Además, los comentarios que incluyen información sobre los parámetros (@param) y el valor de retorno (@return) hacen que
el código sea mucho más familiar. Puede parecer algo innecesario cuando uno mismo ha escrito la función, pero pensando 
a futuro tanto para uno mismo como para otros desarrolladores que tengan que mantener o ampliar el proyecto, tener esa 
documentación clara y bien escrita ahorra tiempo y evita malentendidos.

### 2 ¿Qué te aporta la documentación?

Realmente de primera mano puede llegar a ser algo tedioso de hacer, pero es cierto que si miramos por el futuro del proyecto
y pensamos en el resto de desarrolladores que tengan que mantener o ampliar el proyecto, el hecho de haber realizado una
buena documentación facilita muchísimo las cosas.

Esto se debe a que, gracias a la documentación, el funcionamiento del código y lo que se busca con él está 
infinitamente más claro y fácil de comprender. Además, lo bonito, por decirlo de alguna manera, es la parte que se genera
automáticamente con dokka, y la parte tediosa que sería generar la documentación kdoc acaba rentando.

