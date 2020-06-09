##Parte obrigatoria

Debese realizar unha aplicación que cumpra os seguintes requisitos para acadar 5 puntos utilizando SQLite:

- A aplicación comprobará se existe a base de datos “modelos.db”. Se non existe creará xunto con todas as táboas necesarias.
- A partir do arquivo “modelos.xml” procesa a información para obter os datos por pais e continente do número de casos e número de falecementos para cada día.
 - Gardará a información anterior na base de datos en SQLite.
- Un menú permitiranos realizar as seguintes consultas:
    - Obter aqueles paises con un número determinado de casos totais maior ao número proporcionado.
    - Obter para cada pais o maior número de mortes nun día. Ademais deberase indicar cal foi ese día.

NOTAS: Débese utilizar SAX para parsear o arquivo XML.

##Parte opcional

Engadiranse 3 puntos máis se:

- Descargarase a última fonte de datos posible dos eguinte enlace https://opendata.ecdc.europa.eu/covid19/casedistribution/xml e a partir deste actualizarnse todos os datos da base de datos.

  *NOTAS: Non se pode borrar todos os datos da base de datos e cargalos de novo*

Engadiranse 2 puntos máis se:

- Se realizan as seguintes consultas:
    - O pais con maior número de casos para cada día do que se teña información.
    - Os paises ordenador de maior porcentaxe de crecemento a menor entre o dato de casos do día e o anterior.