package aplicacion

import datos.IUsuarioRepository
import dominio.Usuario

/**
 * # UsuarioService
 *
 * Implementación del servicio de usuarios.
 *
 * Esta clase se encarga de la lógica para gestionar usuarios mediante un repositorio,
 * incluyendo operaciones para crear, eliminar, obtener y buscar usuarios.
 *
 * @property repositorio Repositorio de usuarios utilizado para acceder a los datos.
 */
class UsuarioService(private val repositorio: IUsuarioRepository) : IUsuarioService {


    /**
     * ## crearUsuario
     *
     * Crea un nuevo usuario con el nombre especificado.
     *
     * @param nombre Nombre del usuario a crear.
     * @return `true` si el usuario fue agregado correctamente, `false` en caso contrario.
     */
    override fun crearUsuario(nombre: String): Boolean {
        val usuario = Usuario.crear(nombre)
        return repositorio.agregar(usuario)
    }


    /**
     * ## eliminarUsuario
     *
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return `true` si el usuario fue eliminado correctamente, `false` si no se encontró.
     */
    override fun eliminarUsuario(id: Int): Boolean {
        return repositorio.eliminar(id)
    }


    /**
     * ## obtenerUsuarios
     *
     * Recupera la lista de todos los usuarios registrados.
     *
     * @return Lista de objetos [Usuario].
     */
    override fun obtenerUsuarios(): List<Usuario> {
        return repositorio.recuperarTodos()
    }



    /**
     * ## buscarUsuarioPorId
     *
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return El usuario correspondiente si existe, o `null` si no se encuentra.
     */
    override fun buscarUsuarioPorId(id: Int): Usuario? {
        return repositorio.recuperarPorId(id)
    }
}
