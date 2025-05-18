package datos.dao

interface IGenericoDAO<T> {
    fun create(t: T): T

    fun read(): List<T>

    fun update(t: T): Boolean

    fun delete(id: Int): T?
}