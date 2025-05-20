package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import dominio.Status
import aplicacion.IActividadService
import aplicacion.IUsuarioService
import aplicacion.UsuarioService
import dominio.RangoFecha
import dominio.Actividad
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import dominio.Tarea
import org.practicatrim2.presentacion.aplicacion.Dashboard

/**
 * # UI
 *
 * Clase encargada de la interfaz de usuario en consola para interactuar con el sistema TaskManager.
 *
 * Proporciona un menú con opciones para ejecutar todas las funciones disponibles.
 */
class UI {
    companion object {

        /**
         * Constantes que representan las opciones del menú para facilitar la selección de acciones.
         */
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


    /**
     * ## mostrarMenu
     *
     * Muestra el menú principal y permite al usuario seleccionar diferentes opciones para
     * gestionar tareas, eventos, usuarios y otras funcionalidades.
     *
     * @param servicio Servicio que administra las actividades (tareas y eventos).
     * @param usuarioService Servicio que administra los usuarios.
     */
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


    /**
     * ## verHistorial
     *
     * Muestra el historial de cambios o eventos asociados a una tarea específica.
     *
     * Solicita al usuario el ID de la tarea, valida la entrada y busca la tarea en el servicio proporcionado.
     * Si la tarea existe, imprime cada entrada del historial con su fecha y descripción.
     * Si no se encuentra la tarea o el historial está vacío, muestra mensajes informativos según lo ocurrido.
     *
     * @param servicio Servicio que provee acceso a las actividades y tareas.
     */
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


    /**
     * ## agregarTarea
     *
     * Solicita al usuario la descripción y etiquetas para crear una nueva tarea, y la agrega
     * mediante el servicio proporcionado.
     *
     * @param servicio Servicio encargado de administrar las actividades.
     */
    private fun agregarTarea(servicio: ActividadService) {
        println("\n=== Agregar Tarea ===")
        print("Descripción: ")
        val descripcion = readln()
        val etiquetas = agregarEtiquetas()
        servicio.crearTarea(descripcion, etiquetas)
        println("Tarea agregada correctamente.")
    }


    /**
     * ## agregarEvento
     *
     * Solicita datos para crear un evento (descripción, fecha, ubicación y etiquetas) y lo agrega
     * mediante el servicio.
     *
     * @param servicio Servicio encargado de administrar las actividades.
     */
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


    /**
     * ## agregarSubtarea
     *
     * Permite agregar una subtarea a una tarea principal existente.
     *
     * @param servicio Servicio encargado de administrar las actividades.
     */
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


    /**
     * ## listarActividades
     *
     * Imprime por consola la lista de actividades principales disponibles en el sistema.
     *
     * Obtiene todas las actividades desde el servicio, y filtra aquellas que no son subtareas para mostrar solo las
     * actividades principales.
     * Ordena estas actividades por su ID y las imprime.
     * Para las tareas, utiliza un formato específico que incluye sus subtareas.
     * Si no hay actividades, muestra un mensaje indicando que no existen actividades.
     *
     * @param servicio Servicio que proporciona acceso a las actividades.
     */
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


    /**
     * ## cambiarEstadoTarea
     *
     * Permite cambiar el estado de una tarea existente.
     *
     * Solicita al usuario el ID de la tarea que desea actualizar y el nuevo estado deseado.
     * Los estados disponibles son: ABIERTA, EN PROGRESO y FINALIZADA.
     * Luego, intenta actualizar el estado de la tarea mediante el servicio proporcionado.
     * Imprime un mensaje indicando si la actualización se hizo correctamente o si no se encontró dicha tarea.
     *
     * @param servicio Servicio utilizado para actualizar el estado de la tarea.
     */
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


    /**
     * ##crearUsuario
     *
     * Solicita el nombre de un nuevo usuario y lo crea a través del servicio correspondiente.
     *
     * @param servicio Servicio encargado de administrar los usuarios.
     */
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


    /**
     * ## eliminarUsuario
     *
     * Solicita el nombre de un nuevo usuario y lo elimina a través del servicio correspondiente.
     *
     * @param servicio Servicio encargado de administrar los usuarios.
     */
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


    /**
     * ## listarUsuarios
     *
     * Muestra en consola la lista de usuarios registrados.
     *
     * Obtiene la lista de usuarios desde el servicio proporcionado.
     * Si no hay usuarios registrados, informa al usuario con un mensaje por consola.
     * De lo contrario, imprime cada usuario por consola.
     *
     * @param servicio Servicio para obtener la lista de usuarios.
     */
    private fun listarUsuarios(servicio: IUsuarioService) {
        println("\n=== Lista de Usuarios ===")
        val usuarios = servicio.obtenerUsuarios()
        if (usuarios.isEmpty()) {
            println("No hay usuarios registrados.")
        } else {
            usuarios.forEach { println(it) }
        }
    }


    /**
     * ## asignarUsuarioATarea
     *
     * Asigna o desasigna un usuario a una tarea específica.
     *
     * Solicita al usuario Id de la tarea e Id del usuario a asignar.
     * Si se ingresa `0` como Id de usuario, se desasigna cualquier usuario asignado a la tarea.
     * Comprueba que los Ids ingresados sean válidos y que el usuario exista (si no es `0`).
     * Luego, realiza la asignación o desasignación a través del servicio de actividades.
     * Muestra mensajes informativos según el resultado de la operación.
     *
     * @param actividadService Servicio para gestionar las actividades y tareas.
     * @param usuarioService Servicio para gestionar los usuarios.
     */
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


    /**
     * ## agregarEtiquetas
     *
     * Solicita al usuario que ingrese etiquetas para una tarea o evento.
     *
     * Las etiquetas deben ser ingresadas en una única línea, separadas por punto y coma (`;`).
     * La función divide la cadena ingresada en una lista de etiquetas individuales.
     *
     * @return Lista de etiquetas ingresadas por el usuario.
     */
    private fun agregarEtiquetas(): List<String>{

        println("Ingrese las etiquetas para la tarea o evento (separadas por ; ):   ")
        val etiquetas = readln()
        return etiquetas.split(";")

    }


    /**
     * ## verTareasPorUsuario
     *
     * Muestra las tareas asignadas a un usuario específico.
     *
     * Solicita al usuario ingresar Id del usuario para buscar.
     * Si el Id es inválido, muestra un mensaje de error.
     * Si el usuario no tiene tareas asignadas, informa que no hay tareas.
     * De lo contrario, imprime la lista de tareas asignadas a ese usuario.
     *
     * @param servicio Servicio que provee acceso a las actividades y tareas.
     */
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


    /**
     * ## filtrarActividades
     *
     * Permite al usuario filtrar actividades según diferentes criterios.
     *
     * Muestra un menú para seleccionar el tipo de filtro:
     * 1. Filtrar por tipo de actividad (Tarea o Evento).
     * 2. Filtrar por estado de la actividad (ABIERTA, EN PROGRESO, FINALIZADA).
     * 3. Filtrar por rango de fecha (Hoy, Esta Semana, Este Mes).
     * 4. Filtrar por etiquetas (separadas por punto y coma).
     *
     * Según la opción elegida, solicita detalles para realizar el filtrado.
     * Luego, muestra las actividades que cumplen con los criterios seleccionados.
     *
     * @param servicio Servicio que provee métodos para obtener y filtrar actividades.
     */
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

                val tipo = when(opcionTipo) {
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


    /**
     * ## mostrarActividadesFiltradas
     *
     * Muestra una lista de actividades filtradas en la consola.
     *
     * Si la lista está vacía, informa que no hay actividades que coincidan con los filtros.
     * En caso contrario, imprime los detalles y la fecha de creación de cada actividad.
     *
     * @param actividades Lista de actividades a mostrar.
     */
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


    /**
     * ## seleccionarRangoDeFecha
     *
     * Solicita al usuario seleccionar un rango de fechas y devuelve un objeto RangoFecha
     * que representa el intervalo seleccionado.
     *
     * Las opciones disponibles son:
     * 1. Hoy
     * 2. Esta Semana
     * 3. Este Mes
     *
     * Si el usuario ingresa una opción inválida, se solicita la selección nuevamente.
     *
     * @return Un objeto [RangoFecha] con las fechas de inicio y fin correspondientes al rango seleccionado.
     */
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


    /**
     * ## obtenerInicioDeLaSemana
     *
     * Calcula la fecha correspondiente al inicio de la semana (lunes) para una fecha dada.
     *
     * La hora, minutos, segundos y milisegundos se establecen en cero para representar el inicio del día.
     *
     * @param fecha La fecha de referencia para calcular el inicio de la semana.
     * @return Un objeto Date que representa el lunes (inicio de la semana) de la semana de la fecha proporcionada.
     */
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


    /**
     * ## obtenerFinDeLaSemana
     *
     * Calcula la fecha correspondiente al final de la semana (domingo) para una fecha dada.
     *
     * La hora se establece a las 23:59:59.999 para representar el final del día.
     *
     * @param fecha La fecha de referencia para calcular el final de la semana.
     * @return Un objeto Date que representa el domingo (fin de la semana) de la semana de la fecha proporcionada.
     */
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


    /**
     * ## obtenerInicioDelMes
     *
     * Calcula la fecha correspondiente al inicio del mes para una fecha dada.
     *
     * La fecha resultante corresponde al primer día del mes con la hora establecida
     * a las 00:00:00.000 (inicio del día).
     *
     * @param fecha La fecha de referencia para calcular el inicio del mes.
     * @return Un objeto Date que representa el primer día del mes de la fecha proporcionada.
     */
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


    /**
     * ## obtenerFinDelMes
     *
     * Calcula la fecha correspondiente al inicio del mes para una fecha dada.
     *
     * La fecha resultante corresponde al primer día del mes con la hora establecida
     * a las 00:00:00.000 (inicio del día).
     *
     * @param fecha La fecha de referencia para calcular el inicio del mes.
     * @return Un objeto Date que representa el primer día del mes de la fecha proporcionada.
     */
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


    /**
     * ## eliminarActividad
     *
     * Elimina una actividad existente a partir de su Id.
     *
     * Solicita al usuario que introduzca Id de la actividad que desea eliminar.
     * Comprueba si Id es válido y, en caso afirmativo, intenta eliminar la actividad
     * utilizando el servicio proporcionado. Muestra un mensaje indicando si la actividad
     * fue eliminada correctamente o si no se encontró ninguna actividad con Id dado.
     *
     * @param servicio Instancia de ActividadService utilizada para eliminar la actividad.
     */
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


    /**
     * ## verDashboard
     *
     * Muestra un resumen general (dashboard) de todas las actividades registradas.
     *
     * Obtiene la lista de actividades desde el servicio proporcionado y muestra el resumen al objeto `Dashboard`.
     *
     * @param servicio Instancia de ActividadService utilizada para obtener las actividades.
     */
    private fun verDashboard(servicio: ActividadService) {
        println("\n=== Dashboard ===")
        val actividades = servicio.obtenerActividades()
        val dashboard = Dashboard()
        dashboard.mostrarResumen(actividades)
    }
}
