package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import dominio.Status
import datos.ActividadRepository
import aplicacion.IActividadService
import aplicacion.IUsuarioService
import aplicacion.UsuarioService


class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val CREAR_USUARIO = "3"
        private const val ASIGNAR_USUARIO_A_TAREA = "4"
        private const val LISTAR_ACTIVIDADES = "5"
        private const val LISTAR_USUARIOS = "6"
        private const val VER_TAREAS_POR_USUARIO = "7"
        private const val CAMBIAR_ESTADO_TAREA = "8"
        private const val ELIMINAR_USUARIO = "9"
        private const val SALIR = "0"
    }

    fun mostrarMenu(servicio: ActividadService, usuarioService: UsuarioService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Agregar usuario")
            println("4 | Asignar un usuario a una tarea")
            println("5 | Listar Actividades")
            println("6 | Listar Usuarios")
            println("7 | Listar tareas por usuario")
            println("8 | Cambiar Estado de Tarea")
            println("9 | Eliminar Usuario")
            println("0 | Salir")
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                CREAR_USUARIO -> crearUsuario(usuarioService)
                ASIGNAR_USUARIO_A_TAREA -> asignarUsuarioATarea(servicio, usuarioService)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
                LISTAR_USUARIOS -> listarUsuarios(usuarioService)
                VER_TAREAS_POR_USUARIO -> verTareasPorUsuario(servicio)
                CAMBIAR_ESTADO_TAREA -> cambiarEstadoTarea(servicio)
                ELIMINAR_USUARIO -> eliminarUsuario(usuarioService)
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
        val etiquetas = agregarEtiquetas()
        servicio.crearTarea(descripcion, etiquetas)
        println("Tarea agregada correctamente.")
    }


    private fun agregarEvento(servicio: ActividadService) {
        println("\n=== Agregar Evento ===")
        print("Descripción: ")
        val descripcion = readln()
        print("Fecha de realización, formato -> (dd/MM/yyyy): ")
        val fechaRealizacion = readln()

        val etiquetas = agregarEtiquetas()

        print("Ubicación: ")
        val ubicacion = readln()
        servicio.crearEvento(descripcion, fechaRealizacion, ubicacion, etiquetas)
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

    private fun cambiarEstadoTarea(servicio: ActividadService) {
        println("\n=== Cambiar Estado de Tarea ===")
        print("Ingrese el ID de la tarea que desea actualizar: ")
        val id = readln().toInt()

        println("Seleccione el nuevo estado para la tarea:")
        println("1. ABIERTA")
        println("2. EN PROGRESO")
        println("3. FINALIZADA")
        print("Seleccione una opción: ")
        val opcion = readln().toInt()

        val nuevoEstado = when (opcion) {
            1 -> Status.ABIERTA
            2 -> Status.EN_PROGRESO
            3 -> Status.CERRADA
            else -> {
                println("Opción no válida.")
                return
            }
        }

        val exito = servicio.actualizarEstadoTarea(id, nuevoEstado)
        if (exito) {
            println("El estado de la tarea se ha actualizado correctamente.")
        } else {
            println("No se encontró la tarea con el ID proporcionado.")
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

    private fun agregarEtiquetas(): List<String>{

        println("Ingrese las etiquetas para la tarea o evento (separadas por ; ):   ")
        val etiquetas = readln()
        return etiquetas.split(";")

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

