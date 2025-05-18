package datos.repository

import dominio.Actividad
import dominio.Evento
import datos.dao.EventoDAO

class Repository(
    private val eventoDAO: EventoDAO
) : IRepository {
    init {
        cargarCsv("evento")
    }

    // Métodos Actividades
    override fun agregarActividad(actividad: Actividad): Boolean {
        return when (actividad) {
            is Evento -> eventoDAO.create(actividad).let { true }
            else -> false
        }
    }

    override fun recuperarActividadPorID(id: Int): Actividad? {
        return eventoDAO.readById(id)
    }

    override fun recuperarActividades(): List<Actividad> {
        return eventoDAO.read()
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        return when (actividad) {
            is Evento -> {
                eventoDAO.update(actividad)
                true
            }
            else -> false
        }
    }

    override fun eliminarActividad(id: Int): Actividad? {
        val evento = eventoDAO.readById(id)
        if (evento != null) {
            eventoDAO.delete(id)
            return evento
        }
        return null
    }

    // Métodos específicos - Evento
    override fun recuperarEventos() : List<Evento> {
        return eventoDAO.read()
    }

    // Métodos ficheros - CSV
    override fun cargarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "evento" -> eventoDAO.cargarEventosCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }

    override fun guardarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "evento" -> eventoDAO.guardarEventosCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }
}