// archivo: FactoriesYCommands.kt
package servicio

import dominio.Tarea
import dominio.Evento
import dominio.Status

// Factory para crear tareas y eventos
interface ActividadFactory {
    fun crearTarea(descripcion: String, etiquetas: List<String>): Tarea
    fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>): Evento
}

class ActividadFactoryImpl : ActividadFactory {
    override fun crearTarea(descripcion: String, etiquetas: List<String>) =
        Tarea.crearInstancia(descripcion, etiquetas)

    override fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>) =
        Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, etiquetas)
}

// Command para cambiar estado
interface CambiarEstadoCommand {
    fun ejecutar(): Boolean
}

class CambiarEstadoCommandImpl(
    private val tarea: Tarea,
    private val nuevoEstado: Status
) : CambiarEstadoCommand {
    override fun ejecutar(): Boolean {
        if (nuevoEstado == Status.CERRADA && !tarea.puedeFinalizar()) {
            println("No se puede cerrar esta tarea porque tiene subtareas abiertas.")
            return false
        }
        tarea.estado = nuevoEstado
        tarea.agregarRegistro("Estado cambiado a $nuevoEstado")
        tarea.tareaMadre?.cerrarPorSubtareasFinalizadas()
        return true
    }
}

// Factory para crear comandos
interface CambiarEstadoCommandFactory {
    fun crear(tarea: Tarea, nuevoEstado: Status): CambiarEstadoCommand
}

class CambiarEstadoCommandFactoryImpl : CambiarEstadoCommandFactory {
    override fun crear(tarea: Tarea, nuevoEstado: Status) =
        CambiarEstadoCommandImpl(tarea, nuevoEstado)
}
