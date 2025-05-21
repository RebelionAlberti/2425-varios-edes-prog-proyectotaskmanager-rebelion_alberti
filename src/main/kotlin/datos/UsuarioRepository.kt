package datos

import dominio.Usuario
import java.io.File

/**
 * Repositorio que gestiona el almacenamiento y recuperación de usuarios
 * mediante un archivo CSV.
 *
 * Implementa la interfaz [IUsuarioRepository] para las operaciones
 * básicas.
 */
class UsuarioRepository : IUsuarioRepository {

    private val archivo = File("usuarios.csv")
    private val usuarios = mutableListOf<Usuario>()

    /**
     * Carga los usuarios desde el archivo CSV al repositorio.
     *
     * Si el archivo no existe se crea con la cabecera inicial.
     * Actualiza el contador de id en la clase [Usuario] para evitar duplicados.
     */
    fun cargarUsuarios() {
        if (!archivo.exists()) {
            archivo.writeText("id,nombre\n")
        }
        usuarios.clear()
        var maxId = 0
        archivo.forEachLine { linea ->
            if (linea.startsWith("id")) {
                return@forEachLine
            }
            val partes = linea.split(",")
            if (partes.size >= 2) {
                val id = partes[0].toIntOrNull()
                val nombre = partes[1]
                if (id != null) {
                    usuarios.add(Usuario(id, nombre))
                    if (id > maxId) maxId = id
                }
            }
        }
        Usuario.configurarContador(maxId + 1)
    }

    /**
     * Guarda en el archivo CSV el contenido actual de los usuarios.
     *
     * Esta función es privada y se llama después de agregar o eliminar usuarios para mantener
     * actualizado el archivo con el estado actual.
     */
    private fun guardarUsuarios() {
        val contenido = buildString {
            append("id,nombre\n")
            usuarios.forEach {
                append("${it.id},${it.nombre}\n")
            }
        }
        archivo.writeText(contenido)
    }

    /**
     * Agrega un nuevo usuario al repositorio y actualiza el archivo CSV.
     *
     * @param usuario Usuario para agregar.
     * @return true si el usuario se agregó exitosamente, false si ya existe un usuario con el mismo id creado.
     */
    override fun agregar(usuario: Usuario): Boolean {
        if (usuarios.any { it.id == usuario.id }) {
            return false
        }
        usuarios.add(usuario)
        guardarUsuarios()
        return true
    }

    /**
     * Elimina un usuario por su id del repositorio y actualiza el archivo CSV.
     *
     * @param id id del usuario para eliminar.
     * @return true si el usuario fue encontrado y eliminado, false si no existe usuario con ese, id.
     */
    override fun eliminar(id: Int): Boolean {
        val usuario = usuarios.find { it.id == id }
        return if (usuario != null) {
            usuarios.remove(usuario)
            guardarUsuarios()
            true
        } else {
            false
        }
    }

    /**
     * Recupera todos los usuarios almacenados en el repositorio.
     *
     * @return Lista con todos los usuarios.
     */
    override fun recuperarTodos(): List<Usuario> {
        return usuarios.toList()
    }

    /**
     * Busca y devuelve un usuario por su id.
     *
     * @param id id del usuario que se busca.
     * @return Usuario si se encuentra o null si no existe.
     */
    override fun recuperarPorId(id: Int): Usuario? {
        return usuarios.find { it.id == id }
    }
}
