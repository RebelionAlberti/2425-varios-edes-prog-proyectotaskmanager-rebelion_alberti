package dominio

/**
 * # Status
 *
 * Representa los posibles estados de una tarea.
 *
 * @property descripcion Descripción legible del estado.
 */
enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    EN_PROGRESO("En progreso"),
    CERRADA("Cerrada")
}


/**
 * # Tarea
 *
 * Representa una tarea con su estado, subtareas, historial y asignación.
 *
 * @property descripcion Descripción breve de la tarea.
 * @property estadoInicial Estado inicial de la tarea, por defecto Status.ABIERTA.
 * @property subTareas Lista mutable de subtareas asociadas a esta tarea.
 * @property tareaMadre Referencia a la tarea principal si esta es una subtarea.
 * @property etiquetas Lista de etiquetas asociadas para clasificación.
 *
 * @property asignadoA Usuario asignado a la tarea, puede ser nulo si no está asignada.
 * @property subtareas Lista mutable interna para manejo de subtareas.
 * @property estado Estado actual de la tarea. No permite cerrar si existen subtareas abiertas.
 *
 * @constructor Constructor privado para forzar creación controlada mediante crearInstancia.
 */
class Tarea private constructor(
    descripcion: String,
    estadoInicial: Status = Status.ABIERTA,
    var subTareas: MutableList<Tarea> = mutableListOf(),
    var tareaMadre: Tarea? = null,
    override val etiquetas: List<String> = listOf())
    : Actividad(descripcion){
    var asignadoA: Usuario? = null
    val subtareas: MutableList<Tarea> = mutableListOf()
    private val historial: MutableList<RegistroHistorial> = mutableListOf()
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

    companion object {

        /**
         * ## crearInstancia
         *
         * Crea una nueva instancia de Tarea con la descripción y etiquetas especificadas.
         * Además, agrega un registro inicial indicando la creación.
         *
         * @param descripcion Descripción de la tarea.
         * @param etiquetas Lista de etiquetas asociadas.
         * @return Una nueva instancia de Tarea.
         */
        fun crearInstancia(descripcion: String, etiquetas: List<String>): Tarea {
            val tarea = Tarea(descripcion, etiquetas = etiquetas)
            tarea.agregarRegistro("Tarea creada")
            return tarea
        }
    }


    /**
     * ## agregarRegistro
     *
     * Agrega un registro de historial con la descripción dada.
     *
     * @param descripcion Texto que describe el cambio o evento a registrar.
     */
    fun agregarRegistro(descripcion: String) {
        historial.add(RegistroHistorial.crearRegistro(descripcion))
    }



    /**
     * ## obtenerHistorial
     *
     * Obtiene una copia de la lista de registros del historial de la tarea.
     *
     * @return Lista con los registros del historial.
     */
    fun obtenerHistorial(): List<RegistroHistorial> {
        return historial.toList()
    }


    /**
     * ## cerrarPorSubtareasFinalizadas
     *
     * Cierra esta tarea automáticamente si todas sus subtareas están finalizadas.
     * Agrega un registro en el historial indicando el cierre automático.
     */
    fun cerrarPorSubtareasFinalizadas() {
       if(subTareas.all { it.estado == Status.CERRADA }){
           this.estado = Status.CERRADA
           agregarRegistro("Tarea cerrada automáticamente al completarse todas las subtareas")
       }
   }


    /**
     * ## puedeFinalizar
     *
     * Comprueba que la tarea pueda finalizar, es decir, que todas sus subtareas estén cerradas.
     *
     * @return `true` si puede finalizar, `false` en caso contrario.
     */
    fun puedeFinalizar(): Boolean {
        return subTareas.all { it.estado == Status.CERRADA }
    }


    /**
     * ## agregarSubtarea
     *
     * Agrega una subtarea a la lista de subtareas si no está ya en ella.
     *
     * @param subtarea La subtarea a agregar.
     * @return `true` si la subtarea fue agregada, `false` si ya existía.
     */
    fun agregarSubtarea(subtarea: Tarea): Boolean {
        if (subtareas.any { it.id == subtarea.id }) {
            return false
        }
        subtareas.add(subtarea)
        return true
    }


    /**
     * ## formatoTareas
     *
     * Formatea la tarea y sus subtareas en un string descriptivo.
     *
     * @param esSubtarea Indica si la tarea es una subtarea para ajustar el formato.
     * @return String con los detalles de la tarea y sus subtareas.
     */
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


    /**
     * ## toString
     *
     * Devuelve una representación legible de la tarea, con detalles principales.
     *
     * @return String con la información de la tarea.
     */
    override fun toString(): String {
        val asignado = asignadoA?.nombre ?: "No asignado"
        return "Tarea=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Detalle: $detalle, Estado: ${estado.descripcion}, Asignado a: $asignado, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}