package datos.dao

import org.junit.jupiter.api.*
import java.io.File

class TareaDaoTest {
    private lateinit var tareaDao: TareaDAO
    private val carpeta = File("CsvFiles")
    private val archivo = File(carpeta, "tareas.csv")

    private val contenidoCsv = """
        id,descripcion,estado,asignado,etiquetas,fechaCreacion,esSubtarea,idTareaMadre
        1,Tarea Principal,Abierta,yiisus,etiqueta1;etiqueta2,01/01/2025,false,
        2,Subtarea 1,Abierta,adri,etiqueta3,01/01/2025,true,1
        3,Subtarea 2,Cerrada,,etiqueta4,01/01/2025,true,1
    """.trimIndent()

    @BeforeEach
    fun setup() {
        carpeta.mkdirs()
        archivo.writeText(contenidoCsv)
        tareaDao = TareaDAO()
        tareaDao.cargarTareasCsv()
    }

    @AfterEach
    fun cleanup() {
        archivo.delete()
        carpeta.delete()
    }

    @Test
    fun `cargar tareas y subtareas correctamente`() {
        val tareas = tareaDao.read()
        Assertions.assertEquals(1, tareas.size)
        val tareaPrincipal = tareas[0]
        Assertions.assertEquals("Tarea Principal", tareaPrincipal.descripcion)
        Assertions.assertEquals(2, tareaPrincipal.subtareas.size)
        Assertions.assertTrue(tareaPrincipal.subtareas.any { it.descripcion == "Subtarea 1" })
        Assertions.assertTrue(tareaPrincipal.subtareas.any { it.descripcion == "Subtarea 2" })
    }
}
