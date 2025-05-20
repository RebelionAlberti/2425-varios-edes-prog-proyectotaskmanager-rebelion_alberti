package dominio

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Clase abstracta Actividad con descripción, identificación y fecha de creación.
 *
 * @property descripcion Descripción breve de la actividad.
 */
abstract class Actividad(val descripcion: String) {

    companion object {
        /**
         * Contador para asignar id únicos a cada actividad.
         *
         * Se incrementa automáticamente cada vez que se accede.
         */
        private var contadorId: Int = 0
            get() = field++
    }

    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    private val fecha = Date()

    /**
     * Lista de etiquetas asociadas a la actividad.
     */
    abstract val etiquetas: List<String>

    /**
     * Identificador único de la actividad.
     */
    val id: Int

    /**
     * Fecha de creación de la actividad en formato dd/MM/yyyy.
     */
    val fechaCreacion: String

    /**
     * Detalles de la actividad (id, descripcion).
     *
     * Puede ser sobrescrito por las subclases para incluir más detalles, tanto de las tareas como de los eventos.
     */
    open val detalle: String
        get() = "$id - $descripcion"

    init {
        id = contadorId
        fechaCreacion = formatoFecha.format(fecha)
    }
}
