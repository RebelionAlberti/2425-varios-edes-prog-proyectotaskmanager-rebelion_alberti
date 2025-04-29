package dominio

class Evento private constructor(val fechaRealizacion: String, val ubicacion: String, descripcion: String) : Actividad(descripcion) {
    // Companion object
    companion object {
        fun crearInstancia(fechaRealizacion: String, ubicacion: String, descripcion: String): Evento {
            return Evento(fechaRealizacion, ubicacion, descripcion)
        }
    }

    init {
        require(fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}""")))
    }

    // Métodos
    override val detalle: String
        get() = "$id - $ubicacion - $descripcion"

    override fun toString(): String {
        return "Evento=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Fecha de realización: $fechaRealizacion,  Detalle: $detalle, Ubicacion: $ubicacion]"
    }
}