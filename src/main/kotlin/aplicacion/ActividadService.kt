package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Status
import dominio.Tarea
import dominio.Evento
import dominio.RangoFecha
import java.text.SimpleDateFormat

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
        return repositorio.recuperarTodas().filterIsInstance<Tarea>().filter { it.estado == estado }
    }

    override fun filtrarPorFecha(rango: RangoFecha): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(it.fechaCreacion)
            rango.estaDentroDelRango(fechaCreacion)
        }
    }

    override fun eliminarActividadPorId(id: Int): Actividad? {
        return repositorio.borrarPorId(id)
    }
}
