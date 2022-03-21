# WordCounter

Pequeño programa encargado de listar en orden descendiente los ficheros con mayor porcentaje de apariciones de una palabra dada.

## Installation

Usar el compilador de scala para generar el .class ejecutable.

```bash
scalac WordCounter.scala
```
Y a continuación, invocar el objeto WordCounter pasando un el nombre del folder que contiene los ficheros (Ej: /home/orlando/test_files, desde unix): 
```bash
scala WordCounter mis_archivos
```
_mis_archivos_ es un directorio incorporado en el desarrollo y que tiene 20 ficheros (generados con lorem ipsum) en los que adicionalmente se han puesto aleatoriamente las palabras 'casa', 'coche', 'perro', 'gato', 'arbol', 'radio' y 'calle' para realizar las pruebas

## Ejemplos de uso

![Ejemplos](img/Ejemplo_1.png)

## Descripción de Funcionamiento

Antes de detallar el funcionamiento se mencionan algunas consideraciones:

- El nombre del folder que contiene los ficheros sobre los que se hará la búsqueda debe ser pasado como argumento al momento de ejecutar WordCounter
- Las palabras que son consideradas como tal son aquellos segmentos de texto separados por espacio o saltos de línea
- La búsqueda es _case sentive_ y por tanto 'Casa' y 'casa' son distintas palabras; Las tildes también pueden diferenciar una palabra de otra, por tanto "esta" y "está" también son palabras diferentes.
- Para salir del prompt se debe escribir 'quit'.

### Funcionamiento
Antes de iniciar la búsqueda de la palabra lo que se hace es *pre-procesar los ficheros* , así que para que cada uno de ellos :

 - Lo primero que se hace es contar las palabras dentro del archivo apoyandonos para ello en un Map llamado 'wordMap' que almacena cada palabra (key) asociando al número de ocurrencias de la misma (value).

    ![wordMap](img/wordMap.png)

 - En seguida, se obtiene el total de palabras de el fichero sumando todos los valores del Map 'wordMap'

    ![total](img/total.png)

 - Teniendo el total anterior y el número de ocurrencias de cada palabra (wordMap) es posible calcular el porcentaje de apariciones de cada palabra. Estos porcentajes se guardan en un Map llamado 'percentageMap', cuyas claves son las palabras y los valores correspondientes es el porcentaje de ocurrencias.

    ![pctMap](img/pctMap.png)

 - Este último Map es a su vez almacenado en otro Map ('fileMap') que lo asocia con el nombre de archivo al que pertenece. El nombre del fichero es la clave de es nuevo Map.


Con todos los ficheros del directorio ya procesados, entonces: 

 - Se solicita la palabra de búsqueda ('targetWord') para *filtrar en 'fileMap' todos los ficheros que contienen la palabra* y guardarlos en un nueva colección llamada 'filteredtMap'

    ![filtered](img/filtered.png)

 - 'filteredtMap' es ordenada descendentemente y de esta se toman los N-primeros elementos que luego son enseñados al usuario.

    ![sorted](img/sorted.png)

## License
[Licencia GNU LGPL](https://es.wikipedia.org/wiki/GNU_Lesser_General_Public_License)
