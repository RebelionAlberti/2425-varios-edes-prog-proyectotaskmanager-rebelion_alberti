package dominio

import java.text.SimpleDateFormat
import java.util.Date


/**
 * # RegistroHistorial
 *
 * Representa una entrada en el historial de una actividad o tarea.
 *
 * Contiene información sobre una acción realizada y la fecha en la que ocurrió.
 *
 * @property fecha Fecha y hora en que se registró la acción, en formato `dd/MM/yyyy HH:mm:ss`.
 * @property descripcion Descripción de la acción realizada.
 */
data class RegistroHistorial(val fecha: String, val descripcion: String) {


    /**
     * Companion object para la creación de un registro.
     */
    companion object {

        /**
         * ## crearRegistro
         *
         * Crea un nuevo registro del historial con la fecha y hora actual.
         *
         * @param descripcion Descripción de la acción a registrar.
         * @return Una instancia de [RegistroHistorial] con la fecha actual y la descripción proporcionada.
         */
        fun crearRegistro(descripcion: String): RegistroHistorial {
            val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val fechaActual = Date()
            return RegistroHistorial(formato.format(fechaActual), descripcion)
        }
    }
}
