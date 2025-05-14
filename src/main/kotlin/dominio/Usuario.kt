package dominio

data class Usuario private constructor(val id: Int, val nombre: String) {

    companion object {
        private var contadorId = 1

        fun crear(id: Int= contadorId++, nombre: String): Usuario {
            return Usuario(id, nombre)
        }
    }

    override fun toString(): String {
        return "Usuario=[ID: $id, Nombre: $nombre]"
    }
}