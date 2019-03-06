package de.elite.games.maplib;

import de.elite.games.geolib.GeoPoint;

import java.util.HashSet;
import java.util.Set;

/**
 * basic implementation fo the Point interface, it's fully implemented, the only
 * thing that's not implemented is the drawable Interface
 * 
 * @author martinFrank
 *
 */
public abstract class AbstractPoint<P> implements MapPoint<P> {

	
	private Set<MapEdge>edges = new HashSet<>();
	
	/**
	 * the GeoPoint of the Point (it's a rasteredMap so we use GeoPoint - that's
	 * a 2D integer Implementation)
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
	 * scale factor
	 */
	private double scaleFactor = 1D;

	/**
	 * A Point at a certain location
	 * 
	 * @param x
	 *            location
	 * @param y
	 *            location
	 */
	public AbstractPoint(int x, int y) {
		point = new GeoPoint(x, y);
	}

	@Override
	public void scale(double s) {
		scale = new GeoPoint((int) (point.getX() * s), (int) (point.getY() * s));
		scaleFactor = s;
	}

	@Override
	public void pan(double dx, double dy) {
		pan = new GeoPoint((int)dx, (int)dy);
	}

	@Override
	public double getScale() {
		return scaleFactor;
	}

	@Override
	public double getPanX() {
		return pan.getX();
	}

	@Override
	public double getPanY() {
		return pan.getY();
	}

	@Override
	public int getTransformedX() {
		return scale.getX() + pan.getX();
	}

	@Override
	public int getTransformedY() {
		return scale.getY() + pan.getY();
	}

	@Override
	public void setXY(int x, int y) {
		point = new GeoPoint(x, y);
	}

	@Override
	public int getX() {
		return point.getX();
	}

	@Override
	public int getY() {
		return point.getY();
	}


	@Override
	public Set<MapEdge> getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		return "("+point.getX()+"/"+point.getY()+")";

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
		@SuppressWarnings("unchecked")
		AbstractPoint<P> other = (AbstractPoint<P>) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}

}
