package de.frank.martin.maplib;

/**
 * Edges surround fields, this is a full implementation, only the drawable
 * interface is not implemented
 * 
 * @author martinFrank
 * 
 */
public abstract class AbstractEdge implements MapEdge {

	/**
	 * an edge goes from a -> b; this is a
	 */
	private final MapPoint a;
	
	/**
	 * an edge goes from a -> b; this is b
	 */
	private final MapPoint b;

	/**
	 * constructor requires both a and b, because an edge goes from a -> b
	 * @param a
	 * @param b
	 */
	public AbstractEdge(MapPoint a, MapPoint b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void scale(float scale) {
		a.scale(scale);
		b.scale(scale);
	}

	@Override
	public void pan(int dx, int dy) {
		a.pan(dx, dy);
		b.pan(dx, dy);
	}

	@Override
	public MapPoint getA() {
		return a;
	}

	@Override
	public MapPoint getB() {
		return b;
	}

	@Override
	public String toString() {
		return  getA().toString() + " --> " + b.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
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
		AbstractEdge other = (AbstractEdge) obj;
		if (a.equals(other.a) && b.equals(other.b)) {
			return true;
		}
		return a.equals(other.b) && b.equals(other.a);
	}

}
