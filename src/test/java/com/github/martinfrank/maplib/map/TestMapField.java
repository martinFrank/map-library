package com.github.martinfrank.maplib.map;

import com.github.martinfrank.drawlib.Point;
import com.github.martinfrank.drawlib.Shape;
import com.github.martinfrank.maplib.MapField;
import com.github.martinfrank.maplib.TestGraphics;
import com.github.martinfrank.maplib.data.TestMapFieldData;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class TestMapField extends MapField<TestMapFieldData, TestMapField, TestMapEdge, TestMapNode> {

//    private static final Logger LOGGER = LoggerFactory.getLogger(TestMapField.class);

//    public TestMapField(GeoPoint index, TestMapFieldData testFieldData) {
//        super(index, testFieldData);
//    }


    public TestMapField(TestMapFieldData testFieldData) {
        super(testFieldData);
    }

//    @Override
//    public void draw(Object graphics) {
//        TestGraphics gc = (TestGraphics) graphics;
//
//        Shape transformed = getShape().getTransformed();
//        transformed.getPoints();
//
//        int amount = transformed.getPoints().size();
//        double[] xs = new double[amount];
//        double[] ys = new double[amount];
//
//        for (int i = 0; i < amount; i++) {
//            Point point = transformed.getPoints().get(i);
//            xs[i] = point.getX();
//            ys[i] = point.getY();
//        }
//
//
//        gc.drawPolygon(xs, ys, amount);
//
//        for (TestMapEdge e : getEdges()) {
//            e.draw(graphics);
//        }
//
//    }

}
