package de.elite.games.maplib;

import java.util.HashMap;

/**
 * the map factory is required to create the map parts:
 * <li>Map</li>
 * <li>field</li>
 * <li>edge</li>
 * <li>point</li>
 *
 * @param <M> any desired map data object
 * @param <F> any desired field data object
 * @param <E> any desired edge data object
 * @param <P> any desired point data object
 * @author martinFrank
 */
public class MapFactory<M extends AbstractMap<F,E,P> ,F,E,P > {

    private final MapPartFactory<M, F, E, P> mapPartFactory ;
    private final MapStyle style;

    public MapFactory (MapPartFactory<M,F,E,P> mapPartFactory, MapStyle style){
        this.mapPartFactory = mapPartFactory;
        this.style = style;
    }

    public M createMap(int width, int height) {
        M map =  mapPartFactory.createMap(width, height);
        generateFields(map);
        reduceMap(map);
        setNeighbors(map);
        map.scale(1F);
        return map;
    }

    private void generateFields(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                MapPoint<P> center = mapPartFactory.createPoint(dx, dy);
                MapField<F, E, P> field = mapPartFactory.createField();
                field.setCenter(center, style);
                field.createShape(mapPartFactory, style);
                map.getFields().add(field);
            }
        }

        // create points for the field as well
        for (MapField<F, E, P> field : map.getFields()) {
            for (MapEdge<E, P> edge : field.getEdges()) {
                field.getPoints().add(edge.getA());
            }
        }
    }

    private void reduceMap(M map) {
        reducePoints(map);
        reduceEdges(map);
    }

    private void reducePoints(M map) {
        java.util.Map<MapPoint<P>, MapPoint<P>> points = new HashMap<>();
        for (MapField<F, E, P> field : map.getFields()) {
            // field.getPoints().stream().forEach(e -> pointList.add(e))
            for (MapPoint<P> p : field.getPoints()) {
                points.put(p, p);
            }
        }

        // replace points in fields
        for (MapField<F, E, P> field : map.getFields()) {
            int length = field.getPoints().size();
            for (int i = 0; i < length; i++) {
                MapPoint<P> original = field.getPoints().get(i);
                MapPoint<P> r = points.get(original);
                field.getPoints().set(i, r);
            }
        }

        // replace points in edges
        for (MapField<F, E, P> field : map.getFields()) {
            int length = field.getPoints().size();
            for (int i = 0; i < length; i++) {
                MapPoint<P> aOriginal = field.getEdges().get(i).getA();
                MapPoint<P> bOriginal = field.getEdges().get(i).getB();
                MapPoint<P> ra = points.get(aOriginal);
                MapPoint<P> rb = points.get(bOriginal);
                field.getEdges().set(i, mapPartFactory.createEdge(ra, rb));
            }
        }
    }

    private void reduceEdges(M map) {
        java.util.Map<MapEdge<E, P>, MapEdge<E, P>> edges = new HashMap<>();
        for (MapField<F, E, P> field : map.getFields()) {
            // field.getPoints().stream().forEach(e -> pointList.add(e))
            for (MapEdge<E, P> edge : field.getEdges()) {
                edges.put(edge, edge);
            }
        }

        // replace edges in field
        for (MapField<F, E, P> field : map.getFields()) {
            int length = field.getPoints().size();
            for (int i = 0; i < length; i++) {
                MapEdge<E, P> original = field.getEdges().get(i);
                MapEdge<E, P> r = edges.get(original);
                field.getEdges().set(i, r);
            }
        }
    }

    private void setNeighbors(M map) {
        setFieldNeighbors(map);
        setEdgeFields(map);
        setPointEdges(map);
    }

    private void setPointEdges(M map) {
        for (MapField<F, E, P> field : map.getFields()) {
            for (MapEdge<E, P> e : field.getEdges()) {
                for (MapField<F, E, P> other : field.getNeigbours()) {
                    for (MapEdge<E, P> otherEdge : other.getEdges()) {

                        if (e.getA().equals(otherEdge.getA())) {
                            e.getA().getEdges().add(otherEdge);
                        }

                        if (e.getB().equals(otherEdge.getA())) {
                            e.getB().getEdges().add(otherEdge);
                        }

                        if (e.getA().equals(otherEdge.getB())) {
                            e.getA().getEdges().add(otherEdge);
                        }

                        if (e.getB().equals(otherEdge.getB())) {
                            e.getB().getEdges().add(otherEdge);
                        }
                    }
                }
            }
        }
    }

    private void setEdgeFields(M map) {
        for (MapField<F, E, P> field : map.getFields()) {
            for (MapEdge<E, P> e : field.getEdges()) {
                e.getFields().add(field);

                for (MapField<F, E, P> nbg : field.getNeigbours()) {
                    for (MapEdge<E, P> nbgEdge : nbg.getEdges()) {
                        if (nbgEdge.equals(e)) {
                            e.getFields().add(nbg);
                        }
                    }
                }
            }
        }
    }

    private void setFieldNeighbors(M map) {
        switch (style) {
            case SQUARE4:
                setNeighborRelationsSquare4(map);
                break;
            case SQUARE8:
                setNeighborRelationsSquare4(map);
                setNeighborRelationsSquare8(map);
                break;
            case HEX_HORIZONTAL:
                setNeighborRelationsHexHorizontal(map);
                break;
            case HEX_VERTICAL:
                setNeighborRelationsHexVertical(map);
                break;
            case TRIANGLE_HORIZONTAL:
                setNeighborRelationsTriangleHorizontal(map);
                break;
            case TRIANGLE_VERTICAL:
                setNeighborRelationsTriangleVertical(map);
                break;
        }
    }

    private void setNeighborRelationsSquare4(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                int nbx;
                int nby;
                MapField<F, E, P> center = map.getFieldByIndex(dx, dy);

                nbx = dx;
                nby = dy - 1;
                if (nby >= 0) {
                    addNbg(map, nbx, nby, center);
                }
                nbx = dx + 1;
                nby = dy;
                if (nbx < map.getWidth()) {
                    addNbg(map, nbx, nby, center);
                }
                nbx = dx;
                nby = dy + 1;
                if (nby < map.getHeight()) {
                    addNbg(map, nbx, nby, center);
                }
                nbx = dx - 1;
                nby = dy;
                if (nbx >= 0) {
                    addNbg(map, nbx, nby, center);
                }
            }
        }
    }

    private void setNeighborRelationsSquare8(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                int nbx;
                int nby;
                MapField<F, E, P> center = map.getFieldByIndex(dx, dy);

                nbx = dx - 1;
                nby = dy - 1;
                if (nbx >= 0 && nby >= 0) {
                    addNbg(map, nbx, nby, center);
                }
                nbx = dx + 1;
                nby = dy - 1;
                if (nbx < map.getWidth() && nby >= 0) {
                    addNbg(map, nbx, nby, center);
                }
                nbx = dx + 1;
                nby = dy + 1;
                if (nbx < map.getWidth() && nby < map.getHeight()) {
                    addNbg(map, nbx, nby, center);
                }
                nbx = dx - 1;
                nby = dy + 1;
                if (nbx >= 0 && nby < map.getHeight()) {
                    addNbg(map, nbx, nby, center);
                }
            }
        }
    }

    private void setNeighborRelationsHexHorizontal(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                int nbx;
                int nby;
                MapField<F, E, P> center = map.getFieldByIndex(dx, dy);

                // oben
                nbx = dx;
                nby = dy - 1;
                if (nby >= 0) {
                    addNbg(map, nbx, nby, center);
                }

                // unten
                nbx = dx;
                nby = dy + 1;
                if (nby < map.getHeight()) {
                    addNbg(map, nbx, nby, center);
                }

                if (center.getIndex().getX() % 2 == 0) {
                    // links oben
                    nbx = dx - 1;
                    nby = dy - 1;
                    if (nbx >= 0 && nby >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // links unten
                    nbx = dx - 1;
                    nby = dy;
                    if (nbx >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // rechts oben
                    nbx = dx + 1;
                    nby = dy - 1;
                    if (nbx < map.getWidth() && nby >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // rechts unten
                    nbx = dx + 1;
                    nby = dy;
                    if (nbx < map.getWidth()) {
                        addNbg(map, nbx, nby, center);//
                    }
                } else {
                    // links oben
                    nbx = dx - 1;
                    nby = dy;
                    if (nbx >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // links unten
                    nbx = dx - 1;
                    nby = dy + 1;
                    if (nbx >= 0 && nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);
                    }

                    // rechts oben
                    nbx = dx + 1;
                    nby = dy;
                    if (nbx < map.getWidth()) {
                        addNbg(map, nbx, nby, center);//
                    }

                    // rechts unten
                    nbx = dx + 1;
                    nby = dy + 1;
                    if (nbx < map.getWidth() && nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);
                    }
                }
            }
        }
    }

    private void setNeighborRelationsHexVertical(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                int nbx;
                int nby;
                MapField<F, E, P> center = map.getFieldByIndex(dx, dy);

                // links
                nbx = dx - 1;
                nby = dy;
                if (nbx >= 0) {
                    addNbg(map, nbx, nby, center);
                }

                // rechts
                nbx = dx + 1;
                nby = dy;
                if (nbx < map.getWidth()) {
                    addNbg(map, nbx, nby, center);
                }

                if (center.getIndex().getY() % 2 == 0) {
                    // oben links
                    nbx = dx - 1;
                    nby = dy - 1;
                    if (nbx >= 0 && nby >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // oben rechts
                    nbx = dx;
                    nby = dy - 1;
                    if (nby >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // unten links
                    nbx = dx - 1;
                    nby = dy + 1;
                    if (nbx >= 0 && nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);
                    }

                    // unten rechts
                    nbx = dx;
                    nby = dy + 1;
                    if (nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);
                    }
                } else {
                    // oben links
                    nbx = dx;
                    nby = dy - 1;
                    if (nby >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // oben rechts
                    nbx = dx + 1;
                    nby = dy - 1;
                    if (nbx < map.getWidth() && nby >= 0) {
                        addNbg(map, nbx, nby, center);
                    }

                    // unten links
                    nbx = dx;
                    nby = dy + 1;
                    if (nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);
                    }

                    // unten rechts
                    nbx = dx + 1;
                    nby = dy + 1;
                    if (nbx < map.getWidth() && nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);//
                    }
                }
            }
        }
    }

    private void setNeighborRelationsTriangleHorizontal(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                int nbx;
                int nby;
                MapField<F, E, P> center = map.getFieldByIndex(dx, dy);

                nbx = dx - 1;
                nby = dy;
                if (nbx >= 0) {
                    addNbg(map, nbx, nby, center);//
                }

                nbx = dx + 1;
                nby = dy;
                if (nbx < map.getWidth()) {
                    addNbg(map, nbx, nby, center);//
                }

                if ((dx + dy) % 2 == 0) {

                    nbx = dx;
                    nby = dy + 1;
                    if (nby < map.getHeight()) {
                        addNbg(map, nbx, nby, center);//
                    }
                } else {
                    nbx = dx;
                    nby = dy - 1;
                    if (nby >= 0) {
                        addNbg(map, nbx, nby, center);//
                    }
                }
            }
        }
    }

    private void setNeighborRelationsTriangleVertical(M map) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                int nbx;
                int nby;

                MapField<F, E, P> center = map.getFieldByIndex(dx, dy);
                nbx = dx;
                nby = dy - 1;
                if (nby >= 0) {
                    addNbg(map, nbx, nby, center);
                }

                nbx = dx;
                nby = dy + 1;
                if (nby < map.getHeight()) {
                    addNbg(map, nbx, nby, center);
                }

                if ((dx + dy) % 2 == 0) {
                    nbx = dx - 1;
                    nby = dy;
                    if (nbx >= 0) {
                        addNbg(map, nbx, nby, center);
                    }
                } else {
                    nbx = dx + 1;
                    nby = dy;
                    if (nbx < map.getWidth()) {
                        addNbg(map, nbx, nby, center);
                    }
                }
            }
        }
    }

    private void addNbg(M map, int nbx, int nby, MapField<F, E, P> center) {
        MapField<F, E, P> nb = map.getFieldByIndex(nbx, nby);
        center.getNeigbours().add(nb);
    }
}
