package dominio

enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    CERRADA("Cerrada")
}

class Tarea private constructor(descripcion: String, var estado: Status = Status.ABIERTA) : Actividad(descripcion){
    var asignadoA: Usuario? = null

    // Companion object
    companion object {
        fun crearInstancia(descripcion: String): Tarea {
            return Tarea(descripcion)
        }
    }

    override fun toString(): String {
        val asignado = asignadoA?.nombre ?: "No asignado"
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Detalle: $detalle, Estado: ${estado.descripcion}, Asignado a: $asignado]"
    }
}