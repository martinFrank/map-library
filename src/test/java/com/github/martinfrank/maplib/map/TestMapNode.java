package com.github.martinfrank.maplib.map;

import com.github.martinfrank.maplib.MapNode;
import com.github.martinfrank.maplib.data.TestMapNodeData;

public class TestMapNode extends MapNode<TestMapNodeData, TestMapField, TestMapEdge, TestMapNode> {

    public TestMapNode(TestMapNodeData data) {
        super(data);
    }

    public void draw(Object graphics) {

    }

}

