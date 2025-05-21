package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import aplicacion.IActividadService
import aplicacion.IUsuarioService
import aplicacion.UsuarioService
import dominio.Actividad
import dominio.Dashboard
import dominio.RangoFecha
import dominio.Status
import dominio.Tarea
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val CREAR_SUBTAREA = "3"
        private const val CREAR_USUARIO = "4"
        private const val ASIGNAR_USUARIO_A_TAREA = "5"
        private const val LISTAR_ACTIVIDADES = "6"
        private const val LISTAR_USUARIOS = "7"
        private const val VER_TAREAS_POR_USUARIO = "8"
        private const val VER_DASHBOARD = "9"
        private const val CAMBIAR_ESTADO_TAREA = "10"
        private const val FILTRAR_ACTIVIDADES = "11"
        private const val ELIMINAR_ACTIVIDAD = "12"
        private const val ELIMINAR_USUARIO = "13"
        private const val VER_HISTORIAL = "14"
        private const val SALIR = "0"
    }

    fun mostrarMenu(servicio: ActividadService, usuarioService: UsuarioService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Agregar Subtarea")
            println("4 | Agregar usuario")
            println("5 | Asignar un usuario a una tarea")
            println("6 | Listar Actividades")
            println("7 | Listar Usuarios")
            println("8 | Listar tareas por usuario")
            println("9 | Ver Dashboard")
            println("10| Cambiar Estado de Tarea")
            println("11| Filtrar Actividades")
            println("12| Eliminar Actividad")
            println("13| Eliminar Usuario")
            println("14 | Ver Historial de Tarea")
            println("0 | Salir")
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                CREAR_SUBTAREA -> agregarSubtarea(servicio)
                CREAR_USUARIO -> crearUsuario(usuarioService)
                ASIGNAR_USUARIO_A_TAREA -> asignarUsuarioATarea(servicio, usuarioService)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
                LISTAR_USUARIOS -> listarUsuarios(usuarioService)
                VER_TAREAS_POR_USUARIO -> verTareasPorUsuario(servicio)
                VER_DASHBOARD -> verDashboard(servicio)
                CAMBIAR_ESTADO_TAREA -> cambiarEstadoTarea(servicio)
                FILTRAR_ACTIVIDADES -> filtrarActividades(servicio)
                ELIMINAR_ACTIVIDAD -> eliminarActividad(servicio)
                ELIMINAR_USUARIO -> eliminarUsuario(usuarioService)
                VER_HISTORIAL -> verHistorial(servicio)
                SALIR -> {
                    println("Saliendo...")
                    seguir = false
                }
                else -> println("Opción no válida, prueba de nuevo")
            }
        } while (seguir)
    }

    private fun verHistorial(servicio: IActividadService) {
        println("\n=== Ver Historial de Tarea ===")
        print("ID de la tarea: ")
        val idTarea = readln().toIntOrNull()

        if (idTarea == null) {
            println("ID inválido.")
            return
        }

        val tarea = servicio.obtenerActividades().find { it.id == idTarea } as? Tarea

        if (tarea != null) {
            val historial = tarea.obtenerHistorial()
            if (historial.isEmpty()) {
                println("No hay historial para esta tarea.")
            } else {
                println("Historial de la Tarea ID: $idTarea")
                historial.forEach { println("Fecha: ${it.fecha} - Descripción: ${it.descripcion}") }
            }
        } else {
            println("Tarea no encontrada.")
        }
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

    private fun agregarSubtarea(servicio: ActividadService) {
        println("\n=== Agregar Subtarea ===")
        print("ID de la tarea principal: ")
        val idTarea = readln().toIntOrNull()

        if (idTarea == null) {
            println("El ID de la tarea principal no es válido.")
            return
        }

        print("Introduce la descripción de lasubtarea: ")
        val descripcion = readln()

        val fueAgregada = servicio.agregarSubtarea(idTarea, descripcion)

        if (fueAgregada) {
            println("Subtarea agregada correctamente.")
        } else {
            println("No se pudo agregar la subtarea.")
        }
    }

    private fun listarActividades(servicio: ActividadService) {
        println("\n=== Lista actividades ===")
        val actividades = servicio.obtenerActividades()
        if (actividades.isEmpty()) {
            println("No hay actividades.")
        } else {
            val tareasAsignadasComoSubtareas = actividades
                .filterIsInstance<Tarea>()
                .flatMap { it.subtareas }

            val actividadesPrincipales = actividades
                .filter { it !in tareasAsignadasComoSubtareas }
                .sortedBy { it.id }

            actividadesPrincipales.forEach { actividad ->
                if (actividad is Tarea) {
                    println(actividad.formatoTareas())
                } else {
                    println(actividad)
                }
            }
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

    private fun agregarEtiquetas(): List<String> {
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

    private fun filtrarActividades(servicio: ActividadService) {
        println("\n=== Filtrar Actividades ===")
        println("1 | Filtrar por Tipo (Tarea/Evento)")
        println("2 | Filtrar por Estado (ABIERTA/EN PROGRESO/FINALIZADA)")
        println("3 | Filtrar por Fecha (Hoy, Esta Semana, Este Mes)")
        print("Selecciona una opción de filtrado: ")

        when (readln()) {
            "1" -> {
                println("Selecciona el tipo de actividad:")
                println("1 | Tarea")
                println("2 | Evento")
                print("Selecciona una opción: ")
                val opcionTipo = readln().toIntOrNull()

                val tipo = when (opcionTipo) {
                    1 -> "TAREA"
                    2 -> "EVENTO"
                    else -> {
                        println("Opción no válida.")
                        return
                    }
                }

                val actividadesFiltradas = servicio.filtrarPorTipo(tipo)
                mostrarActividadesFiltradas(actividadesFiltradas)
            }
            "2" -> {
                println("Selecciona el estado:")
                println("1 | ABIERTA")
                println("2 | EN PROGRESO")
                println("3 | FINALIZADA")
                print("Selecciona una opción: ")
                val opcionEstado = readln().toIntOrNull()

                val estado = when (opcionEstado) {
                    1 -> Status.ABIERTA
                    2 -> Status.EN_PROGRESO
                    3 -> Status.CERRADA
                    else -> {
                        println("Opción no válida.")
                        return
                    }
                }

                val actividadesFiltradas = servicio.filtrarPorEstado(estado)
                mostrarActividadesFiltradas(actividadesFiltradas)
            }
            "3" -> {
                println("Introduce el rango de fechas (Hoy, Esta Semana, Este Mes): ")
                val rango = seleccionarRangoDeFecha()
                val actividadesFiltradas = servicio.filtrarPorFecha(rango)
                mostrarActividadesFiltradas(actividadesFiltradas)
            }
            "4" -> {
                println("Introduce las etiquetas a filtrar (separadas por ; ): ")
                val etiquetas = readln().split(";").map { it.trim() }
                val actividadesFiltradas = servicio.filtrarPorEtiquetas(etiquetas)
                mostrarActividadesFiltradas(actividadesFiltradas)
            }
            else -> println("Opción no válida")
        }
    }

    private fun mostrarActividadesFiltradas(actividades: List<Actividad>) {
        if (actividades.isEmpty()) {
            println("No hay actividades que coincidan con los filtros.")
        } else {
            println("\n=== Actividades Filtradas ===")
            actividades.forEach {
                println("${it.detalle} - Creada el: ${it.fechaCreacion}")
            }
        }
    }

    private fun seleccionarRangoDeFecha(): RangoFecha {
        println("Selecciona un rango de fechas:")
        println("1 | Hoy")
        println("2 | Esta Semana")
        println("3 | Este Mes")
        print("Selecciona una opción: ")

        val opcion = readln()
        val hoy = Date()
        val formato = SimpleDateFormat("dd/MM/yyyy")

        return when (opcion) {
            "1" -> {
                val inicioHoy = formato.parse(formato.format(hoy))
                RangoFecha(inicioHoy, inicioHoy)
            }
            "2" -> {
                val inicioSemana = obtenerInicioDeLaSemana(hoy)
                val finSemana = obtenerFinDeLaSemana(hoy)
                RangoFecha(inicioSemana, finSemana)
            }
            "3" -> {
                val inicioMes = obtenerInicioDelMes(hoy)
                val finMes = obtenerFinDelMes(hoy)
                RangoFecha(inicioMes, finMes)
            }
            else -> {
                println("Opción no válida")
                seleccionarRangoDeFecha()
            }
        }
    }

    private fun obtenerInicioDeLaSemana(fecha: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    private fun obtenerFinDeLaSemana(fecha: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    private fun obtenerInicioDelMes(fecha: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    private fun obtenerFinDelMes(fecha: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    private fun eliminarActividad(servicio: ActividadService) {
        println("Introduce el ID de la actividad a eliminar: ")
        val id = readln().toIntOrNull()
        if (id != null) {
            val actividadEliminada = servicio.eliminarActividadPorId(id)
            if (actividadEliminada != null) {
                println("Actividad eliminada: $actividadEliminada")
            } else {
                println("No se encontró una actividad con ese ID.")
            }
        } else {
            println("ID inválido.")
        }
    }

    private fun verDashboard(servicio: ActividadService) {
        println("\n=== Dashboard ===")
        val actividades = servicio.obtenerActividades()
        val dashboard = Dashboard()
        dashboard.mostrarResumen(actividades)
    }
}
