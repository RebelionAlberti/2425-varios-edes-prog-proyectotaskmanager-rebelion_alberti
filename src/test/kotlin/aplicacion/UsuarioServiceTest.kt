import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import aplicacion.UsuarioService
import datos.IUsuarioRepository
import dominio.Usuario
import io.kotest.assertions.throwables.shouldThrow

class UsuarioServiceTest : DescribeSpec({

    val mockkRepositorio = mockk<IUsuarioRepository>()
    val usuarioService = UsuarioService(mockkRepositorio)

    describe("crearUsuario") {

        it("Debe devolver true cuando se crea un usuario con nombre valido") {
            val nombre = "Jesus"
            val usuario = Usuario.crear(1, nombre)

            every { mockkRepositorio.agregar(usuario) } returns true

            val resultado = usuarioService.crearUsuario(1, nombre)

            resultado shouldBe true
        }

        it("Debe lanzar una excepción cuando se intenta crear un usuario con nombre vacío") {
            val nombre = ""

            shouldThrow<IllegalArgumentException> {
                usuarioService.crearUsuario(1, nombre)
            }
        }
    }

    describe("eliminarUsuario") {

        it("Debe devolver true al eliminar un usuario existente") {
            every { mockkRepositorio.eliminar(1) } returns true

            val resultado = usuarioService.eliminarUsuario(1)

            resultado shouldBe true
        }

        it("Debe devolver false al intentar eliminar un usuario que no existe") {
            every { mockkRepositorio.eliminar(33) } returns false

            val resultado = usuarioService.eliminarUsuario(33)

            resultado shouldBe false
        }
    }

    describe("obtenerUsuarios") {

        it("Debe devolver una lista de usuarios del repositorio") {
            val listaUsuarios = listOf(
                Usuario.crear(1, "Jesus"),
                Usuario.crear(2, "Don Bogotá")
            )

            every { mockkRepositorio.recuperarTodos() } returns listaUsuarios

            val resultado = usuarioService.obtenerUsuarios()

            resultado shouldBe listaUsuarios
        }

        it("Debe lanzar una excepción si recuperarTodos falla") {
            every { mockkRepositorio.recuperarTodos() } throws RuntimeException("Error al recuperar usuarios")

            shouldThrow<RuntimeException> {
                usuarioService.obtenerUsuarios()
            }
        }
    }

    describe("buscarUsuarioPorId") {

        it("Debe devolver el usuario si existe") {
            val usuario = Usuario.crear(1, "yiisus")

            every { mockkRepositorio.recuperarPorId(1) } returns usuario

            val resultado = usuarioService.buscarUsuarioPorId(1)

            resultado shouldBe usuario
        }

        it("Debe devolver null si el usuario no existe") {
            every { mockkRepositorio.recuperarPorId(33) } returns null

            val resultado = usuarioService.buscarUsuarioPorId(33)

            resultado shouldBe null
        }
    }

})
