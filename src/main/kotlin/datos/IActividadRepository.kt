package datos

import dominio.Actividad
import dominio.Usuario
import dominio.Tarea

/**
 * # Interfaz IActividadRepository.
 *
 * Define las operaciones básicas para gestionar las actividades,
 * incluyendo tareas y eventos, permitiendo su almacenamiento, recuperación,
 * actualización, eliminación y asignación de usuarios.
 */
interface IActividadRepository {

    /**
     * ## agregarActividad
     *
     * Agrega una nueva actividad al repositorio.
     *
     * @param actividad Actividad a agregar (tareas o eventos).
     * @return `true` si la actividad fue agregada correctamente, `false` en caso contrario.
     */
    fun agregarActividad(actividad: Actividad): Boolean

    /**
     * ## recuperarTodas
     *
     * Recupera todas las actividades registradas en el repositorio.
     *
     * @return Lista de todas las actividades.
     */
    fun recuperarTodas(): List<Actividad>

    /**
     * recuperarPorId
     *
     * Recupera una actividad específica según su Id.
     *
     * @param id Identificador único de la actividad.
     * @return La actividad correspondiente o `null` si no se encuentra.
     */
    fun recuperarPorId(id: Int): Actividad?

    /**
     * recuperarTareas
     *
     * Recupera únicamente las actividades que son tareas.
     *
     * @return Lista de tareas.
     */
    fun recuperarTareas(): List<Actividad>

    /**
     * ## actualizarActividad
     *
     * Actualiza una actividad existente en el repositorio.
     *
     * @param actividad Actividad actualizada.
     * @return `true` si se actualiza correctamente, `false` si no se pudo realizar.
     */
    fun actualizarActividad(actividad: Actividad): Boolean

    /**
     * ## borrarPorId
     *
     * Elimina una actividad del repositorio según su Id.
     *
     * @param id Identificador de la actividad a eliminar.
     * @return La actividad eliminada o `null` si no se encontró.
     */
    fun borrarPorId(id: Int): Actividad?

    /**
     * ## asignarUsuarioATarea
     *
     * Asigna un usuario a una tarea específica.
     *
     * @param idTarea Id de la tarea a la que se desea asignar el usuario.
     * @param usuario Usuario a asignar (puede ser `null` para desasignar).
     * @return `true` si la asignación fue exitosa, `false` en caso contrario.
     */
    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean

    /**
     * ## recuperarTareaPorUsuario
     *
     * Recupera todas las tareas asignadas a un usuario específico.
     *
     * @param idUsuario Id del usuario cuyas tareas se quieren recuperar.
     * @return Lista de tareas asignadas al usuario.
     */
    fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea>
}