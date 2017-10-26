package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractField<T> implements Field<T>{

	private final List <Edge> edgeList = new ArrayList<Edge>();
	private final Point center;
	private final Point index;

	private final List<Field<? extends T>> nbList = new ArrayList<>();
	
	public AbstractField(Point c, MapFactory<? extends T> f){		
		index = f.createPoint(c.x(),c.y());
		center = f.createPoint(0,0);
		setCenter(c, f);		
		createShape(f);
	}
	
	private  void createShape(MapFactory<? extends T> factory) {
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

	private void setCenterTriangleVertical(Point c) {
		if(index.y() % 2 == 0){
			if(index.x() % 2 == 0){
				center.set(1 + (3*c.x()), 2+(2*c.y()) );
			}else{
				center.set(2 + (3*c.x()), 2+(2*c.y()) );
			}				
		}else{
			
			if(index.x() % 2 == 0){
				center.set(2 + (3*c.x()), 2+(2*c.y()) );
			}else{
				center.set(1 + (3*c.x()), 2+(2*c.y()) );
			}
		}
	}

	private void setCenterTriangleHorizontal(Point c) {
		if(index.y() % 2 == 0){
			if(index.x() % 2 == 0){
				center.set(2 + (2*c.x()), 2+(3*c.y()) );
			}else{
				center.set(2 + (2*c.x()), 1+(3*c.y()) );
			}				
		}else{
			
			if(index.x() % 2 == 0){
				center.set(2 + (2*c.x()), 1+(3*c.y()) );
			}else{
				center.set(2 + (2*c.x()), 2+(3*c.y()) );
			}
		}
	}

	private void setCenterHexHorizontal(Point c) {
		if (c.x() % 2 == 0){
			center.set(2 + (3*c.x()), 2+(4*c.y()) );	
		}else{
			center.set(2 + (3*c.x()), 4+(4*c.y()) );
		}
	}

	private void setCenterHexVertical(Point c) {
		if (c.y() % 2 == 0){
			center.set(2 + (4*c.x()), 2+(3*c.y()) );	
		}else{
			center.set(4 + (4*c.x()), 2+(3*c.y()) );
		}
	}

	private void setCenterSquare(Point c) {
		center.set(1 + (2*c.x()), 1+(2*c.y()) );
	}

	@Override
	public void scale(float scale){
		for (Edge e: edgeList){
			e.scale(scale);
		}
		center.scale(scale);
	}
	
	@Override
	public void pan(int dx, int dy) {
		for (Edge e: edgeList) {
			e.pan(dx,dy);
		}
		center.pan(dx, dy);
	}

	@Override
	public Point center(){
		return center;
	}
	
	@Override
	public int ix(){
		return index.x();
	}
	
	@Override
	public int iy(){
		return index.y();
	}
	


	private void createTriangleVertical(MapFactory<? extends T> factory) {
		boolean isPointingLeft = false;
		if(index.y() % 2 == 0){
			if(index.x() % 2 == 0){
				isPointingLeft = false;
			}else{
				isPointingLeft = true;
			}				
		}else{			
			if(index.x() % 2 == 0){
				isPointingLeft = true;
			}else{
				isPointingLeft = false;
			}
		}
		
		if(isPointingLeft){			
			for (int i = 0; i < 3; i ++){
				Point a = factory.createPoint(1,1);
				Point b = factory.createPoint(1,1);
				if(i == 0){
					a.set(center.x()-2, center.y());
					b.set(center.x()+1, center.y()-2);
				}
				if(i == 1){
					a.set(center.x()+1, center.y()-2);
					b.set(center.x()+1, center.y()+2);
				}				
				if(i == 2){
					a.set(center.x()+1, center.y()+2);
					b.set(center.x()-2, center.y());
				}
				createAndAddEdge(a,b, factory);
			}			
		}else{
			for (int i = 0; i < 3; i ++){
				Point a = factory.createPoint(1,1);
				Point b = factory.createPoint(1,1);
				if(i == 0){
					a.set(center.x()-1, center.y()-2);
					b.set(center.x()+2, center.y());
				}				
				if(i == 1){
					a.set(center.x()+2, center.y());
					b.set(center.x()-1, center.y()+2);
				}				
				if(i == 2){
					a.set(center.x()-1, center.y()+2);
					b.set(center.x()-1, center.y()-2);
				}
				createAndAddEdge(a,b, factory);
			}
		}
	}

	private void createTriangleHorizontal(MapFactory<? extends T> factory) {		
		boolean isUpside = false;
		if(index.y() % 2 == 0){
			if(index.x() % 2 == 0){
				isUpside = true;
			}else{
				isUpside = false;
			}				
		}else{			
			if(index.x() % 2 == 0){
				isUpside = false;
			}else{
				isUpside = true;
			}
		}		
		if(isUpside){			
			for (int i = 0; i < 3; i ++){
				Point a = factory.createPoint(1,1);
				Point b = factory.createPoint(1,1);
				if(i == 0){
					a.set(center.x()-2, center.y()+1);
					b.set(center.x(), center.y()-2);
				}				
				if(i == 1){
					a.set(center.x(), center.y()-2);
					b.set(center.x()+2, center.y()+1);
				}				
				if(i == 2){
					a.set(center.x()+2, center.y()+1);
					b.set(center.x()-2, center.y()+1);
				}
				createAndAddEdge(a,b, factory);
			}			
		}else{
			for (int i = 0; i < 3; i ++){
				Point a = factory.createPoint(1,1);
				Point b = factory.createPoint(1,1);
				if(i == 0){
					a.set(center.x()-2, center.y()-1);
					b.set(center.x()+2, center.y()-1);
				}				
				if(i == 1){
					a.set(center.x()+2, center.y()-1);
					b.set(center.x(), center.y()+2);
				}				
				if(i == 2){
					a.set(center.x(), center.y()+2);
					b.set(center.x()-2, center.y()-1);
				}
				createAndAddEdge(a,b, factory);
			}
		}
			
	}

	private void createHexesVertical(MapFactory<? extends T> factory) {
		for (int i = 0; i < 6; i ++){
			Point a = factory.createPoint(1,1);
			Point b = factory.createPoint(1,1);			
			if(i == 0){
				a.set(center.x()-2, center.y()-1);
				b.set(center.x(), center.y()-2);
			}			
			if(i == 1){
				a.set(center.x(), center.y()-2);
				b.set(center.x()+2, center.y()-1);
			}			
			if(i == 2){
				a.set(center.x()+2, center.y()-1);
				b.set(center.x()+2, center.y()+1);
			}			
			if(i == 3){
				a.set(center.x()+2, center.y()+1);
				b.set(center.x(), center.y()+2);
			}
			if(i == 4){
				a.set(center.x(), center.y()+2);
				b.set(center.x()-2, center.y()+1);
			}			
			if(i == 5){
				a.set(center.x()-2, center.y()+1);
				b.set(center.x()-2, center.y()-1);
			}			
			createAndAddEdge(a,b, factory);			
		}
	}
	
	private void createAndAddEdge(Point a, Point b, MapFactory<? extends T> factory) {		
		Edge edge = factory.createEdge(a,b);
		edgeList.add(edge);		
	}

	private void createHexesHorizontal(MapFactory<? extends T> factory) {
		for (int i = 0; i < 6; i ++){
			Point a = factory.createPoint(1,1);
			Point b = factory.createPoint(1,1);			
			if(i == 0){
				a.set(center.x()-2, center.y());
				b.set(center.x()-1, center.y()-2);
			}			
			if(i == 1){
				a.set(center.x()-1, center.y()-2);
				b.set(center.x()+1, center.y()-2);
			}			
			if(i == 2){
				a.set(center.x()+1, center.y()-2);
				b.set(center.x()+2, center.y());
			}			
			if(i == 3){
				a.set(center.x()+2, center.y());
				b.set(center.x()+1, center.y()+2);
			}
			if(i == 4){
				a.set(center.x()+1, center.y()+2);
				b.set(center.x()-1, center.y()+2);
			}			
			if(i == 5){
				a.set(center.x()-1, center.y()+2);
				b.set(center.x()-2, center.y());
			}			
			createAndAddEdge(a,b, factory);
		}
	}

	private void createSquares(MapFactory<? extends T> factory) {
		for (int i = 0; i < 4; i ++){			
			Point a = factory.createPoint(1,1);
			Point b = factory.createPoint(1,1);			
			if(i == 0){
				a.set(center.x()-1, center.y()-1);
				b.set(center.x()+1, center.y()-1);
			}			
			if(i == 1){
				a.set(center.x()+1, center.y()-1);
				b.set(center.x()+1, center.y()+1);
			}			
			if(i == 2){
				a.set(center.x()+1, center.y()+1);
				b.set(center.x()-1, center.y()+1);
			}			
			if(i == 3){
				a.set(center.x()-1, center.y()+1);
				b.set(center.x()-1, center.y()-1);
			}			
			createAndAddEdge(a,b, factory);
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
		return ""+center.toString();
	}

	
	
}