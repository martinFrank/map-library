package de.elite.games.maplib2;

import java.util.List;

public interface Relation<F extends MapField, E extends MapEdge, P extends MapNode> {

    List<E> getEdges();

    List<F> getFields();

    List<P> getNodes();
}
