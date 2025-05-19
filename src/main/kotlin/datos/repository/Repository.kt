package datos.repository

import datos.dao.EventoDAO
import datos.dao.TareaDAO
import datos.dao.UsuarioDAO
import dominio.Actividad
import dominio.Evento
import dominio.Tarea
import dominio.Usuario

class Repository(
    private val tareaDAO: TareaDAO,
    private val eventoDAO: EventoDAO,
    private val usuarioDAO: UsuarioDAO
) : IRepository {
    init {
        cargarCsv("tarea")
        cargarCsv("evento")
        cargarCsv("usuario")
    }

    // Métodos Actividades
    override fun agregarActividad(actividad: Actividad): Boolean {
        return when (actividad) {
            is Tarea -> tareaDAO.create(actividad).let { true }
            is Evento -> eventoDAO.create(actividad).let { true }
            else -> false
        }
    }

    override fun recuperarActividadPorID(id: Int): Actividad? {
        return tareaDAO.readById(id) ?: eventoDAO.readById(id)
    }

    override fun recuperarActividades(): List<Actividad> {
        return tareaDAO.read() + eventoDAO.read()
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        return when (actividad) {
            is Tarea -> {
                tareaDAO.update(actividad)
                true
            }
            is Evento -> {
                eventoDAO.update(actividad)
                true
            }
            else -> false
        }
    }

    override fun eliminarActividad(id: Int): Actividad? {
        val tarea = tareaDAO.readById(id)
        if (tarea != null) {
            tareaDAO.delete(id)
            return tarea
        }
        val evento = eventoDAO.readById(id)
        if (evento != null) {
            eventoDAO.delete(id)
            return evento
        }
        return null
    }

    // Métodos específicos - Tarea
    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        return tareaDAO.asingarUsuarioATarea(idTarea, usuario)
    }

    override fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return tareaDAO.recuperarTareasUsuario(idUsuario)
    }

    override fun recuperarTareas(): List<Tarea> {
        return tareaDAO.read()
    }

    // Métodos específicos - Evento
    override fun recuperarEventos(): List<Evento> {
        return eventoDAO.read()
    }

    // Métodos Usuarios
    override fun agregarUsuario(usuario: Usuario): Boolean {
        return usuarioDAO.create(usuario).let { true }
    }

    override fun recuperarUsuario(): List<Usuario> {
        return usuarioDAO.read()
    }

    override fun recuperarUsuarioPorId(id: Int): Usuario? {
        return usuarioDAO.readById(id)
    }

    override fun actualizarUsuario(usuario: Usuario): Boolean {
        return usuarioDAO.update(usuario)
    }

    override fun eliminarUsuario(id: Int): Usuario? {
        return usuarioDAO.delete(id)
    }

    // Métodos ficheros - CSV
    override fun cargarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "tarea" -> tareaDAO.cargarTareasCsv()
            "evento" -> eventoDAO.cargarEventosCsv()
            "usuario" -> usuarioDAO.cargarUsuariosCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }

    override fun guardarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "tarea" -> tareaDAO.guardarTareasCsv()
            "evento" -> eventoDAO.guardarEventosCsv()
            "usuario" -> usuarioDAO.guardarUsuariosCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }
}
