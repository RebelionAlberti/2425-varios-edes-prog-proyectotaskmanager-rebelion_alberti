package dominio

class Usuario(val id: Int, val nombre: String) {

    companion object {
        private var contadorId = 1

        fun crear(nombre: String): Usuario {
            return Usuario(contadorId++, nombre)
        }

        fun configurarContador(inicio: Int) {
            if (inicio >= contadorId) {
                contadorId = inicio
            }
        }
    }

    override fun toString(): String {
        return "Usuario=[ID: $id, Nombre: $nombre]"
    }
}