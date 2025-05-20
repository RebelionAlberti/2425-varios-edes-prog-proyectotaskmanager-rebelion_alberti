package datos

import dominio.Usuario

/**
 * # Interfaz IUsuariosRepository.
 *
 * Define las operaciones básicas de acceso a datos para los usuarios,
 * incluyendo la creación, eliminación, recuperación total y búsqueda por Id.
 */
interface IUsuarioRepository {

    /**
     * ## agregar
     *
     * Agrega un nuevo usuario al repositorio.
     *
     * @param usuario Usuario a agregar.
     * @return `true` si se agregó correctamente, `false` en caso contrario.
     */
    fun agregar(usuario: Usuario): Boolean

    /**
     * ## eliminar
     *
     * Elimina un usuario del repositorio según su Id.
     *
     * @param id Identificador único del usuario a eliminar.
     * @return `true` si se eliminó correctamente, `false` si no se encontró.
     */
    fun eliminar(id: Int): Boolean

    /**
     * ## recuperarTodos
     *
     * Recupera todos los usuarios almacenados en el repositorio.
     *
     * @return Lista de usuarios.
     */
    fun recuperarTodos(): List<Usuario>

    /**
     * ## recuperarPorId
     *
     * Recupera un usuario específico por su Id.
     *
     * @param id Identificador único del usuario.
     * @return El usuario correspondiente, o `null` si no se encuentra.
     */
    fun recuperarPorId(id: Int): Usuario?
}
