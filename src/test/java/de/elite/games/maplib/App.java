package de.elite.games.maplib;

//import de.elite.games.maplib.map.*;

import de.elite.games.maplib.map.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private TestMap demoMap;
    private TestMapWalker walker;

    private TestMapField start;
    private TestMapField end;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        LOGGER.debug("starting creating map ");
        demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE_DIAMOND);
//        demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE);
//        demoMap = mapFactory.createMap(16, 16, MapStyle.TRIANGLE_VERTICAL);
//        demoMap = mapFactory.createMap(2, 2, MapStyle.TRIANGLE_HORIZONTAL);
//        demoMap = mapFactory.createMap(5, 5, MapStyle.SQUARE_ISOMETRIC);
//        demoMap = mapFactory.createMap(2, 2, MapStyle.HEX_VERTICAL);
//        demoMap = mapFactory.createMap(4, 3, MapStyle.HEX_VERTICAL);
//        demoMap = mapFactory.createMap(48, 48, MapStyle.SQUARE);
//                demoMap = mapFactory.createMap(48, 48, MapStyle.HEX_HORIZONTAL);
        LOGGER.debug("finished creating map");
        demoMap.scale(12f);
//        demoMap.pan(10, 10);
        walker = mapPartFactory.createWalker();

        primaryStage.setTitle("Hello World!");
        BorderPane border = new BorderPane();
        Canvas canvas = new Canvas(demoMap.getTransformed().getWidth(), demoMap.getTransformed().getHeight());

        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
//            Optional<TestMapNode> node = demoMap.getNodeAt(x, y);
//            Optional<TestMapEdge> edge = demoMap.getEdgeAt(x, y);
            Optional<TestMapField> field = demoMap.getFieldAt(x, y);
//            LOGGER.debug("x/y:{}/{}  Node:{}", x, y, node);
//            LOGGER.debug("x/y:{}/{}  Edge:{}", x, y, edge);
            LOGGER.debug("x/y:{}/{} Field:{}", x, y, field);

            String type = "???";
            if (field.isPresent()) {
                LOGGER.debug("field.getFields().size()={}", field.get().getFields().size());
                LOGGER.debug("field.getEdges().size()={}", field.get().getEdges().size());
                LOGGER.debug("field.getPoints().size()={}", field.get().getNodes().size());
                LOGGER.debug("field.index={}", field.get().getIndex());
                type = "FIELD";
            }
//            if (edge.isPresent()) {
//                LOGGER.debug("edge.getFields().size()={}", edge.get().getFields().size());
//                LOGGER.debug("edge.getEdges().size()={}", edge.get().getEdges().size());
//                LOGGER.debug("edge: {}, edges {}", edge.get(), edge.get().getEdges());
//                type = "EDGE";
//            }
////
//            if (node.isPresent()) {
//                LOGGER.debug("point.getEdges().size={}", node.get().getEdges().size());
//                LOGGER.debug("point.getFields().size={}", node.get().getFields().size());
//                type = "NODE";
//            }

            LOGGER.debug("selected type: {}", type);
//
            if (mouseEvent.getButton() == MouseButton.PRIMARY && field.isPresent()) {
                start = field.get();
            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY && field.isPresent()) {
                end = field.get();
            }
            if (start != null && end != null && !start.equals(end)) {
                for (TestMapField any : demoMap.getFields()) {
                    any.getData().markAsPath(false);
                }
                List<TestMapField> path = demoMap.aStar(start, end, walker, 100);
                LOGGER.debug("Path length = {}", path.size());
                for (TestMapField pathField : path) {
                    pathField.getData().markAsPath(true);
                }
                GraphicsContext gc = canvas.getGraphicsContext2D();
                drawShapes(gc);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        border.setCenter(canvas);
        primaryStage.setScene(new Scene(border));
        primaryStage.show();
    }


    private void drawShapes(GraphicsContext gc) {
        if (demoMap != null) {
            demoMap.draw(gc);
        }
    }
}
