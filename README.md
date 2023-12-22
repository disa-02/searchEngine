# searchEngine
Aplicacion realizada con Spring Boot. Imita el funcionamiento de un buscador como google. La indexacion de las paginas se hace de forma manual con un limite de 100 paginas por cuestiones de rendimiento.

# funcionalidades
 - Por default la aplicacon tiene cargada una sola pagina.
 - Con el boton "indexPages" se indexaran todas las paginas de las paginas ya cargadas.
 - En el buscador se puede buscar informacion y se mostraran las paginas que la poseen en su descripcion.
 
# swagger
La aplicacion se encuentra integada con swagger lo que permite utilizar y ver los endpoint de la restApi accediendo a: http://localhost:8080/swagger-ui/index.html

# ejecucion
Una vez ejecutada la aplicacion, el acceso se realiza a travez de: http://localhost:8080
La base de datos corre sobre el puerto 3306 con el nombre de searchEngine

# docker
Se encuentra docker integrado en la aplicacion.
Con el comando "docker compose up" se inicializa autimaticamente la aplicacion.
Tener en cuenta que se puede requerir permisos de superusuario dependiendo la configuracion del sistema operativo donde esta corriendo.
Deben estar libres los siguientes puertos:
 - 8080: puerto donde corre la aplicacion
 - 3306: puerto donde corre la base de datos
Los puertos pueden ser modificados en el archivo "docker-compose.yml"
