package dominio


/**
 * # Evento
 *
 * Representa un evento que hereda de la clase abstracta Actividad.
 *
 * Un evento formado por una fecha de realización, una ubicación,
 * una descripción y una lista de etiquetas informativas.
 *
 * @property fechaRealizacion Fecha en la que se realizará el evento, con formato "dd/MM/yyyy".
 * @property ubicacion Lugar donde se llevará a cabo el evento.
 * @property etiquetas Lista de etiquetas informativas sobre el evento.
 */
class Evento private constructor(val fechaRealizacion: String, val ubicacion: String, descripcion: String, override val etiquetas: List<String> = listOf()) : Actividad(descripcion) {

    /**
     * Companion object para la creación de Eventos.
     */
    companion object {

        /**
         * ## crearInstancia
         *
         * Crea una nueva instancia de Evento con los parámetros dados.
         *
         * @param fechaRealizacion Fecha del evento en formato "dd/MM/yyyy".
         * @param ubicacion Ubicación del evento.
         * @param descripcion Descripción general del evento.
         * @param etiquetas Lista de etiquetas informaticas.
         * @return Nueva instancia de Evento.
         */
        fun crearInstancia(fechaRealizacion: String, ubicacion: String, descripcion: String, etiquetas: List<String>): Evento {
            return Evento(fechaRealizacion, ubicacion, descripcion, etiquetas)
        }
    }

    init {
        require(fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}""")))
    }


    /**
     * Devuelve una descripción detallada del evento para mostrar más fácilmente en listados.
     */
    override val detalle: String
        get() = "$id - $ubicacion - $descripcion"


    /**
     * ## toString
     *
     * Representación en forma de cadena del evento, con todos sus atributos.
     *
     * @return String con la información de la tarea.
     */
    override fun toString(): String {
        return "Evento=[ID: $id, Descripcion: $descripcion, Fecha de creación: $fechaCreacion, Fecha de realización: $fechaRealizacion,  Detalle: $detalle, Ubicacion: $ubicacion, Etiquetas: ${etiquetas.joinToString(", ")}]"
    }
}