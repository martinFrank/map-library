package de.elite.games.maplib;

public interface MapFactory<T> {

	Point createPoint(int x, int y);

	Field<T> createField(Point center);

	Edge createEdge(Point a, Point b);

	MapStyle getStyle();

}
