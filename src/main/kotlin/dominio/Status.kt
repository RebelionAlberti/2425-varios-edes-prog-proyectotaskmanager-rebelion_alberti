package dominio

enum class Status(val descripcion: String) {
    ABIERTA("Abierta"),
    EN_PROGRESO("En progreso"),
    CERRADA("Cerrada")
}
