package dominio

import java.text.SimpleDateFormat
import java.util.Date

data class RangoFecha(val inicio: Date, val fin: Date) {
    fun estaDentroDelRango(fecha: Date): Boolean {
        return !fecha.before(inicio) && !fecha.after(fin)
    }
}
