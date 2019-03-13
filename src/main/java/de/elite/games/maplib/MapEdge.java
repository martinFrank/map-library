package de.elite.games.maplib;

import de.elite.games.drawlib.PanScale;

import java.util.HashSet;
import java.util.Set;

public abstract class MapEdge<D, F extends MapField, E extends MapEdge, P extends MapPoint> implements MapData<D>, PanScale {

    private final Set<F> fields = new HashSet<>();
    private final Set<E> edges = new HashSet<>();
    private P a;
    private P b;

    public MapEdge(P a, P b) {
        this.a = a;
        this.b = b;
    }

    public P getA() {
        return a;
    }

    public P getB() {
        return b;
    }

    public Set<E> getEdges() {
        return edges;
    }

    public Set<F> getFields() {
        return fields;
    }

    void addField(F field) {
        fields.add(field);
    }

    void addEdge(E edge) {
        edges.add(edge);
    }

    boolean isConnectedTo(E can) {
        if (can.getA().equals(a)) {
            return true;
        }
        if (can.getA().equals(b)) {
            return true;
        }
        if (can.getB().equals(a)) {
            return true;
        }
        return can.getB().equals(b);
    }

    @Override
    public void scale(double scale) {
        a.scale(scale);
        b.scale(scale);
    }

    @Override
    public void pan(double dx, double dy) {
        a.pan(dx, dy);
        b.pan(dx, dy);
    }

    @Override
    public double getScale() {
        return a.getScale();
    }

    @Override
    public double getScaledX() {
        return a.getScaledX();
    }

    @Override
    public double getScaledY() {
        return a.getScaledY();
    }

    @Override
    public double getPanX() {
        return a.getPanX();
    }

    @Override
    public double getPanY() {
        return a.getPanY();
    }

    @Override
    public double getTransformedX() {
        return getPanX() + getScaledX();
    }

    @Override
    public double getTransformedY() {
        return getPanY() + getScaledY();
    }

    //visible for testing
    protected boolean equalLocation(MapEdge mapEdge) {
        if (this == mapEdge) {
            return true;
        }
        if (mapEdge == null) {
            return false;
        }
        boolean matchesAA = a != null && a.equals(mapEdge.a);
        boolean matchesAB = a != null && a.equals(mapEdge.b);
        boolean matchesBA = b != null && b.equals(mapEdge.a);
        boolean matchesBB = b != null && b.equals(mapEdge.b);

        if (matchesAA && matchesBB) {
            return true;
        }
        return matchesAB && matchesBA;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEdge<?, ?, ?, ?> mapEdge = (MapEdge<?, ?, ?, ?>) o;

        if (a != null ? !a.equals(mapEdge.a) : mapEdge.a != null) return false;
        return b != null ? b.equals(mapEdge.b) : mapEdge.b == null;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
