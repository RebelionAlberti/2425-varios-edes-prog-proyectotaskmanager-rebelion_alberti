package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento

class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
    override fun crearTarea(descripcion: String, etiquetas: List<String>) {
        val tarea = Tarea.crearInstancia(descripcion, etiquetas)
        repositorio.agregarActividad(tarea)
    }

    override fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>) {
        val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, etiquetas)
        repositorio.agregarActividad(evento)
    }

    override fun obtenerActividades(): List<Actividad> {
        return repositorio.recuperarTodas()
    }
}