package aplicacion

import dominio.Actividad
import dominio.Status
import dominio.Usuario
import dominio.Tarea

interface IActividadService {
    fun crearTarea(descripcion: String, etiquetas: List<String>)

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>)

    fun obtenerActividades(): List<Actividad>

    fun actualizarEstadoTarea(id:Int, nuevoEstado: Status) : Boolean

    fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean

    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean

    fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea>
}
