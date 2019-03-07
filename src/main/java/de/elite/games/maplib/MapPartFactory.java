package de.elite.games.maplib;

public interface MapPartFactory<M extends AbstractMap, F extends MapField, E extends MapEdge, P extends MapPoint, W extends Walker<? extends F>> {

    /**
     * method to create the point
     *
     * @param x
     * @param y
     * @return
     */
    P createPoint(int x, int y);

    /**
     * method to create the field - requires the unique center
     *
     * @return
     */
    F createField();

    /**
     * method to create the edge
     *
     * @param a
     * @param b
     * @return
     */
    E createEdge(P a, P b);

    M createMap(int width, int height);

    W createWalker(MapStyle style);
}
