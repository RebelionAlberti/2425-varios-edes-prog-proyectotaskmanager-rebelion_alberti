package aplicacion

import datos.IActividadRepository
import dominio.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class ActividadServiceTest : DescribeSpec({

    val mockRepo = mockk<IActividadRepository>()
    val servicio = ActividadService(mockRepo)

    beforeEach {
        clearAllMocks()
    }

    describe("crearTarea") {
        it("debería crear una tarea correctamente.") {
            val descripcion = "Estudiar Kotlin"
            val etiquetas = listOf("estudio", "kotlin")

            every { mockRepo.agregarActividad(any()) } returns true

            servicio.crearTarea(descripcion, etiquetas)

            verify { mockRepo.agregarActividad(match { it is Tarea && it.descripcion == descripcion }) }
        }

        it("debería crear una tarea incluso si la descripción está vacía.") {
            val descripcion = ""
            val etiquetas = listOf("Muy urgente;Ya de ya")

            every { mockRepo.agregarActividad(any()) } returns true

            servicio.crearTarea(descripcion, etiquetas)

            verify {
                mockRepo.agregarActividad(match {
                    it is Tarea && it.descripcion == descripcion
                })
            }
        }
    }

    describe("obtenerActividades") {
        it("debería devolver una lista con todas las actividades.") {
            val actividades = listOf(mockk<Actividad>(), mockk<Actividad>())
            every { mockRepo.recuperarTodas() } returns actividades

            val resultado = servicio.obtenerActividades()
            resultado shouldBe actividades
            verify { mockRepo.recuperarTodas() }
        }

        it("debería devolver una lista vacía si no hay actividades") {
            every { mockRepo.recuperarTodas() } returns emptyList()

            val resultado = servicio.obtenerActividades()

            resultado shouldBe emptyList()

            verify(exactly = 1) { mockRepo.recuperarTodas() }
        }

    }

    describe("actualizarEstadoTarea()") {
        it("debería actualizar la tarea correctamente.") {
            val id = 1
            val tareaMock = mockk<Tarea>()  // <- Mock no relajado
            every { tareaMock.estado = any() } just Runs
            every { tareaMock.agregarRegistro(any()) } just Runs
            every { mockRepo.recuperarPorId(id) } returns tareaMock
            every { mockRepo.actualizarActividad(tareaMock) } returns true

            val resultado = servicio.actualizarEstadoTarea(id, Status.CERRADA)

            resultado shouldBe true
            verify {
                tareaMock.estado = Status.CERRADA
                tareaMock.agregarRegistro("Estado cambiado a CERRADA")
                mockRepo.actualizarActividad(tareaMock)
            }
        }

        it("debería devolver false si la tarea no existe (ID inexistente)") {
            val idInexistente = 12345
            //clearMocks(mockRepo)
            every { mockRepo.recuperarPorId(idInexistente) } returns null

            val resultado = servicio.actualizarEstadoTarea(idInexistente, Status.CERRADA)
            resultado shouldBe false
            verify(exactly = 1) { mockRepo.recuperarPorId(idInexistente) }
            verify(exactly = 0) { mockRepo.actualizarActividad(any()) }
            confirmVerified(mockRepo)
        }
    }

    describe("asignarUsuarioATarea") {

        it("debería asignarle una tarea a un usuario correctamente") {
            val idTarea = 1
            val usuario = mockk<Usuario>()
            every { usuario.nombre } returns "Lucía"
            val tareaMock = mockk<Tarea>(relaxed = true)

            every { mockRepo.asignarUsuarioATarea(idTarea, usuario) } returns true
            every { mockRepo.recuperarPorId(idTarea) } returns tareaMock

            val resultado = servicio.asignarUsuarioATarea(idTarea, usuario)

            resultado shouldBe true
            verify { mockRepo.asignarUsuarioATarea(idTarea, usuario) }
            verify { mockRepo.recuperarPorId(idTarea) }
            verify { tareaMock.agregarRegistro("Tarea asignada a: Lucía") }
        }

        it("debería fallar al intentar asignar una tarea a un usuario nulo") {
            val idTarea = 2
            every { mockRepo.asignarUsuarioATarea(idTarea, null) } returns false

            val resultado = servicio.asignarUsuarioATarea(idTarea, null)

            resultado shouldBe false

            verify(exactly = 1) { mockRepo.asignarUsuarioATarea(idTarea, null) }
            verify(exactly = 0) { mockRepo.recuperarPorId(any()) }
        }

    }

    describe("eliminarActividadPorId") {

        it("debería eliminar una actividad por id correctamente") {
            val idActividad = 1
            val actividadMock = mockk<Actividad>()

            every { mockRepo.borrarPorId(idActividad) } returns actividadMock

            val resultado = servicio.eliminarActividadPorId(idActividad)

            resultado shouldBe actividadMock
            verify(exactly = 1) { mockRepo.borrarPorId(idActividad) }
        }

        it("debería devolver null si no se encuentra la actividad con ese id") {
            val idInexistente = 99

            every { mockRepo.borrarPorId(idInexistente) } returns null

            val resultado = servicio.eliminarActividadPorId(idInexistente)

            resultado shouldBe null
            verify(exactly = 1) { mockRepo.borrarPorId(idInexistente) }
        }
    }


})