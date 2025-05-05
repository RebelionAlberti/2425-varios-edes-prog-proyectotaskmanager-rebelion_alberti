package org.practicatrim2.presentacion.datos

import dominio.Tarea

interface ITareaRepository {
    fun crearTarea(tarea: Tarea): Boolean

    fun recuperarTareasId(id: Int): Tarea?

    fun actualizarTarea(tarea: Tarea): Boolean

    fun borrarTarea(id: Int): Boolean

    fun recuperarTareas(): List<Tarea>
}
