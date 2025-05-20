package dominio


/**
 * # Usuario
 *
 * Representa un usuario con un identificador único y un nombre.
 *
 * @property id Identificador único del usuario.
 * @property nombre Nombre del usuario.
 *
 * @constructor Privado para controlar la creación mediante el método crear.
 */
class Usuario private constructor(val id: Int, val nombre: String) {

    companion object {
        private var contadorId = 1

        /**
         * ## crear
         *
         * Crea una nueva instancia de Usuario asignándole un identificador único automáticamente.
         *
         * @param nombre Nombre del usuario a crear.
         * @return Una nueva instancia de Usuario con un Id único.
         */
        fun crear(nombre: String): Usuario {
            return Usuario(contadorId++, nombre)
        }
    }


    /**
     * ## toString
     *
     * Devuelve una representación en texto legible del usuario.
     *
     * @return String con Id y el nombre del usuario.
     */
    override fun toString(): String {
        return "Usuario=[ID: $id, Nombre: $nombre]"
    }
}