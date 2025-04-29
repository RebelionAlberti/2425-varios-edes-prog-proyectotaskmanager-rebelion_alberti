package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento

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

    override fun agregarSubtarea(idTareaPrincipal: Int, descripcionSubtarea: String): Boolean {
        val tareaPrincipal = repositorio.recuperarPorId(idTareaPrincipal)

        if (tareaPrincipal is Tarea) {
            val esSubtarea = repositorio.recuperarTodas().any {
                it is Tarea && (it as Tarea).subtareas.contains(tareaPrincipal)
            }

            if (esSubtarea) {
                return false
            }

            val subtarea = Tarea.crearInstancia(descripcionSubtarea)

            val fueGuardada = repositorio.agregarActividad(subtarea)

            if (fueGuardada && tareaPrincipal.agregarSubtarea(subtarea)) {
                return repositorio.actualizarActividad(tareaPrincipal)
            }
        }

        return false
    }

}