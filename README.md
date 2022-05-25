# Update Coins App

Aplicación para actualizar las coins de empleados de C1B en woocommerce

## Requisitos
* JDK 17
* Maven
* Conexión a la base de datos de  mediante el _application.properties_
* Archivo de excel (.xlsx) con dos columnas (email y puntos nuevos) como dato de entrada

## Running the app
* Run this command from Windows Command Prompt
```java -jar target/updatecoins-1.0.0.jar```
* The App generate and Excel's document with the old and new coins for all updated users
* If an email user is not matched agains the DB the output document will have a _not updated_ value for that particular user.


## Author

* Freddy Suárez