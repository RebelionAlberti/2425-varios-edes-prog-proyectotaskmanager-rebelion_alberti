package datos

import dominio.Actividad
import dominio.Tarea
import dominio.Usuario

/**
 * # ActividadRepository.
 *
 * Esta clase gestiona las actividades (tareas y eventos) utilizando una lista.
 * Permite realizar operaciones CRUD y otras funciones específicas como asignación de usuarios o recuperación
 * filtrada por usuario.
 *
 */

class ActividadRepository : IActividadRepository {

    /**
     * lista de actividades registradas.
     */
    private val actividades = mutableListOf<Actividad>()


    /**
     * ## agregarActividad
     *
     * Agrega una nueva actividad al repositorio si su Id no está duplicado.
     *
     * @param actividad Actividad a agregar.
     * @return `true` si se agregó exitosamente, `false` si ya existía una actividad con el mismo Id.
     */
    override fun agregarActividad(actividad: Actividad): Boolean {
        var guardado = false
        if (actividades.find { it.id == actividad.id } == null) {
            actividades.add(actividad)
            guardado = true
        }
        return guardado
    }


    /**
     * ## recuperarTodas
     *
     * Recupera todas las actividades registradas.
     *
     * @return Lista de todas las actividades.
     */
    override fun recuperarTodas(): List<Actividad> {
        return actividades.toList()
    }


    /**
     * ## recuperarPorId
     *
     * Recupera una actividad por su identificador único.
     *
     * @param id Id de la actividad a buscar.
     * @return La actividad correspondiente o `null` si no se encuentra.
     */
    override fun recuperarPorId(id: Int): Actividad? {
        var actividad: Actividad? = null
        val actividadesId = actividades.filter { it.id == id }
        if (actividadesId.isNotEmpty()) {
            actividad = actividadesId[0]
        }
        return actividad
    }


    /**
     * ## recuperarTareas
     *
     * Recupera las actividades que son tareas.
     *
     * @return Lista de tareas. Actualmente no implementado.
     */
    override fun recuperarTareas(): List<Actividad> {
        TODO("Not yet implemented")
    }


    /**
     * ## actualizarActividad
     *
     * Actualiza una actividad existente reemplazándola por una nueva con el mismo Id.
     *
     * @param actividad Actividad con datos actualizados.
     * @return `true` si la actividad fue actualizada, `false` si no se encontró.
     */
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


    /**
     * ## borrarPorId
     *
     * Elimina una actividad del repositorio por su Id.
     *
     * @param id Identificador de la actividad a eliminar.
     * @return La actividad eliminada o `null` si no se encontró.
     */
    override fun borrarPorId(id: Int): Actividad? {
        val actividad = actividades.find { it.id == id }
        return if (actividad != null) {
            actividades.remove(actividad)
            actividad
        } else {
            null
        }
    }



    /**
     * ## asignarUsuarioATarea
     *
     * Asigna un usuario a una tarea si la actividad con Id dado es una tarea.
     *
     * @param idTarea Id de la tarea a la que se asignará el usuario.
     * @param usuario Usuario a asignar (o `null` para desasignar).
     * @return `true` si se realizó la asignación correctamente, `false` si no es una tarea válida.
     */
    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        val actividad = actividades.find { it.id == idTarea }
        if (actividad is Tarea) {
            actividad.asignadoA = usuario
            return true
        }
        return false
    }


    /**
     * ## recuperarTareasPorUsuario
     *
     * Recupera todas las tareas asignadas a un usuario específico.
     *
     * @param idUsuario Id del usuario.
     * @return Lista de tareas asignadas al usuario.
     */
    override fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return actividades
            .filterIsInstance<Tarea>()
            .filter { it.asignadoA?.id == idUsuario }
    }
}