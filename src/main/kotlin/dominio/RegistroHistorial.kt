package dominio

import java.text.SimpleDateFormat
import java.util.Date

data class RegistroHistorial(val fecha: String, val descripcion: String) {
    companion object {
        fun crearRegistro(descripcion: String): RegistroHistorial {
            val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val fechaActual = Date()
            return RegistroHistorial(formato.format(fechaActual), descripcion)
        }
    }
}