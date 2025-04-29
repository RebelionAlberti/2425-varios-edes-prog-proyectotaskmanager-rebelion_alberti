package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import dominio.Tarea


class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val CREAR_SUBTAREA = "3"
        private const val LISTAR_ACTIVIDADES = "4"
        private const val SALIR = "5"
    }

    fun mostrarMenu(servicio: ActividadService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Agregar Subtarea")
            println("4 | Listar Actividades")
            println("5 | Salir")
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                CREAR_SUBTAREA -> agregarSubtarea(servicio)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
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


}
