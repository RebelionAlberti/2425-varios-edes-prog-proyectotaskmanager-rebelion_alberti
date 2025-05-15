package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.ActividadRepository
import datos.UsuarioRepository
import org.practicatrim2.presentacion.presentacion.UI

fun main() {
    val actividadRepo = ActividadRepository()
    val actividadService = ActividadService(actividadRepo)
    val usuarioRepo = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepo)

    val ui = UI()
    ui.mostrarMenu(actividadService, usuarioService)
}