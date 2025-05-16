package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.repository.UsuarioRepository
import datos.dao.EventoDAO
import datos.repository.Repository
import datos.dao.TareaDAO
import presentacion.UI

fun main() {
    val ui = UI()
    val tareaDAO = TareaDAO()
    val eventoDAO = EventoDAO()
    val repositorio = Repository(tareaDAO, eventoDAO)
    val servicio = ActividadService(repositorio)
    val usuarioRepositorio = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepositorio)

    ui.mostrarMenu(servicio, usuarioService)
}