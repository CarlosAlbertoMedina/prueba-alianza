# Prueba GML - Alianza Backend
Prueba práctica ingeniero Fullstack Alianza fiduciaria y valores.

# Descripción 

Se debe hacer un servicio rest que permita consultar, buscar por shared key y crear un cliente, la implementación no necesita llevar base de datos, esto con el fin de hacer más sencillo el ejercicio (pero si le es más practico usar base de datos, lo puede hacer). Las pantallas deben interactuar con este servicio rest para cargar y enviar la información.
# Requerimientos no funcionales 
- En todos los pasos se debe implementar un sistema de logs, de forma que cuando se coloque en producción ayude a dar soporte del sistema.
- Generar pruebas unitarias para los casos que considere necesarios. Puede usan un framework como junit, testNG, si no conoce ninguno puede usar clases planas en java desde un main. Para el caso del front también implementar pruebas unitarias.
- Para la capa de presentación se debe usar Angular.
- Las pantallas deben ser responsive.
- El servicio debe ser implementado en Java, se puede cualquier framework.
# Puntos opcionales
NOTA: Este punto es opcional, no es necesario hacerlo, pero proporciona puntos extras.
- Implementar la funcionalidad del buscador avanzado (botón advanced search), tanto en la parte front como en el servicio.
- Implementar la opción de Exportar (ver botón en la primera pantalla) hacia un archivo csv. 

# Tecnologias utilizadas
Java 21
Spring 3.4.0
JUnit 5 para pruebas unitarias
Base de datos H2 en memoria 
Maven para la gestión de dependencias y construcción del proyecto
IDE InteliJ

# Como correr el proyector 
- Ejecutar la clase (Run) PruebaAlianzaApplication
- el servidor corre por el puerto: 8080
- Swagger: http://localhost:8080/swagger-ui/index.html
  
  ![image](https://github.com/user-attachments/assets/37a53148-6146-46cf-a8d5-7ba2f463f64e)

