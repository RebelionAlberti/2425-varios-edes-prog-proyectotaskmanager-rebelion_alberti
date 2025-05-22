package aplicacion

import datos.ActividadRepository
import dominio.Status
import dominio.Tarea
import dominio.Evento
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.practicatrim2.presentacion.aplicacion.TipoActividad

class ActividadServiceTest {

    private val repo = ActividadRepository()
    private val servicio = ActividadService(repo)

    @Test
    fun filtrarPorEstado_funcionaCorrectamente() {
        val tarea1 = Tarea.crearInstancia("Tarea ABIERTA", listOf()).apply { estado = Status.ABIERTA }
        val tarea2 = Tarea.crearInstancia("Tarea CERRADA", listOf()).apply { estado = Status.CERRADA }

        repo.agregarActividad(tarea1)
        repo.agregarActividad(tarea2)

        val abiertas = servicio.filtrarPorEstado(Status.ABIERTA)
        val cerradas = servicio.filtrarPorEstado(Status.CERRADA)

        assertTrue(abiertas.contains(tarea1))
        assertFalse(abiertas.contains(tarea2))

        assertTrue(cerradas.contains(tarea2))
        assertFalse(cerradas.contains(tarea1))
    }

    @Test
    fun filtrarPorTipo_funcionaCorrectamente() {
        val tarea = Tarea.crearInstancia("Tarea para test", listOf())
        val evento = Evento.crearInstancia("01/01/2025", "Lugar", "Evento para test", listOf())

        repo.agregarActividad(tarea)
        repo.agregarActividad(evento)

        val tareas = servicio.filtrarPorTipo(TipoActividad.TAREA)
        val eventos = servicio.filtrarPorTipo(TipoActividad.EVENTO)

        assertTrue(tareas.contains(tarea))
        assertFalse(tareas.contains(evento))

        assertTrue(eventos.contains(evento))
        assertFalse(eventos.contains(tarea))
    }
}
