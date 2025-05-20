package dominio

/**
 * Evento con fecha de realización, ubicación, descripción y etiquetas.
 *
 * Hereda de [Actividad].
 *
 * @property fechaRealizacion Fecha de realizacion del evento con formato dd/MM/yyyy.
 * @property ubicacion Lugar donde se celebrará el evento.
 * @property etiquetas Lista de etiquetas del evento.
 * @constructor Constructor privado para crearlo mediante al metodo [crearInstancia].
 *
 * @param descripcion Descripción del evento.
 */
class Evento private constructor(
    val fechaRealizacion: String,
    val ubicacion: String,
    descripcion: String,
    override val etiquetas: List<String> = listOf()
) : Actividad(descripcion) {

    companion object {
        /**
         * Crea una instancia de [Evento].
         *
         * @param fechaRealizacion Fecha del evento en formato dd/MM/yyyy.
         * @param ubicacion Ubicación del evento.
         * @param descripcion Descripción del evento.
         * @param etiquetas Lista de etiquetas para el evento.
         * @return Nueva instancia de [Evento].
         */
        fun crearInstancia(
            fechaRealizacion: String,
            ubicacion: String,
            descripcion: String,
            etiquetas: List<String>
        ): Evento {
            return Evento(fechaRealizacion, ubicacion, descripcion, etiquetas)
        }
    }

    init {
        require(fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}"""))) {
            "La fecha debe tener formato dd/MM/yyyy"
        }
    }

    /**
     * Detalles del evento (id, ubicacion, descripcion).
     */
    override val detalle: String
        get() = "$id - $ubicacion - $descripcion"

    /**
     * Cadena del evento con todos sus datos.
     *
     * @return Cadena con toda la información del evento.
     */
    override fun toString(): String {
        return "Evento=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Fecha de realización: $fechaRealizacion, Detalle: $detalle, Ubicacion: $ubicacion, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}
