---
title: "Patrón de Diseño MapReduce en Java"
shortTitle: MapReduce
description: "Aprende el patrón de diseño MapReduce en Java con ejemplos de la vida real, diagramas de clases y tutoriales. Entiende su intención, aplicación, beneficios y usos conocidos para mejorar tu conocimiento sobre patrones de diseño."
category: Optimización de Rendimiento
language: es
tag:
    - Data processing
    - Code simplification
    - Delegation
    - Performance
    - Procesamiento de datos
    - Simplificación de coódigo
    - Delegación
    - Rendimiento
---

## También conocido como:

-   Estrategia Separa-Aplica-Combina (Split-Apply-Combine Strategy)
-   Patrón Dispersa-Recolecta (Scatter-Gather Pattern)

## Intención del Patrón de Diseño Map Reduce

MapReduce pretende procesar y generar grandes conjuntos de datos con un algoritmo en paralelo y distribuido en un grupo. Divide la carga de trabajo en dos fases principales: Mapear (Map) y Reducir (Reduce), permitiendo así una mayor eficiencia en procesamiento de datos en paralelo.

## Explicación Detallada del Patrón de Diseño Map Reduce con Ejemplos del Mundo Real

Ejemplo Práctico

> Imagina una compañía de comercio en línea (e-commerce) que quiere analizar sus datos de ventas a lo largo de múltplies regiones. Tienen terabytes de datos de transacciones almacenados en cientos de servidores. Usando MapReduce, pueden procesar estos datos eficientemente para calcular las ventas totales por categoría de producto. La función Map procesaría registros de ventas individuales, regresando pares clave-valor de (categoría, cantidad de venta). La función Reduce entonces sumaría todas las cantidades de ventas por cada categoría, produciendo el resultado final.

En palabras sencillas

> MapReduce divide un gran problema en partes más pequeñas, las procesa en paralelo y después combina los resultados.

En Wikipedia dice:

> "MapReduce es un modelo de programación e implementación asociada para procesar y generar grandes conjuntos de información con un algoritmo en paralelo y distribuido en un grupo".
> MapReduce consiste de dos funciones principales:
> El nodo principal toma la entrada de información, la divide en problemas más pequeños (sub-problemas) y los distribuye a los nodos restantes (de trabajo). Un nodo de trabajo puede repetir este proceso llevando a una estructura de árbol multinivel. El nodo de trabajo procesa el sub-problema y le regresa la respuesta al nodo principal.
> El nodo principal recolecta todas las respuestas de todos los sub-problemas y las combina de cierta manera para regresar la salida de información - la respuesta del principal problema que estaba resolviendo.
> Este acercamiento permite un procesamiento eficiente de grandes cantidades de datos através de múltiples máquinas, convirtiéndola en una técnica fundamental en análisis de cantidades enormes de datos y computo distribuido.

## Ejemplo Programático de Map Reduce en Java

### 1. Fase de Map (Mapeador; División & Procesamiento de Datos)

-   El Mapper toma una entrada de texto (string), lo divide en palabras y cuenta las ocurrencias.
-   Salida: Un mapa {palabra → conteo} por cada línea de entrada.

#### `Mapper.java`

```java
public class Mapper {
    public static Map<String, Integer> map(String input) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = input.split("\\s+");
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!word.isEmpty()) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        return wordCount;
    }
}
```

Ejemplo de Entrada: `"Hello world hello"`
Salida: `{hello=2, world=1}`

### 2. Fase de Shuffle (Combinación; Agrupar Datos por Clave)

-   La Combinación recolecta pares clave-valor de múltiples mapeadores (mappers) y valores de grupo por clave.

#### `Shuffler.java`

```java
public class Shuffler {
    public static Map<String, List<Integer>> shuffleAndSort(List<Map<String, Integer>> mapped) {
        Map<String, List<Integer>> grouped = new HashMap<>();
        for (Map<String, Integer> map : mapped) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                grouped.putIfAbsent(entry.getKey(), new ArrayList<>());
                grouped.get(entry.getKey()).add(entry.getValue());
            }
        }
        return grouped;
    }
}
```

Ejemplo de Entrada:

```
[
    {"hello": 2, "world": 1},
    {"hello": 1, "java": 1}
]
```

Salida:

```
{
    "hello": [2, 1],
    "world": [1],
    "java": [1]
}
```

### 3. Fase de Reduce (Reductor; Agregar Resultados)

-   El Reductor suma todas las ocurrencias de cada palabra.

#### `Reducer.java`

```java
public class Reducer {
    public static List<Map.Entry<String, Integer>> reduce(Map<String, List<Integer>> grouped) {
        Map<String, Integer> reduced = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : grouped.entrySet()) {
            reduced.put(entry.getKey(), entry.getValue().stream().mapToInt(Integer::intValue).sum());
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>(reduced.entrySet());
        result.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return result;
    }
}
```

Ejemplo de Entrada:

```
{
    "hello": [2, 1],
    "world": [1],
    "java": [1]
}
```

Salida:

```
[
    {"hello": 3},
    {"world": 1},
    {"java": 1}
]
```

### 4. Ejecutar el Proceso Completo de MapReduce

-   La clase MapReduce coordina los tres pasos.

#### `MapReduce.java`

```java
public class MapReduce {
    public static List<Map.Entry<String, Integer>> mapReduce(List<String> inputs) {
        List<Map<String, Integer>> mapped = new ArrayList<>();
        for (String input : inputs) {
            mapped.add(Mapper.map(input));
        }

        Map<String, List<Integer>> grouped = Shuffler.shuffleAndSort(mapped);

        return Reducer.reduce(grouped);
    }
}
```

### 5. Ejecución Principal (Llamar a MapReduce)

-   La clase Main ejecuta el "pipeline" de MapReduce y regresa el conteo final de palabras.

#### `Main.java`

```java
  public static void main(String[] args) {
    List<String> inputs = Arrays.asList(
            "Hello world hello",
            "MapReduce is fun",
            "Hello from the other side",
            "Hello world"
    );
    List<Map.Entry<String, Integer>> result = MapReduce.mapReduce(inputs);
    for (Map.Entry<String, Integer> entry : result) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}
```

Salida:

```
hello: 4
world: 2
the: 1
other: 1
side: 1
mapreduce: 1
is: 1
from: 1
fun: 1
```

## Cuándo Usar el Patrón de Diseño MapReduce en Java

Usa MapReduce cuando se están:

-   Procesando grandes conjuntos de información que no quepa en la memoria de una sola máquina.
-   Realizando cálculos que se puedan desarrollar en paralelo.
-   Trabajando en escenarios tolerante a fallos y computación distribuida.
-   Analizando archivos de registro, datos de rastreo web o datos científicos.

## Tutoriales del Patrón de Diseño MapReduce en Java

-   [Tutorial MapReduce (Apache Hadoop)](https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)
-   [Ejemplo MapReduce (Simplilearn)](https://www.youtube.com/watch?v=l2clwKnrtO8)

## Ventajas y Desventajas del Patrón de Diseño MapReduce

Ventajas:

-   Escalabilidad: Puede procesar grandes cantidades de datos através de múltiples máquinas
-   Tolerancia a Fallos: Maneja los fallos elegantemente
-   Simplicidad: Resume detalles complejos de distribución computacional

Desventajas:

-   Costo-Beneficio: Ineficiente para conjuntos de datos chicos por la preparación y coordinación necesaria
-   Flexibilidad Limitada: Inadecuado para todos los tipos de computación o algoritmos
-   Latencia: En relación a lotes de información podría ser inadecaudo para necesidades de procesamiento en tiempo real

## Aplicaciones Reales para el Patrón de Diseño MapReduce en Java

-   Implementación original de Google para indexar páginas web
-   Hadoop MapReduce para procesamiento de información extensa
-   Sistemas de análisis de registros a gran escala
-   Secuencia genómica en análisis de bio-informática

## Patrones de Diseño relacionados a Java

-   Patrón de Encadenamiento (Chaining Pattern)
-   Patrón de Maestro-Trabajador (Master-Worker Pattern)
-   Patrón de Tubería (Pipeline Pattern)

## Referencias y Creditos

-   [¿Qué es MapReduce?](https://www.ibm.com/think/topics/mapreduce)
-   [¿Por qué MapReduce no ha muerto?](https://www.codemotion.com/magazine/ai-ml/big-data/mapreduce-not-dead-heres-why-its-still-ruling-in-the-cloud/)
-   [Soluciones Escalables de Procesamiento de Datos Distribuidos](https://tcpp.cs.gsu.edu/curriculum/?q=system%2Ffiles%2Fch07.pdf)
-   [Patrones de Diseño en Java: Experiencia Práctica con Ejemplos del Mundo Real](https://amzn.to/3HWNf4U)
