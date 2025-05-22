package org.practicatrim2.presentacion.aplicacion

import dominio.Actividad
import dominio.Status
import dominio.Tarea

/**
 * Clase que muestra un resumen de las tareas.
 * Filtra las tareas por su estado y las imprime por consola.
 */
class Dashboard {
    /**
     * Muestra por pantalla un resumen de las tareas pasadas.
     * Separa las tareas en abiertas, en progreso y cerradas.
     *
     * @param actividades La lista de actividades que hay, de cualquier tipo.
     */
    fun mostrarResumen(actividades: List<Actividad>) {
        // Solo nos interesan las tareas, quitamos lo demás
        val tareas = actividades.filterIsInstance<Tarea>()

        // Filtramos las tareas según el estado que tengan
        val abiertas = tareas.filter { it.estado == Status.ABIERTA }
        val en_progreso = tareas.filter { it.estado == Status.EN_PROGRESO }
        val cerradas = tareas.filter { it.estado == Status.CERRADA }

        println("===== DASHBOARD DEL PROYECTO =====")
        println("Total de tareas principales: ${tareas.size}")
        println("Completadas: ${cerradas.size}")
        println("Pendientes: ${abiertas.size}")
        println("===================================")

        println("\n====== TAREAS ABIERTAS ======")
        abiertas.forEach { println("- $it") }

        println("\n====== TAREAS EN PROGRESO ======")
        en_progreso.forEach { println("- $it") }

        println("\n====== TAREAS CERRADAS ======")
        cerradas.forEach { println("- $it") }

        println("===================================")
    }
}
