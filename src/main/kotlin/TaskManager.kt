package org.practicatrim2.presentacion

import aplicacion.ActividadService
import datos.ActividadRepository
import org.practicatrim2.presentacion.presentacion.UI

fun main() {
    val ui = UI()
    val repositorio = ActividadRepository()
    val servicio = ActividadService(repositorio)

    ui.mostrarMenu(servicio)
}
