package datos.repository

import dominio.Usuario
import org.practicatrim2.presentacion.datos.dao.UsuarioDAO
import org.practicatrim2.presentacion.datos.repository.IRepository

class Repository(private val usuarioDAO: UsuarioDAO): IRepository {
    init {
        cargarCsv("usuario")
    }

    override fun agregarUsuario(usuario: Usuario): Boolean {
        return usuarioDAO.create(usuario).let { true }
    }

    override fun recuperarUsuario(): List<Usuario> {
        return usuarioDAO.read()
    }

    override fun recuperarUsuarioPorId(id: Int): Usuario? {
        return usuarioDAO.readById(id)
    }

    override fun actualizarUsuario(usuario: Usuario): Boolean {
        return usuarioDAO.update(usuario)
    }

    override fun eliminarUsuario(id: Int): Usuario? {
        return usuarioDAO.delete(id)
    }

    // Métodos ficheros - CSV
    override fun cargarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "usuario" -> usuarioDAO.cargarUsuariosCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }

    override fun guardarCsv(tipo: String) {
        when (tipo.lowercase()) {
            "usuario" -> usuarioDAO.guardarUsuariosCsv()
            else -> throw IllegalArgumentException("Error: $tipo")
        }
    }
}