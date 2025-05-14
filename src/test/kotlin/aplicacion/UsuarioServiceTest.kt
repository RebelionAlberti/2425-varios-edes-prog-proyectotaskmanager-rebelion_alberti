import aplicacion.UsuarioService
import datos.IUsuarioRepository
import dominio.Usuario
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UsuarioServiceTest : DescribeSpec({

    val repositorio = mockk<IUsuarioRepository>()
    val servicio = UsuarioService(repositorio)


    /***************************************************************
    CREAR USUARIO
    -------------
    ***************************************************************/

    // Caso nominal
    describe("crearUsuarioTrue") {
        it("Aqui creo un usuario pa después agregarlo.") {
            every { repositorio.agregar(any()) } returns true

            val resultado = servicio.crearUsuario("Aarón")

            resultado shouldBe true
        }
    }

    // Caso de error
    describe("crearUsuarioFalse") {
        it("Como no puede crear el usuario, cuando se agrega devuelve false.") {
            every { repositorio.agregar(any()) } returns false

            val resultado = servicio.crearUsuario("Aarón")

            resultado shouldBe false
        }
    }

    /*******************************************************************************************************************
    ELIMINAR USUARIO
    -------------
    ****************************************************************************************************************+**/

    // Caso nominal
    describe("eliminarUsuarioTrue") {
        it("Aqui borro un usuario.") {
            every { repositorio.eliminar(1) } returns true

            val resultado = servicio.eliminarUsuario(1)

            resultado shouldBe true
        }
    }

    //Caso de error
    describe("eliminarUsuarioFalse") {
        it("Como no puede eliminar el usuario, cuando se elimina devuelve false.") {
            every { repositorio.eliminar(1) } returns false

            val resultado = servicio.eliminarUsuario(1)

            resultado shouldBe false
        }
    }

    /*******************************************************************************************************************
    OBTENER USUARIOS
    -------------
     ****************************************************************************************************************+**/

    // Caso nominal
    describe("obtenerUsuariosTrue") {
        it("Aqui te da una lista con los usuarios.") {
            val usuarios = listOf(Usuario.crear("Aarón Grande"), Usuario.crear("Aarón chico"))
            every { repositorio.recuperarTodos() } returns usuarios

            val resultado = servicio.obtenerUsuarios()

            resultado shouldBe usuarios
        }
    }

    //Caso de error
    describe("obtenerUsuariosFalse") {
        it("Si no hay usuario, te devuelve una lista vacia.") {
            every { repositorio.recuperarTodos() } returns emptyList()

            val resultado = servicio.obtenerUsuarios()

            resultado shouldBe emptyList()
        }
    }
    /*******************************************************************************************************************
    BUSCAR USUARIO
    -------------
     ****************************************************************************************************************+**/

    // Caso nominal
    describe("buscarUsuarioPorIdTrue") {
        it("Aqui te da un usuario si existe.") {
            val usuario = Usuario.crear("Aarón")
            every { repositorio.recuperarPorId(1) } returns usuario

            val resultado = servicio.buscarUsuarioPorId(1)

            resultado shouldBe usuario
        }
    }

    //Caso de error
    describe("buscarUsuarioPorIdFalse") {
        it("Si no hay ese usuario, te devuelve null.") {
            every { repositorio.recuperarPorId(1) } returns null

            val resultado = servicio.buscarUsuarioPorId(1)

            resultado shouldBe null
        }
    }
})
