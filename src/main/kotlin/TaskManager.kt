package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.ActividadRepository
import datos.UsuarioRepository
import org.practicatrim2.presentacion.presentacion.UI

fun main() {
    val ui = UI()
    val repositorio = ActividadRepository()
    val servicio = ActividadService(repositorio)
    val usuarioRepositorio = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepositorio)

    ui.mostrarMenu(servicio, usuarioService)
}
