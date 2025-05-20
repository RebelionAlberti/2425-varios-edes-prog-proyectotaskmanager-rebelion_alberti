package dominio

class Usuario private constructor(val id: Int, val nombre: String) {

    companion object {
        private var contadorId = 1

        fun crear(nombre: String): Usuario {
            return Usuario(contadorId++, nombre)
        }
    }

    override fun toString(): String {
        return "Usuario=[ID: $id, Nombre: $nombre]"
    }
}
