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
 * @param <F> any desired object
 */
public interface MapField<F,E,P> extends PanScale {

	MapPoint<?> getIndex();

	Set<MapField<F,E,P>> getNeigbours();

	List<MapEdge<E,P>> getEdges();
	
	List<MapPoint<P>> getPoints();

	F getFieldData();

	void setFieldData(F t);

	MapPoint<P> getCenter();
}
