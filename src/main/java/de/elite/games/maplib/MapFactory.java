package de.elite.games.maplib;

import de.elite.games.geolib.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapFactory<M extends Map<?, F, E, N, W>,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>,
        W extends MapWalker<F, E, N>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapFactory.class);

    private final MapPartFactory<M, F, E, N, W> mapPartFactory;
    private final MapFieldShaper<F, E, N> fieldShaper;

    public MapFactory(MapPartFactory<M, F, E, N, W> mapPartFactory) {
        this.mapPartFactory = mapPartFactory;
        fieldShaper = new MapFieldShaper<>(mapPartFactory);
    }

    public M createMap(int columns, int rows, MapStyle style) {
        M map = mapPartFactory.createMap(columns, rows, style);
        MapNodes<?, F, E, N> mapNodes = new MapNodes<>();
        MapEdges<?, F, E, N> mapEdges = new MapEdges<>();
        createFields(map, mapNodes, mapEdges);
        connectEdges(mapEdges);
        map.setNodes(mapNodes);
        map.setEdges(mapEdges);
        return map;
    }

    private void connectEdges(MapEdges<?, F, E, N> mapEdges) {
        for (E edge : mapEdges.values()) {
            N na = edge.getNodes().get(0);
            N nb = edge.getNodes().get(1);
            mapEdges.withNode(na).forEach(e -> {
                edge.addEdge(e);
                e.addEdge(edge);
            });
            mapEdges.withNode(nb).forEach(e -> {
                edge.addEdge(e);
                e.addEdge(edge);
            });

            if (edge.getFields().size() == 2) {
                F fa = edge.getFields().get(0);
                F fb = edge.getFields().get(1);
                fa.addField(fb);
                fb.addField(fa);
            }

        }
    }

    private void createFields(M map, MapNodes<?, F, E, N> nodes, MapEdges<?, F, E, N> edges) {
        for (int dy = 0; dy < map.getRows(); dy++) {
            for (int dx = 0; dx < map.getColumns(); dx++) {
                F field = mapPartFactory.createMapField();
                field.setShape(fieldShaper.createFieldShape(field, map.getStyle(), dx, dy, nodes, edges));
                field.setIndex(new GeoPoint(dx, dy));
                map.addField(field);
            }
        }
    }

}
