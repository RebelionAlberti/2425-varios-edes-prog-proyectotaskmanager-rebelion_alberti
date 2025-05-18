package datos.dao

import dominio.Tarea
import dominio.Status
import dominio.Usuario
import java.io.File

class TareaDAO : IGenericoDAO<Tarea> {
    private val carpeta = File("CsvFiles")
    private val archivo = File(carpeta, "tareas.csv")
    private val tareas = mutableListOf<Tarea>()

    init {
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }
    }

    fun cargarTareasCsv() {
        if (!archivo.exists()) {
            archivo.writeText("id,descripcion,estado,asignado,etiquetas,fechaCreacion,esSubtarea,idTareaMadre\n")
            return
        }

        tareas.clear()

        val mapaTareas = mutableMapOf<Int, Tarea>()

        val lineas = archivo.readLines()
        lineas.drop(1).forEach { linea ->
            val partes = linea.split(",")
            if (partes.size >= 8) {
                val id = partes[0].toIntOrNull()
                val descripcion = partes[1].replace(";", ",")
                val estadoStr = partes[2]
                val asignadoStr = partes[3]
                val etiquetasStr = partes[4]

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

        tareas.addAll(mapaTareas.values.filter { it.tareaMadre == null })
    }

    fun guardarTareasCsv() {
        archivo.printWriter().use { out ->
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

            val tareasPrincipales = tareas.filter { it.tareaMadre == null }
            tareasPrincipales.forEach { tarea -> formatearTarea(tarea) }
        }
    }

    override fun create(t: Tarea): Tarea {
        tareas.add(t)
        guardarTareasCsv()
        return t
    }

    override fun read(): List<Tarea> {
        return tareas.toList()
    }

    fun readById(id: Int): Tarea? {
        return tareas.find { it.id == id }
    }

    override fun update(t: Tarea) : Boolean {
        val actual = tareas.find { it.id == t.id }
        return if (actual != null) {
            tareas.remove(actual)
            tareas.add(t)

            guardarTareasCsv()

            true
        } else {
            false
        }
    }

    override fun delete(id: Int): Tarea? {
        val tarea = tareas.find { it.id == id }
        return if (tarea != null) {
            tareas.remove(tarea)

            guardarTareasCsv()

            tarea
        } else {
            null
        }
    }

    fun asingarUsuarioATarea(id: Int, usuario: Usuario?): Boolean {
        val tarea = tareas.find { it.id == id }
        if (tarea is Tarea) {
            tarea.asignadoA = usuario
            return true
        }
        return false
    }

    fun recuperarTareasUsuario(idUsuario: Int) : List<Tarea> {
        return tareas
            .filter { it.asignadoA?.id == idUsuario }    }
}