package org.practicatrim2.presentacion

import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.ActividadRepository
import datos.UsuarioRepository
import org.practicatrim2.presentacion.presentacion.UI


/**
 * # main TaskManger
 *
 * Punto de entrada principal de la aplicación.
 *
 * Esta función inicializa las dependencias necesarias, incluyendo los servicios y repositorios
 * de actividades y usuarios, y luego lanza la interfaz de usuario para interactuar con el sistema.
 *
 * El flujo general es:
 * 1. Crear instancia de la interfaz de usuario.
 * 2. Inicializar el repositorio y servicio de actividades.
 * 3. Inicializar el repositorio y servicio de usuarios.
 * 4. Mostrar el menú principal al usuario.
 */
fun main() {
    val ui = UI()
    val repositorio = ActividadRepository()
    val servicio = ActividadService(repositorio)
    val usuarioRepositorio = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepositorio)

    ui.mostrarMenu(servicio, usuarioService)
}