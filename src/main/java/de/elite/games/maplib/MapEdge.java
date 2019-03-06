package de.elite.games.maplib;

import de.elite.games.drawlib.PanScale;

import java.util.Set;

/**
 * an edge is an elemental part of a map field - it surrounds the field. An edge
 * always requires two points a and b and between them is a line called 'edge'.
 * 
 * @author martinFrank
 * 
 * @param <E> any desired edge data object
 */
public interface MapEdge<D,P extends MapPoint> extends PanScale {

	/**
	 * the a of the edge
	 * @return a
	 */
	P getA();

	/**
	 * the be of the edge
	 * @return b
	 */
	P getB();

	/**
	 * Customizable data
	 * @return data
	 */
	D getEdgeData();

	/**
	 * Customizable data
	 * @param v data
	 */
	void setEdgeData(D v);
		
	/**
	 * a set of all fields that are connected to this edge (should be one or two)
	 * @return
	 */
	Set<MapField> getFields();
}
