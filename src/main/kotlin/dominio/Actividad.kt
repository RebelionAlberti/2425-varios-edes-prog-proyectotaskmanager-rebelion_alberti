package dominio

import java.text.SimpleDateFormat
import java.util.Date

abstract class Actividad(val descripcion: String) {
    // Companion object
    companion object {
        private var contadorId: Int = 0
            get() = field++
    }

    // Atributos
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    private val fecha = Date()
    abstract val etiquetas: List<String>
    val id: Int = contadorId
    val fechaCreacion: String = formatoFecha.format(fecha)
    open val detalle: String
        get() = "$id - $descripcion"
}
