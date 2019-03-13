package de.elite.games.maplib;

import de.elite.games.drawlib.PanScale;
import de.elite.games.geolib.GeoPoint;

import java.util.HashSet;
import java.util.Set;

public abstract class MapPoint<D, F extends MapField, E extends MapEdge, P extends MapPoint> implements MapData<D>, PanScale {

    private final Set<F> fields = new HashSet<>();
    private final Set<E> edges = new HashSet<>();
    private GeoPoint point;
    private double panx;
    private double pany;
    private double scaledx;
    private double scaledy;
    private double scale;

    public MapPoint(int x, int y) {
        point = new GeoPoint(x, y);
    }

    GeoPoint getPoint() {
        return point;
    }

    public Set<E> getEdges() {
        return edges;
    }

    public Set<F> getFields() {
        return fields;
    }

    void setPoint(int x, int y) {
        point = new GeoPoint(x, y);
    }

    void addField(F field) {
        fields.add(field);
    }

    void addEdge(E edge) {
        edges.add(edge);
    }

    @Override
    public void scale(double scale) {
        this.scale = scale;
        scaledx = point.getX() * scale;
        scaledy = point.getY() * scale;
    }

    @Override
    public void pan(double dx, double dy) {
        this.panx = dx;
        this.pany = dy;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public double getScaledX() {
        return scaledx;
    }

    @Override
    public double getScaledY() {
        return scaledy;
    }

    @Override
    public double getPanX() {
        return panx;
    }

    @Override
    public double getPanY() {
        return pany;
    }

    @Override
    public double getTransformedX() {
        return getPanX() + getScaledX();
    }

    @Override
    public double getTransformedY() {
        return getPanY() + getScaledY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapPoint<?, ?, ?, ?> mapPoint = (MapPoint<?, ?, ?, ?>) o;
        return point.equals(mapPoint.point);
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }
}
