package de.frank.martin.maplib;

import java.util.List;
import java.util.Set;

import de.frank.martin.drawlib.PanScale;

/**
 * A field is the elemental object of a map - all field together represent the
 * map. A field is surrounded by edges, these are equal to the connection to the
 * neighbor fields.
 * <br> A field is unique identified by either the center Point or the index Point
 * 
 * @author martinFrank
 *
 * @param <F> any desired field data object
 * @param <E> any desired edge data object
 * @param <P> any desired point data object
 */
public interface MapField<F,E,P> extends PanScale {

	/**
	 * unique index point
	 * @return
	 */
	MapPoint<P> getIndex();

	/**
	 * center point of the field
	 * @return
	 */
	MapPoint<P> getCenter();

	/**
	 * a set of all surrounding neighbor fields
	 * @return
	 */
	Set<MapField<F,E,P>> getNeigbours();

	/**
	 * the edges around the field
	 * @return
	 */
	List<MapEdge<E,P>> getEdges();
	
	/**
	 * the points around the field
	 * @return
	 */
	List<MapPoint<P>> getPoints();

	/**
	 * Customizable data
	 * @return
	 */
	F getFieldData();

	/**
	 * Customizable data
	 * @param t
	 */
	void setFieldData(F t);

}
