package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import dominio.Status
import datos.ActividadRepository


class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val LISTAR_ACTIVIDADES = "3"
        private const val CAMBIAR_ESTADO_TAREA = "5"
        private const val SALIR = "4"
    }

    fun mostrarMenu(servicio: ActividadService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Listar Actividades")
            println("5 | Cambiar Estado de Tarea")
            println("4 | Salir")
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
                CAMBIAR_ESTADO_TAREA -> cambiarEstadoTarea(servicio)
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
}
