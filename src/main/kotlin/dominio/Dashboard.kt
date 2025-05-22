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
        val tareas = actividades.filterIsInstance<Tarea>()

        val tareasPorEstado = tareas.groupBy { it.estado }

        println("===== DASHBOARD DEL PROYECTO =====")
        println("Total de tareas principales: ${tareas.size}")
        println("Completadas: ${tareasPorEstado[Status.CERRADA]?.size ?: 0}")
        println("Pendientes: ${tareasPorEstado[Status.ABIERTA]?.size ?: 0}")
        println("===================================")

        tareasPorEstado.forEach { (estado, tareasEstado) ->
            println("\n====== TAREAS $estado ======")
            tareasEstado.forEach { println("- $it") }
        }

        println("===================================")
    }
}
