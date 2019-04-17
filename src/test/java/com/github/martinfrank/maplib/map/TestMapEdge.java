package com.github.martinfrank.maplib.map;

import com.github.martinfrank.drawlib.Point;
import com.github.martinfrank.maplib.MapEdge;
import com.github.martinfrank.maplib.data.TestMapEdgeData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TestMapEdge extends MapEdge<TestMapEdgeData, TestMapField, TestMapEdge, TestMapNode> {

    public TestMapEdge(TestMapEdgeData testEdgeData) {
        super(testEdgeData);
    }


    @Override
    public void draw(Object graphics) {
        GraphicsContext gc = (GraphicsContext) graphics;
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        Point a = getLine().getA().getTransformed();
        Point b = getLine().getB().getTransformed();
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }


}
