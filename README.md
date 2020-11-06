# GUÍA DE LA APLICACIÓN

### ENUNCIADO DEL EJERCICIO

Aplicación 2:
2.1 Paso 1:
Leer de manera **secuencial** todos los registros y mostrar **el tiempo
empleado** en la lectura y la suma (sin usar sentencias SQL de suma) del
campo INGRESOS.
2.2 Paso 2:
Leer la base de datos anterior con **cinco hilos de ejecución concurrentes**
y mostrar el **tiempo empleado en la lectura y la suma** (sin usar
sentencias SQL de suma) del campo INGRESOS

### REPOSITORIOS

GitHub [link del repositorio](https://github.com/PabloViniegra/practica2_PSP_Pablo.git)

### TECNOLOGÍA EMPLEADA

- **Lenguaje de programación:** Java
- **Versión:** 8
- **JDK:** 1.8.0_251-b08
- **Entorno de Desarrollo:** IntelliJ IDEA ultimate version
- **Estándar:** Proyecto Maven

### ¿ EN QUÉ CONSISTE?

La aplicación se ejecuta sin interacción alguna del usuario, dado que tiene que hacer un único trabajo
(la especificada en el ejercicio). Primero, realiza una lectura del número de registros de la Base de
Datos del ejercicio anterior para obtener el número de registros. Luego, se realiza una lectura temporizada
de la Base de Datos de forma secuencial. Por último, se despliegan cinco hilos que leerán cada uno un número
asignado de registros de forma temporizada. El resultado último debe ser que la tarea se realice más rápido
de forma concurrente que de forma secuencial.

### REQUISITOS PARA INICIAR LA APLICACIÓN

Los mismos que los de la anterior aplicación [link](https://gitlab.com/PabloViniegra/practica1_psp_pablo.git). Se inicia también de la misma forma.


