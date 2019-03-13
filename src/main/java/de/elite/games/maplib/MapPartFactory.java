package de.elite.games.maplib2;

import de.elite.games.geolib.GeoPoint;

public abstract class MapPartFactory<M extends Map, F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>, W extends MapWalker> {

    public abstract P createMapPoint(int x, int y);

    public abstract E createMapEdge(P a, P b);

    public abstract F createMapField(GeoPoint index);

    public abstract M createMap(int width, int height, MapStyle style);

    public abstract W createWalker();
}
