package org.practicatrim2.presentacion.aplicacion

import dominio.Actividad
import dominio.Status
import dominio.Tarea

class Dashboard {

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
        abiertas.forEach { println("- $it") }

        println("\n====== TAREAS EN PROGRESO ======")
        en_progreso.forEach { println("- $it") }

        println("\n====== TAREAS CERRADAS ======")
        cerradas.forEach { println("- $it") }

        println("===================================")
    }
}
