package aplicacion

import dominio.Actividad
import dominio.Status
import dominio.RangoFecha

interface IActividadService {
    fun crearTarea(descripcion: String)

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String)

    fun obtenerActividades(): List<Actividad>

    fun filtrarPorTipo(tipo: String): List<Actividad>

    fun filtrarPorEstado(estado: Status): List<Actividad>

    fun filtrarPorFecha(rango: RangoFecha): List<Actividad>

    fun eliminarActividadPorId(id: Int): Actividad?
}
