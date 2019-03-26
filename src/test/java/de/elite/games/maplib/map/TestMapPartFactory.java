package de.elite.games.maplib.map;

import de.elite.games.maplib.MapPartFactory;
import de.elite.games.maplib.MapStyle;
import de.elite.games.maplib.data.TestMapData;
import de.elite.games.maplib.data.TestMapEdgeData;
import de.elite.games.maplib.data.TestMapFieldData;
import de.elite.games.maplib.data.TestMapNodeData;

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
    public TestMap createMap(int width, int height, MapStyle style) {
        return new TestMap(width, height, style, new TestMapData());
    }

    @Override
    public TestMapWalker createWalker() {
        return new TestMapWalker();
    }
}
