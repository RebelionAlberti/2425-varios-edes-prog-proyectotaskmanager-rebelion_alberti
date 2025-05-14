import aplicacion.UsuarioService
import datos.IUsuarioRepository
import dominio.Usuario
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class UsuarioServiceTest : DescribeSpec({

    val mockRepo = mockk<IUsuarioRepository>()
    val usuarioService = UsuarioService(mockRepo)

    describe("Métodos de UsuarioService") {

        context("crearUsuario") {
            it("Crear usuario con nombre que incluye letras y números") {
                val nombre = "Adri11"
                coEvery { mockRepo.agregar(any()) } returns true

                val resultado = usuarioService.crearUsuario(nombre)
                resultado shouldBe true
                coVerify { mockRepo.agregar(any()) }
            }

            it("Devolver false al crear usuario con nombre vacío") {
                val nombre = ""
                coEvery { mockRepo.agregar(any()) } returns false

                val resultado = usuarioService.crearUsuario(nombre)
                resultado shouldBe false
            }

            it("Crear un usuario con nombre que contiene espacios") {
                val nombre = "Adrian Fernandez"
                coEvery { mockRepo.agregar(any()) } returns true

                val resultado = usuarioService.crearUsuario(nombre)
                resultado shouldBe true
                coVerify { mockRepo.agregar(any()) }
            }
        }

        context("eliminarUsuario") {
            it("Eliminar un usuario correctamente con un ID que exista") {
                val id = 1
                coEvery { mockRepo.eliminar(id) } returns true

                val resultado = usuarioService.eliminarUsuario(id)
                resultado shouldBe true
                coVerify { mockRepo.eliminar(id) }
            }

            it("Devolver false al intentar eliminar un usuario con ID que no existe") {
                val id = 1000
                coEvery { mockRepo.eliminar(id) } returns false

                val resultado = usuarioService.eliminarUsuario(id)
                resultado shouldBe false
            }

            it("Devolver false al intentar eliminar un usuario con un ID negativo") {
                val id = -5
                coEvery { mockRepo.eliminar(id) } returns false

                val resultado = usuarioService.eliminarUsuario(id)
                resultado shouldBe false
            }
        }

        context("obtenerUsuarios") {
            it("Devolver la lista de usuarios cuando hay usuarios") {
                val usuarios = listOf(Usuario.crear("Adri"), Usuario.crear("Jesus"))
                coEvery { mockRepo.recuperarTodos() } returns usuarios

                val resultado = usuarioService.obtenerUsuarios()
                resultado.size shouldBe 2
                resultado[0].nombre shouldBe "Adri"
                resultado[1].nombre shouldBe "Jesus"
            }

            it("Devolver una lista vacía cuando no hay usuarios creados") {
                coEvery { mockRepo.recuperarTodos() } returns emptyList()

                val resultado = usuarioService.obtenerUsuarios()
                resultado.size shouldBe 0
            }

            it("Devolver usuarios con nombres que tengan letras y números") {
                val usuarios = listOf(Usuario.crear("Adri11"), Usuario.crear("Jesus123"))
                coEvery { mockRepo.recuperarTodos() } returns usuarios

                val resultado = usuarioService.obtenerUsuarios()
                resultado.size shouldBe 2
                resultado[0].nombre shouldBe "Adri11"
                resultado[1].nombre shouldBe "Jesus123"
            }
        }

        context("buscarUsuarioPorId") {
            it("Devolver un usuario con un ID existente") {
                val usuario = Usuario.crear("Adri")
                val id = usuario.id

                coEvery { mockRepo.recuperarPorId(id) } returns usuario

                val resultado = usuarioService.buscarUsuarioPorId(id)
                resultado shouldBe usuario
            }

            it("Devolver null para un ID no existente") {
                val id = 100
                coEvery { mockRepo.recuperarPorId(id) } returns null

                val resultado = usuarioService.buscarUsuarioPorId(id)
                resultado shouldBe null
            }

            it("Devolver null para un ID negativo") {
                val id = -1
                coEvery { mockRepo.recuperarPorId(id) } returns null

                val resultado = usuarioService.buscarUsuarioPorId(id)
                resultado shouldBe null
            }
        }
    }
})