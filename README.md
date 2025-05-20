## Identificación de la Actividad

- **ID de la Actividad:** 4.4.1.- Documentación
- **Módulo:** EDES
- **Unidad de Trabajo:** Unidad 4: SCV, refactorización y documentación
- **Fecha de Creación:** 20-05-2025
- **Fecha de Entrega:** 20-05-2025
- **Alumno(s):**
    - **Nombre y Apellidos:** Adrián Fernández Garrido
    - **Correo electrónico:** afergar613@g.educaand.es
    - **Iniciales del Alumno/Grupo:** AFG

## Descripción de la Actividad

Trabajamos en la documentación de 3 clases usando KDoc y generamos documentación automática html.

## Desarrollo de la Actividad

### Descripción del Desarrollo

- Se Documenta con KDoc las clases Usuario, UsuarioService y UsuarioRepository
- Se genera documentación automática con Dokka
- Se genera un informe donde se explica paso a paso como se genera la documentación, se muestra como y que se documento con KDoc y se responden las preguntas.

### Código Fuente

```
package dominio

/**
 * Usuario que cuenta con un, id y un nombre.
 *
 * @property id Identificador del usuario.
 * @property nombre Nombre del usuario.
 */
class Usuario(val id: Int, val nombre: String) {

    companion object {
        private var contadorId = 1

        /**
         * Crea un nuevo usuario asignando un id de forma automática.
         *
         * @param nombre Nombre del usuario.
         * @return Instancia de [Usuario] con el id asignado.
         */
        fun crear(nombre: String): Usuario {
            return Usuario(contadorId++, nombre)
        }

        /**
         * Configura el número inicial del contador de las id.
         *
         * Solo se actualiza el contador si el número de inicio es mayor o igual
         * al contador actual para evitar id duplicados.
         *
         * @param inicio Nuevo número inicial para el contador de id.
         */
        fun configurarContador(inicio: Int) {
            if (inicio >= contadorId) {
                contadorId = inicio
            }
        }
    }

    /**
     * Devuelve el usuario en una string.
     *
     * @return Cadena que tiene él, id y el nombre del usuario.
     */
    override fun toString(): String {
        return "Usuario=[ID: $id, Nombre: $nombre]"
    }
}
```

## Conclusiones

- Se aprende a documentar con KDoc de manera correcta.
- Se aprende a generar documentación automática con Dokka.