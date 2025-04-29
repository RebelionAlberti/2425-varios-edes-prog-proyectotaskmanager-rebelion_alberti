package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Usuario

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

    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        return repositorio.asignarUsuarioATarea(idTarea, usuario)
    }

    override fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return repositorio.recuperarTareasPorUsuario(idUsuario)
    }
}