package org.practicatrim2.presentacion.presentacion

import aplicacion.ActividadService
import dominio.Status
import dominio.RangoFecha
import dominio.Actividad
import java.text.SimpleDateFormat
import java.util.Date

class UI {
    companion object {
        private const val CREAR_TAREA = "1"
        private const val CREAR_EVENTO = "2"
        private const val LISTAR_ACTIVIDADES = "3"
        private const val FILTRAR_ACTIVIDADES = "4"
        private const val ELIMINAR_ACTIVIDAD = "5"
        private const val SALIR = "6"
    }

    fun mostrarMenu(servicio: ActividadService) {
        var seguir = true

        do {
            println("\n=== TaskManager ===")
            println("1 | Agregar Tarea")
            println("2 | Agregar Evento")
            println("3 | Listar Actividades")
            println("4 | Filtrar Actividades")
            println("5 | Eliminar Actividad")
            println("6 | Salir")
            print("Selecciona una opción: ")

            when (readln()) {
                CREAR_TAREA -> agregarTarea(servicio)
                CREAR_EVENTO -> agregarEvento(servicio)
                LISTAR_ACTIVIDADES -> listarActividades(servicio)
                FILTRAR_ACTIVIDADES -> filtrarActividades(servicio)
                ELIMINAR_ACTIVIDAD -> eliminarActividad(servicio)
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

    private fun filtrarActividades(servicio: ActividadService) {
        println("\n=== Filtrar Actividades ===")
        println("1 | Filtrar por Tipo (Tarea/Evento)")
        println("2 | Filtrar por Estado (ABIERTA/FINALIZADA)")
        println("3 | Filtrar por Fecha (Hoy, Esta Semana, Este Mes)")
        print("Selecciona una opción de filtrado: ")

        when (readln()) {
            "1" -> {
                println("Introduce el tipo (Tarea/Evento): ")
                val tipo = readln().uppercase()
                val actividadesFiltradas = servicio.filtrarPorTipo(tipo)
                mostrarActividadesFiltradas(actividadesFiltradas)
            }
            "2" -> {
                println("Introduce el estado (ABIERTA/FINALIZADA): ")
                val estado = readln().uppercase()
                try {
                    val actividadesFiltradas = servicio.filtrarPorEstado(Status.valueOf(estado))
                    mostrarActividadesFiltradas(actividadesFiltradas)
                } catch (e: IllegalArgumentException) {
                    println("Estado inválido. Por favor, introduce un estado válido (ABIERTA/FINALIZADA).")
                }
            }
            "3" -> {
                println("Introduce el rango de fechas (Hoy, Esta Semana, Este Mes): ")
                val rango = seleccionarRangoDeFecha()
                val actividadesFiltradas = servicio.filtrarPorFecha(rango)
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
        return fecha
    }

    private fun obtenerFinDeLaSemana(fecha: Date): Date {
        return fecha
    }

    private fun obtenerInicioDelMes(fecha: Date): Date {
        return fecha
    }

    private fun obtenerFinDelMes(fecha: Date): Date {
        return fecha
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
}
