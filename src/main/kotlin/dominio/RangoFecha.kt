package dominio

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Representa un rango de fechas con inicio y fin.
 *
 * @property inicio Fecha de comienzo del rango.
 * @property fin Fecha de finalización del rango.
 */
data class RangoFecha(
    val inicio: Date,
    val fin: Date,
) {
    /**
     * Comprueba si una fecha dada está dentro del rango (incluyendo inicio y fin).
     *
     * @param fecha La fecha que queremos comprobar.
     * @return true si la fecha está entre inicio y fin, false si no.
     */
    fun estaDentroDelRango(fecha: Date): Boolean {
        val formatoFecha = SimpleDateFormat("yyyy/MM/dd")
        val fechaInicio = formatoFecha.format(inicio)
        val fechaFin = formatoFecha.format(fin)
        val fechaActividad = formatoFecha.format(fecha)

        // Compara las fechas en formato string para ver si está dentro del rango
        return fechaActividad >= fechaInicio && fechaActividad <= fechaFin
    }
}
