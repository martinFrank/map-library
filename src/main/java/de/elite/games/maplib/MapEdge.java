package de.elite.games.maplib;

import de.elite.games.drawlib.Draw;
import de.elite.games.drawlib.Line;
import de.elite.games.drawlib.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MapEdge<D,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> implements MapData<D>, Draw, Relation {

    //relation
    private final List<F> fields = new ArrayList<>();
    private final List<E> edges = new ArrayList<>();
    private final List<N> nodes = new ArrayList<>();

    //data
    private final D d;

    //draw
    private Line line;

    private MapEdge(Point a, Point b, D d) {
        this.line = new Line(a, b);
        this.d = d;
    }

    public MapEdge(D d) {
        this(new Point(), new Point(), d);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
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

    @Override
    public D getData() {
        return d;
    }

    @Override
    public String toString() {
        return "Edge with  line " + getLine();

    }

    void addField(F field) {
        if (!fields.contains(field)) {
            fields.add(field);
        }
    }

    boolean isConnected(E nbg) {
        return line.isConnectedTo(nbg.getLine());
    }

    boolean isConnected(N node) {
        return line.isConnectedTo(node.getPoint());
    }

    void addEdge(E nbg) {
        if (!this.equals(nbg) && !edges.contains(nbg)) {
            edges.add(nbg);
        }
    }

    public void addNode(N n) {
        if (!nodes.contains(n)) {
            nodes.add(n);
        }
    }

    @Override
    public int hashCode() {
        return getLine().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapEdge) {
            return getLine().equals(((MapEdge) obj).getLine());
        }
        return false;
    }
}
