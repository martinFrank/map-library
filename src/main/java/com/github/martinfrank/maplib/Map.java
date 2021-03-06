package com.github.martinfrank.maplib;

import com.github.martinfrank.drawlib.Aggregation;
import com.github.martinfrank.drawlib.Point;
import com.github.martinfrank.drawlib.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Map<D,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>,
        W extends MapWalker<F, E, N>> implements MapData<D> {

    //map
    private final int rows;
    private final int columns;
    private final MapStyle style;

    private final Astar<Map<D, F, E, N, W>, F, E, N, W> astar;

    //relation
    private final List<F> fields;

    private final java.util.Map<F, List<F>> neighbors;

    //data
    private final D d;

    //draw
    private final Aggregation aggregation;
    private MapEdges<F, E, N> edges;
    private MapNodes<F, E, N> nodes;

    public Map(int columns, int rows, MapStyle style, D d) {
        aggregation = new Aggregation();
        fields = new ArrayList<>();
        edges = new MapEdges<>();
        nodes = new MapNodes<>();
        neighbors = new HashMap<>();
        this.style = (style == null ? MapStyle.SQUARE4 : style);
        this.rows = rows;
        this.columns = columns;
        this.d = d;
        astar = new Astar<>(this);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public MapStyle getStyle() {
        return style;
    }

    public List<F> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public List<F> getNeighbors(F center) {
        List<F> neighbors = this.neighbors.get(center);
        if (neighbors == null) {
            List<F> candidates = new ArrayList<>();
            for (F f : fields) {
                if (!f.equals(center) && f.isNeighbor(center)) {
                    candidates.add(f);
                }
            }
            neighbors = Collections.unmodifiableList(candidates);
            this.neighbors.put(center, neighbors);
        }
        return neighbors;
    }

    void addField(F field) {
        if (!fields.contains(field)) {
            fields.add(field);
            aggregation.addShape(field.getShape());
        }
    }

    @Override
    public D getData() {
        return d;
    }

    public void scale(double scale) {
        aggregation.scale(scale);
    }

    public void pan(double dx, double dy) {
        aggregation.pan(dx, dy);
    }

    public void rotate(double rot, double x, double y) {
        aggregation.rotate(rot, x, y);
    }

    public Aggregation getTransformed() {
        return aggregation.getTransformed();
    }

    public F getField(int x, int y) {
        for (F field : fields) {
            if (field.isIndex(x, y)) {
                return field;
            }
        }
        return null;
    }

    void setNodes(MapNodes<F, E, N> nodes) {
        this.nodes = nodes;
    }

    void setEdges(MapEdges<F, E, N> edges) {
        this.edges = edges;
    }

    public N getNodeAt(double x, double y) {
        double radius = getRadiusForScale() / 4d;
        for (N node : nodes.values()) {
            Point point = node.getPoint().getTransformed();
            double px = x - point.getX();
            double py = y - point.getY();
            double distance = Math.sqrt(px * px + py * py);
            if (distance < radius) {
                return node;
            }
        }
        return null;
    }

    public E getEdgeAt(double x, double y) {
        double radius = getRadiusForScale() / 2d;
        for (E edge : edges.values()) {
            Point point = edge.getLine().getCenter().getTransformed();
            double px = x - point.getX();
            double py = y - point.getY();
            double distance = Math.sqrt(px * px + py * py);
            if (distance < radius) {
                return edge;
            }
        }
        return null;
    }

    public F getFieldAt(double x, double y) {
        double radius = getRadiusForScale();
        for (F field : fields) {
            Point point = field.getShape().getTransformed().getCenter();
            double px = x - point.getX();
            double py = y - point.getY();
            double distance = Math.sqrt(px * px + py * py);
            if (distance < radius) {
                return field;
            }
        }
        return null;
    }

    private double getRadiusForScale() {
        Shape shape = getFields().get(0).getShape().getTransformed();
        Point a = shape.getPoints().get(0).getTransformed();
        Point c = shape.getCenter().getTransformed();
        double dx = c.getX() - a.getX();
        double dy = c.getY() - a.getY();
        return Math.sqrt(dx * dx + dy * dy) / Math.sqrt(2);
    }


    public List<F> aStar(F start, F end, W walker, int depth) {
        return astar.getShortestPath(start, end, walker, depth);
    }

    public F getTarget(F center, Direction dir) {
        if (!style.getDirections().contains(dir)) {
            throw new IllegalArgumentException("" + dir + " is not in " + style.getDirections());
        }
        int targetX = center.getIndex().getX() + dir.getDx();
        int targetY = center.getIndex().getY() + dir.getDy();
        for (F field : fields) {
            if (field.isIndex(targetX, targetY)) {
                return field;
            }
        }
        return null;
    }
}
