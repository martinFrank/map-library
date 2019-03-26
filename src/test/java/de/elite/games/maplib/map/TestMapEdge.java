package de.elite.games.maplib.map;

import de.elite.games.drawlib.Point;
import de.elite.games.maplib.MapEdge;
import de.elite.games.maplib.data.TestMapEdgeData;
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
