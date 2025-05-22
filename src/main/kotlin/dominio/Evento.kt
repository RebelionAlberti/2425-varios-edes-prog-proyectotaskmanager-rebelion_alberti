package dominio

class Evento(
    val fechaRealizacion: String,
    val ubicacion: String,
    descripcion: String,
    override val etiquetas: List<String> = listOf(),
) : Actividad(descripcion) {
    init {
        require(fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}"""))) {
            "La fecha de realización debe tener el formato dd/MM/yyyy"
        }
    }

    override val detalle: String
        get() = "$id - $ubicacion - $descripcion"

    override fun toString(): String = "Evento=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Fecha de realización: $fechaRealizacion, Detalle: $detalle, Ubicacion: $ubicacion, Etiquetas: ${etiquetas.joinToString(", ")}]"
}
