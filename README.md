# map-library

a map is a geographical 2D representation of fields that are connected to each other. The concept of a map originates mainly from 2D-games. Hence the map is rastered.

the major reason for using this library is that it is platform independ and uses only java basic functionality (only java.lang and java.util)

#### Map Types
there are 3 different types of maps implemented:
 + hexagonal (vertical or horizontal)
 + squared map (either 4 neighbours or 8 neighbours)
 + triangle map (vertical or horizontal)
 
#### Data Types
the map is build up from fields, edges and points, they all together form the map.

##### A field has:
  + neighbors (other map fields)<br>
    ```
    Set<MapField> nbgs = MapField.getFields();
    ```
 + edges (around the field) <br>
    ```
    Set<MapEdge> edges = mapField.getEdges();
    ```
 + points (outline and center)<br>
    ```
    Set<MapPoint> points = mapField.getPoints();
    ```
 
##### Edges have:
 + Points a and Point b these are start and end point<br>
   ```
   MapPoint a = mapEdge.getA();
   MapPoint a = mapEdge.getB();
   ```
 + fields, the two field adjected to the edge (or only one if it is on the border)
   ```
   Set<MapField> fields = mapEdge.getFields();
   ```
 + edges, whom they are connected to
   ```
   Set<MapEge> edges = mapEdge.getEdges();
   ```

##### Points have:
 + Edges to which they are connected
   ```
   Set<MapEdge> edges = mapPoint.getEdges();
   ```
 + Fields to which they are connected
   ```
   Set<MapField> fields = mapPoint.getFields();
   ```

all data types can be parametrized and have getters/setters for the customizable objects
 
#### API
 the map library provides expected basic functionality 
 + access to all data fields
 + access to Data Types on x/y - position (either field, edge or point) - very helpful for GUIs, see [example implementaion](https://github.com/martinFrank/map-library-demo)
 + A* (aStar) search on a field-based path (edge-based is planned for further releases)

#### Example / Tutorial
this screen shot is taken from the [example of the map-library](https://github.com/martinFrank/map-library-demo):

![swing-based tutorial](https://user-images.githubusercontent.com/33021138/32279466-1fc971ca-bf19-11e7-8a17-2ba9f1ad5a71.png)

### Requirements:
 + [draw-library](https://github.com/martinFrank/draw-library)
 + [geometry-library](https://github.com/martinFrank/geometry-library)

