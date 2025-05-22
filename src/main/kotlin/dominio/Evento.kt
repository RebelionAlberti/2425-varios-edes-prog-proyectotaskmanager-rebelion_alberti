package dominio

/**
 * Clase que representa un evento con fecha, lugar y descripción.
 *
 * Usa constructor privado y se crea con la función `crearInstancia`.
 * Tiene etiquetas para categorizarlo.
 *
 * @property fechaRealizacion La fecha en formato dd/MM/yyyy cuando se realizara el evento.
 * @property ubicacion El lugar donde se va a realizar el evento.
 * @property descripcion Qué es el evento (viene de la clase padre Actividad).
 * @property etiquetas Lista de etiquetas para clasificar el evento.
 */
class Evento private constructor(
    val fechaRealizacion: String,
    val ubicacion: String,
    descripcion: String,
    override val etiquetas: List<String> = listOf(),
) : Actividad(descripcion) {
    companion object {
        /**
         * Crea un evento nuevo usando los datos dados.
         */
        fun crearInstancia(
            fechaRealizacion: String,
            ubicacion: String,
            descripcion: String,
            etiquetas: List<String>,
        ): Evento = Evento(fechaRealizacion, ubicacion, descripcion, etiquetas)
    }

    init {
        // Se asegura que la fecha esté en formato dd/MM/yyyy
        require(fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}""")))
    }

    /**
     * Detalle corto del evento, con id, ubicación y descripción.
     */
    override val detalle: String
        get() = "$id - $ubicacion - $descripcion"

    /**
     * Cadena que muestra toda la información del evento.
     */
    override fun toString(): String = "Evento=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Fecha de realización: $fechaRealizacion,  Detalle: $detalle, Ubicacion: $ubicacion, Etiquetas: ${etiquetas.joinToString(", ")}]"
}
