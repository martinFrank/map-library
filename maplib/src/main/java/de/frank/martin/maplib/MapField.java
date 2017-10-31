package de.frank.martin.maplib;

import java.util.List;

import de.frank.martin.drawlib.PanScale;

/**
 * A field is the elemental object of a map - all field together represent the
 * map. A field is surrounded by edges, these are equal to the connection to the
 * neighbor fields.
 * <br> A field is unique identified by either the center Point or the index Point
 * 
 * @author martinFrank
 *
 * @param <T> any desired object
 */
public interface MapField<T,V,U> extends PanScale {

	MapPoint<?> getIndex();

	List<MapField<T,V,U>> getNeigbourList();

	List<MapEdge<V,U>> getEdgeList();

	T getFieldData();

	void setFieldData(T t);

	MapPoint<U> getCenter();
}
