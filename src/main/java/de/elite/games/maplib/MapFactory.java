package de.elite.games.maplib;

import de.elite.games.geolib.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class MapFactory<M extends Map<?, F, E, P, W>, F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>, W extends MapWalker<F, E, P>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapFactory.class);

    private final MapPartFactory<M, F, E, P, W> mapPartFactory;
    private final MapFieldShaper<F, E, P> fieldShaper;

    public MapFactory(MapPartFactory<M, F, E, P, W> mapPartFactory) {
        this.mapPartFactory = mapPartFactory;
        fieldShaper = new MapFieldShaper<>(mapPartFactory);
    }

    public M createMap(int width, int height, MapStyle style) {
        M map = mapPartFactory.createMap(width, height, style);
        generateFields(map, style);
        LOGGER.debug("creating edges");
        MapEdges<M, F, E, P, W> edges = getMapEdges(map);
        LOGGER.debug("reducing edges");
        reduceEdges(map, edges);
        LOGGER.debug("reducing points");
        reducePoints(map);
        LOGGER.debug("setRelations");
        setRelations(map, edges);
        LOGGER.debug(("calculate map size"));
        map.calculateSize();

        map.scale(1);
        return map;
    }


    private void setRelations(M map, MapEdges<M, F, E, P, W> edges) {
        for (F field : map.getFields()) {
            for (P p : field.getPoints()) {
                p.addField(field);
            }
            for (E edge : field.getEdges()) {
                setRelationEdgeField(edge, field);
                setRelationPointEdge(edge.getA(), edge);
                setRelationPointEdge(edge.getB(), edge);

                for (E connected : field.getEdges()) {
                    if (edge.isConnectedTo(connected)) {
                        edge.addEdge(connected);
                    }
                }
            }
        }

        for (E edge : edges.getAll()) {
            for (F field : edge.getFields()) {
                for (F can : edge.getFields()) {
                    if (!can.equals(field)) {
                        field.addField(can);
                        can.addField(field);
                    }
                }
            }
        }

    }


    private void setRelationEdgeField(E edge, F field) {
        edge.addField(field);

    }

    private void setRelationPointEdge(P p, E edge) {
        p.addEdge(edge);
    }

    private void generateFields(M map, MapStyle style) {
        for (int dy = 0; dy < map.getAmountFieldsInColumn(); dy++) {
            for (int dx = 0; dx < map.getAmountFieldsInRow(); dx++) {
                F field = mapPartFactory.createMapField(new GeoPoint(dx, dy));
                fieldShaper.createFieldShape(field, style);
                map.addField(field);
            }
        }
    }

    private void reducePoints(M map) {
        map.getFields().forEach(MapField::reducePoints);
    }

    private void reduceEdges(M map, MapEdges<M, F, E, P, W> edges) {
        for (F field : map.getFields()) {
            Set<E> newEdges = new HashSet<>();
            field.getEdges().forEach(e -> newEdges.add(edges.get(e)));
            field.replaceEdges(newEdges);
        }
    }

    private MapEdges<M, F, E, P, W> getMapEdges(M map) {
        MapEdges<M, F, E, P, W> mapEdges = new MapEdges<>();
        for (F field : map.getFields()) {
            for (E edge : field.getEdges()) {
                mapEdges.put(edge);
            }
        }
        return mapEdges;
    }


}
