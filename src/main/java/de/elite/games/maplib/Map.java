package de.elite.games.maplib;

import de.elite.games.drawlib.Draw;
import de.elite.games.drawlib.Shape;
import de.elite.games.geolib.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class Map<D, F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>, W extends MapWalker<F, E, P>> implements MapData<D>, Shape, Draw {

    private static final Logger LOGGER = LoggerFactory.getLogger(Map.class);
    private final int fieldsInRow;
    private final int fieldsInColumn;
    private double width;
    private double height;
    private final MapStyle style;
    private final Set<F> fields;
    private final Astar<Map<D, F, E, P, W>, F, E, P, W> astar = new Astar<>();
    private final D d;
    private double scale;

    public Map(int width, int height, MapStyle style, D d) {
        this.fieldsInRow = width;
        this.fieldsInColumn = height;
        this.style = style;
        fields = new HashSet<>();
        this.d = d;
    }

    public Set<F> getFields() {
        return fields;
    }

    public int getAmountFieldsInRow() {
        return fieldsInRow;
    }

    public int getAmountFieldsInColumn() {
        return fieldsInColumn;
    }

    public MapStyle getStyle() {
        return style;
    }

    public List<F> aStar(F start, F destiny, W walker, int maxSearchDepth) {
        return astar.getShortestPath(start, destiny, walker, this, maxSearchDepth);
    }

    public Optional<E> getEdge(int x, int y) {
        double radius = getRadiusForScale() / 2d;
        for (F field : fields) {
            for (E edge : field.getEdges()) {
                double mx = (edge.getA().getTransformedX() + edge.getB().getTransformedX()) / 2d;
                double my = (edge.getA().getTransformedY() + edge.getB().getTransformedY()) / 2d;
                double dx = (double) x - mx;
                double dy = (double) y - my;
                double aDistance = Math.sqrt(dx * dx + dy * dy);
                if (aDistance < radius) {
                    return Optional.of(edge);
                }
            }

        }
        return Optional.empty();
    }

    public Optional<P> getPoint(int x, int y) {
        double radius = getRadiusForScale() / 4d;
        for (F field : fields) {
            for (P point : field.getPoints()) {
                double px = (double) x - point.getTransformedX();
                double py = (double) y - point.getTransformedY();
                double distance = Math.sqrt(px * px + py * py);
                if (distance < radius) {
                    return Optional.of(point);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<F> getField(int x, int y) {
        double radius = getRadiusForScale();
        for (F field : fields) {
            double dx = (double) x - field.getCenter().getTransformedX();
            double dy = (double) y - field.getCenter().getTransformedY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < radius) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }

    void calculateSize() {

        double left = 10000;
        double right = -10000;
        double bottom = 10000;
        double top = -10000;

        for (F field : getFields()) {
            for (P point : field.getPoints()) {
                left = Math.min(point.getPoint().getX(), left);
                bottom = Math.min(point.getPoint().getY(), bottom);
                right = Math.max(point.getPoint().getX(), right);
                top = Math.max(point.getPoint().getY(), top);
            }
        }
        width = right - left + anyField().getWidth();
        height = top - bottom + anyField().getHeight();

    }

    void addField(F field) {
        fields.add(field);
    }

    Optional<F> getFieldByIndex(int ix, int iy) {
        GeoPoint point = new GeoPoint(ix, iy);
        return fields.stream().filter(f -> f.getIndex().equals(point)).findAny();
    }

    private double getRadiusForScale() {
        F anyField = anyField();
        P center = anyField.getCenter();
        E anyEdge = anyField().anyEdge();
        P anyPoint = anyEdge.getA();
        double anyx = center.getTransformedX() - anyPoint.getTransformedX();
        double anyy = center.getTransformedY() - anyPoint.getTransformedY();
        return Math.sqrt(anyx * anyx + anyy * anyy) / Math.sqrt(2);
    }

    private F anyField() {
        return fields.iterator().next();
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
    public double getTransformedX() {
        return anyField().getTransformedX();
    }

    @Override
    public double getTransformedY() {
        return anyField().getTransformedY();
    }

    @Override
    public void scale(double scale) {
        this.scale = scale;
        for (F field : fields) {
            field.scale(scale);
        }
    }

    @Override
    public void pan(double panx, double pany) {
        for (F field : fields) {
            field.pan(panx, pany);
        }
    }

    @Override
    public double getScaledX() {
        return anyField().getScaledX();
    }

    @Override
    public double getScaledY() {
        return anyField().getScaledY();
    }

    @Override
    public double getPanX() {
        return anyField().getPanX();
    }

    @Override
    public double getPanY() {
        return anyField().getPanY();
    }

    @Override
    public D getData() {
        return d;
    }


}
