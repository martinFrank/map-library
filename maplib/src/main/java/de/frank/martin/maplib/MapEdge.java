package de.frank.martin.maplib;

import de.frank.martin.drawlib.PanScale;

/**
 * an edge is an elemental part of a map field - it surrounds the field. An edge
 * always requires two points a and b and between them is a line called 'edge'.
 * 
 * @author martinFrank
 *
 */
public interface MapEdge extends PanScale {

	/**
	 * the a of the edge
	 * @return a
	 */
	MapPoint getA();

	/**
	 * the be of the edge
	 * @return b
	 */
	MapPoint getB();

}
