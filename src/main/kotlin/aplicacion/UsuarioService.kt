package aplicacion

import datos.UsuarioRepository
import dominio.Usuario

class UsuarioService(private val repositorio: UsuarioRepository) : IUsuarioService {

    init {
        repositorio.cargarUsuarios()
    }

    override fun crearUsuario(nombre: String): Boolean {
        val usuario = Usuario.crear(nombre)
        return repositorio.agregar(usuario)
    }

    override fun eliminarUsuario(id: Int): Boolean {
        return repositorio.eliminar(id)
    }

    override fun obtenerUsuarios(): List<Usuario> {
        return repositorio.recuperarTodos()
    }

    override fun buscarUsuarioPorId(id: Int): Usuario? {
        return repositorio.recuperarPorId(id)
    }
}