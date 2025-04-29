package datos

import dominio.Usuario

interface IUsuarioRepository {
    fun agregar(usuario: Usuario): Boolean
    fun eliminar(id: Int): Boolean
    fun recuperarTodos(): List<Usuario>
    fun recuperarPorId(id: Int): Usuario?
}