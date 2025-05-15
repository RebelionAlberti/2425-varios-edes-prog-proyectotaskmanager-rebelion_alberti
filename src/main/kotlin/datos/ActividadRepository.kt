package datos

import dominio.Actividad
import dominio.Status
import dominio.Tarea
import dominio.Usuario
import java.io.File

class ActividadRepository : IActividadRepository {

    private val actividades = mutableListOf<Actividad>()

    init {
        cargarActividadesCsv()
    }

    override fun agregarActividad(actividad: Actividad): Boolean {
        var guardado = false
        if (actividades.find { it.id == actividad.id } == null) {
            actividades.add(actividad)
            guardado = true

            guardarActividadesCsv()
        }
        return guardado
    }

    override fun recuperarTodas(): List<Actividad> {
        return actividades.toList()
    }

    override fun recuperarPorId(id: Int): Actividad? {
        var actividad: Actividad? = null
        val actividadesId = actividades.filter { it.id == id }
        if (actividadesId.isNotEmpty()) {
            actividad = actividadesId[0]
        }
        return actividad
    }

    override fun recuperarTareas(): List<Actividad> {
        TODO("Not yet implemented")
    }

    override fun actualizarActividad(actividad: Actividad): Boolean {
        val actual = actividades.find { it.id == actividad.id }
        return if (actual != null) {
            actividades.remove(actual)
            actividades.add(actividad)

            guardarActividadesCsv()

            true
        } else {
            false
        }
    }

    override fun borrarPorId(id: Int): Actividad? {
        val actividad = actividades.find { it.id == id }
        return if (actividad != null) {
            actividades.remove(actividad)

            guardarActividadesCsv()

            actividad
        } else {
            null
        }
    }

    override fun asignarUsuarioATarea(idTarea: Int, usuario: Usuario?): Boolean {
        val actividad = actividades.find { it.id == idTarea }
        if (actividad is Tarea) {
            actividad.asignadoA = usuario
            return true
        }
        return false
    }

    override fun recuperarTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return actividades
            .filterIsInstance<Tarea>()
            .filter { it.asignadoA?.id == idUsuario }
    }

    fun cargarActividadesCsv() {
        val archivo = File("tareas.csv")
        if (!archivo.exists()) {
            archivo.writeText("id,descripcion,estado,asignado,etiquetas,fechaCreacion,esSubtarea,idTareaMadre\n")
            return
        }

        actividades.clear()

        val mapaTareas = mutableMapOf<Int, Tarea>()
        var maxId = 0

        val lineas = archivo.readLines()
        lineas.drop(1).forEach { linea ->
            val partes = linea.split(",")
            if (partes.size >= 8) {
                val id = partes[0].toIntOrNull()
                val descripcion = partes[1].replace(";", ",")
                val estadoStr = partes[2]
                val asignadoStr = partes[3]
                val etiquetasStr = partes[4]
                val fechaCreacion = partes[5]
                val esSubtarea = partes[6].toBoolean()
                val idTareaMadre = partes[7].takeIf { it.isNotBlank() }?.toInt()

                if (id != null) {
                    val estado = when (estadoStr) {
                        "Abierta" -> Status.ABIERTA
                        "En progreso" -> Status.EN_PROGRESO
                        "Cerrada" -> Status.CERRADA
                        else -> Status.ABIERTA
                    }

                    val etiquetas = if (etiquetasStr.isBlank()) emptyList()
                    else etiquetasStr.split(";").map { it.replace(";", ",") }

                    val asignado = if (asignadoStr.isBlank()) null else Usuario.crear(nombre = asignadoStr)

                    val tarea = Tarea.crearInstancia(descripcion, etiquetas).apply {
                        this.estado = estado
                        this.asignadoA = asignado
                    }

                    mapaTareas[id] = tarea
                    if (id > maxId) maxId = id
                }
            }
        }

        lineas.drop(1).forEach { linea ->
            val partes = linea.split(",")
            if (partes.size >= 8) {
                val id = partes[0].toIntOrNull()
                val esSubtarea = partes[6].toBoolean()
                val idTareaMadre = partes[7].takeIf { it.isNotBlank() }?.toInt()

                if (id != null && esSubtarea && idTareaMadre != null) {
                    val subtarea = mapaTareas[id]
                    val madre = mapaTareas[idTareaMadre]
                    if (subtarea != null && madre != null) {
                        madre.agregarSubtarea(subtarea)
                        subtarea.tareaMadre = madre
                    }
                }
            }
        }

        actividades.addAll(mapaTareas.values.filter { it.tareaMadre == null })

    }

    fun guardarActividadesCsv(ruta: String = "tareas.csv") {
        val file = File(ruta)
        file.printWriter().use { out ->
            out.println("id,descripcion,estado,asignado,etiquetas,fechaCreacion,esSubtarea,idTareaMadre")

            fun formatearTarea(tarea: Tarea, esSubtarea: Boolean = false, idTareaMadre: Int? = null) {
                val descripcionFormateada = tarea.descripcion.replace(",", ";")
                val asignado = tarea.asignadoA?.nombre ?: ""
                val etiquetas = tarea.etiquetas.joinToString(";") { it.replace(",", ";") }
                val linea = listOf(
                    tarea.id.toString(),
                    descripcionFormateada,
                    tarea.estado.descripcion,
                    asignado,
                    etiquetas,
                    tarea.fechaCreacion,
                    esSubtarea.toString(),
                    idTareaMadre?.toString() ?: ""
                ).joinToString(",")
                out.println(linea)

                tarea.subtareas.forEach { subtarea ->
                    formatearTarea(subtarea, esSubtarea = true, idTareaMadre = tarea.id)
                }
            }

            val tareasPrincipales = actividades.filterIsInstance<Tarea>().filter { tarea ->
                actividades.none { it is Tarea && it.subtareas.contains(tarea) }
            }

            tareasPrincipales.forEach { tarea ->
                formatearTarea(tarea)
            }

        }
    }
}