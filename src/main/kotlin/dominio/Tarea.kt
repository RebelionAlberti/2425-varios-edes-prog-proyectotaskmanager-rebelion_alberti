package dominio

enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    EN_PROGRESO("En progreso"),
    CERRADA("Cerrada")
}

class Tarea private constructor(
    descripcion: String,
    estadoInicial: Status = Status.ABIERTA,
    var subTareas: MutableList<Tarea> = mutableListOf(),
    var tareaMadre: Tarea? = null,
    override val etiquetas: List<String> = listOf())
    : Actividad(descripcion){
    var asignadoA: Usuario? = null
    val subtareas: MutableList<Tarea> = mutableListOf()
    var estado: Status = estadoInicial
        set(value) {
            if (value == Status.CERRADA) {
                val haySubtareasAbiertas = subtareas.any { it.estado == Status.ABIERTA }
                if (haySubtareasAbiertas) {
                    println("No se puede cerrar la tarea principal hasta que todas las subtareas estén cerradas.")
                    return
                }
            }
            field = value
        }
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

    fun agregarSubtarea(subtarea: Tarea): Boolean {
        if (subtareas.any { it.id == subtarea.id }) {
            return false
        }
        subtareas.add(subtarea)
        return true
    }

    fun formatoTareas(esSubtarea: Boolean = false): String {
        val tipo = if (esSubtarea) "Subtarea" else "Tarea"
        val asignado = if (esSubtarea) "" else "Asignado a: ${asignadoA?.nombre ?: "No asignado"}"
        val etiquetasTexto = if (esSubtarea) "" else "Etiquetas: ${etiquetas.joinToString(", ")}"

        val detalles = StringBuilder("ID: $id, Descripción: $descripcion, Fecha de creación: $fechaCreacion, Estado: ${estado.descripcion}")

        if (asignado.isNotEmpty()) detalles.append(", $asignado")
        if (etiquetasTexto.isNotEmpty()) detalles.append(", $etiquetasTexto")

        val subtareasTexto = if (subtareas.isEmpty()) {
            ""
        } else {
            "\n" + subtareas.joinToString("\n") { it.formatoTareas(true).prependIndent("    ") }
        }

        return "$tipo=[$detalles]$subtareasTexto"
    }



    override fun toString(): String {
        val asignado = asignadoA?.nombre ?: "No asignado"
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Detalle: $detalle, Estado: ${estado.descripcion}, Asignado a: $asignado, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}