package de.elite.games.maplib;


public abstract class MapWalker<F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> {

    public abstract boolean canEnter(F from, F into);

    public abstract int getEnterCosts(F from, F into);
}
