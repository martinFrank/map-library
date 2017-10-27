package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.List;

/**
 * this is an implementation of the map field, merely the drawable interface is
 * not implemented.
 *
 * @param <T>
 *            any desired object
 */
public abstract class AbstractField<T> implements Field<T> {

	/**
	 * depending on the map type each field has a certain amount of edges.
	 */
	private final List<Edge> edgeList = new ArrayList<>();

	/**
	 * the center of the field - it's used as uniqe identifier
	 */
	private final Point center;

	/**
	 * the fields are indiced as well - its another unique identifier
	 */
	private final Point index;

	/**
	 * each field is connected (via the edges) to other fields, these are the
	 * neighbors - all neighbors are listed here
	 */
	private final List<Field<? extends T>> nbList = new ArrayList<>();

	/**
	 * the constructor requires the factory to created it's edges - it also requires
	 * a center point
	 * 
	 * @param c
	 *            center
	 * @param f
	 *            factory
	 */
	public AbstractField(Point c, MapFactory<? extends T> f) {
		index = f.createPoint(c.x(), c.y());
		center = f.createPoint(0, 0);
		setCenter(c, f);
		createShape(f);
	}

	/**
	 * this is where the map magic happens - here are the fields created according
	 * to the factory's map style.
	 * 
	 * @param factory
	 */
	private void createShape(MapFactory<? extends T> factory) {
		switch (factory.getStyle()) {
		case SQUARE4:
		case SQUARE8:
			createSquares(factory);
			break;
		case HEX_VERTICAL:
			createHexesVertical(factory);
			break;
		case HEX_HORIZONTAL:
			createHexesHorizontal(factory);
			break;
		case TRIANGLE_HORIZONTAL:
			createTriangleHorizontal(factory);
			break;
		case TRIANGLE_VERTICAL:
			createTriangleVertical(factory);
			break;
		}
	}

	/**
	 * before map fields are created you need to create a center of the field.
	 * according to the factory's map style the center is located on different
	 * positions.
	 * 
	 * @param c
	 *            temporary center
	 * @param f
	 *            factory
	 */
	private void setCenter(Point c, MapFactory<? extends T> f) {
		switch (f.getStyle()) {
		case SQUARE4:
		case SQUARE8:
			setCenterSquare(c);
			break;
		case HEX_VERTICAL:
			setCenterHexVertical(c);
			break;
		case HEX_HORIZONTAL:
			setCenterHexHorizontal(c);
			break;
		case TRIANGLE_HORIZONTAL:
			setCenterTriangleHorizontal(c);
			break;
		case TRIANGLE_VERTICAL:
			setCenterTriangleVertical(c);
			break;
		}
	}

	/**
	 *  helper method to set triangle center
	 * @param c temporary center
	 */
	private void setCenterTriangleVertical(Point c) {
		if (index.y() % 2 == 0) {
			if (index.x() % 2 == 0) {
				center.set(1 + (3 * c.x()), 2 + (2 * c.y()));
			} else {
				center.set(2 + (3 * c.x()), 2 + (2 * c.y()));
			}
		} else {

			if (index.x() % 2 == 0) {
				center.set(2 + (3 * c.x()), 2 + (2 * c.y()));
			} else {
				center.set(1 + (3 * c.x()), 2 + (2 * c.y()));
			}
		}
	}

	/**
	 * helper method to create triangle center
	 * @param c temporary center
	 */
	private void setCenterTriangleHorizontal(Point c) {
		if (index.y() % 2 == 0) {
			if (index.x() % 2 == 0) {
				center.set(2 + (2 * c.x()), 2 + (3 * c.y()));
			} else {
				center.set(2 + (2 * c.x()), 1 + (3 * c.y()));
			}
		} else {

			if (index.x() % 2 == 0) {
				center.set(2 + (2 * c.x()), 1 + (3 * c.y()));
			} else {
				center.set(2 + (2 * c.x()), 2 + (3 * c.y()));
			}
		}
	}

	/**
	 * helper method to create center for hex fields
	 * @param c temporary center
	 */
	private void setCenterHexHorizontal(Point c) {
		if (c.x() % 2 == 0) {
			center.set(2 + (3 * c.x()), 2 + (4 * c.y()));
		} else {
			center.set(2 + (3 * c.x()), 4 + (4 * c.y()));
		}
	}

	/**
	 * helper method to create center for hex fields
	 * @param c temporary center
	 */
	private void setCenterHexVertical(Point c) {
		if (c.y() % 2 == 0) {
			center.set(2 + (4 * c.x()), 2 + (3 * c.y()));
		} else {
			center.set(4 + (4 * c.x()), 2 + (3 * c.y()));
		}
	}

	/**
	 * helper method to create center for squared fields
	 * @param c temporary center
	 */
	private void setCenterSquare(Point c) {
		center.set(1 + (2 * c.x()), 1 + (2 * c.y()));
	}

	@Override
	public void scale(float scale) {
		for (Edge e : edgeList) {
			e.scale(scale);
		}
		center.scale(scale);
	}

	@Override
	public void pan(int dx, int dy) {
		for (Edge e : edgeList) {
			e.pan(dx, dy);
		}
		center.pan(dx, dy);
	}

	@Override
	public Point center() {
		return center;
	}

//	@Override
//	public int ix() {
//		return index.x();
//	}
//
//	@Override
//	public int iy() {
//		return index.y();
//	}
	
	@Override
	public Point index() {
		return index();
	}
	/**
	 * helper method to create triangle fields
	 * @param factory
	 */
	private void createTriangleVertical(MapFactory<? extends T> factory) {
		boolean isPointingLeft = false;
		if (index.y() % 2 == 0) {
			if (index.x() % 2 == 0) {
				isPointingLeft = false;
			} else {
				isPointingLeft = true;
			}
		} else {
			if (index.x() % 2 == 0) {
				isPointingLeft = true;
			} else {
				isPointingLeft = false;
			}
		}

		if (isPointingLeft) {
			for (int i = 0; i < 3; i++) {
				Point a = factory.createPoint(1, 1);
				Point b = factory.createPoint(1, 1);
				if (i == 0) {
					a.set(center.x() - 2, center.y());
					b.set(center.x() + 1, center.y() - 2);
				}
				if (i == 1) {
					a.set(center.x() + 1, center.y() - 2);
					b.set(center.x() + 1, center.y() + 2);
				}
				if (i == 2) {
					a.set(center.x() + 1, center.y() + 2);
					b.set(center.x() - 2, center.y());
				}
				createAndAddEdge(a, b, factory);
			}
		} else {
			for (int i = 0; i < 3; i++) {
				Point a = factory.createPoint(1, 1);
				Point b = factory.createPoint(1, 1);
				if (i == 0) {
					a.set(center.x() - 1, center.y() - 2);
					b.set(center.x() + 2, center.y());
				}
				if (i == 1) {
					a.set(center.x() + 2, center.y());
					b.set(center.x() - 1, center.y() + 2);
				}
				if (i == 2) {
					a.set(center.x() - 1, center.y() + 2);
					b.set(center.x() - 1, center.y() - 2);
				}
				createAndAddEdge(a, b, factory);
			}
		}
	}

	/**
	 * helper method to create triangle fields
	 * @param factory
	 */
	private void createTriangleHorizontal(MapFactory<? extends T> factory) {
		boolean isUpside = false;
		if (index.y() % 2 == 0) {
			if (index.x() % 2 == 0) {
				isUpside = true;
			} else {
				isUpside = false;
			}
		} else {
			if (index.x() % 2 == 0) {
				isUpside = false;
			} else {
				isUpside = true;
			}
		}
		if (isUpside) {
			for (int i = 0; i < 3; i++) {
				Point a = factory.createPoint(1, 1);
				Point b = factory.createPoint(1, 1);
				if (i == 0) {
					a.set(center.x() - 2, center.y() + 1);
					b.set(center.x(), center.y() - 2);
				}
				if (i == 1) {
					a.set(center.x(), center.y() - 2);
					b.set(center.x() + 2, center.y() + 1);
				}
				if (i == 2) {
					a.set(center.x() + 2, center.y() + 1);
					b.set(center.x() - 2, center.y() + 1);
				}
				createAndAddEdge(a, b, factory);
			}
		} else {
			for (int i = 0; i < 3; i++) {
				Point a = factory.createPoint(1, 1);
				Point b = factory.createPoint(1, 1);
				if (i == 0) {
					a.set(center.x() - 2, center.y() - 1);
					b.set(center.x() + 2, center.y() - 1);
				}
				if (i == 1) {
					a.set(center.x() + 2, center.y() - 1);
					b.set(center.x(), center.y() + 2);
				}
				if (i == 2) {
					a.set(center.x(), center.y() + 2);
					b.set(center.x() - 2, center.y() - 1);
				}
				createAndAddEdge(a, b, factory);
			}
		}

	}

	/**
	 * helper method to create hexagonal fields
	 * @param factory
	 */
	private void createHexesVertical(MapFactory<? extends T> factory) {
		for (int i = 0; i < 6; i++) {
			Point a = factory.createPoint(1, 1);
			Point b = factory.createPoint(1, 1);
			if (i == 0) {
				a.set(center.x() - 2, center.y() - 1);
				b.set(center.x(), center.y() - 2);
			}
			if (i == 1) {
				a.set(center.x(), center.y() - 2);
				b.set(center.x() + 2, center.y() - 1);
			}
			if (i == 2) {
				a.set(center.x() + 2, center.y() - 1);
				b.set(center.x() + 2, center.y() + 1);
			}
			if (i == 3) {
				a.set(center.x() + 2, center.y() + 1);
				b.set(center.x(), center.y() + 2);
			}
			if (i == 4) {
				a.set(center.x(), center.y() + 2);
				b.set(center.x() - 2, center.y() + 1);
			}
			if (i == 5) {
				a.set(center.x() - 2, center.y() + 1);
				b.set(center.x() - 2, center.y() - 1);
			}
			createAndAddEdge(a, b, factory);
		}
	}

	/**
	 * helper method to create and add edges
	 * @param a 
	 * @param b
	 * @param factory
	 */
	private void createAndAddEdge(Point a, Point b, MapFactory<? extends T> factory) {
		Edge edge = factory.createEdge(a, b);
		edgeList.add(edge);
	}

	/**
	 * helper method to create hexagonal fields
	 * @param factory
	 */
	private void createHexesHorizontal(MapFactory<? extends T> factory) {
		for (int i = 0; i < 6; i++) {
			Point a = factory.createPoint(1, 1);
			Point b = factory.createPoint(1, 1);
			if (i == 0) {
				a.set(center.x() - 2, center.y());
				b.set(center.x() - 1, center.y() - 2);
			}
			if (i == 1) {
				a.set(center.x() - 1, center.y() - 2);
				b.set(center.x() + 1, center.y() - 2);
			}
			if (i == 2) {
				a.set(center.x() + 1, center.y() - 2);
				b.set(center.x() + 2, center.y());
			}
			if (i == 3) {
				a.set(center.x() + 2, center.y());
				b.set(center.x() + 1, center.y() + 2);
			}
			if (i == 4) {
				a.set(center.x() + 1, center.y() + 2);
				b.set(center.x() - 1, center.y() + 2);
			}
			if (i == 5) {
				a.set(center.x() - 1, center.y() + 2);
				b.set(center.x() - 2, center.y());
			}
			createAndAddEdge(a, b, factory);
		}
	}

	/**
	 * helper method to create squared fields
	 * @param factory
	 */
	private void createSquares(MapFactory<? extends T> factory) {
		for (int i = 0; i < 4; i++) {
			Point a = factory.createPoint(1, 1);
			Point b = factory.createPoint(1, 1);
			if (i == 0) {
				a.set(center.x() - 1, center.y() - 1);
				b.set(center.x() + 1, center.y() - 1);
			}
			if (i == 1) {
				a.set(center.x() + 1, center.y() - 1);
				b.set(center.x() + 1, center.y() + 1);
			}
			if (i == 2) {
				a.set(center.x() + 1, center.y() + 1);
				b.set(center.x() - 1, center.y() + 1);
			}
			if (i == 3) {
				a.set(center.x() - 1, center.y() + 1);
				b.set(center.x() - 1, center.y() - 1);
			}
			createAndAddEdge(a, b, factory);
		}
	}

	@Override
	public List<Field<? extends T>> getNeigbourList() {
		return nbList;
	}

	@Override
	public List<Edge> getEdgeList() {
		return edgeList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
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
		AbstractField<?> other = (AbstractField<?>) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + center.toString();
	}

}
