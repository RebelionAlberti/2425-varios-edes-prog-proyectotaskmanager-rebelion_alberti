package datos

import dominio.Usuario

class UsuarioRepository : IUsuarioRepository {
    private val usuarios = mutableListOf<Usuario>()

    override fun agregar(usuario: Usuario): Boolean {
        if (usuarios.any { it.id == usuario.id })
            return false

        usuarios.add(usuario)
        return true
    }

    override fun eliminar(id: Int): Boolean {
        val usuario = usuarios.find { it.id == id }
        var eliminado = false
        if (usuario != null) {
            usuarios.remove(usuario)
            eliminado = true
        }
        return eliminado
    }

    override fun recuperarTodos(): List<Usuario> {
        return usuarios.toList()
    }

    override fun recuperarPorId(id: Int): Usuario? {
        return usuarios.find { it.id == id }
    }
}