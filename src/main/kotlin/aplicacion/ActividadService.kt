package aplicacion

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Status
import dominio.Usuario
import dominio.RangoFecha
import java.text.SimpleDateFormat


/**
 * # ActividadService
 *
 * Servicio encargado de gestionar el CRUD de nuestro proyecto; la creación, la actualización,
 * el filtrado de actividades...
 *
 *  @property repositorio Repositorio para acceso y gestión de actividades.
 */


class ActividadService(private val repositorio: IActividadRepository) : IActividadService {
    /**
     * ## CrearTarea
     *
     *  Crea una nueva tarea con la descripción y las etiquetas dadas.
     *
     * @param descripcion
     * @param etiquetas
     */
    override fun crearTarea(descripcion: String, etiquetas: List<String>) {
        val tarea = Tarea.crearInstancia(descripcion, etiquetas)
        repositorio.agregarActividad(tarea)
    }

    /**
     * ## CrearEvento
     *
     * Crea un evento con la descripción, fecha, ubicación y etiquetas proporcionadas.
     *
     * @param descripcion descripción del evento.
     * @param fechaRealizacion Fecha en la que ocurrirá el evento, en formato "dd/MM/yyyy".
     * @param ubicacion Lugar donde tendrá lugar el evento.
     * @param etiquetas Lista de etiquetas sobre el evento.
     */
    override fun crearEvento(descripcion: String, fechaRealizacion: String, ubicacion: String, etiquetas: List<String>) {
        val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, etiquetas)
        repositorio.agregarActividad(evento)
    }

    /**
     * ## ObtenerActividades
     *
     * Obtiene todas las actividades almacenadas en el repositorio.
     *
     * @return Lista de actividades que incluye tareas y eventos.
     */
    override fun obtenerActividades(): List<Actividad> {
        return repositorio.recuperarTodas()
    }

    /**
     * ## ActualizarEstadoTarea
     *
     * Actualiza el estado de una tarea identificada por su ID.
     *
     * @param id Identificador único de la tarea a actualizar.
     * @param nuevoEstado Nuevo estado que se asignará a la tarea.
     * @return `true` si la tarea fue encontrada y actualizada exitosamente, `false` en caso contrario.
     */
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

    /**
     * ## ActualizarEstadoSubtareas
     *
     * Actualiza el estado de una subtarea identificada por su ID.
     *
     * Si se intenta cerrar la subtarea, comprueba que no tenga subtareas abiertas antes de cambiar el estado.
     *
     * @param idSubtarea Identificador único de la subtarea a actualizar.
     * @param nuevoEstado Nuevo estado que se asignará a la subtarea.
     * @return `true` si la subtarea fue encontrada y actualizada exitosamente; `false` si no se encontró o si no se
     * puede cerrar por tener subtareas abiertas.
     */
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


    /**
     * ## AsignarUsuarioATarea
     *
     * Asigna un usuario a una tarea específica.
     *
     * @param idTarea Identificador único de la tarea a la que se asignará el usuario.
     * @param usuario Usuario que se asignará a la tarea. Puede ser `null` para desasignar.
     * @return `true` si la asignación fue exitosa, `false` en caso contrario.
     */
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

    /**
     * ## ObtenerTareasPorUsuario
     * Obtiene todas las tareas asignadas a un usuario específico.
     *
     * @param idUsuario Identificador único del usuario.
     * @return Lista de tareas asignadas al usuario.
     */
    override fun obtenerTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return repositorio.recuperarTareasPorUsuario(idUsuario)
    }

    /**
     * ## FiltrarPorTipo
     *
     * Filtra las actividades según su tipo.
     *
     * @param tipo Tipo de actividad a filtrar. Valores posibles: "TAREA" o "EVENTO".
     * @return Lista de actividades que coinciden con el tipo especificado.
     */
    override fun filtrarPorTipo(tipo: String): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            when (tipo) {
                "TAREA" -> it is Tarea
                "EVENTO" -> it is Evento
                else -> false
            }
        }
    }

    /**
     * ## FiltrarPorEstado
     *
     * Filtra las tareas según su estado.
     *
     * @param estado Estado en el que se encuentra dicha tarea.
     * @return Lista de tareas que se encuentren en el estado propuesto.
     */
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

    /**
     * ## FiltrarPorFecha
     *
     * Filtra las actividades según su fecha de creación y el rango solicitado.
     *
     * @param rango Objeto que representa el rango de fechas para filtrar las actividades.
     * @return Lista de actividades cuya fecha de creación está dentro del rango especificado.
     */
    override fun filtrarPorFecha(rango: RangoFecha): List<Actividad> {
        return repositorio.recuperarTodas().filter {
            val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(it.fechaCreacion)
            rango.estaDentroDelRango(fechaCreacion)
        }
    }

    /**
     * ## FiltrarPorEtiquetas
     *
     * Filtra las actividades que contienen al menos una de las etiquetas especificadas.
     *
     * @param etiquetas Lista de etiquetas usadas para filtrar las actividades.
     * @return Lista de actividades que contienen alguna de las etiquetas propuestas.
     */
    override fun filtrarPorEtiquetas(etiquetas: List<String>): List<Actividad> {
        return repositorio.recuperarTodas().filter { actividad ->
            etiquetas.any { etiqueta -> actividad.etiquetas.contains(etiqueta) }
        }
    }

    /**
     * ## EliminarActividadPorId
     *
     * Elimina una actividad buscándola por su id.
     *
     * @param id Identificador único de la actividad a eliminar.
     * @return La actividad eliminada si existía, o null si no se encontró.
     */
    override fun eliminarActividadPorId(id: Int): Actividad? {
        return repositorio.borrarPorId(id)
    }

    /**
     * ## AgregarSubtarea
     *
     * Agrega una subtarea a una tarea principal existente.
     *
     * No se permite agregar subtareas a tareas que ya son subtareas.
     *
     * @param idTareaPrincipal ID de la tarea principal a la que se desea agregar la subtarea.
     * @param descripcionSubtarea Descripción de la nueva subtarea.
     * @return `true` si la subtarea fue agregada correctamente y actualizada en el repositorio, `false` en caso contrario.
     */
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
                return repositorio.actualizarActividad(tareaPrincipal)
            }
        }
        return false
    }

}