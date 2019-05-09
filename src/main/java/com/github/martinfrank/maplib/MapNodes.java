package com.github.martinfrank.maplib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapNodes<
        F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> {

    private java.util.Map<Integer, N> nodes = new HashMap<>();

    public N get(N node) {
        N n = nodes.get(node.getPoint().hashCode());
        if (n == null) {
            nodes.put(node.getPoint().hashCode(), node);
            n = node;
        }
        return n;
    }

    public List<N> values() {
        return new ArrayList<>(nodes.values());
    }
}
