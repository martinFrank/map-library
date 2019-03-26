package de.elite.games.maplib2;

import de.elite.games.drawlib.Aggregation;
import de.elite.games.drawlib.Draw;
import de.elite.games.drawlib.Point;
import de.elite.games.drawlib.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class Map<D,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>,
        W extends MapWalker<F, E, N>> implements MapData<D>, Draw {

    private static final Logger LOGGER = LoggerFactory.getLogger(Map.class);

    //map
    private final int rows;
    private final int columns;
    private final MapStyle style;

    private final Astar<Map<D, F, E, N, W>, F, E, N, W> astar = new Astar<>();

    //relation
    private final List<F> fields;
    //data
    private final D d;
    //draw
    private final Aggregation aggregation;
    private MapEdges<?, F, E, N> edges;
    private MapNodes<?, F, E, N> nodes;

    public Map(int rows, int columns, MapStyle style, D d) {
        aggregation = new Aggregation();
        fields = new ArrayList<>();
        edges = new MapEdges<>();
        nodes = new MapNodes<>();
        this.style = (style == null ? MapStyle.SQUARE : style);
        this.rows = rows;
        this.columns = columns;
        this.d = d;
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

    public void addField(F field) {
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

    Optional<F> getField(int x, int y) {
        return fields.stream().filter(f -> f.isIndex(x, y)).findFirst();
    }

    void setNodes(MapNodes<?, F, E, N> nodes) {
        this.nodes = nodes;
    }

    void setEdges(MapEdges<?, F, E, N> edges) {
        this.edges = edges;
    }

    public Optional<N> getNodeAt(double x, double y) {
        double radius = getRadiusForScale() / 4d;
        for (N node : nodes.values()) {
            Point point = node.getPoint().getTransformed();
            double px = x - point.getX();
            double py = y - point.getY();
            double distance = Math.sqrt(px * px + py * py);
            if (distance < radius) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    public Optional<E> getEdgeAt(double x, double y) {
        double radius = getRadiusForScale() / 2d;
        for (E edge : edges.values()) {
            Point point = edge.getLine().getCenter().getTransformed();
            double px = x - point.getX();
            double py = y - point.getY();
            double distance = Math.sqrt(px * px + py * py);
            if (distance < radius) {
                return Optional.of(edge);
            }
        }
        return Optional.empty();
    }

    public Optional<F> getFieldAt(double x, double y) {
        double radius = getRadiusForScale();
        for (F field : fields) {
            Point point = field.getShape().getTransformed().getCenter();
            double px = x - point.getX();
            double py = y - point.getY();
            double distance = Math.sqrt(px * px + py * py);
            if (distance < radius) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
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
        //FIXME this muss raus
        return astar.getShortestPath(start, end, walker, this, depth);
    }
}
