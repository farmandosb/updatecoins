# Update Coins App

Aplicación para actualizar las coins de empleados de C1B en woocommerce

## Requisitos
* JDK 17
* Maven
* Configuración para conectarse a las bases de datos en cada _application-environment.properties_
* Hoja de cálculo _usersByEmailWithCoins.xlsx_ con dos columnas (email y puntos nuevos) sin rows vacías en medio.

### Archivo de entrada
* El _archivo de entrada_ debe tener este nombre y extensión: _usersByEmailWithCoins.xlsx_.
* La primera fila es el encabezado. No importa lo que esté en esta fila, la app empieza a revisar a partir de la segunda fila.
* La primera columna debe ser el email de hexacta de cada usuario en texto plano.
* La segunda columna debe ser la cantidad de puntos totales que van a reemplazar a los existentes en el woocommerce. No deben tener comas ni puntos, ni otros caracteres especiales.
* La app solamente evalua las dos primeras columnas.
* No debe haber filas en blanco en el medio de los datos de entrada.

## Empaquetando la app en un JAR
* Cambiar al directorio raiz de la App.
* Ejecutar este comando para empaquetar la App.
  ```mvn clean install```
* Ahora el paquete _updatecoins-X.X.X.jar_ estará dentro de la carpeta target.
* El paquete _.jar_ puede moverse a cualquier otra carpeta.

## Ejecuntando la app por línea de comandos
* Agregar en la misma carpeta del .jar el _archivo de entrada_.
* Ejecutar
  ```java -jar -Dspring.profiles.active=argentina updatecoins-1.0.0.jar```
* Cambie el valor -Dspring.profiles.active de acuerdo a la DB deseada, (local, argentina. colombia). Por defecto la App toma la DB como "local".
* La applicación genera una hoja de cálculo _(xlsx)_ con las coins antes y después del cambio, solo si el email fue encontrado  y las nuevas coins son diferentes de las existentes.
* El nombre de la hoja será el ambiente activo.
* Si no se encuentra el email o las coins son iguales a las existentes el reporte agregará una nota _not updated_.

## Ejecuntando la app desde un IDE
* Agregar en eldirectorio raíz del proyecto el _archivo de entrada_.
* Se puede ejecutar como una aplicación de springboot mediante la Application.class
* El _archivo de salida_ se creará en el directorio raíz del proyecto.

## Author

* Freddy Suárez
* 15-06-2022