package dominio

class Dashboard {

    fun mostrarResumen(actividades: List<Actividad>) {
        val tareas = actividades.filterIsInstance<Tarea>()

        val abiertas = tareas.filter { it.estado == Status.ABIERTA }
        val enProgreso = tareas.filter { it.estado == Status.EN_PROGRESO }
        val cerradas = tareas.filter { it.estado == Status.CERRADA }

        println("===== DASHBOARD DEL PROYECTO =====")
        println("Total de tareas principales: ${tareas.size}")
        println("Completadas: ${cerradas.size}")
        println("Pendientes: ${abiertas.size}")
        println("===================================")

        println("\n====== TAREAS ABIERTAS ======")
        abiertas.forEach { println("- $it") }

        println("\n====== TAREAS EN PROGRESO ======")
        enProgreso.forEach { println("- $it") }

        println("\n====== TAREAS CERRADAS ======")
        cerradas.forEach { println("- $it") }

        println("===================================")
    }
}
