
package datos.dao

import dominio.Evento
import java.io.File

class EventoDAO : IGenericoDAO<Evento> {
    private val carpeta = File("CsvFiles")
    private val archivo = File(carpeta, "eventos.csv")
    private val eventos = mutableListOf<Evento>()

    init {
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }
    }

    fun cargarEventosCsv() {
        if (!archivo.exists()) {
            archivo.writeText("id,descripcion,fechaRealizacion,ubicacion,etiquetas\n")
            return
        }

        eventos.clear()

        val lineas = archivo.readLines()
        lineas.drop(1).forEach { linea ->
            val partes = linea.split(",")
            if (partes.size >= 5) {
                val id = partes[0].toIntOrNull()
                val descripcion = partes[1].replace(";", ",")
                val fechaRealizacion = partes[2]
                val ubicacion = partes[3].replace(";", ",")
                val etiquetasStr = partes[4]

                if (id != null) {
                    val etiquetas = if (etiquetasStr.isBlank()) {
                        emptyList()
                    } else {
                        etiquetasStr.split(";").map { it.replace(";", ",") }
                    }

                    val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, etiquetas)
                    eventos.add(evento)
                }
            }
        }
    }

    fun guardarEventosCsv() {
        archivo.printWriter().use { out ->
            out.println("id,descripcion,fechaRealizacion,ubicacion,etiquetas")
            eventos.forEach { evento ->
                val descripcionFormateada = evento.descripcion.replace(",", ";")
                val ubicacionFormateada = evento.ubicacion.replace(",", ";")
                val etiquetas = evento.etiquetas.joinToString(";") { it.replace(",", ";") }

                val linea = listOf(
                    evento.id.toString(),
                    descripcionFormateada,
                    evento.fechaRealizacion,
                    ubicacionFormateada,
                    etiquetas
                ).joinToString(",")

                out.println(linea)
            }
        }
    }

    override fun create(t: Evento): Evento {
        eventos.add(t)
        guardarEventosCsv()
        return t
    }

    override fun read(): List<Evento> {
        return eventos.toList()
    }

    fun readById(id: Int): Evento? {
        return eventos.find { it.id == id }
    }

    override fun update(t: Evento): Boolean {
        val actual = eventos.find { it.id == t.id }
        return if (actual != null) {
            eventos.remove(actual)
            eventos.add(t)

            guardarEventosCsv()

            true
        } else {
            false
        }
    }

    override fun delete(id: Int): Evento? {
        val tarea = eventos.find { it.id == id }
        return if (tarea != null) {
            eventos.remove(tarea)

            guardarEventosCsv()

            tarea
        } else {
            null
        }
    }
}
