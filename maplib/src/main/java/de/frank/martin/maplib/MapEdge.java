package de.frank.martin.maplib;

import java.util.Set;

import de.frank.martin.drawlib.PanScale;

/**
 * an edge is an elemental part of a map field - it surrounds the field. An edge
 * always requires two points a and b and between them is a line called 'edge'.
 * 
 * @author martinFrank
 * 
 * @param <E> any desired edge data object
 * @param <P> any desired point data object
 */
public interface MapEdge<E,P> extends PanScale {

	/**
	 * the a of the edge
	 * @return a
	 */
	MapPoint<P> getA();

	/**
	 * the be of the edge
	 * @return b
	 */
	MapPoint<P> getB();

	/**
	 * Customizable data
	 * @return data
	 */
	E getEdgeData();

	/**
	 * Customizable data
	 * @param v data
	 */
	void setEdgeData(E v);
		
	/**
	 * a set of all fields that are connected to this edge (should be one or two)
	 * @return
	 */
	Set<MapField<?,E,P>> getFields();
}
