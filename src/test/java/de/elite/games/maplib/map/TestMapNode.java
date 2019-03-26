package de.elite.games.maplib.map;

import de.elite.games.maplib.MapNode;
import de.elite.games.maplib.data.TestMapNodeData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TestMapNode extends MapNode<TestMapNodeData, TestMapField, TestMapEdge, TestMapNode> {

    public TestMapNode(TestMapNodeData data) {
        super(data);
    }

    @Override
    public void draw(Object graphics) {
        GraphicsContext gc = (GraphicsContext) graphics;
        gc.setFill(Color.RED);
        gc.setLineWidth(3);

//        double x = getTransformedX();
//        double y = getTransformedY();
//        gc.strokeLine(x, y, x, y);
    }

}

