package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.repository.ActividadRepository
import datos.repository.Repository
import org.practicatrim2.presentacion.datos.dao.UsuarioDAO
import org.practicatrim2.presentacion.presentacion.UI

fun main() {
    val actividadRepo = ActividadRepository()
    val actividadService = ActividadService(actividadRepo)
    val usuarioDAO = UsuarioDAO()
    val repositorio = Repository(usuarioDAO)
    val usuarioService = UsuarioService(repositorio)

    val ui = UI()
    ui.mostrarMenu(actividadService, usuarioService)
}