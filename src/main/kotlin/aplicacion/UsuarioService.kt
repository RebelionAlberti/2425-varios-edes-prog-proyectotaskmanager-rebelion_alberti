package aplicacion

import dominio.Usuario
import datos.repository.IRepository

class UsuarioService(private val repositorio: IRepository) : IUsuarioService {

    override fun crearUsuario(nombre: String): Boolean {
        val usuario = Usuario.crear(nombre)
        return repositorio.agregarUsuario(usuario)
    }

    override fun eliminarUsuario(id: Int): Usuario? {
        return repositorio.eliminarUsuario(id)
    }

    override fun obtenerUsuarios(): List<Usuario> {
        return repositorio.recuperarUsuario()
    }

    override fun buscarUsuarioPorId(id: Int): Usuario? {
        return repositorio.recuperarUsuarioPorId(id)
    }
}
