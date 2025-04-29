package aplicacion

import dominio.Actividad
import dominio.Usuario
import dominio.Tarea

interface IActividadService {
    fun crearTarea(descripcion: String)
    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String)
    fun obtenerActividades(): List<Actividad>
    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean
    fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea>
}
