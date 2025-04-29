package datos

import dominio.Actividad

interface IActividadRepository {
    fun agregarActividad(actividad: Actividad): Boolean
    fun recuperarTodas(): List<Actividad>
    fun recuperarPorId(id: Int): Actividad?
    fun recuperarTareas(): List<Actividad>
    fun actualizarActividad(actividad: Actividad) : Boolean
    fun borrarPorId(id: Int): Actividad?
}
