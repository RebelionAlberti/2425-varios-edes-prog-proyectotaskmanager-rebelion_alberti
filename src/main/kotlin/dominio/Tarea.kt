package dominio

enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    CERRADA("Cerrada")
}

class Tarea private constructor(descripcion: String, estadoInicial: Status = Status.ABIERTA) : Actividad(descripcion) {
    // Subtareas
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
        fun crearInstancia(descripcion: String): Tarea {
            return Tarea(descripcion)
        }
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
        val subtareasTexto = if (subtareas.isEmpty()) {
            ""
        } else {
            "\n" + subtareas.joinToString("\n") { it.formatoTareas(true).prependIndent("    ") }
        }

        return "$tipo=[ID: $id, Descripción: $descripcion, Fecha de creación: $fechaCreacion, Estado: ${estado.descripcion}]$subtareasTexto"
    }

    override fun toString(): String {
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Detalle: $detalle, Estado: ${estado.descripcion}]"
    }
}
