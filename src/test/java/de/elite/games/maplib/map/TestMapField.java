package de.elite.games.maplib.map;

import de.elite.games.drawlib.Point;
import de.elite.games.drawlib.Shape;
import de.elite.games.maplib.MapField;
import de.elite.games.maplib.data.TestMapFieldData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMapField extends MapField<TestMapFieldData, TestMapField, TestMapEdge, TestMapNode> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMapField.class);

//    public TestMapField(GeoPoint index, TestMapFieldData testFieldData) {
//        super(index, testFieldData);
//    }


    public TestMapField(TestMapFieldData testFieldData) {
        super(testFieldData);
    }

    @Override
    public void draw(Object graphics) {
        Color color = getData().isMarkedAsPath() ? Color.YELLOW : Color.WHITE;
//        Color color = Color.BLACK;
        GraphicsContext gc = (GraphicsContext) graphics;
        gc.setFill(color);

        Shape transformed = getTransformed();
        transformed.getPoints();

        double[] xs = transformed.getPoints().stream().mapToDouble(Point::getX).toArray();
        double[] ys = transformed.getPoints().stream().mapToDouble(Point::getY).toArray();
        int amount = Math.min(xs.length, ys.length);
//        LOGGER.debug("amount: {}", amount);
//        LOGGER.debug("xs {}", Arrays.toString(xs));
//        LOGGER.debug("ys {}", Arrays.toString(ys));
        gc.fillPolygon(xs, ys, amount);

        for (TestMapEdge e : getEdges()) {
            e.draw(graphics);
        }

    }

}
