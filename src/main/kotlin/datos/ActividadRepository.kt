package datos

import dominio.Actividad
import dominio.Tarea
import dominio.Usuario

class ActividadRepository : IActividadRepository {

    private val actividades = mutableListOf<Actividad>()

    override fun agregarActividad(actividad: Actividad): Boolean {
        var guardado = false
        if (actividades.find { it.id == actividad.id } == null) {
            actividades.add(actividad)
            guardado = true
        }
        return guardado
    }

    override fun recuperarTodas(): List<Actividad> {
        return actividades.toList()
    }

    override fun recuperarPorId(id: Int): Actividad? {
        var actividad: Actividad? = null
        val actividadesId = actividades.filter { it.id == id }
        if (actividadesId.isNotEmpty()) {
            actividad = actividadesId[0]
        }
        return actividad
    }

    override fun recuperarTareas(): List<Actividad> {
        TODO("Not yet implemented")
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        val actual = actividades.find { it.id == actividad.id }
        return if (actual != null) {
            actividades.remove(actual)
            actividades.add(actividad)
            true
        } else {
            false
        }
    }

    override fun borrarPorId(id: Int): Actividad? {
        val actividad = actividades.find { it.id == id }
        return if (actividad != null) {
            actividades.remove(actividad)
            actividad
        } else {
            null
        }
    }

    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        val actividad = actividades.find { it.id == idTarea }
        return actividad?.asignarUsuario(usuario) ?: false
    }

    override fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return actividades
            .filterIsInstance<Tarea>()
            .filter { it.asignadoA?.id == idUsuario }
    }
}