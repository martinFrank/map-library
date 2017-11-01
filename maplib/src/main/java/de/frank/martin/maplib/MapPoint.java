package de.frank.martin.maplib;

import java.util.Set;

import de.frank.martin.drawlib.PanScale;

/**
 * a point is a central element of each map - it's used for mapfield center
 * points and for map edges (they're a line from point a --> b)
 * 
 * @author martinFrank
 *
 * @param <P> any desired point data object
 */
public interface MapPoint<P> extends PanScale {

	/**
	 * x of the point after scaling and panning
	 * @return
	 */
	int getTransformedX();

	/**
	 * y of the point after scaling and panning
	 * @return
	 */
	
	int getTransformedY();

	/**
	 * set point location
	 * @param x
	 * @param y
	 */
	void setXY(int x, int y);

	/**
	 * the point location 
	 * @return x
	 */
	int getX();

	/**
	 * the point location
	 * @return y
	 */
	int getY();
	
	
	/**
	 * Customizable data
	 * @return
	 */
	P getPointData();

	/**
	 * Customizable data
	 * @param u
	 */
	void setPointData(P u);
	
	/**
	 * set of all edges that are connected to this point
	 * @return
	 */
	Set<MapEdge<?,P>> getEdges();

}
