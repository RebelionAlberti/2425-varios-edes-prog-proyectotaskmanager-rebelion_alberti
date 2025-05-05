package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.DBConfig
import datos.TareaDAO
import datos.TareaRepository
import datos.UsuarioRepository
import org.practicatrim2.presentacion.presentacion.UI

fun main() {
    val db = DBConfig()

    val tareaDao = TareaDAO(db)
    val tareaRepository = TareaRepository(tareaDao)

    val servicio = ActividadService(tareaRepository)
    val usuarioRepositorio = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepositorio)

    val ui = UI()
    ui.mostrarMenu(servicio, usuarioService)
}
