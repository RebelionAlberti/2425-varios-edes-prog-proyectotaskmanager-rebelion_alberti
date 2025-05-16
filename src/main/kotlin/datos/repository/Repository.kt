package datos.repository

import dominio.Actividad
import dominio.Evento
import dominio.Tarea
import dominio.Usuario
import datos.dao.EventoDAO
import datos.dao.TareaDAO

class Repository(
    private val tareaDAO: TareaDAO,
    private val eventoDAO: EventoDAO
) : IRepository {
    init {
        cargarCsv("tarea")
        cargarCsv("evento")
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
    override fun recuperarEventos() : List<Evento> {
        return eventoDAO.read()
    }

    // Métodos ficheros - CSV
    override fun cargarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "tarea" -> tareaDAO.cargarTareasCsv()
            "evento" -> eventoDAO.cargarEventosCsv()
            else -> throw IllegalArgumentException("Tipo no soportado: $tipo")
        }
    }

    override fun guardarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "tarea" -> tareaDAO.guardarTareasCsv()
            "evento" -> eventoDAO.guardarEventosCsv()
            else -> throw IllegalArgumentException("Tipo no soportado: $tipo")
        }
    }
}