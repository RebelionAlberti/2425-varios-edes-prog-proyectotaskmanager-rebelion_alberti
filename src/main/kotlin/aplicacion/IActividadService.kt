package aplicacion

import dominio.Actividad

interface IActividadService {
    fun crearTarea(descripcion: String)

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String)

    fun obtenerActividades(): List<Actividad>
}
