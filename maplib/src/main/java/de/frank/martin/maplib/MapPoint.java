package de.frank.martin.maplib;

import de.frank.martin.drawlib.PanScale;

/**
 * a point is a central element of each map - it's used for mapfield center
 * points and for map edges (they're a line from point a --> b)
 * 
 * @author martinFrank
 *
 */
public interface MapPoint extends PanScale {

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

}
