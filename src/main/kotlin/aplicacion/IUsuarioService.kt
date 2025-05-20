package aplicacion

import dominio.Usuario

/**
 * # Interfaz IUsuarioService
 *
 * Servicio para la gestión de usuarios dentro del sistema.
 *
 * Define las operaciones básicas para crear, eliminar, recuperar y buscar usuarios.
 */
interface IUsuarioService {

    /**
     * ## crearUsuario
     *
     * Crea un nuevo usuario con el nombre proporcionado.
     *
     * @param nombre Nombre del usuario a crear.
     * @return `true` si el usuario fue creado exitosamente, `false` si ocurrió algún error.
     */
    fun crearUsuario(nombre: String): Boolean

    /**
     * ## eliminarUsuario
     *
     * Elimina un usuario por su id.
     *
     * @param id ID del usuario a eliminar.
     * @return `true` si se eliminó correctamente, `false` si no se encontró el usuario.
     */
    fun eliminarUsuario(id: Int): Boolean

    /**
     * ## obtenerUsuarios
     *
     * Recupera todos los usuarios registrados en el sistema.
     *
     * @return Lista de objetos [Usuario].
     */
    fun obtenerUsuarios(): List<Usuario>

    /**
     * ## buscarUsuarioPorId
     *
     * Busca un usuario específico por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return El usuario encontrado, o `null` si no existe.
     */
    fun buscarUsuarioPorId(id: Int): Usuario?
}
