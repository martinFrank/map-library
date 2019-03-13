package de.elite.games.maplib2;

public abstract class MapWalker<F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>> {

    public abstract boolean canEnter(F from, F into);

    public abstract int getEnterCosts(F from, F into);
}
