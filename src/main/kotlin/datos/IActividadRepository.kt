package datos

import dominio.Actividad
import dominio.Usuario
import dominio.Tarea

interface IActividadRepository {
    fun agregarActividad(actividad: Actividad): Boolean
    fun recuperarTodas(): List<Actividad>
    fun recuperarPorId(id: Int): Actividad?
    fun recuperarTareas(): List<Actividad>
    fun actualizarActividad(actividad: Actividad) : Boolean
    fun borrarPorId(id: Int): Actividad?
    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean
    fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea>
}