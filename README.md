Abre una consola de comandos desde el directorio donde se encuentra el archivo .pom. Escribe el siguiente comando para compilar todas las clases y ejecutar los teses unitarios.

      mvn test

Luego hay que ir al MySQL Workbench y ejecutar el contenido del archivo “create-message.sql” dentro de la carpeta sql para conceder los permisos necesarios y crear la base de datos. Después vuelve a la consola y escribe este comando para hacer el enhance de las clases.

      mvn datanucleus:enhance


Ahora ejecuta el siguiente comando para crear el esquema de la base de datos. 

      mvn datanucleus:schema-create
      
Con la base de datos creada se pueden ejecutar los test de rendimiento mediante este comando:

      mvn verify -Pintegration-tests

La carpeta también contiene el archivo datos-ejemplo.sql, que se puede ejecutar en MySQL para tener algunos datos de ejemplo.

Una vez se construye exitosamente toca lanzar el servidor con el comando:

      mvn jetty:run 
      
Si el servidor se lanza exitosamente, hay que abrir una nueva consola desde el mismo directorio que la anterior y ejecutar el cliente con el comando:
      
      mvn exec:java -Pclient
