package de.frank.martin.maplib;

/**
 * the map factory is required to create the map parts:
 * <li>field</li>
 * <li>edge</li>
 * <li>point</li>
 * 
 * @author martinFrank
 *
 * @param <T>
 *            any desired object
 */
public interface MapFactory<T> {

	/**
	 * method to create the point
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	Point createPoint(int x, int y);

	/**
	 * method to create the field - requires the unique center
	 * 
	 * @param center
	 *            must be uniqe
	 * @return
	 */
	Field<T> createField(Point center);

	/**
	 * method to create the edge
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	Edge createEdge(Point a, Point b);

	/**
	 * override this method to set the map style
	 * 
	 * @return
	 */
	MapStyle getStyle();

}
