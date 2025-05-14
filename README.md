# 3.3 Test Unitarios y Moock

## ¿Qué son?

#### ·Test Unitarios

Son pruebas pequeñas que se hacen para verificar que una unidad concreta de código (como una función o método) funciona correctamente por sí sola.
El objetivo es detectar errores rápido y asegurarse de que cada parte del programa hace lo que debe.

#### ·Moock

Es una imitación o simulación de una parte del sistema (como una base de datos, un servicio externo o una dependencia) que usamos en los tests para no depender de cosas reales.
Con un mock controlamos lo que devuelve esa parte, así podemos probar solo el código que nos interesa sin que interfiera el resto.

## Actividad

En esta actividad vas a diseñar y ejecutar un conjunto de pruebas unitarias para uno de los servicios de tu proyecto (por ejemplo, el servicio que gestiona tareas, usuarios, pedidos, etc.). El objetivo es asegurar que todos los métodos públicos de ese servicio funcionan correctamente, aislándolos de su repositorio subyacente mediante un mock.

### 1. Selección del servicio

Para esta actividad, he utilizado el servicio UsuarioService.

### 2. Identificación de métodos

#### 1. `crearUsuario`
- **Parámetros de entrada:**
    - `nombre: String` (El nombre del nuevo usuario que quiero crear)

- **Resultado esperado o efecto en el repositorio:**  
  Este método crea un usuario con el nombre que paso y lo guarda en el repositorio. Si todo va bien, me devuelve `true`, pero si hay algún problema, me devuelve `false`.

---

#### 2. `eliminarUsuario`
- **Parámetros de entrada:**
    - `id: Int` (El ID del usuario que quiero borrar)

- **Resultado esperado o efecto en el repositorio:**  
  Aquí, si el usuario con el `id` que paso existe, lo borra del repositorio y me devuelve `true`. Si no lo encuentra, devuelve `false`.

---

#### 3. `obtenerUsuarios`
- **Parámetros de entrada:**
    - No lleva parámetros, es solo para recuperar todos los usuarios.

- **Resultado esperado o efecto en el repositorio:**  
  Este método devuelve una lista con todos los usuarios guardados en el repositorio. Si no hay usuarios, me devuelve una lista vacía.

---

#### 4. `buscarUsuarioPorId`
- **Parámetros de entrada:**
    - `id: Int` (El ID del usuario que quiero buscar)

- **Resultado esperado o efecto en el repositorio:**  
  Si el usuario con ese `id` existe, me lo devuelve. Si no, me devuelve `null`, o sea, no lo encontró.

### 3. Diseño de casos de prueba  

| Método de servicio          | Caso de prueba                         | Valor pasado al método       | Resultado esperado, de los métodos del mock a los que se llama     | Acción                                         | Resultado esperado                                            |
|-----------------------------|----------------------------------------|------------------------------|--------------------------------------------------------------------|------------------------------------------------|---------------------------------------------------------------|
| `crearUsuarioTrue()`        | Caso nominal (Datos válidos)           | `"Aarón"`                    | `repositorio.agregar()` devuelve `true`                             | Llamar a `crearUsuario("Aarón")`               | Usuario creado correctamente, devuelve `true`                 |
| `crearUsuarioFalse()`       | Caso de error (Datos inválidos)        | `"Aarón"`                    | `repositorio.agregar()` devuelve `false`                            | Llamar a `crearUsuario("Aarón")`               | No se puede crear el usuario, devuelve `false`                |
| `eliminarUsuarioTrue()`     | Caso nominal (ID válido)               | `1`                           | `repositorio.eliminar(1)` devuelve `true`                           | Llamar a `eliminarUsuario(1)`                  | Usuario eliminado correctamente, devuelve `true`              |
| `eliminarUsuarioFalse()`    | Caso de error (ID no existente)        | `1`                           | `repositorio.eliminar(1)` devuelve `false`                          | Llamar a `eliminarUsuario(1)`                  | No se puede eliminar el usuario, devuelve `false`             |
| `obtenerUsuariosTrue()`     | Caso nominal (Usuarios existentes)     | Ninguno                      | `repositorio.recuperarTodos()` devuelve una lista con usuarios     | Llamar a `obtenerUsuarios()`                   | Retorna la lista de usuarios encontrada                        |
| `obtenerUsuariosFalse()`    | Caso de error (Sin usuarios)           | Ninguno                      | `repositorio.recuperarTodos()` devuelve una lista vacía            | Llamar a `obtenerUsuarios()`                   | Retorna una lista vacía                                        |
| `buscarUsuarioPorIdTrue()`  | Caso nominal (ID existente)            | `1`                           | `repositorio.recuperarPorId(1)` devuelve un `Usuario`               | Llamar a `buscarUsuarioPorId(1)`               | Retorna el usuario con ID `1` encontrado                      |
| `buscarUsuarioPorIdFalse()` | Caso de error (ID no existente)        | `1`                           | `repositorio.recuperarPorId(1)` devuelve `null`                    | Llamar a `buscarUsuarioPorId(1)`               | Retorna `null` si no se encuentra el usuario                  |
