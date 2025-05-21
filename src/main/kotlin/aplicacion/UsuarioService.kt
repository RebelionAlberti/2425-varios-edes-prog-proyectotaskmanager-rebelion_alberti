package aplicacion
import datos.*
import dominio.*

class UsuarioService(private val repositorio: IUsuarioRepository) : IUsuarioService {

    override fun crearUsuario(nombre: String): Boolean {
        val usuario = Usuario.crear(nombre)
        return repositorio.agregar(usuario)
    }

    override fun eliminarUsuario(id: Int): Boolean {
        return if (usuarioExiste(id)) repositorio.eliminar(id) else false
    }

    override fun obtenerUsuarios(): List<Usuario> {
        return repositorio.recuperarTodos()
    }

    override fun buscarUsuarioPorId(id: Int): Usuario? {
        return recuperarUsuario(id)
    }

    // método reutilizable
    private fun recuperarUsuario(id: Int): Usuario? = repositorio.recuperarPorId(id)

    // método reutilizable
    private fun usuarioExiste(id: Int): Boolean = recuperarUsuario(id) != null
}

