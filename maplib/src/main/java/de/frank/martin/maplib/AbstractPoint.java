package de.frank.martin.maplib;

import de.frank.martin.geolib.GeoPoint;

/**
 * basic implementation fo the Point interface, it's fully implemented, the only
 * thing that's not implemented is the drawable Interface
 * 
 * @author martinFrank
 *
 */
public abstract class AbstractPoint implements Point {

	/**
	 * the GeoPoint of the Point (it's a rasteredMap so we use GeoPoint - that's a 2D integer Implementation)
	 */
	private GeoPoint point;
	
	/**
	 * this is the panned point
	 */
	private GeoPoint pan = new GeoPoint();
	
	/**
	 * this is the scaled point
	 */
	private GeoPoint scale = new GeoPoint();

	/**
	 * A Point at a certain location
	 * @param x location
	 * @param y location
	 */
	public AbstractPoint(int x, int y) {
		point = new GeoPoint(x, y);
	}

	@Override
	public void scale(float s) {
		scale = new GeoPoint((int)(point.x() * s), (int)(point.y() * s));
	}

	@Override
	public void pan(int dx, int dy) {
		pan = new GeoPoint(dx, dy);
	}

	@Override
	public int xPanScaled() {
		return scale.x() + pan.x();
	}

	@Override
	public int yPanScaled() {
		return scale.y() + pan.y();
	}

	@Override
	public void set(int x, int y) {
		point = new GeoPoint(x, y);
	}

	@Override
	public int x() {
		return point.x();
	}

	@Override
	public int y() {
		return point.y();
	}

	@Override
	public String toString() {
		return point.toString();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPoint other = (AbstractPoint) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		}
			else if (!point.equals(other.point))
			return false;
		return true;
	}
	
	

}
