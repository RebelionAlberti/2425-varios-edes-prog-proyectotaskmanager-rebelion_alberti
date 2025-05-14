package aplicacion

import dominio.Actividad
import dominio.Status
import dominio.Usuario
import dominio.Tarea
import dominio.RangoFecha

interface IActividadService {
    fun crearTarea(descripcion: String, etiquetas: List<String>) : Boolean

    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>)

    fun obtenerActividades(): List<Actividad>

    fun actualizarEstadoTarea(id:Int, nuevoEstado: Status) : Boolean

    fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean

    fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean

    fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea>

    fun filtrarPorTipo(tipo: String): List<Actividad>

    fun filtrarPorEstado(estado: Status): List<Actividad>

    fun filtrarPorFecha(rango: RangoFecha): List<Actividad>

    fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad>

    fun eliminarActividadPorId(id: Int): Actividad?

    fun agregarSubtarea(idTareaPrincipal: Int, descripcionSubtarea: String): Boolean

}
