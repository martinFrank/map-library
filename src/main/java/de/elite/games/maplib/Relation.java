package de.elite.games.maplib;

import java.util.List;

public interface Relation<F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> {

    List<E> getEdges();

    List<F> getFields();

    List<N> getNodes();
}
