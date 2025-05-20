package dominio

import java.text.SimpleDateFormat
import java.util.Date

/**
 * # RangoFecha
 *
 * Representa un rango limitado de fechas con una fecha de inicio y una de fin.
 *
 * Se utiliza para realizar comparaciones y filtros para fechas concretas.
 *
 * @property inicio Fecha de inicio del rango.
 * @property fin Fecha de fin del rango.
 */
data class RangoFecha(val inicio: Date, val fin: Date) {

    /**
     * ## estaDentroDelRango
     *
     * Comprueba si una fecha dada se encuentra dentro del rango definido.
     *
     * La comparación se realiza usando el formato `yyyy/MM/dd`.
     *
     * @param fecha Fecha a comprobar.
     * @return `true` si la fecha está entre inicio y fin (con ambos incluidos en el rango), `false` en caso contrario.
     */
    fun estaDentroDelRango(fecha: Date): Boolean {
        val formatoFecha = SimpleDateFormat("yyyy/MM/dd")
        val fechaInicio = formatoFecha.format(inicio)
        val fechaFin = formatoFecha.format(fin)
        val fechaActividad = formatoFecha.format(fecha)

        return fechaActividad >= fechaInicio && fechaActividad <= fechaFin
    }
}

