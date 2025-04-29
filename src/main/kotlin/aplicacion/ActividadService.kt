package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Status

class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
    override fun crearTarea(descripcion: String) {
        val tarea = Tarea.crearInstancia(descripcion)
        repositorio.agregarActividad(tarea)
    }

    override fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String) {
        val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion)
        repositorio.agregarActividad(evento)
    }

    override fun obtenerActividades(): List<Actividad> {
        return repositorio.recuperarTodas()
    }

    override fun actualizarEstadoTarea(id: Int, nuevoEstado: Status): Boolean {
        val tarea = repositorio.recuperarPorId(id)

        return if (tarea is Tarea) {
            tarea.estado = nuevoEstado
            repositorio.actualizarActividad(tarea)
            true
        } else {
            false
        }
    }

    override fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean {
        val tarea = repositorio.recuperarPorId(idSubtarea)

        if (tarea is Tarea) {
            if (nuevoEstado == Status.CERRADA && !tarea.puedeFinalizar()) {
                println("No se puede cerrar esta tarea porque tiene subtareas abiertas.")
                return false
            }
            tarea.estado = nuevoEstado
            repositorio.actualizarActividad(tarea)

            tarea.tareaMadre?.cerrarPorSubtareasFinalizadas()
            return true
        }
        return false
    }

}