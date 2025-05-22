package datos

import dominio.Usuario

class UsuarioRepository : IUsuarioRepository {
    private val usuarios = mutableListOf<Usuario>()

    override fun agregar(usuario: Usuario): Boolean {
        if (usuarios.any { it.id == usuario.id }) {
            return false
        }

        usuarios.add(usuario)
        return true
    }

    override fun eliminar(id: Int): Boolean {
        val usuario = usuarios.find { it.id == id }
        return if (usuario != null) {
            usuarios.remove(usuario)
        } else {
            false
        }
    }

    override fun recuperarTodos(): List<Usuario> = usuarios.toList()

    override fun recuperarPorId(id: Int): Usuario? = usuarios.find { it.id == id }
}
