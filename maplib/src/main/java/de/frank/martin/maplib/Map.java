package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.List;

import de.frank.martin.drawlib.PanScale;

public class Map<T,V,U> implements PanScale {

	private final List<MapField<T,V,U>> fieldList = new ArrayList<>();

	private final int width;
	private final int height;
	private final MapStyle style;
	private MapFactory<T,V,U> factory;

	public Map(int width, int height, MapFactory<T,V,U> factory) {
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
				MapPoint<U> center = factory.createPoint(dx, dy);
				MapField<T,V,U> field = factory.createField(center);
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
				MapField<T,V,U> center = (MapField<T,V,U>) getFieldByIndex(dx, dy);

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
				MapField<T,V,U> center = (MapField<T,V,U>) getFieldByIndex(dx, dy);

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
				MapField<T,V,U> center = (MapField<T,V,U>) getFieldByIndex(dx, dy);
				
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
				MapField<T,V,U> center = (MapField<T,V,U>) getFieldByIndex(dx, dy);

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
				MapField<T,V,U> center = (MapField<T,V,U>) getFieldByIndex(dx, dy);

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
				
				MapField<T,V,U> center = getFieldByIndex(dx, dy);
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

	@Override
	public void draw(Object gObj, int xOff, int yOff) {
//		fieldList.stream().forEach(e -> e.draw(gObj, xOff, yOff))
		for(MapField<T,V,U> field: fieldList){
			field.draw(gObj, xOff, yOff);
		}
	}

	private void addNbg(int nbx, int nby, MapField<T,V,U> center) {
		MapField<T,V,U> nb =  getFieldByIndex(nbx, nby);
		center.getNeigbourList().add(nb);
	}

	@Override
	public void scale(float scale) {
//		fieldList.stream().forEach(e -> e.scale(scale))
		for(MapField<T,V,U> field: fieldList){
			field.scale(scale);
		}
	}

	@Override
	public void pan(int dx, int dy){
//		fieldList.stream().forEach(e -> e.pan(dx,dy))
		for(MapField<T,V,U> field: fieldList){
			field.pan(dx,dy);
		}
	}

	public MapField<T,V,U> getFieldByIndex(int ix, int iy) {
		int index = iy * width + ix;
		return fieldList.get(index);
	}

	public List<MapField<T,V,U>> aStar(MapField<T,V,U> start, MapField<T,V,U> destiny, Walker<T,V,U> walker, int maxSearchDepth) {
		return new Astar<T,V,U>().getShortestPath(start, destiny, walker, this, maxSearchDepth);
	}

	public MapStyle getMapStyle() {
		return style;
	}

	public MapField<T,V,U> getFieldByCenter(int x, int y) {
		double radius = getRadiusForScale();				
		for (MapField<T,V,U> field : fieldList) {
			double dx = (double)x - field.getCenter().getTransformedX();
			double dy = (double)y - field.getCenter().getTransformedY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < radius) {
				return field;
			}
		}
		return null;
	}
	
	private double getRadiusForScale() {
		MapField<T,V,U> anyField = fieldList.get(0);
		double anyx = (double)anyField.getCenter().getTransformedX() - anyField.getEdgeList().get(0).getA().getTransformedX(); 
		double anyy = (double)anyField.getCenter().getTransformedY() - anyField.getEdgeList().get(0).getA().getTransformedY();
		return Math.sqrt(anyx * anyx + anyy * anyy) / Math.sqrt(2);
	}

	public List<MapEdge<V,U>> getEdges(int x, int y){
		double radius = getRadiusForScale()/2d;
		List<MapEdge<V,U>> list = new ArrayList<>();
		for (MapField<T,V,U> field : fieldList) {
			for (MapEdge<V,U> edge: field.getEdgeList()){
				double mx = (edge.getA().getTransformedX()+edge.getB().getTransformedX())/2d;
				double my = (edge.getA().getTransformedY()+edge.getB().getTransformedY())/2d;
				double dx = (double)x - mx;
				double dy = (double)y - my;
				double aDistance = Math.sqrt(dx * dx + dy * dy);
				if (aDistance < radius) {
					list.add(edge);
				}
			}
			
		}
		return list;
	}
	
	public List<MapPoint<U>> getPoints(int x, int y){
		List<MapPoint<U>> list = new ArrayList<>();
		double radius = getRadiusForScale()/4d;		
		for (MapField<T,V,U> field : fieldList) {
			for (MapEdge<V,U> edge: field.getEdgeList()){
				double ax = (double)x - edge.getA().getTransformedX();
				double ay = (double)y - edge.getA().getTransformedY();
				double aDistance = Math.sqrt(ax * ax + ay * ay);
				if (aDistance < radius) {
					list.add(edge.getA());
				}
				
				double bx = (double)x - edge.getB().getTransformedX();
				double by = (double)y - edge.getB().getTransformedY();
				double bDistance = Math.sqrt(bx * bx + by * by);
				if (bDistance < radius) {
					list.add((MapPoint<U>) edge.getB());
				}
			}
			
		}
		return list;
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
		for (MapField<T,V,U> field: fieldList) {
			if(field.getCenter().getX() > cx) {
				cx = field.getCenter().getX();
				for(MapEdge<V,U> e: field.getEdgeList()) {
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
		for (MapField<T,V,U> field: fieldList) {
			if(field.getCenter().getY() > cy) {
				cy = field.getCenter().getY();
				for(MapEdge<V,U> e: field.getEdgeList()) {
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
