package dominio

enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    EN_PROGRESO("En progreso"),
    CERRADA("Cerrada")
}

class Tarea private constructor(
    descripcion: String,
    var estado: Status = Status.ABIERTA,
    var subTareas: MutableList<Tarea> = mutableListOf(),
    var tareaMadre: Tarea? = null,
    var etiquetas: List<String> = listOf()
    ) : Actividad(descripcion){
    var asignadoA: Usuario? = null
    // Companion object
    companion object {
        fun crearInstancia(descripcion: String, etiquetas: List<String>): Tarea {
            return Tarea(descripcion, etiquetas = etiquetas)
        }
    }

   fun cerrarPorSubtareasFinalizadas() {
       if(subTareas.all { it.estado == Status.CERRADA }){
           this.estado = Status.CERRADA
       }
   }

    fun puedeFinalizar(): Boolean {
        return subTareas.all { it.estado == Status.CERRADA }
    }

    override fun toString(): String {
        val asignado = asignadoA?.nombre ?: "No asignado"
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Detalle: $detalle, Estado: ${estado.descripcion}, Asignado a: $asignado, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}