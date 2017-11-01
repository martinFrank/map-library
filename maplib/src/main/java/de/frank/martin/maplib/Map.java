package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.frank.martin.drawlib.PanScale;

public abstract class Map<F, E, P> implements PanScale {

	private final List<MapField<F, E, P>> fields = new ArrayList<>();

	private final int width;
	private final int height;
	private final MapStyle style;
	private MapFactory<F, E, P> factory;

	public Map(int width, int height, MapFactory<F, E, P> factory) {
		this.width = width;
		this.height = height;
		this.factory = factory;
		this.style = factory.getStyle();
		generateFields();
		reduceMap();
		setNeigbors();
		scale(1);
	}

	public List<MapField<F, E, P>> aStar(MapField<F, E, P> start, MapField<F, E, P> destiny, Walker<F, E, P> walker,
			int maxSearchDepth) {
		return new Astar<F, E, P>().getShortestPath(start, destiny, walker, this, maxSearchDepth);
	}

	public MapStyle getMapStyle() {
		return style;
	}

	public List<MapField<F, E, P>> getFields() {
		return fields;
	}

	public MapField<F, E, P> getFieldByCenter(int x, int y) {
		double radius = getRadiusForScale();
		for (MapField<F, E, P> field : fields) {
			double dx = (double) x - field.getCenter().getTransformedX();
			double dy = (double) y - field.getCenter().getTransformedY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < radius) {
				return field;
			}
		}
		return null;
	}

	public MapField<F, E, P> getFieldByIndex(int ix, int iy) {
		int index = iy * width + ix;
		return fields.get(index);
	}

	public MapEdge<E, P> getEdge(int x, int y) {
		double radius = getRadiusForScale() / 2d;
		for (MapField<F, E, P> field : fields) {
			for (MapEdge<E, P> edge : field.getEdges()) {
				double mx = (edge.getA().getTransformedX() + edge.getB().getTransformedX()) / 2d;
				double my = (edge.getA().getTransformedY() + edge.getB().getTransformedY()) / 2d;
				double dx = (double) x - mx;
				double dy = (double) y - my;
				double aDistance = Math.sqrt(dx * dx + dy * dy);
				if (aDistance < radius) {
					return edge;
				}
			}

		}
		return null;
	}

	public MapPoint<P> getPoint(int x, int y) {
		double radius = getRadiusForScale() / 4d;
		for (MapField<F, E, P> field : fields) {
			for (MapEdge<E, P> edge : field.getEdges()) {
				double ax = (double) x - edge.getA().getTransformedX();
				double ay = (double) y - edge.getA().getTransformedY();
				double aDistance = Math.sqrt(ax * ax + ay * ay);
				if (aDistance < radius) {
					return edge.getA();
				}

				double bx = (double) x - edge.getB().getTransformedX();
				double by = (double) y - edge.getB().getTransformedY();
				double bDistance = Math.sqrt(bx * bx + by * by);
				if (bDistance < radius) {
					return edge.getB();
				}
			}
		}
		return null;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getScaledWidth() {
		int max = 0;
		int cx = 0;
		for (MapField<F, E, P> field : fields) {
			if (field.getCenter().getX() > cx) {
				cx = field.getCenter().getX();
				for (MapEdge<E, P> e : field.getEdges()) {
					if (e.getA().getTransformedX() > max) {
						max = e.getA().getTransformedX();
					}
					if (e.getB().getTransformedX() > max) {
						max = e.getB().getTransformedX();
					}
				}
			}
		}
		return max;
	}

	public int getScaledHeight() {
		int max = 0;
		int cy = 0;
		for (MapField<F, E, P> field : fields) {
			if (field.getCenter().getY() > cy) {
				cy = field.getCenter().getY();
				for (MapEdge<E, P> e : field.getEdges()) {
					if (e.getA().getTransformedY() > max) {
						max = e.getA().getTransformedY();
					}
					if (e.getB().getTransformedY() > max) {
						max = e.getB().getTransformedY();
					}
				}
			}
		}
		return max;
	}

	@Override
	public void scale(float scale) {
		// fieldList.stream().forEach(e -> e.scale(scale))
		for (MapField<F, E, P> field : fields) {
			field.scale(scale);
		}
	}

	@Override
	public void pan(int dx, int dy) {
		// fieldList.stream().forEach(e -> e.pan(dx,dy))
		for (MapField<F, E, P> field : fields) {
			field.pan(dx, dy);
		}
	}

	private double getRadiusForScale() {
		MapField<F, E, P> anyField = fields.get(0);
		MapEdge<E, P> anyEdge = anyField.getEdges().iterator().next();
		double anyx = (double) anyField.getCenter().getTransformedX() - anyEdge.getA().getTransformedX();
		double anyy = (double) anyField.getCenter().getTransformedY() - anyEdge.getA().getTransformedY();
		return Math.sqrt(anyx * anyx + anyy * anyy) / Math.sqrt(2);
	}

	private void reduceMap() {
		reducePoints();
		reduceEdges();
	}

	private void reducePoints() {
		java.util.Map<MapPoint<P>, MapPoint<P>> points = new HashMap<>();
		int unreduced = 0;
		for (MapField<F, E, P> field : fields) {
			// field.getPoints().stream().forEach(e -> pointList.add(e))
			for (MapPoint<P> p : field.getPoints()) {
				points.put(p, p);
				unreduced = unreduced + 1;
			}
		}
		System.out.println("original=" + unreduced + " reduced=" + points.size());

		// replace points in fields
		for (MapField<F, E, P> field : fields) {
			int length = field.getPoints().size();
			for (int i = 0; i < length; i++) {
				MapPoint<P> original = field.getPoints().get(i);
				MapPoint<P> r = points.get(original);
				field.getPoints().set(i, r);
			}
		}

		// replace points in edges
		for (MapField<F, E, P> field : fields) {
			int length = field.getPoints().size();
			for (int i = 0; i < length; i++) {
				MapPoint<P> aOriginal = field.getEdges().get(i).getA();
				MapPoint<P> bOriginal = field.getEdges().get(i).getB();
				MapPoint<P> ra = points.get(aOriginal);
				MapPoint<P> rb = points.get(bOriginal);
				field.getEdges().set(i, factory.createEdge(ra, rb));
			}
		}
	}

	private void reduceEdges() {
		java.util.Map<MapEdge<E, P>, MapEdge<E, P>> edges = new HashMap<>();
		for (MapField<F, E, P> field : fields) {
			// field.getPoints().stream().forEach(e -> pointList.add(e))
			for (MapEdge<E, P> edge : field.getEdges()) {
				edges.put(edge, edge);
			}
		}

		// replace edges in field
		for (MapField<F, E, P> field : fields) {
			int length = field.getPoints().size();
			for (int i = 0; i < length; i++) {
				MapEdge<E, P> original = field.getEdges().get(i);
				MapEdge<E, P> r = edges.get(original);
				field.getEdges().set(i, r);
			}
		}
	}

	private void generateFields() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				MapPoint<P> center = factory.createPoint(dx, dy);
				MapField<F, E, P> field = factory.createField(center);
				fields.add(field);
			}
		}

		// create points for the field as well
		for (MapField<F, E, P> field : fields) {
			for (MapEdge<E, P> edge : field.getEdges()) {
				field.getPoints().add(edge.getA());
			}
		}
	}

	private void setNeigbors() {
		setFieldNeigbors();
		setEdgeFields();
		setPointEdges();
	}

	private void setPointEdges() {
		for (MapField<F, E, P> field : getFields()) {
			for (MapEdge<E, P> e : field.getEdges()) {		
				for (MapField<F, E, P> other : field.getNeigbours()) {
					for (MapEdge<E, P> otherEdge: other.getEdges()){
						
						if(e.getA().equals(otherEdge.getA())){
							e.getA().getEdges().add(otherEdge);
						}
						
						if(e.getB().equals(otherEdge.getA())){
							e.getB().getEdges().add(otherEdge);
						}
						
						if(e.getA().equals(otherEdge.getB())){
							e.getA().getEdges().add(otherEdge);
						}
						
						if(e.getB().equals(otherEdge.getB())){
							e.getB().getEdges().add(otherEdge);
						}
					}
				}
			}
		}
	}

	private void setEdgeFields() {
		for (MapField<F, E, P> field : getFields()) {
			for (MapEdge<E, P> e : field.getEdges()) {
				e.getFields().add(field);
				
				for (MapField<F, E, P> nbg : field.getNeigbours()) {
					for (MapEdge<E, P> nbgEdge: nbg.getEdges()){
						if(nbgEdge.equals(e)){
							e.getFields().add(nbg);
						}
					}
				}
			}
		}
	}

	private void setFieldNeigbors() {
		switch (style) {
		case SQUARE4:
			setNeigbourRelationsSquare4();
			break;
		case SQUARE8:
			setNeigbourRelationsSquare4();
			setNeigbourRelationsSquare8();
			break;
		case HEX_HORIZONTAL:
			setNeigbourRelationsHexHorizontal();
			break;
		case HEX_VERTICAL:
			setNeigbourRelationsHexVertical();
			break;
		case TRIANGLE_HORIZONTAL:
			setNeigbourRelationsTriangleHorizontal();
			break;
		case TRIANGLE_VERTICAL:
			setNeigbourRelationsTriangleVertical();
			break;
		}
	}

	private void setNeigbourRelationsSquare4() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				int nbx = 0;
				int nby = 0;
				MapField<F, E, P> center = (MapField<F, E, P>) getFieldByIndex(dx, dy);

				nbx = dx;
				nby = dy - 1;
				if (nby >= 0) {
					addNbg(nbx, nby, center);
				}
				nbx = dx + 1;
				nby = dy;
				if (nbx < width) {
					addNbg(nbx, nby, center);
				}
				nbx = dx;
				nby = dy + 1;
				if (nby < height) {
					addNbg(nbx, nby, center);
				}
				nbx = dx - 1;
				nby = dy;
				if (nbx >= 0) {
					addNbg(nbx, nby, center);
				}
			}
		}
	}

	private void setNeigbourRelationsSquare8() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				int nbx = 0;
				int nby = 0;
				MapField<F, E, P> center = (MapField<F, E, P>) getFieldByIndex(dx, dy);

				nbx = dx - 1;
				nby = dy - 1;
				if (nbx >= 0 && nby >= 0) {
					addNbg(nbx, nby, center);
				}
				nbx = dx + 1;
				nby = dy - 1;
				if (nbx < width && nby >= 0) {
					addNbg(nbx, nby, center);
				}
				nbx = dx + 1;
				nby = dy + 1;
				if (nbx < width && nby < height) {
					addNbg(nbx, nby, center);
				}
				nbx = dx - 1;
				nby = dy + 1;
				if (nbx >= 0 && nby < height) {
					addNbg(nbx, nby, center);
				}
			}
		}
	}

	private void setNeigbourRelationsHexHorizontal() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				int nbx = 0;
				int nby = 0;
				MapField<F, E, P> center = (MapField<F, E, P>) getFieldByIndex(dx, dy);

				// oben
				nbx = dx;
				nby = dy - 1;
				if (nby >= 0) {
					addNbg(nbx, nby, center);
				}

				// unten
				nbx = dx;
				nby = dy + 1;
				if (nby < height) {
					addNbg(nbx, nby, center);
				}

				if (center.getIndex().getX() % 2 == 0) {
					// links oben
					nbx = dx - 1;
					nby = dy - 1;
					if (nbx >= 0 && nby >= 0) {
						addNbg(nbx, nby, center);
					}

					// links unten
					nbx = dx - 1;
					nby = dy;
					if (nbx >= 0) {
						addNbg(nbx, nby, center);
					}

					// rechts oben
					nbx = dx + 1;
					nby = dy - 1;
					if (nbx < width && nby >= 0) {
						addNbg(nbx, nby, center);
					}

					// rechts unten
					nbx = dx + 1;
					nby = dy;
					if (nbx < width) {
						addNbg(nbx, nby, center);//
					}
				} else {
					// links oben
					nbx = dx - 1;
					nby = dy;
					if (nbx >= 0) {
						addNbg(nbx, nby, center);
					}

					// links unten
					nbx = dx - 1;
					nby = dy + 1;
					if (nbx >= 0 && nby < height) {
						addNbg(nbx, nby, center);
					}

					// rechts oben
					nbx = dx + 1;
					nby = dy;
					if (nbx < width) {
						addNbg(nbx, nby, center);//
					}

					// rechts unten
					nbx = dx + 1;
					nby = dy + 1;
					if (nbx < width && nby < height) {
						addNbg(nbx, nby, center);
					}
				}
			}
		}
	}

	private void setNeigbourRelationsHexVertical() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				int nbx = 0;
				int nby = 0;
				MapField<F, E, P> center = (MapField<F, E, P>) getFieldByIndex(dx, dy);

				// links
				nbx = dx - 1;
				nby = dy;
				if (nbx >= 0) {
					addNbg(nbx, nby, center);
				}

				// rechts
				nbx = dx + 1;
				nby = dy;
				if (nbx < width) {
					addNbg(nbx, nby, center);
				}

				if (center.getIndex().getY() % 2 == 0) {
					// oben links
					nbx = dx - 1;
					nby = dy - 1;
					if (nbx >= 0 && nby >= 0) {
						addNbg(nbx, nby, center);
					}

					// oben rechts
					nbx = dx;
					nby = dy - 1;
					if (nby >= 0) {
						addNbg(nbx, nby, center);
					}

					// unten links
					nbx = dx - 1;
					nby = dy + 1;
					if (nbx >= 0 && nby < height) {
						addNbg(nbx, nby, center);
					}

					// unten rechts
					nbx = dx;
					nby = dy + 1;
					if (nby < height) {
						addNbg(nbx, nby, center);
					}
				} else {
					// oben links
					nbx = dx;
					nby = dy - 1;
					if (nby >= 0) {
						addNbg(nbx, nby, center);
					}

					// oben rechts
					nbx = dx + 1;
					nby = dy - 1;
					if (nbx < width && nby >= 0) {
						addNbg(nbx, nby, center);
					}

					// unten links
					nbx = dx;
					nby = dy + 1;
					if (nby < height) {
						addNbg(nbx, nby, center);
					}

					// unten rechts
					nbx = dx + 1;
					nby = dy + 1;
					if (nbx < width && nby < height) {
						addNbg(nbx, nby, center);//
					}
				}
			}
		}
	}

	private void setNeigbourRelationsTriangleHorizontal() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				int nbx = 0;
				int nby = 0;
				MapField<F, E, P> center = (MapField<F, E, P>) getFieldByIndex(dx, dy);

				nbx = dx - 1;
				nby = dy;
				if (nbx >= 0) {
					addNbg(nbx, nby, center);//
				}

				nbx = dx + 1;
				nby = dy;
				if (nbx < width) {
					addNbg(nbx, nby, center);//
				}

				if ((dx + dy) % 2 == 0) {

					nbx = dx;
					nby = dy + 1;
					if (nby < height) {
						addNbg(nbx, nby, center);//
					}
				} else {
					nbx = dx;
					nby = dy - 1;
					if (nby >= 0) {
						addNbg(nbx, nby, center);//
					}
				}
			}
		}
	}

	private void setNeigbourRelationsTriangleVertical() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				int nbx = 0;
				int nby = 0;

				MapField<F, E, P> center = getFieldByIndex(dx, dy);
				nbx = dx;
				nby = dy - 1;
				if (nby >= 0) {
					addNbg(nbx, nby, center);
				}

				nbx = dx;
				nby = dy + 1;
				if (nby < height) {
					addNbg(nbx, nby, center);
				}

				if ((dx + dy) % 2 == 0) {
					nbx = dx - 1;
					nby = dy;
					if (nbx >= 0) {
						addNbg(nbx, nby, center);
					}
				} else {
					nbx = dx + 1;
					nby = dy;
					if (nbx < width) {
						addNbg(nbx, nby, center);
					}
				}
			}
		}
	}

	private void addNbg(int nbx, int nby, MapField<F, E, P> center) {
		MapField<F, E, P> nb = getFieldByIndex(nbx, nby);
		center.getNeigbours().add(nb);
	}
}
