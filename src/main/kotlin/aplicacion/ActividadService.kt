package aplicacion

import datos.ActividadRepository
import datos.IActividadRepository
import dominio.Actividad
import dominio.Evento
import dominio.RangoFecha
import dominio.Status
import dominio.Tarea
import dominio.Usuario
import java.text.SimpleDateFormat

class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
    override fun crearTarea(descripcion: String, etiquetas: List<String>) {
        val tarea = Tarea.crearInstancia(descripcion, etiquetas)
        repositorio.agregarActividad(tarea)
    }

    override fun crearEvento(
        descripcion: String,
        fechaRealizacion: String,
        ubicacion: String,
        etiquetas: List<String>
    ) {
        val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, etiquetas)
        repositorio.agregarActividad(evento)
    }

    override fun obtenerActividades(): List<Actividad> {
        return repositorio.recuperarTodas()
    }

    override fun actualizarEstadoTarea(id: Int, nuevoEstado: Status): Boolean {
        val tarea = repositorio.recuperarPorId(id)

        return if (tarea is Tarea) {
            tarea.estado = nuevoEstado
            tarea.agregarRegistro("Estado cambiado a $nuevoEstado")
            repositorio.actualizarActividad(tarea)
            true
        } else {
            false
        }
    }

    override fun actualizarEstadoSubtareas(idSubtarea: Int, nuevoEstado: Status): Boolean {
        val tarea = repositorio.recuperarPorId(idSubtarea)

        if (tarea is Tarea) {
            if (nuevoEstado == Status.CERRADA && !tarea.puedeFinalizar()) {
                println("No se puede cerrar esta tarea porque tiene subtareas abiertas.")
                return false
            }
            tarea.estado = nuevoEstado
            tarea.agregarRegistro("Estado de subtarea cambiado a $nuevoEstado")
            repositorio.actualizarActividad(tarea)

            tarea.tareaMadre?.cerrarPorSubtareasFinalizadas()
            return true
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

                tarea.subtareas.forEach { subtarea ->
                    repositorio.asignarUsuarioATarea(subtarea.id, usuario)
                    subtarea.agregarRegistro(
                        "Usuario asignado automáticamente desde tarea madre ID ${tarea.id}"
                    )
                    repositorio.actualizarActividad(subtarea)
                }

                tarea.tareaMadre?.let { madre ->
                    if (usuario != null && madre.asignadoA == null) {
                        val madreEnRepo = repositorio.recuperarPorId(madre.id)
                        if (madreEnRepo is Tarea) {
                            madreEnRepo.asignadoA = usuario
                            madreEnRepo.agregarRegistro(
                                "Usuario asignado automáticamente desde subtarea ID ${tarea.id}"
                            )
                            repositorio.actualizarActividad(madreEnRepo)
                        }
                    }
                }

                if (usuario != null) {
                    (repositorio as? ActividadRepository)?.guardarActividadesCsv()
                }
            }
        }
        return exito
    }

    override fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return repositorio.recuperarTareasPorUsuario(idUsuario)
    }

    override fun filtrarPorTipo(tipo: String): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            when (tipo) {
                "TAREA" -> it is Tarea
                "EVENTO" -> it is Evento
                else -> false
            }
        }
    }

    override fun filtrarPorEstado(estado: Status): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            when (estado) {
                Status.ABIERTA -> it is Tarea && it.estado == Status.ABIERTA
                Status.EN_PROGRESO -> it is Tarea && it.estado == Status.EN_PROGRESO
                Status.CERRADA -> it is Tarea && it.estado == Status.CERRADA
            }
        }
    }

    override fun filtrarPorFecha(rango: RangoFecha): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(it.fechaCreacion)
            rango.estaDentroDelRango(fechaCreacion)
        }
    }

    override fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad> {
        return repositorio.recuperarTodas().filter { actividad ->
            etiquetas.any { etiqueta -> actividad.etiquetas.contains(etiqueta) }
        }
    }

    override fun eliminarActividadPorId(id: Int): Actividad? {
        return repositorio.borrarPorId(id)
    }

    override fun agregarSubtarea(idTareaPrincipal: Int, descripcionSubtarea: String): Boolean {
        val tareaPrincipal = repositorio.recuperarPorId(idTareaPrincipal)

        if (tareaPrincipal is Tarea) {
            val esSubtarea = repositorio.recuperarTodas().any {
                it is Tarea && it.subtareas.contains(tareaPrincipal)
            }

            if (esSubtarea) {
                return false
            }

            val subtarea = Tarea.crearInstancia(descripcionSubtarea, listOf())

            val fueGuardada = repositorio.agregarActividad(subtarea)

            if (fueGuardada && tareaPrincipal.agregarSubtarea(subtarea)) {
                val exito = repositorio.actualizarActividad(tareaPrincipal)

                if (repositorio is ActividadRepository) {
                    repositorio.guardarActividadesCsv()
                }

                return exito
            }
        }

        return false
    }
}
