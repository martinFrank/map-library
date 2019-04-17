package com.github.martinfrank.maplib;


import java.util.ArrayList;
import java.util.List;

public abstract class MapWalker<F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> {

    public abstract boolean canEnter(F from, F into);

    public abstract int getEnterCosts(F from, F into);

    public abstract List<F> getNeighbours(F field);

    public final List<F> getNeighboursFromNodes(F field) {
        List<F> nbgFields = new ArrayList<>();
        for (N node : field.getNodes()) {
            for (F nbg : node.getFields()) {
                if (!nbgFields.contains(nbg)) {
                    nbgFields.add(nbg);
                }
            }
        }
        return nbgFields;
    }

    public final List<F> getNeighboursFromEdges(F field) {
        List<F> nbgFields = new ArrayList<>();
        for (E edge : field.getEdges()) {
            for (F nbg : edge.getFields()) {
                if (!nbgFields.contains(nbg)) {
                    nbgFields.add(nbg);
                }
            }
        }
        return nbgFields;
    }
}
