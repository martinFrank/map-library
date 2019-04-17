package com.github.martinfrank.maplib;

//import de.elite.games.maplib.map.*;

import com.github.martinfrank.maplib.map.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.Optional;

public class App extends Application {

    //    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
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
//        LOGGER.debug("starting creating map ");
        demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE_DIAMOND);
//        demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE);
//        demoMap = mapFactory.createMap(16, 16, MapStyle.TRIANGLE_VERTICAL);
//        demoMap = mapFactory.createMap(2, 2, MapStyle.TRIANGLE_HORIZONTAL);
//        demoMap = mapFactory.createMap(5, 5, MapStyle.SQUARE_ISOMETRIC);
//        demoMap = mapFactory.createMap(2, 2, MapStyle.HEX_VERTICAL);
//        demoMap = mapFactory.createMap(4, 3, MapStyle.HEX_VERTICAL);
//        demoMap = mapFactory.createMap(48, 48, MapStyle.SQUARE);
//                demoMap = mapFactory.createMap(48, 48, MapStyle.HEX_HORIZONTAL);
//        LOGGER.debug("finished creating map");
        demoMap.scale(12f);
//        demoMap.pan(10, 10);
        walker = mapPartFactory.createWalker();

        primaryStage.setTitle("Hello World!");
        BorderPane border = new BorderPane();
        final Canvas canvas = new Canvas(demoMap.getTransformed().getWidth(), demoMap.getTransformed().getHeight());




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
