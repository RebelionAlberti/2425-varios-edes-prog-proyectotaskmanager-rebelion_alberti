package datos.repository

import dominio.Actividad
import dominio.Tarea
import dominio.Usuario
import datos.dao.TareaDAO

class Repository(
    private val tareaDAO: TareaDAO,
) : IRepository {
    init {
        cargarCsv("tarea")
    }

    // Métodos Actividades
    override fun agregarActividad(actividad: Actividad): Boolean {
        return when (actividad) {
            is Tarea -> tareaDAO.create(actividad).let { true }
            else -> false
        }
    }

    override fun recuperarActividadPorID(id: Int): Actividad? {
        return tareaDAO.readById(id)
    }

    override fun recuperarActividades(): List<Actividad> {
        return tareaDAO.read()
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        return when (actividad) {
            is Tarea -> {
                tareaDAO.update(actividad)
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

    // Métodos ficheros - CSV
    override fun cargarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "tarea" -> tareaDAO.cargarTareasCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }

    override fun guardarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "tarea" -> tareaDAO.guardarTareasCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }
}