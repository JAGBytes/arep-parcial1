# arep-parcial1



# Ejecución proyecto

primero clonar el proyecto y dirigirse a su ubicación

```
https://github.com/JAGBytes/arep-parcial1.git
cd arep-parcial1.git
```
luego se hace el siguiente comando
```
mvn clean package
```
Para correr el proyecto ejecutar la fachada

```
java -cp target/classes edu.eci.arep.calculator.Fachada
```

y el server

```
java -cp target/classes edu.eci.arep.calculator.HttpServer
```

# Demostracion funcionamiento fachada

<video controls src="src/main/java/resources/Recording 2025-09-10 112057.mp4" title="Title"></video>

Ejemplo añadiendo un usuario<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 111041.png>)


ejemplo obteniendo valores de linked list<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 111119.png>)

ejemplo añadiendo otro numero<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 111227.png>)


ejemplo obteniendo stats<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 111318.png>)

toda la evidencia se encuentra en la carpeta resources dentro del proyecto

link de video en one drive  
```
https://pruebacorreoescuelaingeduco-my.sharepoint.com/:v:/g/personal/jorge_gamboa-s_mail_escuelaing_edu_co/EevXnPVaigpMpI4iIG2HFLsB8lO9CNI8kUpSc9Tty4HZgw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=6LbcaV
```


# Ejemplos de solicitudes al back

primero se añaden tres usuarios<br>

![alt text](<src/main/java/resources/Screenshot 2025-09-10 115033.png>)

![alt text](<src/main/java/resources/Screenshot 2025-09-10 115045.png>)

![alt text](<src/main/java/resources/Screenshot 2025-09-10 115058.png>)

Luego se rectifica que fueron registrados exitosamente<br>

![alt text](<src/main/java/resources/Screenshot 2025-09-10 115108.png>)


Se revisa que stats funciona correctamente<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 115119.png>)


# Ejemplos de solicitudes al back sin cliente

Añadimos varios números<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 115716.png>)

Obtenemos los números añadidos anteriormente<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 115755.png>)

Solicitamos las stats de los números<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 115805.png>)

Limpiamos la lista<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 115814.png>)

Observamos que al estar vacía nos devuelve un error del servidor que corresponde con el estado actual de la lista<br>
![alt text](<src/main/java/resources/Screenshot 2025-09-10 115848.png>)

# Autor 
Jorge Andres Gamboa Sierra 
