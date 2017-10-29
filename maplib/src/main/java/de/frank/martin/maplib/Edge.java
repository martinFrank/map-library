package de.frank.martin.maplib;

import de.frank.martin.drawlib.PanScale;

/**
 * an edge is an elemental part of a map field - it surrounds the field. An edge
 * alsways reuqires two points a and b and between them is a line called 'edge'.
 * 
 * @author martinFrank
 *
 */
public interface Edge extends PanScale {

	/**
	 * the a of the edge
	 * @return a
	 */
	Point a();

	/**
	 * the be of the edge
	 * @return b
	 */
	Point b();

}
