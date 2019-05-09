package com.github.martinfrank.maplib.map;

import com.github.martinfrank.drawlib.Point;
import com.github.martinfrank.maplib.MapEdge;
import com.github.martinfrank.maplib.TestGraphics;
import com.github.martinfrank.maplib.data.TestMapEdgeData;

public class TestMapEdge extends MapEdge<TestMapEdgeData, TestMapField, TestMapEdge, TestMapNode> {

    public TestMapEdge(TestMapEdgeData testEdgeData) {
        super(testEdgeData);
    }


    @Override
    public void draw(Object graphics) {
        TestGraphics gc = (TestGraphics) graphics;
        Point a = getLine().getA().getTransformed();
        Point b = getLine().getB().getTransformed();
        gc.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
    }


}
