# Update Coins App

Aplicación para actualizar las coins de empleados de C1B en woocommerce

## Requisitos
* JDK 17
* Maven
* Conexión a la base de datos de  mediante el _application.properties_
* Archivo de excel (.xlsx) con dos columnas (email y puntos nuevos) como dato de entrada.

## Running the app from cmd
* Go to the root application folder.
* Run this maven command for package the application.
  ```mvn clean install```
* The updatecoins-X.X.X.jar will be in the target folder.
* Add the usersByEmailWithCoins.xlsx file to the target folder.
* Run
  ```java -jar -Dspring.profiles.active=argentina updatecoins-1.0.0.jar```
* Change the value of -Dspring.profiles.active accordingly to the required DB.(local, argentina. colombia). By default the app takes the "local" DB.
* The App generate and Excel's document with the old and new coins for all updated users.
* The Sheet name is the current active profile.
* If an email user is not matched agains the DB the output document will have a _not updated_ value for that particular user.


## Author

* Freddy Suárez