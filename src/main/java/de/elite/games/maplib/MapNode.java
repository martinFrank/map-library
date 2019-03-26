package de.elite.games.maplib;

import de.elite.games.drawlib.Draw;
import de.elite.games.drawlib.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MapNode<D,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> implements MapData<D>, Draw, Relation<F, E, N> {

    //relations
    private final List<F> fields = new ArrayList<>();
    private final List<E> edges = new ArrayList<>();

    //data
    private final D d;

    //draw
    private Point point;

    public MapNode(D d) {
        this(new Point(0, 0), d);
    }

    public MapNode(Point point, D d) {
        this.point = point;
        this.d = d;
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
        return Collections.emptyList();
    }

    @Override
    public D getData() {
        return d;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void addField(F field) {
        if (!fields.contains(field)) {
            fields.add(field);
        }
    }

    public void addEdge(E edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    @Override
    public String toString() {
        return "Node at :" + getPoint().toString();
    }

    public boolean isConnected(E edge) {
        return edge.getLine().isConnectedTo(getPoint());
    }

    @Override
    public int hashCode() {
        return getPoint().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapNode) {
            return getPoint().equals(((MapNode) obj).getPoint());
        }
        return false;
    }
}
