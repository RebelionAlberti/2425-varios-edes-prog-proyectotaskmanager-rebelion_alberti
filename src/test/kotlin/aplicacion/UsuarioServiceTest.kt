package aplicacion

import datos.UsuarioRepository
import dominio.Usuario
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.sql.SQLException

class UsuarioTestService : DescribeSpec({
    val repository = mockk<UsuarioRepository>()
    val usuario = UsuarioService(repository)

    describe("Usuarios do produto") {
        every { repository.eliminar(1) } returns true
        val resultado = repository.eliminar(1)
        resultado shouldBe true
    }
})
