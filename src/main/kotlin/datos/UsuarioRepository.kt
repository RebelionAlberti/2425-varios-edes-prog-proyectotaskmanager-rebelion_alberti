package datos

import dominio.Usuario


/**
 * # UsuarioRepository
 *
 * Gestiona un conjunto de usuarios utilizando una lista.
 *
 */
class UsuarioRepository : IUsuarioRepository {

    /**
     * Lista mutable de usuarios registrados.
     */
    private val usuarios = mutableListOf<Usuario>()


    /**
     * ## agregar
     *
     * Agrega un nuevo usuario al repositorio si no existe uno con el mismo ID.
     *
     * @param usuario Usuario a agregar.
     * @return `true` si el usuario fue agregado correctamente, `false` si ya existe un usuario con ese Id.
     */
    override fun agregar(usuario: Usuario): Boolean {
        if (usuarios.any { it.id == usuario.id })
            return false

        usuarios.add(usuario)
        return true
    }



    /**
     * ## eliminar
     *
     * Elimina un usuario del repositorio por su Id.
     *
     * @param id Identificador único del usuario.
     * @return `true` si el usuario fue eliminado, `false` si no se encontró.
     */
    override fun eliminar(id: Int): Boolean {
        val usuario = usuarios.find { it.id == id }
        var eliminado = false
        if (usuario != null) {
            usuarios.remove(usuario)
            eliminado = true
        }
        return eliminado
    }


    /**
     * ## recuperarTodos
     *
     * Recupera todos los usuarios registrados.
     *
     * @return Lista de todos los usuarios.
     */
    override fun recuperarTodos(): List<Usuario> {
        return usuarios.toList()
    }


    /**
     * ## recuperarPorId
     *
     * Busca un usuario por su Id.
     *
     * @param id Identificador único del usuario.
     * @return El usuario correspondiente, o `null` si no fue encontrado.
     */
    override fun recuperarPorId(id: Int): Usuario? {
        return usuarios.find { it.id == id }
    }
}