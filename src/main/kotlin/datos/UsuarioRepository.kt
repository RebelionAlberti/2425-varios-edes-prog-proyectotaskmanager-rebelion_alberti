package datos

import dominio.Usuario
import java.io.File

class UsuarioRepository : IUsuarioRepository {

    private val archivo = File("usuarios.csv")
    private val usuarios = mutableListOf<Usuario>()

    fun cargarUsuarios() {
        if (!archivo.exists()) {
            archivo.writeText("id,nombre\n")
        }
        usuarios.clear()
        var maxId = 0
        archivo.forEachLine { linea ->
            if (linea.startsWith("id"))
                return@forEachLine
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

    private fun guardarUsuarios() {
        val contenido = buildString {
            append("id,nombre\n")
            usuarios.forEach {
                append("${it.id},${it.nombre}\n")
            }
        }
        archivo.writeText(contenido)
    }

    override fun agregar(usuario: Usuario): Boolean {
        if (usuarios.any { it.id == usuario.id })
            return false
        usuarios.add(usuario)
        guardarUsuarios()
        return true
    }

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

    override fun recuperarTodos(): List<Usuario> {
        return usuarios.toList()
    }

    override fun recuperarPorId(id: Int): Usuario? {
        return usuarios.find { it.id == id }
    }
}