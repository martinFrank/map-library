package de.elite.games.maplib;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;


public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private DemoMap demoMap;
    private DemoWalker walker;
    private DemoMapField start;
    private DemoMapField end;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DemoMapPartFactory mapPartFactory = new DemoMapPartFactory();
        MapFactory<DemoMap, DemoMapField, DemoMapEdge, DemoMapPoint, DemoWalker> mapFactory = new MapFactory<>(mapPartFactory, MapStyle.HEX_VERTICAL);
        demoMap = mapFactory.createMap(5, 4);
        demoMap.scale(12f);
        demoMap.pan(10, 10);
        walker = mapFactory.createWalker();

        shuffleWalkCosts();

        primaryStage.setTitle("Hello World!");
        BorderPane border = new BorderPane();
        Canvas canvas = new Canvas(300, 250);
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            int x = (int) mouseEvent.getX();
            int y = (int) mouseEvent.getY();
            DemoMapPoint point = demoMap.getPoint(x, y);
            DemoMapEdge edge = demoMap.getEdge(x, y);
            DemoMapField field = demoMap.getField(x, y);
            LOGGER.debug("x/y:{}/{} Point:{}", x, y, point);
            LOGGER.debug("x/y:{}/{} Edge:{} ", x, y, edge);
            LOGGER.debug("x/y:{}/{} Field:{}, index{} ", x, y, field, (field == null ? "" : field.getIndex()));

            if (mouseEvent.getButton() == MouseButton.PRIMARY && field != null) {
                start = field;
            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY && field != null) {
                end = field;
            }
            if (start != null && !start.equals(end)) {
                for (DemoMapField any : demoMap.getFields()) {
                    any.getFieldData().markAsPath(false);
                }
                List<DemoMapField> path = demoMap.aStar(start, end, walker, 10);
                for (DemoMapField pathField : path) {
                    pathField.getFieldData().markAsPath(true);
                }
                GraphicsContext gc = canvas.getGraphicsContext2D();
                drawShapes(gc);
            }

        });

        Button btn = new Button();
        btn.setText("Shuffle Map");
        btn.setOnAction(event -> {
            shuffleWalkCosts();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            drawShapes(gc);
        });
        border.setBottom(btn);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        border.setCenter(canvas);
        primaryStage.setScene(new Scene(border));
        primaryStage.show();
    }

    private void shuffleWalkCosts() {
        Random random = new Random();
        for (DemoMapField demoMapField : demoMap.getFields()) {
            demoMapField.getFieldData().setWalkCostFactor(1d);
            int die = random.nextInt(6) + 1;
            if (die == 1) {
                demoMapField.getFieldData().setWalkCostFactor(6d);
            }
            if (die == 2) {
                demoMapField.getFieldData().setWalkCostFactor(3d);
            }
        }
    }


    private void drawShapes(GraphicsContext gc) {
        if (demoMap != null) {
            demoMap.draw(gc);
        }
    }
}
