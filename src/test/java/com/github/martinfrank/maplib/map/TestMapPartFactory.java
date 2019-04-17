package com.github.martinfrank.maplib.map;

import com.github.martinfrank.maplib.MapPartFactory;
import com.github.martinfrank.maplib.MapStyle;
import com.github.martinfrank.maplib.data.TestMapData;
import com.github.martinfrank.maplib.data.TestMapEdgeData;
import com.github.martinfrank.maplib.data.TestMapFieldData;
import com.github.martinfrank.maplib.data.TestMapNodeData;

public class TestMapPartFactory extends MapPartFactory<TestMap, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> {

    @Override
    public TestMapNode createMapNode() {
        return new TestMapNode(new TestMapNodeData());
    }

    @Override
    public TestMapEdge createMapEdge() {
        return new TestMapEdge(new TestMapEdgeData());
    }

    @Override
    public TestMapField createMapField() {
        return new TestMapField(new TestMapFieldData());
    }

    @Override
    public TestMap createMap(int columns, int rows, MapStyle style) {
        return new TestMap(columns, rows, style, new TestMapData());
    }

    @Override
    public TestMapWalker createWalker() {
        return new TestMapWalker();
    }
}
