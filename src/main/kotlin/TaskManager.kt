
import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.repository.UsuarioRepository
import datos.dao.EventoDAO
import datos.repository.Repository
import presentacion.UI

fun main() {
    val ui = UI()
    val eventoDAO = EventoDAO()
    val repositorio = Repository(eventoDAO)
    val servicio = ActividadService(repositorio)
    val usuarioRepositorio = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepositorio)

    ui.mostrarMenu(servicio, usuarioService)
}