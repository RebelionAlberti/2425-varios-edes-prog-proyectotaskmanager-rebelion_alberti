package datos.dao

import dominio.Usuario
import dominio.Tarea
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TareaDaoUsuarioTest {
    private lateinit var tareaDao: TareaDAO

    @BeforeEach
    fun setup() {
        tareaDao = TareaDAO()
        tareaDao.cargarTareasCsv()
    }

    @Test
    fun `asignar usuario a tarea correctamente`() {
        val usuario = Usuario.crear("UsuarioTest")
        val tareas = tareaDao.read()
        val tarea = if (tareas.isEmpty()) {
            tareaDao.create(Tarea.crearInstancia("Tarea prueba", listOf()))
        } else {
            tareas.first()
        }

        val exito = tareaDao.asignarTareaAUsuarios(tarea.id, usuario)

        assertTrue(exito, "La asignación del usuario debe ser exitosa")
        val tareaActualizada = tareaDao.readById(tarea.id)
        assertEquals(usuario.nombre, tareaActualizada?.asignadoA?.nombre, "El nombre del usuario asignado debe coincidir")
    }
}
