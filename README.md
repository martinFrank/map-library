# map-library

a map is a geographical 2D representation of fields that are connected to each other. The concept of a map originates mainly from 2D-games. Hence the map is rastered.

the major reason for using this library is that it is platform independ and uses only java basic functionality (only java.lang and java.util)

#### Map Types
there are 3 different types of maps implemented:
 + hexagonal (vertical or horizontal)
 + squared map (squares, diamonds, isometric)
 + triangle map (vertical or horizontal)
 
#### Data Types
the map is build up from fields: ```F```, edges ```E``` and nodes ```N```, they all together form the map.

##### A Map has:
  + all fields:
    ```
    List<F> fields = getFields();
    ```
  + an aggregation - a set of shape that form the map.<br>
    the aggreagtion is the graphical representation of the map
    ```
    Aggregation aggregation = getAggregation();
    double widthOfMap = aggregation.getWidth(); //real size after scale
    double heightOfMap = aggregation.getHeight(); //real size after scale
    ```
##### A field has:
  + neighbors (other map fields)<br>
    ```
    List<F> nbgs = MapField.getFields();
    ```
 + edges (around the field) <br>
    ```
    List<E> edges = mapField.getEdges();
    ```
 + points (outline and center)<br>
    ```
    List<N> points = mapField.getNodes();
    ```
  + a Shape that represents the graphic:<br>
    this shape should be used for any drawing tasks
    ```
    Shape shape = getShape();
    ```



##### Edges have:
 + A List of Nodes - always of size 2, these are start and end nodes<br>
   ```
   N a = mapEdge.getNodes().get(0);
   N a = mapEdge.getNodes().get(1);
   ```
 + fields, the two field adjected to the edge (or only one if it is on the border)
   ```
   List<F> fields = mapEdge.getFields();
   ```
 + edges, whom they are connected to
   ```
   List<E> edges = mapEdge.getEdges();
   ```
 + a Line that represents the graphic:<br>
   this line should be used for any drawing tasks
   ```
   Line line = getLine();
   ```
##### Nodes have:
 + MapEdges to which they are connected
   ```
   List<E> edges = mapPoint.getEdges();
   ```
 + Fields to which they are connected
   ```
   List<F> fields = mapPoint.getFields();
   ```
 + a Point that represents the graphic:<br>
   this point should be used for any drawing tasks
   ```
   Point point = getPoint();
   ```
##### Nodes have:
all data types can be parametrized and have getters/setters for the customizable objects
 
#### API
 the map library provides expected basic functionality 
 + access to all data fields
 + access to Data Types on x/y - position (either field, edge or point) - very helpful for GUIs, see [example implementaion](https://github.com/martinFrank/map-library-demo)
 + A* (aStar) search on a field-based path (edge-based is planned for further releases)

#### Example / Tutorial
this screen shot is taken from the [example of the map-library](https://github.com/martinFrank/map-library-demo):

![screenshot](https://github.com/martinFrank/map-library/blob/master/images/screenshot.png)

### Requirements:
 + [draw-library](https://github.com/martinFrank/draw-library)
 + [geometry-library](https://github.com/martinFrank/geometry-library)

