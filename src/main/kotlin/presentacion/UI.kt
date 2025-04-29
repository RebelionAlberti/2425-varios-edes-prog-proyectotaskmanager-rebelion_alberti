package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import org.practicatrim2.presentacion.aplicacion.Dashboard

class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val LISTAR_ACTIVIDADES = "3"
        private const val VER_DASHBOARD = "5"
        private const val SALIR = "4"
    }

    fun mostrarMenu(servicio: ActividadService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Listar Actividades")
            println("4 | Salir")
            println("5 | Ver Dashboard") // Nueva opción
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
                VER_DASHBOARD -> verDashboard(servicio)
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

    private fun verDashboard(servicio: ActividadService) {
        println("\n=== Dashboard ===")
        val actividades = servicio.obtenerActividades()
        val dashboard = Dashboard()
        dashboard.mostrarResumen(actividades)
    }
}
