package aplicacion

import datos.UsuarioRepository
import dominio.Usuario
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UsuarioServiceTest {

    private lateinit var repo: UsuarioRepository

    @BeforeEach
    fun setup() {
        repo = UsuarioRepository()
    }

    @Test
    fun recuperarTodosUsuarios_debeDevolverListaCorrecta() {
        repo.agregar(Usuario.crear("Usuario1"))
        repo.agregar(Usuario.crear("Usuario2"))

        val usuarios = repo.recuperarTodos()

        assertEquals(2, usuarios.size)
        assertTrue(usuarios.any { it.nombre == "Usuario1" })
        assertTrue(usuarios.any { it.nombre == "Usuario2" })
    }
}
