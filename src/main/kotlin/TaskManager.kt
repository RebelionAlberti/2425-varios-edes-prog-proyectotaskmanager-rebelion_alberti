package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.dao.EventoDAO
import datos.repository.Repository
import datos.dao.TareaDAO
import datos.dao.UsuarioDAO
import presentacion.UI

fun main() {
    val ui = UI()
    val tareaDAO = TareaDAO()
    val eventoDAO = EventoDAO()
    val usuarioDAO = UsuarioDAO()
    val repositorio = Repository(tareaDAO, eventoDAO, usuarioDAO)
    val servicio = ActividadService(repositorio)
    val usuarioService = UsuarioService(repositorio)

    ui.mostrarMenu(servicio, usuarioService)
}