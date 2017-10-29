package de.frank.martin.maplib;

import java.util.List;

import de.frank.martin.drawlib.PanScale;

/**
 * a field is the elemental object of a map - all field together represent the
 * map. a field is surrounded by edges, these are equal to the connection to the
 * neighbor fields.
 * <br> a field is unique identified by either the center or the index
 * 
 * @author martinFrank
 *
 * @param <T> any desired object
 */
public interface Field<T> extends PanScale {

	Point index();

	List<Field<? extends T>> getNeigbourList();

	List<Edge> getEdgeList();

	T getFieldData();

	void setFieldData(T t);

	Point center();
}
