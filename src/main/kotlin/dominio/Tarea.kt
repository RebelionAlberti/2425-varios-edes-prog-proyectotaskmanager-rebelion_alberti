package dominio

enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    CERRADA("Cerrada")
}

class Tarea private constructor(descripcion: String, var estado: Status = Status.ABIERTA, var etiquetas: List<String> = listOf()) : Actividad(descripcion){
    // Companion object
    companion object {
        fun crearInstancia(descripcion: String, etiquetas: List<String>): Tarea {
            return Tarea(descripcion, etiquetas = etiquetas)
        }
    }

    override fun toString(): String {
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Detalle: $detalle, Estado: ${estado.descripcion}, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}