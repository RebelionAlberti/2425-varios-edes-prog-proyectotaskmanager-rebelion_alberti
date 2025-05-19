package datos.repository

import dominio.Actividad
import dominio.Evento
import dominio.Tarea
import dominio.Usuario

interface IRepository {
    // Métodos Actividades
    fun agregarActividad(actividad: Actividad): Boolean
    fun recuperarActividadPorID(id: Int): Actividad?
    fun recuperarActividades(): List<Actividad>
    fun actualizarActividad(actividad: Actividad): Boolean
    fun eliminarActividad(id: Int): Actividad?

    // Métodos específicos - Tarea
    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean
    fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea>
    fun recuperarTareas(): List<Tarea>

    // Métodos específicos - Evento
    fun recuperarEventos(): List<Evento>

    // Métodos Usuarios
    fun agregarUsuario(usuario: Usuario): Boolean
    fun recuperarUsuario(): List<Usuario>
    fun recuperarUsuarioPorId(id: Int): Usuario?
    fun actualizarUsuario(usuario: Usuario): Boolean
    fun eliminarUsuario(id: Int): Usuario?

    // Métodos ficheros - CSV
    fun cargarCsv(tipo: String)
    fun guardarCsv(tipo: String)
}
