package com.github.martinfrank.maplib.map;

import com.github.martinfrank.maplib.MapFactory;
import com.github.martinfrank.maplib.MapPartFactory;

public class TestMapFactory extends MapFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> {

    public TestMapFactory(MapPartFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> mapPartFactory) {
        super(mapPartFactory);
    }

}
