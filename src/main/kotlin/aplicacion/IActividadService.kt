package aplicacion

import dominio.Actividad
import dominio.Evento
import dominio.RangoFecha
import dominio.Status

interface IActividadService {
    fun crearTarea(descripcion: String, etiquetas: List<String>)

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>)

    fun obtenerActividades(): List<Actividad>

    fun obtenerEventos(): List<Evento>

    fun actualizarEstadoTarea(id:Int, nuevoEstado: Status) : Boolean

    fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean

    fun filtrarPorTipo(tipo: String): List<Actividad>

    fun filtrarPorEstado(estado: Status): List<Actividad>

    fun filtrarPorFecha(rango: RangoFecha): List<Actividad>

    fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad>

    fun eliminarActividadPorId(id: Int): Actividad?

    fun agregarSubtarea(idTareaPrincipal: Int, descripcionSubtarea: String): Boolean

}