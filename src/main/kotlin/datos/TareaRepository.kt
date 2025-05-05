package datos

import dominio.Actividad
import dominio.Tarea
import dominio.Usuario


class TareaRepository(private val tareaDao: TareaDAO) : IActividadRepository {

    override fun agregarActividad(actividad: Actividad): Boolean {
        if (actividad is Tarea) {
            return tareaDao.crearTarea(actividad)
        }
        return false
    }

    override fun recuperarTodas(): List<Actividad> {
        return tareaDao.recuperarTareas()
    }

    override fun recuperarPorId(id: Int): Actividad? {
        return tareaDao.recuperarTareasId(id)
    }

    override fun recuperarTareas(): List<Actividad> {
        return tareaDao.recuperarTareas()
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        if (actividad is Tarea) {
            return tareaDao.actualizarTarea(actividad)
        }
        return false
    }

    override fun borrarPorId(id: Int): Actividad? {
        val tarea = tareaDao.recuperarTareasId(id)
        return if (tarea != null && tareaDao.borrarTarea(id)) tarea else null
    }

    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        val tarea = tareaDao.recuperarTareasId(idTarea)
        if (tarea != null) {
            tarea.asignadoA = usuario
            return tareaDao.actualizarTarea(tarea)
        }
        return false
    }

    override fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return tareaDao.recuperarTareas()
            .filter { it.asignadoA?.id == idUsuario }
    }
}
