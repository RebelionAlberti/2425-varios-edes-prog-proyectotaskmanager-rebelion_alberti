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
        // Llamar al repositorio para recuperar la tarea
        val tarea = repositorio.recuperarPorId(id)

        // Verificar si la tarea existe
        return if (tarea is Tarea) {
            // Actualizar el estado de la tarea
            tarea.estado = nuevoEstado
            // Actualizar la tarea en el repositorio
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
                println("❌ No se puede cerrar esta tarea porque tiene subtareas abiertas.")
                return false
            }
            // Si pasa la validación, actualizamos el estado de las subtareas
            tarea.estado = nuevoEstado
            repositorio.actualizarActividad(tarea)

            // Cerramos la tarea madre si todas las subtareas están finalizadas
            tarea.tareaMadre?.cerrarPorSubtareasFinalizadas()
            return true
        }
        return false
    }

}