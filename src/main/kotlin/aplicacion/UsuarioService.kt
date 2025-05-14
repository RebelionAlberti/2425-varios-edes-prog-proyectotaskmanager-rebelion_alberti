package aplicacion

import datos.IUsuarioRepository
import dominio.Usuario

class UsuarioService(private val repositorio: IUsuarioRepository) : IUsuarioService {
    override fun crearUsuario(id: Int, nombre: String): Boolean {
        lateinit var usuario : Usuario

        if (id<=0) {
            usuario = Usuario.crear(nombre=nombre)
        } else {
            usuario = Usuario.crear(id, nombre)
        }

        if (nombre.isBlank()) {
            throw IllegalArgumentException("El nombre del usuario no puede estar vacío")
        }

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
