package de.elite.games.maplib;

import de.elite.games.drawlib.Draw;
import de.elite.games.drawlib.Shape;
import de.elite.games.geolib.GeoPoint;

import java.util.*;

public abstract class MapField<D,
        F extends MapField<?, F, E, P>,
        E extends MapEdge<?, F, E, P>,
        P extends MapPoint<?, F, E, P>> implements MapData<D>, Shape, Draw {

    private final GeoPoint index;
    private final Set<E> edges = new HashSet<>();
    private final Set<P> points = new HashSet<>();
    private final Set<F> fields = new HashSet<>();
    private P center;
    private final D d;
    private double scale;
    private double width;
    private double height;

    public MapField(GeoPoint index, D d) {
        super();
        this.index = index;
        this.d = d;
    }

    public GeoPoint getIndex() {
        return index;
    }

    public Set<P> getPoints() {
        return points;
    }

    public List<P> getPointsOrdered() {
        MapPointComperator ci = new MapPointComperator(center);
        List<P> points = new ArrayList<>(getPoints());
        points.sort(ci);
        return points;
    }

    public Set<E> getEdges() {
        return edges;
    }

    public Set<F> getFields() {
        return fields;
    }

    public P getCenter() {
        return center;
    }

    public Optional<E> getEdge(F to) {
        for (E e : getEdges()) {
            if (e.getFields().contains(to)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    void setCenter(P center) {
        this.center = center;
        calculateSize();
    }

    void addEdge(E edge) {
        edges.add(edge);
        calculateSize();
    }

    boolean isConnectedByPointsTo(F can) {
        for (P pCan : can.getPoints()) {
            for (P p : getPoints()) {
                if (p.equals(pCan)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isConnectedByEdgesTo(F can) {
        for (E edgeOfCan : can.getEdges()) {
            for (E e : getEdges()) {
                if (e.equalLocation(edgeOfCan)) {
                    return true;
                }
            }
        }
        return false;
    }

    void addField(F field) {
        fields.add(field);
    }

    E anyEdge() {
        return edges.iterator().next();
    }

    void replaceEdges(Set<E> replacement) {
        edges.clear();
        edges.addAll(replacement);
        calculateSize();
    }

    void reducePoints() {
        points.clear();
        for (E edge : edges) {
            points.add(edge.getA());
            points.add(edge.getB());
        }
        calculateSize();
    }

    private void calculateSize() {
        double left = Double.MAX_VALUE;
        double right = Double.MIN_VALUE;
        double bottom = Double.MAX_VALUE;
        double top = Double.MIN_VALUE;
        for (P point : getPoints()) {
            left = Math.min(point.getPanX(), left);
            right = Math.max(point.getPanX(), right);
            bottom = Math.min(point.getPanY(), bottom);
            top = Math.max(point.getPanY(), top);
        }
        width = right - left;
        height = top - bottom;
    }

    @Override
    public void scale(double scale) {
        this.scale = scale;
        center.scale(scale);
        for (E edge : edges) {
            edge.scale(scale);
        }
    }

    @Override
    public void pan(double panx, double pany) {
        center.pan(panx, pany);
        for (E edge : edges) {
            edge.pan(panx, pany);
        }
    }

    @Override
    public double getScaledX() {
        return center.getScaledX();
    }

    @Override
    public double getScaledY() {
        return center.getScaledY();
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
    public double getTransformedX() {
        return getPanX() + getScaledX();
    }

    @Override
    public double getTransformedY() {
        return getPanY() + getScaledY();
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getScaledWidth() {
        return scale * width;
    }

    @Override
    public double getScaledHeight() {
        return scale * height;
    }

    @Override
    public D getData() {
        return d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapField<?, ?, ?, ?> mapField = (MapField<?, ?, ?, ?>) o;
        return index.equals(mapField.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }
}
