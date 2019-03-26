package de.elite.games.maplib;

public abstract class MapPartFactory<M extends Map, F extends MapField<?, F, E, N>, E extends MapEdge<?, F, E, N>, N extends MapNode<?, F, E, N>, W extends MapWalker> {

    public abstract N createMapNode();

    public abstract E createMapEdge();

    public abstract F createMapField();

    public abstract M createMap(int width, int height, MapStyle style);

    public abstract W createWalker();
}
