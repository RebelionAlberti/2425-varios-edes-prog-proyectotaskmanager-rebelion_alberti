package datos

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class DBConfig(
    private val jdbcUrl: String = "jdbc:h2:./DataBase/mydb",
    private val user: String = "user",
    private val password: String = "password"
) {

    init {
        try {
            Class.forName("org.h2.Driver")
            inicializarBaseDeDatos()
        } catch (e: ClassNotFoundException) {
            println("No se pudo cargar el driver de H2: ${e.message}")
        }
    }

    fun obtenerConexion(): Connection {
        return DriverManager.getConnection(jdbcUrl, user, password)
    }

    private fun inicializarBaseDeDatos() {
        val sql = """
            CREATE TABLE IF NOT EXISTS tarea (
                id INT PRIMARY KEY AUTO_INCREMENT,
                descripcion VARCHAR(255) NOT NULL,
                fecha_creacion VARCHAR(20) NOT NULL,
                estado VARCHAR(20) NOT NULL,
                etiquetas TEXT,
                asignado_a INT,
                tarea_madre INT
            );
        """.trimIndent()

        obtenerConexion().use { conn ->
            conn.createStatement().use { stmt: Statement ->
                stmt.execute(sql)
            }
        }

        println("Tabla Tarea creada.")
    }
}
