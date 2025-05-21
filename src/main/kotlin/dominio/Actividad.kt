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
    val id : Int
    val fechaCreacion: String
    open val detalle: String
        get() = "$id - $descripcion"

    init {
        id = contadorId
        fechaCreacion = formatoFecha.format(fecha)
    }

    open fun asignarUsuario(usuario: Usuario?): Boolean {
        // Por defecto, una actividad no permite asignación de usuario
        return false
    }

}