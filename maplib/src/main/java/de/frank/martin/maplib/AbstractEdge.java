package de.frank.martin.maplib;

import java.util.HashSet;
import java.util.Set;

/**
 * Edges surround fields, this is a full implementation, only the drawable
 * interface is not implemented
 * 
 * @author martinFrank
 * @param <P>
 * 
 */
public abstract class AbstractEdge<E, P> implements MapEdge<E, P> {

	/**
	 * an edge goes from a -> b; this is a
	 */
	private final MapPoint<P> a;

	/**
	 * an edge goes from a -> b; this is b
	 */
	private final MapPoint<P> b;

	private Set<MapField<?, E, P>> fields = new HashSet<>();
	

	/**
	 * constructor requires both a and b, because an edge goes from a -> b
	 * 
	 * @param a
	 * @param b
	 */
	public AbstractEdge(MapPoint<P> a, MapPoint<P> b) {
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
	public MapPoint<P> getA() {
		return a;
	}

	@Override
	public MapPoint<P> getB() {
		return b;
	}

	@Override
	public Set<MapField<?, E, P>> getFields() {
		return fields;
	}

	@Override
	public String toString() {
		return "["+getA().toString()+":"+getB().toString()+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int as = (a == null) ? 0 : a.hashCode();
		int bs = (b == null) ? 0 : b.hashCode();
		int abSum = as+bs;
		result = prime * result + abSum;
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
		AbstractEdge<E, P> other = (AbstractEdge<E, P>) obj;
		if (a.equals(other.a) && b.equals(other.b)) {
			return true;
		}
		return a.equals(other.b) && b.equals(other.a);
	}

}
