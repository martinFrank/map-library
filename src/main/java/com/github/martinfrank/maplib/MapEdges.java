package com.github.martinfrank.maplib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MapEdges<D,
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> {

    private java.util.Map<Integer, E> edges = new HashMap<>();
    private java.util.Map<Integer, List<E>> nbgEdges = new HashMap<>();

    public E get(E edge) {
        E e = edges.get(edge.getLine().hashCode());
        if (e == null) {
            edges.put(edge.getLine().hashCode(), edge);
            e = edge;
        }
        setNbgs(e);
        return e;
    }

    private void setNbgs(E edge) {
        int aHash = edge.getLine().getA().hashCode();
        List<E> aNbgs = nbgEdges.get(aHash);
        if (aNbgs == null) {
            aNbgs = new ArrayList<>();
            nbgEdges.put(aHash, aNbgs);
            if (!aNbgs.contains(edge)) {
                aNbgs.add(edge);
            }
        }
        int bHash = edge.getLine().getB().hashCode();
        List<E> bNbgs = nbgEdges.get(bHash);
        if (bNbgs == null) {
            bNbgs = new ArrayList<>();
            nbgEdges.put(bHash, bNbgs);
            if (!bNbgs.contains(edge)) {
                bNbgs.add(edge);
            }
        }
    }

    Collection<E> values() {
        return edges.values();
    }

    public List<E> withNode(N node) {
        return nbgEdges.get(node.getPoint().hashCode());
    }
}
