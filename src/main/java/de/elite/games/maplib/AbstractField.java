package de.elite.games.maplib;

import de.elite.games.geolib.GeoPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * this is an implementation of the map field, merely the drawable interface is
 * not implemented.
 *
 */
public abstract class AbstractField<D, E extends MapEdge<?,P>, P extends MapPoint<?>> implements MapField<D,E,P> {



	/**
	 * depending on the map type each field has a certain amount of edges.
	 */
	private final List<E> edges = new ArrayList<>();

	/**
	 * the center of the field - it's used as unique identifier
	 */
	private P  center;

	/**
	 * the fields are indiced as well - its another unique identifier
	 */
	private GeoPoint index;
	
	/**
	 * each field is connected (via the edges) to other fields, these are the
	 * neighbors - all neighbors are listed here
	 */
	private final Set<MapField<?,E,P>> neighbours = new HashSet<>();

	
	private List<P>points = new ArrayList<>();

	/**
	 * this is where the map magic happens - here are the fields created according
	 * to the factory's map style.
	 * 
	 * @param factory
	 */
    @Override
    public void createShape(MapPartFactory<?, ?, E, P, ?> factory, MapStyle style) {
		switch (style) {
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
	 * @param center temporary center
	 * @param style style
	 */
	@Override
	public void setCenter(P center, MapStyle style) {
	    initCenter(center);
	    adjustCenter(style);
	}

    protected void adjustCenter(MapStyle style){
        switch (style) {
            case SQUARE4:
            case SQUARE8:
                setCenterSquare(center);
                break;
            case HEX_VERTICAL:
                setCenterHexVertical(center);
                break;
            case HEX_HORIZONTAL:
                setCenterHexHorizontal(center);
                break;
            case TRIANGLE_HORIZONTAL:
                setCenterTriangleHorizontal(center);
                break;
            case TRIANGLE_VERTICAL:
                setCenterTriangleVertical(center);
                break;
        }
    }

    protected void initCenter(P center){
        this.index = new GeoPoint(center.getX(), center.getY());
        this.center = center;
    }


    /**
	 *  helper method to set triangle center
	 * @param c temporary center
	 */
	private void setCenterTriangleVertical(P c) {
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
	private void setCenterTriangleHorizontal(P c) {
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
	private void setCenterHexHorizontal(P c) {
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
	private void setCenterHexVertical(P c) {
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
	private void setCenterSquare(P c) {
		center.setXY(1 + (2 * c.getX()), 1 + (2 * c.getY()));
	}

	@Override
	public void scale(double scale) {
		for (MapEdge e : edges) {
			e.scale(scale);
		}
		center.scale(scale);
	}

	@Override
	public void pan(double dx, double dy) {
		for (MapEdge e : edges) {
			e.pan(dx, dy);
		}
		center.pan(dx, dy);
	}

	@Override
	public double getScale() {
		return center.getScale();
	}

	@Override
	public double getPanX() {
		return center.getPanX();
	}

	@Override
	public double getPanY() {
		return center.getPanY();
	}

	@Override
	public P getCenter() {
		return center;
	}

	
	@Override
	public GeoPoint getIndex() {
		return index;
	}
	
	@Override
	public List<P> getPoints() {
		return points;
	}
	
	/**
	 * helper method to create triangle fields
	 * @param factory
	 */
    private void createTriangleVertical(MapPartFactory<?, ?, E, P, ?> factory) {
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
				P a = factory.createPoint(1, 1);
				P b = factory.createPoint(1, 1);
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
				P a = factory.createPoint(1, 1);
				P b = factory.createPoint(1, 1);
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
    private void createTriangleHorizontal(MapPartFactory<?, ?, E, P, ?> factory) {
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
				P a = factory.createPoint(1, 1);
				P b = factory.createPoint(1, 1);
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
				P a = factory.createPoint(1, 1);
				P b = factory.createPoint(1, 1);
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
    private void createHexesVertical(MapPartFactory<?, ?, E, P, ?> factory) {
		for (int i = 0; i < 6; i++) {
			P a = factory.createPoint(1, 1);
			P b = factory.createPoint(1, 1);
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
    private void createAndAddEdge(P a, P b, MapPartFactory<?, ?, E, P, ?> factory) {
		E edge = factory.createEdge(a, b);
		edges.add(edge);
	}

	/**
	 * helper method to create hexagonal fields
	 * @param factory
	 */
    private void createHexesHorizontal(MapPartFactory<?, ?, E, P, ?> factory) {
		for (int i = 0; i < 6; i++) {
			P a = factory.createPoint(1, 1);
			P b = factory.createPoint(1, 1);
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
    private void createSquares(MapPartFactory<?, ?, E, P, ?> factory) {
		for (int i = 0; i < 4; i++) {
			P a = factory.createPoint(1, 1);
			P b = factory.createPoint(1, 1);
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
	public Set<MapField<?,E,P>> getNeigbours() {
		return neighbours;
	}

	@Override
	public List<E> getEdges() {
		return edges;
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
		@SuppressWarnings("unchecked")
		AbstractField other = (AbstractField) obj;
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
