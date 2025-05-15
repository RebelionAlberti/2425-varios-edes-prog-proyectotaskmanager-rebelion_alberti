package aplicacion

import dominio.Usuario

interface IUsuarioService {
    fun crearUsuario(nombre: String): Boolean

    fun eliminarUsuario(id: Int): Boolean

    fun obtenerUsuarios(): List<Usuario>

    fun buscarUsuarioPorId(id: Int): Usuario?
}