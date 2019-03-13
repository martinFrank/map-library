package de.elite.games.maplib;

import de.elite.games.geolib.GeoPoint;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MapFactory<M extends Map<?, F, E, P, W>, F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>, W extends MapWalker<F, E, P>> {

    private final MapPartFactory<M, F, E, P, W> mapPartFactory;
    private final MapFieldShaper<F, E, P> fieldShaper;

    public MapFactory(MapPartFactory<M, F, E, P, W> mapPartFactory) {
        this.mapPartFactory = mapPartFactory;
        fieldShaper = new MapFieldShaper<>(mapPartFactory);
    }

    public M createMap(int width, int height, MapStyle style) {
        M map = mapPartFactory.createMap(width, height, style);
        generateFields(map, style);
        reduceEdges(map);
        reducePoints(map);
        setRelations(map, style);
        return map;
    }

    private void setRelations(M map, MapStyle style) {
        Set<F> fields = map.getFields();
        Set<E> edges = getAllEdgesFromMap(map);
        for (F field : map.getFields()) {
            for (E edge : field.getEdges()) {
                setRelationPointField(edge.getA(), fields);
                setRelationPointField(edge.getB(), fields);
                setRelationPointEdge(edge.getA(), edges);
                setRelationPointEdge(edge.getB(), edges);
                setRelationEdgeField(edge, field);
                setRelationEdgeEdge(edge, edges);
            }
            setRelationFieldField(field, fields, style);
        }
    }

    private void setRelationFieldField(F field, Set<F> fields, MapStyle style) {
        for (F can : fields) {
            if (style == MapStyle.SQUARE8) {
                if (field.isConnectedByPointsTo(can) && !field.equals(can)) {
                    field.addField(can);
                }
            } else {
                if (field.isConnectedByEdgesTo(can) && !field.equals(can)) {
                    field.addField(can);
                }
            }
        }
    }

    private void setRelationEdgeEdge(E edge, Set<E> edges) {
        for (E can : edges) {
            if (edge.isConnectedTo(can)) {
                edge.addEdge(can);
            }
        }
    }

    private void setRelationEdgeField(E edge, F field) {
        edge.addField(field);

    }

    private void setRelationPointField(P p, Set<F> fields) {
        for (F field : fields) {
            if (field.getPoints().contains(p)) {
                p.addField(field);
            }
        }
    }

    private void setRelationPointEdge(P p, Set<E> edges) {
        for (E edge : edges) {
            if (edge.getA().equals(p) || edge.getB().equals(p)) {
                p.addEdge(edge);
            }
        }
    }

    private void generateFields(M map, MapStyle style) {
        for (int dy = 0; dy < map.getHeight(); dy++) {
            for (int dx = 0; dx < map.getWidth(); dx++) {
                F field = mapPartFactory.createMapField(new GeoPoint(dx, dy));
                fieldShaper.createFieldShape(field, style);
                map.addField(field);
            }
        }
    }

    private void reducePoints(M map) {
        for (F field : map.getFields()) {
            field.reducePoints();
        }
    }

    private void reduceEdges(M map) {
        Set<E> edges = getAllEdgesFromMap(map);
        for (F field : map.getFields()) {
            edges.addAll(field.getEdges());
        }
        for (F field : map.getFields()) {
            while (replaceNext(field, edges)) ;
        }
    }

    private Set<E> getAllEdgesFromMap(M map) {
        Set<E> edges = new HashSet<>();
        for (F field : map.getFields()) {
            edges.addAll(field.getEdges());
        }
        return edges;
    }

    private boolean replaceNext(F field, Set<E> edges) {
        for (E edge : field.getEdges()) {
            Optional<E> newOne = findEqualLocation(edges, edge);
            if (newOne.isPresent()) {
                field.replaceEdge(edge, newOne.get());
                return true;
            }
        }
        return false;
    }


    private Optional<E> findEqualLocation(Set<E> edges, E edge) {
        Optional<E> opt = edges.stream().filter(e -> e.equalLocation(edge)).findAny();
        if (opt.isPresent() && edge.equals(opt.get())) {
            return Optional.empty();
        } else {
            return opt;
        }
    }

}
