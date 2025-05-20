package org.practicatrim2.presentacion.aplicacion

import dominio.Tarea
import dominio.Status
import dominio.Actividad


/**
 * # Dashboard
 *
 * Clase encargada de generar y mostrar un resumen visual del estado de las tareas.
 *
 * Esta clase filtra las actividades para identificar las instancias de `Tarea`, y agrupa las tareas según su estado
 * (`ABIERTA`, `EN_PROGRESO`, `CERRADA`),mostrando un resumen en consola.
 */
class Dashboard {


    /**
     * ## mostrarResumen
     *
     * Muestra por consola un resumen de las tareas que forman la lista de actividades.
     *
     * Agrupa las tareas según su estado actual y presenta un conteo general,
     * además de listarlas una a una.
     *
     * @param actividades Lista de actividades para filtrar las tareas.
     */
    fun mostrarResumen(actividades: List<Actividad>) {
        val tareas = actividades.filterIsInstance<Tarea>()

        val abiertas = tareas.filter { it.estado == Status.ABIERTA }
        val en_progreso = tareas.filter { it.estado == Status.EN_PROGRESO }
        val cerradas = tareas.filter { it.estado == Status.CERRADA }

        println("===== DASHBOARD DEL PROYECTO =====")
        println("Total de tareas principales: ${tareas.size}")
        println("Completadas: ${cerradas.size}")
        println("Pendientes: ${abiertas.size}")
        println("===================================")

        println("\n====== TAREAS ABIERTAS ======")
        abiertas.forEach { println("- ${it}") }

        println("\n====== TAREAS EN PROGRESO ======")
        en_progreso.forEach { println("- ${it}") }

        println("\n====== TAREAS CERRADAS ======")
        cerradas.forEach { println("- ${it}") }

        println("===================================")
    }
}

