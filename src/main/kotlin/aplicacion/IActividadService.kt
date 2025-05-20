package aplicacion

import dominio.Actividad
import dominio.Status
import dominio.Usuario
import dominio.Tarea
import dominio.RangoFecha

/**
 *## Interfaz actividadService
 *
 * Define la estructura del servicio para gestionar tanto actividades, como tareas y eventos.
 */

interface IActividadService {

    /**
     * # crearTarea
     *
     * Crea una nueva tarea con descripción y etiquetas.
     *
     * @param descripcion Descripción de la tarea.
     * @param etiquetas Lista de etiquetas asociadas a la tarea.
     */
    fun crearTarea(descripcion: String, etiquetas: List<String>)

    /**
     * ## crearEvento
     *
     * Crea un nuevo evento con descripción, fecha, ubicación y etiquetas.
     *
     * @param descripcion Descripción del evento.
     * @param fechaRealizacion Fecha en formato String.
     * @param ubicacion Lugar del evento.
     * @param etiquetas Lista de etiquetas asociadas.
     */
    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>)


    /**
     * ## obtenerActividades
     *
     * Obtiene todas las actividades registradas.
     *
     * @return Lista de actividades.
     */
    fun obtenerActividades(): List<Actividad>



    /**
     * ## actualizarEstadoTarea
     *
     * Actualiza el estado de una tarea.
     *
     * @param id ID de la tarea.
     * @param nuevoEstado Nuevo estado a aplicar.
     * @return `true` si se actualizó correctamente, `false` si no era una tarea.
     */
    fun actualizarEstadoTarea(id:Int, nuevoEstado: Status) : Boolean


    /**
     * ## actuaizarEstadoSubtareas
     *
     * Actualiza el estado de una subtarea.
     *
     * @param idSubtarea ID de la subtarea.
     * @param nuevoEstado Estado a aplicar.
     * @return `true` si se aplicó correctamente, `false` en caso contrario.
     */
    fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean


    /**
     * ## asignarUsuarioATarea
     *
     * Asigna un usuario a una tarea específica.
     *
     * @param idTarea ID de la tarea.
     * @param usuario Usuario a asignar, o null para desasignar.
     * @return `true` si la asignación fue exitosa.
     */
    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean


    /**
     *## obtenerTareasPorUsuario
     *
     * Obtiene todas las tareas asignadas a un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de tareas.
     */
    fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea>


    /**
     * ## filtrarPorTipo
     *
     * Filtra las actividades por tipo.
     *
     * @param tipo Tipo de actividad.
     * @return Lista filtrada de actividades.
     */
    fun filtrarPorTipo(tipo: String): List<Actividad>


    /**
     * ## filtrarPorEstado
     *
     * Filtra las actividades por estado (solo aplica a tareas).
     *
     * @param estado Estado a filtrar.
     * @return Lista filtrada de actividades.
     */
    fun filtrarPorEstado(estado: Status): List<Actividad>


    /**
     * ## filtrarPorFecha
     *
     * Filtra actividades dentro de un rango de fechas de creación.
     *
     * @param rango Rango de fechas.
     * @return Lista de actividades en ese rango.
     */
    fun filtrarPorFecha(rango: RangoFecha): List<Actividad>



    /**
     * ## filtrarPorEtiquetas
     *
     * Filtra actividades que contengan al menos una de las etiquetas dadas.
     *
     * @param etiquetas Lista de etiquetas.
     * @return Actividades que coincidan.
     */
    fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad>


    /**
     * ## eliminarActividadPorId
     *
     * Elimina una actividad por su ID.
     *
     * @param id ID de la actividad.
     * @return La actividad eliminada, o null si no se encontró.
     */
    fun eliminarActividadPorId(id: Int): Actividad?


    /**
     * ## agregarSubtarea
     *
     * Agrega una subtarea a una tarea principal.
     *
     * @param idTareaPrincipal ID de la tarea principal.
     * @param descripcionSubtarea Descripción de la nueva subtarea.
     * @return `true` si se agregó correctamente.
     */
    fun agregarSubtarea(idTareaPrincipal: Int, descripcionSubtarea: String): Boolean

}
