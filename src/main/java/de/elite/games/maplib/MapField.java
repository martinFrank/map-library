package de.elite.games.maplib;

import de.elite.games.drawlib.PanScale;
import de.elite.games.geolib.GeoPoint;

import java.util.List;
import java.util.Set;

/**
 * A field is the elemental object of a map - all field together represent the
 * map. A field is surrounded by edges, these are equal to the connection to the
 * neighbor fields.
 * <br> A field is unique identified by either the center Point or the index Point
 * 
 * @author martinFrank
 */
public interface MapField<D, E extends MapEdge<?,P>, P extends MapPoint<?>> extends PanScale {

	/**
	 * unique index point
	 * @return
	 */
	GeoPoint getIndex();

	//FIXME doku
	void createShape(MapPartFactory<?, ?, E, P, ?> factory, MapStyle style);

	//FIXME doku
	void setCenter(P c, MapStyle style);

	/**
	 * center point of the field
	 * @return
	 */
	P getCenter();

	/**
	 * a set of all surrounding neighbor fields
	 * @return
	 */
	Set<MapField<?,E,P>> getNeigbours();

	/**
	 * the edges around the field
	 * @return
	 */
	List<E> getEdges();
	
	/**
	 * the points around the field
	 * @return
	 */
	List<P> getPoints();

	/**
	 * Customizable data
	 * @return
	 */
	D getFieldData();

	/**
	 * Customizable data
	 * @param t
	 */
	void setFieldData(D t);

}
