package dominio

import java.text.SimpleDateFormat
import java.util.Date


/**
 * # Actividad.
 *
 * Clase abstracta base que representa una actividad genérica dentro del programa.
 *
 * Cada actividad tiene: una descripción, un identificador único, una fecha de creación
 * y una lista de etiquetas informativas.
 *
 * Esta clase se encarga de generar automáticamente un Id único incremental y de asignar
 * la fecha de creación en el momento que es instanciada.
 *
 * @property descripcion Descripción breve de la actividad.
 * @property etiquetas Lista de etiquetas asociadas a la actividad.
 * @property id Identificador único de la actividad (asignado automáticamente e incremental).
 * @property fechaCreacion Fecha de creación de la actividad en formato "dd/MM/yyyy".
 * @property detalle Descripción detallada de la actividad, incluyendo el ID y la descripción.
 */
abstract class Actividad(val descripcion: String) {

    /**
     * Companion object para generar Ids únicos para cada actividad.
     */
    companion object {
        private var contadorId: Int = 0
            get() = field++
    }


    // Atributos
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    private val fecha = Date()

    /**
     * Lista de etiquetas asociadas a la actividad.
     */
    abstract val etiquetas: List<String>

    /**
     * Identificador único de la actividad, generado automáticamente.
     */
    val id : Int

    /**
     * Fecha de creación de la actividad, en formato "dd/MM/yyyy".
     */
    val fechaCreacion: String

    /**
     * Descripción detallada de la actividad.
     * Incluye Id y descripción general.
     */
    open val detalle: String
        get() = "$id - $descripcion"


    /**
     * Bloque de inicialización que asigna el Id y la fecha de creación.
     */
    init {
        id = contadorId
        fechaCreacion = formatoFecha.format(fecha)
    }
}