package de.elite.games.maplib;

import de.elite.games.drawlib.PanScale;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMap<F, E, P> implements PanScale{


    private final List<MapField<F, E, P>> fields = new ArrayList<>();

    private final int width;
    private final int height;
    private final MapStyle style;

    public AbstractMap(int width, int height, MapStyle style) {
        this.width = width;
        this.height = height;
        this.style = style;
    }

    //FIXME that the 'field-related aStar' - you must implement the edge.related aStar as well!!!
    public List<MapField<F, E, P>> aStar(MapField<F, E, P> start, MapField<F, E, P> destiny, Walker<F, E, P> walker,
                                         int maxSearchDepth) {
        return new Astar<F, E, P>().getShortestPath(start, destiny, walker, this, maxSearchDepth);
    }

    /**
     * list of all fields of the map
     *
     * @return
     */
    public List<MapField<F, E, P>> getFields() {
        return fields;
    }

    /**
     * returns the field 'near' the x/y-position. This method is helpful if you want to
     * get fields by click/touch
     *
     * @param x
     * @param y
     * @return
     */
    public MapField<F, E, P> getField(int x, int y) {
        double radius = getRadiusForScale();
        for (MapField<F, E, P> field : fields) {
            double dx = (double) x - field.getCenter().getTransformedX();
            double dy = (double) y - field.getCenter().getTransformedY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < radius) {
                return field;
            }
        }
        return null;
    }

    /**
     * returns the field by the indexed coordinates. this method is mainly used by the aStar
     *
     * @param ix
     * @param iy
     * @return
     */
    public MapField<F, E, P> getFieldByIndex(int ix, int iy) {
        int index = iy * width + ix;
        return fields.get(index);
    }

    /**
     * returns the edge 'near' the x/y-position. this method is helpful if you want to
     * get edges by click/touch
     *
     * @param x
     * @param y
     * @return
     */
    public MapEdge<E, P> getEdge(int x, int y) {
        double radius = getRadiusForScale() / 2d;
        for (MapField<F, E, P> field : fields) {
            for (MapEdge<E, P> edge : field.getEdges()) {
                double mx = (edge.getA().getTransformedX() + edge.getB().getTransformedX()) / 2d;
                double my = (edge.getA().getTransformedY() + edge.getB().getTransformedY()) / 2d;
                double dx = (double) x - mx;
                double dy = (double) y - my;
                double aDistance = Math.sqrt(dx * dx + dy * dy);
                if (aDistance < radius) {
                    return edge;
                }
            }

        }
        return null;
    }

    /**
     * returns the point 'near' the x/y-position. this method is helpful if you want to
     * get points by click/touch
     *
     * @param x
     * @param y
     * @return
     */
    public MapPoint<P> getPoint(int x, int y) {
        double radius = getRadiusForScale() / 4d;
        for (MapField<F, E, P> field : fields) {
            for (MapEdge<E, P> edge : field.getEdges()) {
                double ax = (double) x - edge.getA().getTransformedX();
                double ay = (double) y - edge.getA().getTransformedY();
                double aDistance = Math.sqrt(ax * ax + ay * ay);
                if (aDistance < radius) {
                    return edge.getA();
                }

                double bx = (double) x - edge.getB().getTransformedX();
                double by = (double) y - edge.getB().getTransformedY();
                double bDistance = Math.sqrt(bx * bx + by * by);
                if (bDistance < radius) {
                    return edge.getB();
                }
            }
        }
        return null;
    }

    /**
     * the width (amount of fields) of the map
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * the height (amount of fields) of the map
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    public MapStyle getStyle(){
        return style;
    }

    /**
     * width (in pixel) of the (already) scaled map
     *
     * @return
     */
    public int getScaledWidth() {
        int max = 0;
        int cx = 0;
        for (MapField<F, E, P> field : fields) {
            if (field.getCenter().getX() > cx) {
                cx = field.getCenter().getX();
                for (MapEdge<E, P> e : field.getEdges()) {
                    if (e.getA().getTransformedX() > max) {
                        max = e.getA().getTransformedX();
                    }
                    if (e.getB().getTransformedX() > max) {
                        max = e.getB().getTransformedX();
                    }
                }
            }
        }
        return max;
    }

    /**
     * height (in pixel) of the (already) scaled map
     *
     * @return
     */
    public int getScaledHeight() {
        int max = 0;
        int cy = 0;
        for (MapField<F, E, P> field : fields) {
            if (field.getCenter().getY() > cy) {
                cy = field.getCenter().getY();
                for (MapEdge<E, P> e : field.getEdges()) {
                    if (e.getA().getTransformedY() > max) {
                        max = e.getA().getTransformedY();
                    }
                    if (e.getB().getTransformedY() > max) {
                        max = e.getB().getTransformedY();
                    }
                }
            }
        }
        return max;
    }

    @Override
    public void scale(double scale) {
        // fieldList.stream().forEach(e -> e.scale(scale))
        for (MapField<F, E, P> field : fields) {
            field.scale(scale);
        }
    }

    @Override
    public void pan(double dx, double dy) {
        // fieldList.stream().forEach(e -> e.pan(dx,dy))
        for (MapField<F, E, P> field : fields) {
            field.pan(dx, dy);
        }
    }

    @Override
    public double getScale() {
        return fields.isEmpty()?0:fields.get(0).getScale();
    }

    @Override
    public double getPanX() {
        return fields.isEmpty()?0:fields.get(0).getPanX();
    }

    @Override
    public double getPanY() {
        return fields.isEmpty()?0:fields.get(0).getPanY();
    }

    private double getRadiusForScale() {
        MapField<F, E, P> anyField = fields.get(0);
        MapEdge<E, P> anyEdge = anyField.getEdges().iterator().next();
        double anyx = (double) anyField.getCenter().getTransformedX() - anyEdge.getA().getTransformedX();
        double anyy = (double) anyField.getCenter().getTransformedY() - anyEdge.getA().getTransformedY();
        return Math.sqrt(anyx * anyx + anyy * anyy) / Math.sqrt(2);
    }












}
