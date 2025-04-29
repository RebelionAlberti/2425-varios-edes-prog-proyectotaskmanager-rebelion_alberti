package dominio

import java.text.SimpleDateFormat
import java.util.Date

data class RangoFecha(val inicio: Date, val fin: Date) {
    fun estaDentroDelRango(fecha: Date): Boolean {
        val formatoFecha = SimpleDateFormat("yyyy/MM/dd")
        val fechaInicio = formatoFecha.format(inicio)
        val fechaFin = formatoFecha.format(fin)
        val fechaActividad = formatoFecha.format(fecha)

        return fechaActividad >= fechaInicio && fechaActividad <= fechaFin
    }
}

