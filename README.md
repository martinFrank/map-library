# map-library

a map is a geographical 2D representation of fields that are connected to each other. The concept of a map originates mainly from 2D-games. The map is hence rasetered.

the major reason for using this library is that it is platform independ and uses onyl java basic functionality (only java.lang and java.util)

there are 3 different types of maps implemented:
 + hexagonal (vertical or horizontal)
 + squared map (either 4 neighbours or 8 neighbours)
 + triangle map (vertical or horizontal)
 
the map library provides a a-star shortestPath algorithm. In order to use this you must define a 'walker' who can walk over certain field or cannot walk into 'blocked' fields.

requirements
 + [draw-library](https://github.com/martinFrank/draw-library)

