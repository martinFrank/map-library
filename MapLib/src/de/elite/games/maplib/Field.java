package de.elite.games.maplib;

import java.util.List;

import de.elite.games.drawlib.PanScale;

public interface Field<T> extends PanScale{

	int ix();
	
	int iy();

	List<Field<? extends T>> getNeigbourList();
	List<Edge> getEdgeList();
	
	T getFieldData();
	void setFieldData(T t);

	Point center();
}
