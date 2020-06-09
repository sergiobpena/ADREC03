##Parte obrigatoria

Debese realizar unha aplicación que cumpra os seguintes requisitos para acadar 5 puntos utilizando SQLite:

- A aplicación comprobará se existe a base de datos “modelos.db”. Se non existe creará xunto con todas as táboas necesarias.
- A partir do arquivo “modelos.xml” procesa a información para obter os datos por pais e continente do número de casos e número de falecementos para cada día.
 - Gardará a información anterior na base de datos en SQLite.
- Un menú permitiranos realizar as seguintes consultas:
    - Obter aqueles paises con un número determinado de casos totais maior ao número proporcionado.
    - Obter para cada pais o maior número de mortes nun día. Ademais deberase indicar cal foi ese día.

NOTAS: Débese utilizar SAX para parsear o arquivo XML.