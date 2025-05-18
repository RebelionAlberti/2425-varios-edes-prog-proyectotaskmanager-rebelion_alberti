import aplicacion.ActividadService
import aplicacion.UsuarioService
import datos.repository.UsuarioRepository
import datos.repository.Repository
import datos.dao.TareaDAO
import presentacion.UI

fun main() {
    val ui = UI()
    val tareaDAO = TareaDAO()
    val repositorio = Repository(tareaDAO)
    val servicio = ActividadService(repositorio)
    val usuarioRepositorio = UsuarioRepository()
    val usuarioService = UsuarioService(usuarioRepositorio)

    ui.mostrarMenu(servicio, usuarioService)
}