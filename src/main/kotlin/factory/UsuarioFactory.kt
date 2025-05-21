package org.practicatrim2.presentacion.factory
import dominio.Usuario

open class UsuarioFactory {
    fun crear(nombre: String): Usuario {
        return Usuario.crear(nombre)
    }
}
