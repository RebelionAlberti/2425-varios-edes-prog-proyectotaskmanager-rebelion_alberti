package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.ActividadRepository
import datos.UsuarioRepository
import org.practicatrim2.presentacion.factory.UsuarioFactory
import org.practicatrim2.presentacion.presentacion.UI
import servicio.ActividadFactory
import servicio.CambiarEstadoCommandFactory

fun main() {
    val usuarioRepositorio = UsuarioRepository()               // Repositorio de usuarios
    val usuarioFactory = UsuarioFactory()                       // Factory para crear usuarios
    val usuarioService = UsuarioService(usuarioRepositorio, usuarioFactory) // Servicio usuarios

    val actividadRepositorio = ActividadRepository()            // Repositorio de actividades
    val actividadService = ActividadService(actividadRepositorio) // Servicio actividades

    val ui = UI()
    ui.mostrarMenu(actividadService, usuarioService)
}


