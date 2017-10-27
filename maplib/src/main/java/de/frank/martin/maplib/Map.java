package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.List;

import de.frank.martin.drawlib.PanScale;

public class Map<T> implements PanScale {

	private final List<Field<T>> fieldList = new ArrayList<Field<T>>();

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
				Point center = factory.createPoint(dx, dy);
				Field<T> field = factory.createField(center);
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
				Field<T> center = (Field<T>) getFieldByIndex(dx, dy);

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
				Field<T> center = (Field<T>) getFieldByIndex(dx, dy);

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
				Field<T> center = (Field<T>) getFieldByIndex(dx, dy);
				
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

//				if (center.ix() % 2 == 0) {
				if (center.index().x() % 2 == 0) {
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
				Field<T> center = (Field<T>) getFieldByIndex(dx, dy);

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
				
//				if (center.iy() % 2 == 0) {
				if (center.index().y() % 2 == 0) {
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
				Field<T> center = (Field<T>) getFieldByIndex(dx, dy);

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
				
				Field<T> center = getFieldByIndex(dx, dy);
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
//		fieldList.stream().forEach(e -> e.draw(gObj, xOff, yOff));
		for(Field<T> field: fieldList){
			field.draw(gObj, xOff, yOff);
		}
	}

	private void addNbg(int nbx, int nby, Field<T> center) {
		Field<? extends T> nb =  getFieldByIndex(nbx, nby);
		center.getNeigbourList().add(nb);
	}

	@Override
	public void scale(float scale) {
//		fieldList.stream().forEach(e -> e.scale(scale));
		for(Field<T> field: fieldList){
			field.scale(scale);
		}
	}

	@Override
	public void pan(int dx, int dy){
//		fieldList.stream().forEach(e -> e.pan(dx,dy));
		for(Field<T> field: fieldList){
			field.pan(dx,dy);
		}
	}

//	public Field<? extends T> getFieldByIndex(int ix, int iy) {
	public Field<T> getFieldByIndex(int ix, int iy) {
		int index = iy * width + ix;
		return (Field<T>) fieldList.get(index);
	}

	public List<Field<T>> aStar(Field<T> start, Field<T> destiny, Walker<T> walker, int maxSearchDepth) {
		return new Astar<T>().getShortestPath(start, destiny, walker, this, maxSearchDepth);
	}

	public MapStyle getMapStyle() {
		return style;
	}

	public Field<T> getFieldByCenter(int x, int y) {
		Field<?> anyField = fieldList.get(0);
		int anyx = anyField.center().xPanScaled() - anyField.getEdgeList().get(0).a().xPanScaled(); 
		int anyy = anyField.center().yPanScaled() - anyField.getEdgeList().get(0).a().yPanScaled();
		double radius = Math.sqrt(anyx * anyx + anyy * anyy) / Math.sqrt(2);
				
		for (Field<T> field : fieldList) {
			int dx = x - field.center().xPanScaled();
			int dy = y - field.center().yPanScaled();
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
		for (Field<T> field: fieldList) {
			if(field.center().x() > cx) {
				cx = field.center().x();
				for(Edge e: field.getEdgeList()) {
					if (e.a().xPanScaled()> max ) {
						max = e.a().xPanScaled();
					}
					if (e.b().xPanScaled() > max ) {
						max = e.b().xPanScaled();
					}
				}
			}
		}
		return max;
	}
	
	public int getScaledHeight() {
		int max = 0;
		int cy = 0;
		for (Field<T> field: fieldList) {
			if(field.center().y() > cy) {
				cy = field.center().y();
				for(Edge e: field.getEdgeList()) {
					if (e.a().yPanScaled() > max ) {
						max = e.a().yPanScaled();
					}
					if (e.b().yPanScaled() > max ) {
						max = e.b().yPanScaled();
					}
				}
			}
		}
		return max;
	}
}
