## Identificación de la Actividad

- **ID de la Actividad:** 7.1 DAO a Ficheros
- **Módulo:** PROG
- **Unidad de Trabajo:** UD 7 - Interfaces texto y gráfica. Entrada/Salida
- **Fecha de Creación:** 18-05-2025
- **Fecha de Entrega:** 18-05-2025
- **Alumno(s):**
    - **Nombre y Apellidos:** Adrián Fernández Garrido
    - **Correo electrónico:** afergar613@g.educaand.es
    - **Iniciales del Alumno/Grupo:** AFG

## Descripción de la Actividad

- Implementación de un sistema de gestion de archivos para guardar los datos sobre los usuarios mediantes archivos tipo .csv

## Desarrollo de la Actividad

### Descripción del Desarrollo

- Se crea una interfaz DAO genérico y un UsuarioDAO para tener una interfaz generica con operaciones de tipo CRUD y un DAO para gestionar todos los datos de usuarios sobre un archivo .csv
- Creación de una interfaz IRepository que tiene todos los métodos necesarios para trabajar los datos de los usuarios y después se crea un Repository que implementa la interfaz IRepository.

### Código Fuente

```kotlin
class UsuarioDAO: IGenericoDAO<Usuario> {
    private val carpeta = File("CsvFiles")
    private val archivo = File(carpeta, "usuarios.csv")
    private val usuario = mutableListOf<Usuario>()

    init {
        if (!carpeta.exists())carpeta.mkdirs()
    }

    fun cargarUsuariosCsv() {
        if (!archivo.exists()) {
            archivo.writeText("id,nombre\n")
        }
        usuario.clear()
        var maxId = 0
        archivo.forEachLine { linea ->
            if (linea.startsWith("id"))
                return@forEachLine
            val partes = linea.split(",")
            if (partes.size >= 2) {
                val id = partes[0].toIntOrNull()
                val nombre = partes[1]
                if (id != null) {
                    usuario.add(Usuario(id, nombre))
                    if (id > maxId) maxId = id
                }
            }
        }
        Usuario.configurarContador(maxId + 1)
    }

    fun guardarUsuariosCsv() {
        val contenido = buildString {
            append("id,nombre\n")
            usuario.forEach {
                append("${it.id},${it.nombre}\n")
            }
        }
        archivo.writeText(contenido)
    }

    override fun create(t: Usuario): Usuario {
        usuario.add(t)
        guardarUsuariosCsv()
        return t
    }

    override fun read(): List<Usuario> {
        return usuario.toList()
    }

    fun readById(id: Int): Usuario? {
        return usuario.find { it.id == id }
    }

    override fun update(t: Usuario): Boolean {
        val actual = usuario.find { it.id == t.id }
        return if (actual != null) {
            usuario.remove(actual)
            usuario.add(t)
            guardarUsuariosCsv()
            true
        }
        else {
            false
        }
    }

    override fun delete(id: Int): Usuario? {
        val usuarios = usuario.find { it.id == id }
        return if (usuarios != null) {
            usuario.remove(usuarios)
            guardarUsuariosCsv()
            usuarios
        }
        else {
            null
        }
    }
}
```

## Conclusiones

- Se aprende a gestionar datos en archivos de tipo .csv
- Se aprende como funcionan las operaciones de entrada y salida de la información