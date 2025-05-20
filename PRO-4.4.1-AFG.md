# Comentarios KDoc

- Para esta actividad tengo que al menos tengo que comentar 3 clases con KDoc en mi caso decidí comentar las clases:
  - Clase [Usuario](src/main/kotlin/dominio/Usuario.kt)
  - Clase[UsuarioService](src/main/kotlin/aplicacion/ActividadService.kt)
  - Clase [UsuarioRepository](src/main/kotlin/datos/UsuarioRepository.kt)

- Funciona de la siguiente manera:
  - Se genera el comentario escribiendo `/** ... */` justo antes de la clase a comentar o método.
  - En el comentario se describe la clase y el método y cualquier información relevante para su funcionamiento.
  - Existen diferentes etiquetas que alguna de ellas pueden ser las siguientes:
    - `@param`: Explica cada parámetro de una función o constructor. 
    - `@return`: Describe el valor que devuelve una función. 
    - `@throws / @exception`: Indica excepciones que puede lanzar. 
    - `@constructor`: Documenta el constructor principal de una clase. 
    - `@property`: Describe una propiedad de una clase.

## Clase Usuario

- Clase usuario
![Clase usuario](assets/usuario_1.png)

- Método crear
![Método crear](assets/usuario_2.png)

- Método configurarContador
![Método configurarContador](assets/usuario_3.png)

- Método toString
![Método toString](assets/usuario_4.png)

## Clase UsuarioService

- Clase UsuarioService
![Clase UsuarioService](assets/usuarioService_1.png)

- Método CrearUsuario
![Método crearUsuario](assets/usuarioService_2.png)

- Método eliminarUsuario
![Método eliminarUsuario](assets/usuarioService_5.png)

- Método obtenerUsuario
![Método obtenerUsuario](assets/usuarioService_3.png)

- Método buscarUsuarioPorId
![Método buscarUsuarioPorId](assets/usuarioService_4.png)

## Clase UsuarioRepository

- Clase UsuarioRepository
![Clase UsuarioRepository](assets/usuarioRepository_1.png)

- Método cargarUsuarios
![Método CargarUsuario](assets/usuarioRepository_2.png)

- Método guardarUsuario
![Método guardarUsuario](assets/usuarioRepository_3.png)

- Método agregar
![Método agregar](assets/usuarioRepository_4.png)

- Método eliminar
![Método eliminar](assets/usuarioRepository_5.png)

- Método recuperarTodos
![Método recuperarTodos](assets/usuarioRepository_6.png)

- Método recuperarPorId
![Método recuperarPorId](assets/usuarioRepository_7.png)

# Instalación de Dokka

- Para comenzar la instalación de dokka tenemos que configurarlo en el proyecto en el build.gradle en el proyecto.
- Ahora tendremos que sincronizar el gradle.

![instalación_dokka](assets/instalacionDokka.png)

# Generar documentación automática html Dokka

- La documentación será generada en el directorio [*build/doc*](build/doc)
- Para generar la documentación tendremos que seguir los siguientes pasos:
  - introducir ./gradlew dokkaHtml en la terminal
    ![Generar documentación](assets/generarDocumentacion.png)
  - Tras esto se creará la documentación html en dentro del directorio build/doc.
  - Ahora podremos abrir el index.html generado y veremos toda nuestra documentación KDoc generada en html.
    ![Generar1](assets/generado1.png)
    ![Generar2](assets/generado2.png)
    ![Generar3](assets/generado3.png)

# Reponder preguntas

- **1 ¿Qué comentarios destacas y por qué?**

- Me gustaría destacar el comentario de la clase UsuarioService me parece que es un comentario perfecto para la clase, ya que explica perfectamente cuál es su función y con que partes del programa interactúa.

![UsuarioService](assets/usuarioService_1.png)

- Me gustaría destacar en este caso un método llamado crear en la clase Usuario me parece que el comentario cumple perfectamente su cometido de explicar que hace el método y gracias a las etiquetas sabemos qué parámetros recibe el método y que devuelve exactamente.

![Método crear clase Usuario](assets/usuario_2.png)

- **2 ¿Qué te aporta la documentación?**

- Obviamente, facilita el entendimiento del código lo cual puede provocar un mantenimiento del código más fácil incluso a la hora de crear nuevas funcionalidades al tener un mejor entendimiento del código el desarrollo será más rápido y se cometerán menos errores además si documentamos el código por ejemplo con KDoc nos permitirá generar documentación automática con herramientas como Dokka.