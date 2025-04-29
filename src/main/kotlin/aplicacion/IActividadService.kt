package aplicacion

import dominio.Actividad

interface IActividadService {
    fun crearTarea(descripcion: String, etiquetas: List<String>)

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>)

    fun obtenerActividades(): List<Actividad>
}
