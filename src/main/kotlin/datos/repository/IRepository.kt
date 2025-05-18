package org.practicatrim2.presentacion.datos.repository

import dominio.Usuario

interface IRepository {
    // Métodos Usuarios
    fun agregarUsuario(usuario: Usuario): Boolean
    fun recuperarUsuario(): List<Usuario>
    fun recuperarUsuarioPorId(id: Int): Usuario?
    fun actualizarUsuario(usuario: Usuario): Boolean
    fun eliminarUsuario(id: Int): Usuario?

    // Métodos ficheros - CSV
    fun cargarCsv(tipo: String)
    fun guardarCsv(tipo: String)
}