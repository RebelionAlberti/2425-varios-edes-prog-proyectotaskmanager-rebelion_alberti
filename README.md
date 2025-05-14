# 3.3 Test Unitarios y Moock

## ¿Qué son?

### Test Unitarios

Son pruebas pequeñas que se hacen para verificar que una unidad concreta de código (como una función o método) funciona correctamente por sí sola.
El objetivo es detectar errores rápido y asegurarse de que cada parte del programa hace lo que debe.

### Moock

Es una imitación o simulación de una parte del sistema (como una base de datos, un servicio externo o una dependencia) que usamos en los tests para no depender de cosas reales.
Con un mock controlamos lo que devuelve esa parte, así podemos probar solo el código que nos interesa sin que interfiera el resto.

## Actividad

En esta actividad vas a diseñar y ejecutar un conjunto de pruebas unitarias para uno de los servicios de tu proyecto (por ejemplo, el servicio que gestiona tareas, usuarios, pedidos, etc.). El objetivo es asegurar que todos los métodos públicos de ese servicio funcionan correctamente, aislándolos de su repositorio subyacente mediante un mock.