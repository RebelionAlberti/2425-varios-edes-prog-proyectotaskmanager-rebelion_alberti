package datos.dao

import dominio.Usuario
import java.io.File

class UsuarioDAO: IGenericoDAO<Usuario> {
    private val carpeta = File("CsvFiles")
    private val archivo = File(carpeta, "usuarios.csv")
    private val usuario = mutableListOf<Usuario>()

    init {
        if (!carpeta.exists())carpeta.mkdirs()
    }

    fun cargarUsuariosCsv() {
        if (!archivo.exists()) {
            archivo.writeText("id,nombre\n")
        }
        usuario.clear()
        var maxId = 0
        archivo.forEachLine { linea ->
            if (linea.startsWith("id"))
                return@forEachLine
            val partes = linea.split(",")
            if (partes.size >= 2) {
                val id = partes[0].toIntOrNull()
                val nombre = partes[1]
                if (id != null) {
                    usuario.add(Usuario(id, nombre))
                    if (id > maxId) maxId = id
                }
            }
        }
        Usuario.configurarContador(maxId + 1)
    }

    fun guardarUsuariosCsv() {
        val contenido = buildString {
            append("id,nombre\n")
            usuario.forEach {
                append("${it.id},${it.nombre}\n")
            }
        }
        archivo.writeText(contenido)
    }

    override fun create(t: Usuario): Usuario {
        usuario.add(t)
        guardarUsuariosCsv()
        return t
    }

    override fun read(): List<Usuario> {
        return usuario.toList()
    }

    fun readById(id: Int): Usuario? {
        return usuario.find { it.id == id }
    }

    override fun update(t: Usuario): Boolean {
        val actual = usuario.find { it.id == t.id }
        return if (actual != null) {
            usuario.remove(actual)
            usuario.add(t)
            guardarUsuariosCsv()
            true
        }
        else {
            false
        }
    }

    override fun delete(id: Int): Usuario? {
        val usuarios = usuario.find { it.id == id }
        return if (usuarios != null) {
            usuario.remove(usuarios)
            guardarUsuariosCsv()
            usuarios
        }
        else {
            null
        }
    }
}