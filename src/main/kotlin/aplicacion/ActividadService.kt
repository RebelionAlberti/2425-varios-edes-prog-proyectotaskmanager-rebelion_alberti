package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Status
import dominio.Usuario
import dominio.RangoFecha
import java.text.SimpleDateFormat

class ActividadService(
    private val repositorio: IActividadRepository
) : IActividadService {

    // --- Factory Methods para crear actividades ---
    private fun crearTareaFactory(descripcion: String, etiquetas: List<String>): Tarea =
        Tarea.crearInstancia(descripcion, etiquetas)

    private fun crearEventoFactory(
        descripcion: String,
        fechaRealizacion: String,
        ubicacion: String,
        etiquetas: List<String>
    ): Evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, etiquetas)

    // --- Command para cambiar estado ---
    private fun ejecutarCambioEstado(tarea: Tarea, nuevoEstado: Status): Boolean {
        if (nuevoEstado == Status.CERRADA && tarea.puedeFinalizar()) {
            println("No se puede cerrar esta tarea porque tiene subtareas abiertas.")
            return false
        }
        tarea.estado = nuevoEstado
        tarea.agregarRegistro("Estado cambiado a $nuevoEstado")
        tarea.tareaMadre?.cerrarPorSubtareasFinalizadas()
        return true
    }

    override fun crearTarea(descripcion: String, etiquetas: List<String>) {
        val tarea = crearTareaFactory(descripcion, etiquetas)
        repositorio.agregarActividad(tarea)
    }

    override fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>) {
        val evento = crearEventoFactory(descripcion, fechaRealizacion, ubicacion, etiquetas)
        repositorio.agregarActividad(evento)
    }

    override fun obtenerActividades(): List<Actividad> = repositorio.recuperarTodas()

    override fun actualizarEstadoTarea(id: Int, nuevoEstado: Status): Boolean {
        val actividad = repositorio.recuperarPorId(id)
        if (actividad is Tarea) {
            val exito = ejecutarCambioEstado(actividad, nuevoEstado)
            if (exito) repositorio.actualizarActividad(actividad)
            return exito
        }
        return false
    }

    override fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean {
        val actividad = repositorio.recuperarPorId(idSubtarea)
        if (actividad is Tarea) {
            val exito = ejecutarCambioEstado(actividad, nuevoEstado)
            if (exito) repositorio.actualizarActividad(actividad)
            return exito
        }
        return false
    }

    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        val exito = repositorio.asignarUsuarioATarea(idTarea, usuario)
        if (exito) {
            val tarea = repositorio.recuperarPorId(idTarea)
            if (tarea is Tarea) {
                val nombreUsuario = usuario?.nombre ?: "Sin asignar"
                tarea.agregarRegistro("Tarea asignada a: $nombreUsuario")
                repositorio.actualizarActividad(tarea)
            }
        }
        return exito
    }

    override fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea> =
        repositorio.recuperarTareasPorUsuario(idUsuario)

    override fun filtrarPorTipo(tipo: String): List<Actividad> =
        repositorio.recuperarTodas().filter {
            when (tipo) {
                "TAREA" -> it is Tarea
                "EVENTO" -> it is Evento
                else -> false
            }
        }

    override fun filtrarPorEstado(estado: Status): List<Actividad> =
        repositorio.recuperarTodas().filter {
            it is Tarea && it.estado == estado
        }

    override fun filtrarPorFecha(rango: RangoFecha): List<Actividad> =
        repositorio.recuperarTodas().filter {
            val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(it.fechaCreacion)
            rango.estaDentroDelRango(fechaCreacion)
        }

    override fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad> =
        repositorio.recuperarTodas().filter { actividad ->
            etiquetas.any { etiqueta -> actividad.etiquetas.contains(etiqueta) }
        }

    override fun eliminarActividadPorId(id: Int): Actividad? =
        repositorio.borrarPorId(id)

    override fun agregarSubtarea(idTareaPrincipal: Int, descripcionSubtarea: String): Boolean {
        val tareaPrincipal = repositorio.recuperarPorId(idTareaPrincipal)
        if (tareaPrincipal is Tarea) {
            val esSubtarea = repositorio.recuperarTodas().any {
                it is Tarea && it.subtareas.contains(tareaPrincipal)
            }
            if (esSubtarea) return false

            val subtarea = crearTareaFactory(descripcionSubtarea, listOf())
            val fueGuardada = repositorio.agregarActividad(subtarea)

            if (fueGuardada && tareaPrincipal.agregarSubtarea(subtarea)) {
                return repositorio.actualizarActividad(tareaPrincipal)
            }
        }
        return false
    }
}
