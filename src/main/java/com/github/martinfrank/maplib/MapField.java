package com.github.martinfrank.maplib;

import com.github.martinfrank.drawlib.Shape;
import com.github.martinfrank.geolib.GeoPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapField<D,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> implements MapData<D>, Relation<F, E, N> {


    //relation
    private final List<E> edges = new ArrayList<>();
    private final List<N> nodes = new ArrayList<>();
    private final List<F> fields = new ArrayList<>();
    //data
    private final D d;
    private GeoPoint index;
    //draw
    private Shape shape;

    public MapField(D d) {
        this.d = d;
        shape = new Shape();
    }

    public Shape getShape() {
        return shape;
    }

    void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "MapField, center at:" + getShape().getCenter();
    }

    @Override
    public List<E> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    @Override
    public List<F> getFields() {
        return Collections.unmodifiableList(fields);
    }

    @Override
    public List<N> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public E getEdge(F to) {
        for (E e : getEdges()) {
            if (e.getFields().contains(to)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public D getData() {
        return d;
    }

    void addNode(N n) {
        if (!nodes.contains(n)) {
            nodes.add(n);
        }
    }

    void addField(F field) {
        if (!field.equals(this) && !fields.contains(field)) {
            fields.add(field);
        }
    }

    void addEdge(E e) {
        if (!edges.contains(e)) {
            edges.add(e);
        }
    }

    @Override
    public int hashCode() {
        return getShape().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapField) {
            return getShape().equals(((MapField) obj).getShape());
        }
        return false;
    }

    public GeoPoint getIndex() {
        return index;
    }

    void setIndex(GeoPoint index) {
        this.index = index;
    }

    boolean isIndex(int x, int y) {
        return index.getX() == x && index.getY() == y;
    }
}
