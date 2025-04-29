package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import aplicacion.IActividadService
import aplicacion.IUsuarioService
import aplicacion.UsuarioService


class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val LISTAR_ACTIVIDADES = "3"
        private const val CREAR_USUARIO = "4"
        private const val ELIMINAR_USUARIO = "5"
        private const val LISTAR_USUARIOS = "6"
        private const val ASIGNAR_USUARIO_A_TAREA = "7"
        private const val VER_TAREAS_POR_USUARIO = "8"
        private const val SALIR = "9"
    }

    fun mostrarMenu(servicio: ActividadService, usuarioService: UsuarioService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Listar Actividades")
            println("4 | Crear Usuario")
            println("5 | Eliminar Usuario")
            println("6 | Listar Usuarios")
            println("7 | Asignar un usuario a una tarea")
            println("8 | Ver tareas asignadas a usuarios")
            println("9 | Salir")
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
                CREAR_USUARIO -> crearUsuario(usuarioService)
                ELIMINAR_USUARIO -> eliminarUsuario(usuarioService)
                LISTAR_USUARIOS -> listarUsuarios(usuarioService)
                ASIGNAR_USUARIO_A_TAREA -> asignarUsuarioATarea(servicio, usuarioService)
                VER_TAREAS_POR_USUARIO -> verTareasPorUsuario(servicio)
                SALIR -> {
                    println("Saliendo...")
                    seguir = false
                }
                else -> println("Opción no válida, prueba de nuevo")
            }
        } while (seguir)
    }

    private fun agregarTarea(servicio: ActividadService) {
        println("\n=== Agregar Tarea ===")
        print("Descripción: ")
        val descripcion = readln()
        servicio.crearTarea(descripcion)
        println("Tarea agregada correctamente.")
    }

    private fun agregarEvento(servicio: ActividadService) {
        println("\n=== Agregar Evento ===")
        print("Descripción: ")
        val descripcion = readln()
        print("Fecha de realización, formato -> (dd/MM/yyyy): ")
        val fechaRealizacion = readln()
        print("Ubicación: ")
        val ubicacion = readln()
        servicio.crearEvento(descripcion, fechaRealizacion, ubicacion)
        println("Evento agregado correctamente.")
    }

    private fun listarActividades(servicio: ActividadService) {
        println("\n=== Lista actividades ===")
        val actividades = servicio.obtenerActividades()
        if (actividades.isEmpty()) {
            println("No hay actividades.")
        } else {
            actividades.forEach { println(it) }
        }
    }

    private fun crearUsuario(servicio: IUsuarioService) {
        println("\n=== Crear Usuario ===")
        print("Nombre del usuario: ")
        val nombre = readln()
        if (servicio.crearUsuario(nombre)) {
            println("Usuario creado correctamente.")
        } else {
            println("Error al crear el usuario.")
        }
    }

    private fun eliminarUsuario(servicio: IUsuarioService) {
        println("\n=== Eliminar Usuario ===")
        print("ID del usuario a eliminar: ")
        val id = readln().toIntOrNull()
        if (id == null || id < 1) {
            println("ID inválido.")
            return
        }
        val eliminado = servicio.eliminarUsuario(id)
        if (eliminado) {
            println("Usuario eliminado correctamente.")
        } else {
            println("No se encontró un usuario con ese ID.")
        }
    }

    private fun listarUsuarios(servicio: IUsuarioService) {
        println("\n=== Lista de Usuarios ===")
        val usuarios = servicio.obtenerUsuarios()
        if (usuarios.isEmpty()) {
            println("No hay usuarios registrados.")
        } else {
            usuarios.forEach { println(it) }
        }
    }

    private fun asignarUsuarioATarea(
        actividadService: IActividadService,
        usuarioService: IUsuarioService
    ) {
        println("\n=== Asignar Usuario a Tarea ===")
        print("ID de la tarea: ")
        val idTarea = readln().toIntOrNull()
        print("ID del usuario (0 para desasignar): ")
        val idUsuario = readln().toIntOrNull()
        if (idTarea == null || idUsuario == null) {
            println("IDs inválidos.")
            return
        }
        val usuario = if (idUsuario == 0) {
            null
        } else {
            usuarioService.buscarUsuarioPorId(idUsuario)
        }
        if (idUsuario != 0 && usuario == null) {
            println("Usuario no encontrado.")
            return
        }
        val resultado = actividadService.asignarUsuarioATarea(idTarea, usuario)
        if (resultado) {
            if (usuario == null) {
                println("Tarea desasignada correctamente.")
            } else {
                println("Usuario asignado correctamente a la tarea.")
            }
        } else {
            println("No se pudo asignar/desasignar la tarea.")
        }
    }

    private fun verTareasPorUsuario(servicio: IActividadService) {
        println("\n=== Ver Tareas por Usuario ===")
        print("ID del usuario: ")
        val idUsuario = readln().toIntOrNull()
        if (idUsuario == null) {
            println("ID inválido.")
            return
        }
        val tareas = servicio.obtenerTareasPorUsuario(idUsuario)
        if (tareas.isEmpty()) {
            println("Este usuario no tiene tareas asignadas.")
        } else {
            println("Tareas asignadas al usuario $idUsuario:")
            tareas.forEach { println(it) }
        }
    }
}