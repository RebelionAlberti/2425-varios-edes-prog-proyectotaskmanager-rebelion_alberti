package aplicacion

import dominio.Actividad
import dominio.Status

interface IActividadService {
    fun crearTarea(descripcion: String)

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String)

    fun obtenerActividades(): List<Actividad>

    fun actualizarEstadoTarea(id:Int, nuevoEstado: Status) : Boolean

    fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean
}
