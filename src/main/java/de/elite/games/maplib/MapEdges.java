package de.elite.games.maplib;

import java.util.Collection;
import java.util.HashMap;

/**
 * this class is only used to store data temporarily during map creation
 *
 * @param <M>
 * @param <F>
 * @param <E>
 * @param <P>
 * @param <W>
 */
class MapEdges<M extends Map<?, F, E, P, W>, F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>, W extends MapWalker<F, E, P>> {

    private java.util.Map<Integer, E> edges = new HashMap<>();

    void put(E edge) {
        edges.put(edge.equalLocationHash(), edge);
    }

    E get(E edge) {
        return edges.get(edge.equalLocationHash());
    }

    Collection<E> getAll() {
        return edges.values();
    }
}
