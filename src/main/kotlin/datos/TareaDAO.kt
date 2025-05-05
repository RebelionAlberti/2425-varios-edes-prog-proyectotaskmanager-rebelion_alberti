package datos

import dominio.Status
import dominio.Tarea
import dominio.Usuario
import java.sql.ResultSet
import org.practicatrim2.presentacion.datos.ITareaRepository

class TareaDAO(private val dbConfig: DBConfig) : ITareaRepository {

    override fun crearTarea(tarea: Tarea): Boolean {
        val sql = """
            INSERT INTO tarea (descripcion, fecha_creacion, estado, etiquetas, asignado_a, tarea_madre)
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        dbConfig.obtenerConexion().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, tarea.descripcion)
                stmt.setString(2, tarea.fechaCreacion)
                stmt.setString(3, tarea.estado.name)
                stmt.setString(4, tarea.etiquetas.joinToString(";"))
                stmt.setObject(5, tarea.asignadoA?.id)
                stmt.setObject(6, tarea.tareaMadre?.id)

                return stmt.executeUpdate() > 0
            }
        }
    }

    override fun recuperarTareasId(id: Int): Tarea? {
        val todas = recuperarTareas()
        return todas.find { it.id == id }
    }


    override fun actualizarTarea(tarea: Tarea): Boolean {
        val sql = """
            UPDATE tarea SET descripcion = ?, estado = ?, etiquetas = ?, asignado_a = ?, tarea_madre = ?
            WHERE id = ?
        """.trimIndent()

        dbConfig.obtenerConexion().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, tarea.descripcion)
                stmt.setString(2, tarea.estado.name)
                stmt.setString(3, tarea.etiquetas.joinToString(";"))
                stmt.setObject(4, tarea.asignadoA?.id)
                stmt.setObject(5, tarea.tareaMadre?.id)
                stmt.setInt(6, tarea.id)

                return stmt.executeUpdate() > 0
            }
        }
    }

    override fun borrarTarea(id: Int): Boolean {
        val sql = "DELETE FROM tarea WHERE id = ?"

        dbConfig.obtenerConexion().use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                return stmt.executeUpdate() > 0
            }
        }
    }

    override fun recuperarTareas(): List<Tarea> {
        val sql = "SELECT * FROM tarea"
        val mapaTareas = mutableMapOf<Int, Tarea>()

        dbConfig.obtenerConexion().use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery(sql)
                while (rs.next()) {
                    val tarea = filaATarea(rs)
                    mapaTareas[tarea.id] = tarea
                }
            }
        }

        for (tarea in mapaTareas.values) {
            val idMadre = tarea.tareaMadre?.id
            if (idMadre != null && mapaTareas.containsKey(idMadre)) {
                val madre = mapaTareas[idMadre]
                if (madre != null) {
                    madre.agregarSubtarea(tarea)
                }
                tarea.tareaMadre = madre

            }
        }

        return mapaTareas.values.filter { tarea ->
            tarea.tareaMadre == null || tarea.tareaMadre?.id !in mapaTareas.keys
        }
    }

    private fun filaATarea(rs: ResultSet): Tarea {
        val descripcion = rs.getString("descripcion")
        val estado = Status.valueOf(rs.getString("estado"))
        val etiquetas = rs.getString("etiquetas")?.split(";") ?: emptyList()

        val tarea = Tarea(
            descripcion = descripcion,
            estadoInicial = estado,
            subTareas = mutableListOf(),
            tareaMadre = null,
            etiquetas = etiquetas
        )

        tarea.id = rs.getInt("id")

        val asignadoA = rs.getInt("asignado_a")
        if (asignadoA != 0) {
            tarea.asignadoA = Usuario.crear("Usuario $asignadoA")
        }

        val madreId = rs.getInt("tarea_madre")
        if (madreId != 0) {
            val madre = Tarea("placeholder", estado)
            madre.id = madreId
            tarea.tareaMadre = madre
        }

        return tarea
    }

}
