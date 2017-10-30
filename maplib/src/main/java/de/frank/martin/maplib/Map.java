package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.List;

import de.frank.martin.drawlib.PanScale;

public class Map<T> implements PanScale {

	private final List<MapField<T>> fieldList = new ArrayList<>();

	private final int width;
	private final int height;
	private final MapStyle style;
	private MapFactory<T> factory;

	public Map(int width, int height, MapFactory<T> factory) {
		this.width = width;
		this.height = height;
		this.factory = factory;
		this.style = factory.getStyle();
		generateFields();
		setNeigbourRelations();
		scale(1);
	}

	private void generateFields() {
		for (int dy = 0; dy < height; dy++) {
			for (int dx = 0; dx < width; dx++) {
				MapPoint center = factory.createPoint(dx, dy);
				MapField<T> field = factory.createField(center);
				fieldList.add(field);
			}
		}
	}

	private void setNeigbourRelations() {
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
				MapField<T> center = (MapField<T>) getFieldByIndex(dx, dy);

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
					addNbg(nbx, nby, center);//
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
				MapField<T> center = (MapField<T>) getFieldByIndex(dx, dy);

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
					addNbg(nbx, nby, center);//
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
				MapField<T> center = (MapField<T>) getFieldByIndex(dx, dy);
				
				// oben
				nbx = dx;
				nby = dy - 1;
				if (nby >= 0) {
					addNbg(nbx, nby, center);//
				}
				
				// unten
				nbx = dx;
				nby = dy + 1;
				if (nby < height) {
					addNbg(nbx, nby, center);//
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
						addNbg(nbx, nby, center);//
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
						addNbg(nbx, nby, center);//
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
				MapField<T> center = (MapField<T>) getFieldByIndex(dx, dy);

				// links
				nbx = dx - 1;
				nby = dy;
				if (nbx >= 0) {
					addNbg(nbx, nby, center);//
				}

				// rechts
				nbx = dx + 1;
				nby = dy;
				if (nbx < width) {
					addNbg(nbx, nby, center);//
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
						addNbg(nbx, nby, center);//
					}

					// unten rechts
					nbx = dx;
					nby = dy + 1;
					if (nby < height) {
						addNbg(nbx, nby, center);//
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
						addNbg(nbx, nby, center);//
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
				MapField<T> center = (MapField<T>) getFieldByIndex(dx, dy);

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
				
				MapField<T> center = getFieldByIndex(dx, dy);
				nbx = dx;
				nby = dy - 1;
				if (nby >= 0) {
					addNbg(nbx, nby, center);//
				}
				
				nbx = dx;
				nby = dy + 1;
				if (nby < height) {
					addNbg(nbx, nby, center);//
				}
				
				if ((dx + dy) % 2 == 0) {
					nbx = dx - 1;
					nby = dy;
					if (nbx >= 0) {
						addNbg(nbx, nby, center);//
					}
				} else {
					nbx = dx + 1;
					nby = dy;
					if (nbx < width) {
						addNbg(nbx, nby, center);//
					}
				}
			}
		}	
	}

	@Override
	public void draw(Object gObj, int xOff, int yOff) {
//		fieldList.stream().forEach(e -> e.draw(gObj, xOff, yOff))
		for(MapField<T> field: fieldList){
			field.draw(gObj, xOff, yOff);
		}
	}

	private void addNbg(int nbx, int nby, MapField<T> center) {
		MapField<? extends T> nb =  getFieldByIndex(nbx, nby);
		center.getNeigbourList().add(nb);
	}

	@Override
	public void scale(float scale) {
//		fieldList.stream().forEach(e -> e.scale(scale))
		for(MapField<T> field: fieldList){
			field.scale(scale);
		}
	}

	@Override
	public void pan(int dx, int dy){
//		fieldList.stream().forEach(e -> e.pan(dx,dy))
		for(MapField<T> field: fieldList){
			field.pan(dx,dy);
		}
	}

	public MapField<T> getFieldByIndex(int ix, int iy) {
		int index = iy * width + ix;
		return fieldList.get(index);
	}

	public List<MapField<T>> aStar(MapField<T> start, MapField<T> destiny, Walker<T> walker, int maxSearchDepth) {
		return new Astar<T>().getShortestPath(start, destiny, walker, this, maxSearchDepth);
	}

	public MapStyle getMapStyle() {
		return style;
	}

	public MapField<T> getFieldByCenter(int x, int y) {
		MapField<?> anyField = fieldList.get(0);
		double anyx = (double)anyField.getCenter().getTransformedX() - anyField.getEdgeList().get(0).getA().getTransformedX(); 
		double anyy = (double)anyField.getCenter().getTransformedY() - anyField.getEdgeList().get(0).getA().getTransformedY();
		double radius = Math.sqrt(anyx * anyx + anyy * anyy) / Math.sqrt(2);
				
		for (MapField<T> field : fieldList) {
			double dx = (double)x - field.getCenter().getTransformedX();
			double dy = (double)y - field.getCenter().getTransformedY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < radius) {
				return field;
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
		for (MapField<T> field: fieldList) {
			if(field.getCenter().getX() > cx) {
				cx = field.getCenter().getX();
				for(MapEdge e: field.getEdgeList()) {
					if (e.getA().getTransformedX()> max ) {
						max = e.getA().getTransformedX();
					}
					if (e.getB().getTransformedX() > max ) {
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
		for (MapField<T> field: fieldList) {
			if(field.getCenter().getY() > cy) {
				cy = field.getCenter().getY();
				for(MapEdge e: field.getEdgeList()) {
					if (e.getA().getTransformedY() > max ) {
						max = e.getA().getTransformedY();
					}
					if (e.getB().getTransformedY() > max ) {
						max = e.getB().getTransformedY();
					}
				}
			}
		}
		return max;
	}
}
