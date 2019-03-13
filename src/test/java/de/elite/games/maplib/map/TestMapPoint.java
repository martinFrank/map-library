package de.elite.games.maplib.map;

import de.elite.games.maplib.MapPoint;
import de.elite.games.maplib.data.TestPointData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TestMapPoint extends MapPoint<TestPointData, TestMapField, TestMapEdge, TestMapPoint> {

    public TestMapPoint(int x, int y, TestPointData testPointData) {
        super(x, y, testPointData);
    }

    @Override
    public void draw(Object graphics) {
        GraphicsContext gc = (GraphicsContext) graphics;
        gc.setFill(Color.RED);
        gc.setLineWidth(3);
        double x = getTransformedX();
        double y = getTransformedY();
        gc.strokeLine(x, y, x, y);
    }

}

