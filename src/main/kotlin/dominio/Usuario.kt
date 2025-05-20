package dominio

/**
 * Usuario con un identificador único y un nombre.
 *
 * @property id Identificador único del usuario.
 * @property nombre Nombre del usuario.
 */
class Usuario(val id: Int, val nombre: String) {

    companion object {
        /**
         * Contador para generar los id únicos automáticamente.
         */
        private var contadorId = 1

        /**
         * Crea un nuevo usuario asignándole automáticamente un ID único.
         *
         * @param nombre Nombre del usuario.
         * @return Un nuevo objeto [Usuario] con un ID único y el nombre.
         */
        fun crear(nombre: String): Usuario {
            return Usuario(contadorId++, nombre)
        }

        /**
         * Configura el contador de id para iniciar desde un valor en concreto.
         * Solo cambiará si el nuevo valor es mayor o igual al valor actual.
         *
         * @param inicio Nuevo valor inicial para el contador.
         */
        fun configurarContador(inicio: Int) {
            if (inicio >= contadorId) {
                contadorId = inicio
            }
        }
    }

    /**
     * Devuelve el usuario en una cadena.
     *
     * @return Cadena: "Usuario=[ID: id, Nombre: nombre]".
     */
    override fun toString(): String {
        return "Usuario=[ID: $id, Nombre: $nombre]"
    }
}
