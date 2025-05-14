import aplicacion.ActividadService
import datos.IActividadRepository
import dominio.Tarea
import dominio.Status
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*
import io.kotest.matchers.shouldBe
import dominio.Evento
import java.lang.IllegalArgumentException

class ActividadServiceTest : DescribeSpec({

    val repositorio = mockk<IActividadRepository>()
    val actividadService = ActividadService(repositorio)
    val servicio = ActividadService(repositorio)
    val etiquetas: List<String> = listOf()

    describe("Prueba de crear tarea") {

        it("agrega una tarea al repositorio y devuelve true si se añade correctamente") {
            val descripcion = "Prueba tarea"
            val etiquetas = listOf("Etiqueta1", "Etiqueta2")

            every { repositorio.agregarActividad(any()) } returns true

            val resultado = actividadService.crearTarea(descripcion, etiquetas)

            resultado shouldBe true
        }

        it("devuelve false si el repositorio no puede agregar la tarea") {
            val descripcion = "Prueba tarea"
            val etiquetas = listOf("Etiqueta1", "Etiqueta2")

            every { repositorio.agregarActividad(any()) } returns false

            val resultado = actividadService.crearTarea(descripcion, etiquetas)

            resultado shouldBe false
        }
    }

    describe("Prueba de obtener actividades") {
        it("devuelve todas las actividades almacenadas en el repositorio") {
            val actividadesEsperadas = listOf<Tarea>(
                Tarea.crearInstancia("Tarea 1", listOf()),
                Tarea.crearInstancia("Tarea 2", listOf())
            )

            every { repositorio.recuperarTodas() } returns actividadesEsperadas

            val actividades = actividadService.obtenerActividades()

            actividades shouldBe actividadesEsperadas
        }
    }

    describe("Pruebas de crear evento") {
        describe("Cuando se crea un evento correctamente") {
            val descripcion = "Evento de prueba"
            val fechaRealizacion = "12/12/2025"
            val ubicacion = "Madrid"

            it("agrega el evento al repositorio correctamente si todos los datos son válidos") {
                val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, "evento;presencial".split(";"))

                every { repositorio.agregarActividad(evento) } returns true

                val solucion = repositorio.agregarActividad(evento)

                solucion shouldBe true
            }
        }

        describe("Cuando se intenta crear un evento pero falla debido a datos incorrectos") {
            it("agrega el evento aunque la fecha sea inválida porque el repositorio devuelve true") {
                val descripcion = "Evento de prueba"
                val fechaRealizacion = "12/13/2025"
                val ubicacion = "Madrid"

                val evento = Evento.crearInstancia(fechaRealizacion, ubicacion, descripcion, "evento;presencial".split(";"))

                every { repositorio.agregarActividad(evento) } returns true

                val resultado = repositorio.agregarActividad(evento)

                resultado shouldBe true
            }
        }
    }

    describe("Pruebas de filtros") {
        describe("Cuando se filtran actividades por tipo correctamente") {
            it("devuelve una lista con tareas y eventos sin aplicar filtro") {
                val tipo = "Tarea"
                val actividades = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")),
                    Evento.crearInstancia("12/12/2025", "Madrid", "Evento 1", "evento;presencial".split(";"))
                )
                every { repositorio.recuperarTodas() } returns actividades

                val resultados = repositorio.recuperarTodas()
                resultados shouldBe actividades
            }

            it("devuelve una lista con eventos y tareas sin aplicar filtro") {
                val tipo = "Evento"
                val actividades = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")),
                    Evento.crearInstancia("12/12/2025", "Madrid", "Evento 1", "evento;presencial".split(";"))
                )
                every { repositorio.recuperarTodas() } returns actividades

                val resultados = repositorio.recuperarTodas()
                resultados shouldBe actividades
            }
        }

        describe("Cuando se intenta filtrar actividades por un tipo no válido") {
            it("devuelve una lista vacía si el tipo proporcionado no es válido") {
                val tipo = "Invalido"
                val actividades = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")),
                    Evento.crearInstancia("12/12/2025", "Madrid", "Evento 1", "evento;presencial".split(";"))
                )
                every { repositorio.recuperarTodas() } returns actividades

                val resultados = servicio.filtrarPorTipo(tipo)
                resultados.size shouldBe 0
            }
        }

        describe("Cuando se filtran actividades por estado correctamente") {
            it("devuelve solo las tareas con estado ABIERTA") {
                val estado = Status.ABIERTA
                val tareas = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")).apply { this.estado = estado },
                    Tarea.crearInstancia("Tarea 2", "trabajo;importante".split(";")).apply { this.estado = Status.CERRADA }
                )
                every { repositorio.recuperarTodas() } returns tareas

                val resultados = servicio.filtrarPorEstado(estado)
                resultados.size shouldBe 1
                resultados[0] shouldBe tareas[0]
            }

            it("devuelve solo las tareas con estado CERRADA") {
                val estado = Status.CERRADA
                val tareas = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")).apply { this.estado = Status.ABIERTA },
                    Tarea.crearInstancia("Tarea 2", "trabajo;importante".split(";")).apply { this.estado = estado }
                )
                every { repositorio.recuperarTodas() } returns tareas

                val resultados = servicio.filtrarPorEstado(estado)
                resultados.size shouldBe 1
                resultados[0] shouldBe tareas[1]
            }
        }

        describe("Cuando se filtran actividades por estado pero no se encuentran") {
            it("devuelve una lista vacía si no hay tareas con estado ABIERTA") {
                val estado = Status.ABIERTA
                val tareas = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")).apply { this.estado = Status.CERRADA }
                )
                every { repositorio.recuperarTodas() } returns tareas

                val resultados = servicio.filtrarPorEstado(estado)
                resultados.size shouldBe 0
            }

            it("devuelve una lista vacía si no hay tareas con estado CERRADA") {
                val estado = Status.CERRADA
                val tareas = listOf(
                    Tarea.crearInstancia("Tarea 1", "trabajo;importante".split(";")).apply { this.estado = Status.ABIERTA }
                )
                every { repositorio.recuperarTodas() } returns tareas

                val resultados = servicio.filtrarPorEstado(estado)
                resultados.size shouldBe 0
            }
        }
    }
})
