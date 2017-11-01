# map-library

a map is a geographical 2D representation of fields that are connected to each other. The concept of a map originates mainly from 2D-games. Hence the map is rastered.

the major reason for using this library is that it is platform independ and uses only java basic functionality (only java.lang and java.util)

#### Map Types
there are 3 different types of maps implemented:
 + hexagonal (vertical or horizontal)
 + squared map (either 4 neighbours or 8 neighbours)
 + triangle map (vertical or horizontal)
 
 #### Data Types
 the map is build up from fields, all fields together form the map.
 A field has: 
 + neighbors (other map fields)
 + edges (outline)
 + points (outline and center)
 
 Edges have: 
 + Points a, b these are start and end point
 + fields, the two field adjected to the edge
 
 Points have:
 + Edges to which they are connected
 
 all data types can be parametrized and have getters/setters for the customizable objects
 
 #### API
 the map library provides expected basic functionality 
 + access to all fields (as list) 
 + access to Data Types on x/y - position (either field, edge or point)
 + access to map Dimension
 + A* (aStar) search on a field-based path (edge-based is planned for further releases)
 
the map library provides a a-star shortestPath algorithm. In order to use this you must define a 'walker' who can walk over certain field or cannot walk into 'blocked' fields.

#### Example / Tutorial
this screen shot is taken from the [swing-based example of the map-library](https://github.com/martinFrank/map-library-swing-demo):

![swing-based tutorial](https://user-images.githubusercontent.com/33021138/32279466-1fc971ca-bf19-11e7-8a17-2ba9f1ad5a71.png)

### Requirements:
 + [draw-library](https://github.com/martinFrank/draw-library)
 + [geometry-library](https://github.com/martinFrank/geometry-library)

