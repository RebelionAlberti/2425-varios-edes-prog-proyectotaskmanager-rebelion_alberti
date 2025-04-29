package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Status
import dominio.Usuario
import dominio.RangoFecha
import java.text.SimpleDateFormat

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


    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        return repositorio.asignarUsuarioATarea(idTarea, usuario)
    }

    override fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return repositorio.recuperarTareasPorUsuario(idUsuario)
    }

    override fun filtrarPorTipo(tipo: String): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            when (tipo) {
                "TAREA" -> it is Tarea
                "EVENTO" -> it is Evento
                else -> false
            }
        }
    }

    override fun filtrarPorEstado(estado: Status): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            when (estado) {
                Status.ABIERTA -> it is Tarea && it.estado == Status.ABIERTA
                Status.EN_PROGRESO -> it is Tarea && it.estado == Status.EN_PROGRESO
                Status.CERRADA -> it is Tarea && it.estado == Status.CERRADA
                else -> false
            }
        }
    }

    override fun filtrarPorFecha(rango: RangoFecha): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(it.fechaCreacion)
            rango.estaDentroDelRango(fechaCreacion)
        }
    }

    override fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad> {
        return repositorio.recuperarTodas().filter { actividad ->
            etiquetas.any { etiqueta -> actividad.etiquetas.contains(etiqueta) }
        }
    }

    override fun eliminarActividadPorId(id: Int): Actividad? {
        return repositorio.borrarPorId(id)
    }
}