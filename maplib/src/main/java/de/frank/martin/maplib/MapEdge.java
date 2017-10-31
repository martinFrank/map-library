package de.frank.martin.maplib;

import de.frank.martin.drawlib.PanScale;

/**
 * an edge is an elemental part of a map field - it surrounds the field. An edge
 * always requires two points a and b and between them is a line called 'edge'.
 * 
 * @author martinFrank
 *
 */
public interface MapEdge<V,U> extends PanScale {

	/**
	 * the a of the edge
	 * @return a
	 */
	MapPoint<U> getA();

	/**
	 * the be of the edge
	 * @return b
	 */
	MapPoint<U> getB();

	V getEdgeData();

	void setEdgeData(V v);
}
