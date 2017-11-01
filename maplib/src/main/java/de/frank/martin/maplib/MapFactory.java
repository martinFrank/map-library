package de.frank.martin.maplib;

/**
 * the map factory is required to create the map parts:
 * <li>field</li>
 * <li>edge</li>
 * <li>point</li>
 * 
 * @author martinFrank
 *
 * @param <F> any desired field data object
 * @param <E> any desired edge data object
 * @param <P> any desired point data object
 */
public interface MapFactory<F,E,P> {

	/**
	 * method to create the point
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	MapPoint<P> createPoint(int x, int y);

	/**
	 * method to create the field - requires the unique center
	 * 
	 * @param center
	 *            must be uniqe
	 * @return
	 */
	MapField<F,E,P> createField(MapPoint<P> center);

	/**
	 * method to create the edge
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	MapEdge<E,P> createEdge(MapPoint<P> a, MapPoint<P> b);

	/**
	 * override this method to set the map style
	 * 
	 * @return
	 */
	MapStyle getStyle();

}
