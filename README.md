Abre una consola de comandos desde el directorio donde se encuentra el archivo .pom. Escribe el siguiente comando para construir todo y realizar la tarea de “enhance” de las clases de la base de datos. 

      mvn clean compile

Luego hay que ir al MySQL Workbench y ejecutar el contenido del archivo “create-message.sql” dentro de la carpeta sql. Después volvemos a la consola y ejecutamos el siguiente comando para crear el esquema de la base de datos. 

      mvn datanucleus:schema-create

La carpeta también contiene el archivo datos-ejemplo.sql, que se puede ejecutar en MySQL para tener algunos datos iniciales.

Una vez se construye exitosamente toca lanzar el servidor con el comando 

      mvn jetty:run 
      
Si el servidor se lanza exitosamente, hay que abrir una nueva consola desde el mismo directorio que la anterior y ejecutar el cliente con el comando 
      
      mvn exec:java -Pclient
