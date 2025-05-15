package aplicacion

import datos.IActividadRepository
import datos.ActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Status
import dominio.Usuario
import dominio.RangoFecha
import java.text.SimpleDateFormat

class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
    override fun crearTarea(descripcion: String, etiquetas: List<String>) {
        val tarea = Tarea.crearInstancia(descripcion, etiquetas)
        repositorio.agregarActividad(tarea)
    }

    override fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>) {
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
                else -> false
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
                it is Tarea && (it as Tarea).subtareas.contains(tareaPrincipal)
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