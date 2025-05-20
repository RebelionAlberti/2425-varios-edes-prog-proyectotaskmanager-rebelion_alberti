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