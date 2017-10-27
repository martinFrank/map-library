package de.frank.martin.maplib;

import de.frank.martin.drawlib.PanScale;

/**
 * a point is a central element of each map - it's used for mapfield center
 * points and for map edges (they're a line from point a --> b)
 * 
 * @author martinFrank
 *
 */
public interface Point extends PanScale {

	/**
	 * x of the point after scaling and panning
	 * @return
	 */
	int xPanScaled();

	/**
	 * y of the point after scaling and panning
	 * @return
	 */
	
	int yPanScaled();

	/**
	 * set point location
	 * @param x
	 * @param y
	 */
	void set(int x, int y);

	/**
	 * the point location 
	 * @return x
	 */
	int x();

	/**
	 * the point location
	 * @return y
	 */
	int y();

}
