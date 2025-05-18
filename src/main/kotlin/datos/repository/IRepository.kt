package datos.repository

import dominio.Actividad
import dominio.Evento

interface IRepository {
    // Métodos Actividades
    fun agregarActividad(actividad: Actividad) : Boolean
    fun recuperarActividadPorID(id: Int) : Actividad?
    fun recuperarActividades() : List<Actividad>
    fun actualizarActividad(actividad: Actividad) : Boolean
    fun eliminarActividad(id: Int) : Actividad?

    // Métodos específicos - Evento
    fun recuperarEventos() : List<Evento>

    // Métodos ficheros - CSV
    fun cargarCsv(tipo: String)
    fun guardarCsv(tipo: String)
}