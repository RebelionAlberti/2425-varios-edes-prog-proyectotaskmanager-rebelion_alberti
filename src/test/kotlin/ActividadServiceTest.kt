package aplicacion

import datos.ActividadRepository
import dominio.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.practicatrim2.presentacion.aplicacion.TipoActividad

class ActividadServiceTest {

    private lateinit var repo: ActividadRepository
    private lateinit var servicio: ActividadService

    @BeforeEach
    fun setup() {
        repo = ActividadRepository()
        servicio = ActividadService(repo)
    }

    @Test
    fun filtrarPorTipo_funcionaParaTareaYEvento() {
        val tarea = Tarea.crearInstancia("Tarea para test", listOf())
        val evento = Evento.crearInstancia("01/01/2025", "Lugar", "Evento para test", listOf())

        repo.agregarActividad(tarea)
        repo.agregarActividad(evento)

        val filtradoTareas = servicio.filtrarPorTipo(TipoActividad.TAREA)
        val filtradoEventos = servicio.filtrarPorTipo(TipoActividad.EVENTO)

        assertTrue(filtradoTareas.all { it is Tarea })
        assertTrue(filtradoTareas.any { it.id == tarea.id })
        assertFalse(filtradoTareas.any { it.id == evento.id })

        assertTrue(filtradoEventos.all { it is Evento })
        assertTrue(filtradoEventos.any { it.id == evento.id })
        assertFalse(filtradoEventos.any { it.id == tarea.id })
    }
}
