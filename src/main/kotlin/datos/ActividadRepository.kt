package datos

import dominio.Actividad
import dominio.Tarea
import dominio.Usuario

class ActividadRepository : IActividadRepository {
    private val actividades = mutableListOf<Actividad>()

    override fun agregarActividad(actividad: Actividad): Boolean {
        var guardado = false
        if (buscarPorId(actividad.id) == null) {
            actividades.add(actividad)
            guardado = true
        }
        return guardado
    }

    override fun recuperarTodas(): List<Actividad> = actividades.toList()

    override fun recuperarPorId(id: Int): Actividad? = buscarPorId(id)

    override fun recuperarTareas(): List<Actividad> {
        TODO("Not yet implemented")
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        val actual = buscarPorId(actividad.id)
        return if (actual != null) {
            actividades.remove(actual)
            actividades.add(actividad)
            true
        } else {
            false
        }
    }

    override fun borrarPorId(id: Int): Actividad? {
        val actividad = buscarPorId(id)
        return if (actividad != null) {
            actividades.remove(actividad)
            actividad
        } else {
            null
        }
    }

    override fun asignarUsuarioATarea(
        idTarea: Int,
        usuario: Usuario?,
    ): Boolean {
        val actividad = buscarPorId(idTarea)
        if (actividad is Tarea) {
            actividad.asignadoA = usuario
            return true
        }
        return false
    }

    override fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea> =
        actividades
            .filterIsInstance<Tarea>()
            .filter { it.asignadoA?.id == idUsuario }

    private fun buscarPorId(id: Int): Actividad? = actividades.find { it.id == id }
}
