package de.elite.games.maplib.map;

import de.elite.games.maplib.MapFactory;
import de.elite.games.maplib.MapPartFactory;

public class TestMapFactory extends MapFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> {

    public TestMapFactory(MapPartFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> mapPartFactory) {
        super(mapPartFactory);
    }

}
