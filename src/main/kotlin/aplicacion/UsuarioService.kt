package aplicacion

import datos.UsuarioRepository
import dominio.Usuario

/**
 * Implementación de la interfaz [IUsuarioService] que gestiona
 * las operaciones relacionadas con los usuarios.
 *
 * Esta clase es la capa de servicio en la aplicación
 * interactuando con él [UsuarioRepository] para realizar
 * operaciones de crear, eliminar, consultar y buscar usuarios.
 *
 * @property repositorio Instancia de [UsuarioRepository] usada para
 * acceder y modificar la información de los usuarios almacenados.
 *
 * @constructor Carga inicialmente los usuarios almacenados mediante el repositorio.
 */
class UsuarioService(private val repositorio: UsuarioRepository) : IUsuarioService {

    init {
        repositorio.cargarUsuarios()
    }

    /**
     * Crea un nuevo usuario con el nombre deseado y lo añade al repositorio.
     *
     * @param nombre Nombre del nuevo usuario que se va a crear.
     * @return true si el usuario se añadió correctamente al repositorio,
     * false si el usuario no pudo agregarse.
     */
    override fun crearUsuario(nombre: String): Boolean {
        val usuario = Usuario.crear(nombre)
        return repositorio.agregar(usuario)
    }

    /**
     * Elimina un usuario creado por su ID.
     *
     * @param id id del usuario que se quiere eliminar.
     * @return true si el usuario fue eliminado exitosamente,
     * false si no se encuentra el usuario con ese, id.
     */
    override fun eliminarUsuario(id: Int): Boolean {
        return repositorio.eliminar(id)
    }

    /**
     * Obtiene una lista con todos los usuarios almacenados.
     *
     * @return Lista de objetos [Usuario] que representa todos los usuarios.
     */
    override fun obtenerUsuarios(): List<Usuario> {
        return repositorio.recuperarTodos()
    }

    /**
     * Busca y devuelve un usuario por su id.
     *
     * @param id id del usuario que se quiere buscar.
     * @return Objeto [Usuario] si se encuentra o null si no existe un usuario con ese id.
     */
    override fun buscarUsuarioPorId(id: Int): Usuario? {
        return repositorio.recuperarPorId(id)
    }
}
