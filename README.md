## Identificación de la Actividad

- **ID de la Actividad:** 3.3. Test Unitarios y Moock
- **Módulo:** EDES
- **Unidad de Trabajo:** Unidad 3 Pruebas y Depuración
- **Fecha de Creación:** 14-05-2025
- **Fecha de Entrega:** 14-05-2025
- **Alumno(s):**
    - **Nombre y Apellidos:** Adrián Fernández Garrido
    - **Correo electrónico:** afergar613@g.educaand.es
    - **Iniciales del Alumno/Grupo:** AFG

## Descripción de la Actividad

- Se realizan tests sobre UsuarioService usando Kotest y Mockk.
- Los test pueden ser casos nominales que verifican el comportamiento esperado y casos de error.

## Desarrollo de la Actividad

### Descripción del Desarrollo

- Se crean los tests usando Kotest y Mockk para simular comportamientos.
- Se crean 3 test por cada método (4 métodos x 3 test = 12 tests).
- Se muestran capturas de las pruebas exitosas y fragmentos del código de las pruebas.
- Se crea una tabla donde se realiza un resumen del funcionamiento de todos los test y el comportamiento esperado.

### Código Fuente

 ```
describe("Métodos de UsuarioService") {
        context("crearUsuario") {
            it("Crear usuario con nombre que incluye letras y números") {
                val nombre = "Adri11"
                coEvery { mockRepo.agregar(any()) } returns true

                val resultado = usuarioService.crearUsuario(nombre)
                resultado shouldBe true
                coVerify { mockRepo.agregar(any()) }
            }

            it("Devolver false al crear usuario con nombre vacío") {
                val nombre = ""
                coEvery { mockRepo.agregar(any()) } returns false

                val resultado = usuarioService.crearUsuario(nombre)
                resultado shouldBe false
            }

            it("Crear un usuario con nombre que contiene espacios") {
                val nombre = "Adrian Fernandez"
                coEvery { mockRepo.agregar(any()) } returns true

                val resultado = usuarioService.crearUsuario(nombre)
                resultado shouldBe true
                coVerify { mockRepo.agregar(any()) }
            }
        }
```

## Conclusiones

- Se aprende desarrollar pruebas para nuestro código tanto con Kotest como con Mockk para simular comportamientos.