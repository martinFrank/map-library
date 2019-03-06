package de.elite.games.maplib;

public interface MapPartFactory<M extends AbstractMap<F,E,P>,F,E,P> {

    /**
     * method to create the point
     *
     * @param x
     * @param y
     * @return
     */
    MapPoint<P> createPoint(int x, int y);

    /**
     * method to create the field - requires the unique center
     *
     * @return
     */
    MapField<F,E,P> createField();

    /**
     * method to create the edge
     *
     * @param a
     * @param b
     * @return
     */
    MapEdge<E,P> createEdge(MapPoint<P> a, MapPoint<P> b);

//    /**
//     * @return mapStyle of the factory
//     */
//    MapStyle getMapStyle();


    M createMap(int width, int height);
}
