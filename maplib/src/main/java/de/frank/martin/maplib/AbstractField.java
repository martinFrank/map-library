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
public abstract class AbstractField<T> implements MapField<T> {

	/**
	 * depending on the map type each field has a certain amount of edges.
	 */
	private final List<MapEdge> edgeList = new ArrayList<>();

	/**
	 * the center of the field - it's used as unique identifier
	 */
	private final MapPoint center;

	/**
	 * the fields are indiced as well - its another unique identifier
	 */
	private final MapPoint index;

	/**
	 * each field is connected (via the edges) to other fields, these are the
	 * neighbors - all neighbors are listed here
	 */
	private final List<MapField<? extends T>> nbList = new ArrayList<>();

	/**
	 * the constructor requires the factory to created it's edges - it also requires
	 * a center point
	 * 
	 * @param c
	 *            center
	 * @param f
	 *            factory
	 */
	public AbstractField(MapPoint c, MapFactory<? extends T> f) {
		index = f.createPoint(c.getX(), c.getY());
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
	private void setCenter(MapPoint c, MapFactory<? extends T> f) {
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
	private void setCenterTriangleVertical(MapPoint c) {
		if (index.getY() % 2 == 0) {
			if (index.getX() % 2 == 0) {
				center.setXY(1 + (3 * c.getX()), 2 + (2 * c.getY()));
			} else {
				center.setXY(2 + (3 * c.getX()), 2 + (2 * c.getY()));
			}
		} else {

			if (index.getX() % 2 == 0) {
				center.setXY(2 + (3 * c.getX()), 2 + (2 * c.getY()));
			} else {
				center.setXY(1 + (3 * c.getX()), 2 + (2 * c.getY()));
			}
		}
	}

	/**
	 * helper method to create triangle center
	 * @param c temporary center
	 */
	private void setCenterTriangleHorizontal(MapPoint c) {
		if (index.getY() % 2 == 0) {
			if (index.getX() % 2 == 0) {
				center.setXY(2 + (2 * c.getX()), 2 + (3 * c.getY()));
			} else {
				center.setXY(2 + (2 * c.getX()), 1 + (3 * c.getY()));
			}
		} else {

			if (index.getX() % 2 == 0) {
				center.setXY(2 + (2 * c.getX()), 1 + (3 * c.getY()));
			} else {
				center.setXY(2 + (2 * c.getX()), 2 + (3 * c.getY()));
			}
		}
	}

	/**
	 * helper method to create center for hex fields
	 * @param c temporary center
	 */
	private void setCenterHexHorizontal(MapPoint c) {
		if (c.getX() % 2 == 0) {
			center.setXY(2 + (3 * c.getX()), 2 + (4 * c.getY()));
		} else {
			center.setXY(2 + (3 * c.getX()), 4 + (4 * c.getY()));
		}
	}

	/**
	 * helper method to create center for hex fields
	 * @param c temporary center
	 */
	private void setCenterHexVertical(MapPoint c) {
		if (c.getY() % 2 == 0) {
			center.setXY(2 + (4 * c.getX()), 2 + (3 * c.getY()));
		} else {
			center.setXY(4 + (4 * c.getX()), 2 + (3 * c.getY()));
		}
	}

	/**
	 * helper method to create center for squared fields
	 * @param c temporary center
	 */
	private void setCenterSquare(MapPoint c) {
		center.setXY(1 + (2 * c.getX()), 1 + (2 * c.getY()));
	}

	@Override
	public void scale(float scale) {
		for (MapEdge e : edgeList) {
			e.scale(scale);
		}
		center.scale(scale);
	}

	@Override
	public void pan(int dx, int dy) {
		for (MapEdge e : edgeList) {
			e.pan(dx, dy);
		}
		center.pan(dx, dy);
	}

	@Override
	public MapPoint getCenter() {
		return center;
	}

	
	@Override
	public MapPoint getIndex() {
		return index;
	}
	/**
	 * helper method to create triangle fields
	 * @param factory
	 */
	private void createTriangleVertical(MapFactory<? extends T> factory) {
		boolean isPointingLeft = false;
		if (index.getY() % 2 == 0) {
			if (index.getX() % 2 == 0) {
				isPointingLeft = false;
			} else {
				isPointingLeft = true;
			}
		} else {
			if (index.getX() % 2 == 0) {
				isPointingLeft = true;
			} else {
				isPointingLeft = false;
			}
		}

		if (isPointingLeft) {
			for (int i = 0; i < 3; i++) {
				MapPoint a = factory.createPoint(1, 1);
				MapPoint b = factory.createPoint(1, 1);
				if (i == 0) {
					a.setXY(center.getX() - 2, center.getY());
					b.setXY(center.getX() + 1, center.getY() - 2);
				}
				if (i == 1) {
					a.setXY(center.getX() + 1, center.getY() - 2);
					b.setXY(center.getX() + 1, center.getY() + 2);
				}
				if (i == 2) {
					a.setXY(center.getX() + 1, center.getY() + 2);
					b.setXY(center.getX() - 2, center.getY());
				}
				createAndAddEdge(a, b, factory);
			}
		} else {
			for (int i = 0; i < 3; i++) {
				MapPoint a = factory.createPoint(1, 1);
				MapPoint b = factory.createPoint(1, 1);
				if (i == 0) {
					a.setXY(center.getX() - 1, center.getY() - 2);
					b.setXY(center.getX() + 2, center.getY());
				}
				if (i == 1) {
					a.setXY(center.getX() + 2, center.getY());
					b.setXY(center.getX() - 1, center.getY() + 2);
				}
				if (i == 2) {
					a.setXY(center.getX() - 1, center.getY() + 2);
					b.setXY(center.getX() - 1, center.getY() - 2);
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
		if (index.getY() % 2 == 0) {
			if (index.getX() % 2 == 0) {
				isUpside = true;
			} else {
				isUpside = false;
			}
		} else {
			if (index.getX() % 2 == 0) {
				isUpside = false;
			} else {
				isUpside = true;
			}
		}
		if (isUpside) {
			for (int i = 0; i < 3; i++) {
				MapPoint a = factory.createPoint(1, 1);
				MapPoint b = factory.createPoint(1, 1);
				if (i == 0) {
					a.setXY(center.getX() - 2, center.getY() + 1);
					b.setXY(center.getX(), center.getY() - 2);
				}
				if (i == 1) {
					a.setXY(center.getX(), center.getY() - 2);
					b.setXY(center.getX() + 2, center.getY() + 1);
				}
				if (i == 2) {
					a.setXY(center.getX() + 2, center.getY() + 1);
					b.setXY(center.getX() - 2, center.getY() + 1);
				}
				createAndAddEdge(a, b, factory);
			}
		} else {
			for (int i = 0; i < 3; i++) {
				MapPoint a = factory.createPoint(1, 1);
				MapPoint b = factory.createPoint(1, 1);
				if (i == 0) {
					a.setXY(center.getX() - 2, center.getY() - 1);
					b.setXY(center.getX() + 2, center.getY() - 1);
				}
				if (i == 1) {
					a.setXY(center.getX() + 2, center.getY() - 1);
					b.setXY(center.getX(), center.getY() + 2);
				}
				if (i == 2) {
					a.setXY(center.getX(), center.getY() + 2);
					b.setXY(center.getX() - 2, center.getY() - 1);
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
			MapPoint a = factory.createPoint(1, 1);
			MapPoint b = factory.createPoint(1, 1);
			if (i == 0) {
				a.setXY(center.getX() - 2, center.getY() - 1);
				b.setXY(center.getX(), center.getY() - 2);
			}
			if (i == 1) {
				a.setXY(center.getX(), center.getY() - 2);
				b.setXY(center.getX() + 2, center.getY() - 1);
			}
			if (i == 2) {
				a.setXY(center.getX() + 2, center.getY() - 1);
				b.setXY(center.getX() + 2, center.getY() + 1);
			}
			if (i == 3) {
				a.setXY(center.getX() + 2, center.getY() + 1);
				b.setXY(center.getX(), center.getY() + 2);
			}
			if (i == 4) {
				a.setXY(center.getX(), center.getY() + 2);
				b.setXY(center.getX() - 2, center.getY() + 1);
			}
			if (i == 5) {
				a.setXY(center.getX() - 2, center.getY() + 1);
				b.setXY(center.getX() - 2, center.getY() - 1);
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
	private void createAndAddEdge(MapPoint a, MapPoint b, MapFactory<? extends T> factory) {
		MapEdge edge = factory.createEdge(a, b);
		edgeList.add(edge);
	}

	/**
	 * helper method to create hexagonal fields
	 * @param factory
	 */
	private void createHexesHorizontal(MapFactory<? extends T> factory) {
		for (int i = 0; i < 6; i++) {
			MapPoint a = factory.createPoint(1, 1);
			MapPoint b = factory.createPoint(1, 1);
			if (i == 0) {
				a.setXY(center.getX() - 2, center.getY());
				b.setXY(center.getX() - 1, center.getY() - 2);
			}
			if (i == 1) {
				a.setXY(center.getX() - 1, center.getY() - 2);
				b.setXY(center.getX() + 1, center.getY() - 2);
			}
			if (i == 2) {
				a.setXY(center.getX() + 1, center.getY() - 2);
				b.setXY(center.getX() + 2, center.getY());
			}
			if (i == 3) {
				a.setXY(center.getX() + 2, center.getY());
				b.setXY(center.getX() + 1, center.getY() + 2);
			}
			if (i == 4) {
				a.setXY(center.getX() + 1, center.getY() + 2);
				b.setXY(center.getX() - 1, center.getY() + 2);
			}
			if (i == 5) {
				a.setXY(center.getX() - 1, center.getY() + 2);
				b.setXY(center.getX() - 2, center.getY());
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
			MapPoint a = factory.createPoint(1, 1);
			MapPoint b = factory.createPoint(1, 1);
			if (i == 0) {
				a.setXY(center.getX() - 1, center.getY() - 1);
				b.setXY(center.getX() + 1, center.getY() - 1);
			}
			if (i == 1) {
				a.setXY(center.getX() + 1, center.getY() - 1);
				b.setXY(center.getX() + 1, center.getY() + 1);
			}
			if (i == 2) {
				a.setXY(center.getX() + 1, center.getY() + 1);
				b.setXY(center.getX() - 1, center.getY() + 1);
			}
			if (i == 3) {
				a.setXY(center.getX() - 1, center.getY() + 1);
				b.setXY(center.getX() - 1, center.getY() - 1);
			}
			createAndAddEdge(a, b, factory);
		}
	}

	@Override
	public List<MapField<? extends T>> getNeigbourList() {
		return nbList;
	}

	@Override
	public List<MapEdge> getEdgeList() {
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
		return center.toString();
	}

}
