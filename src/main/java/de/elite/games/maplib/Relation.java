package de.elite.games.maplib;

import java.util.List;

public interface Relation<F extends MapField, E extends MapEdge, P extends MapNode> {

    List<E> getEdges();

    List<F> getFields();

    List<P> getNodes();
}
