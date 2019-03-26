package de.elite.games.maplib.map;

import de.elite.games.maplib2.MapFactory;
import de.elite.games.maplib2.MapPartFactory;

public class TestMapFactory extends MapFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> {

    public TestMapFactory(MapPartFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> mapPartFactory) {
        super(mapPartFactory);
    }

}
