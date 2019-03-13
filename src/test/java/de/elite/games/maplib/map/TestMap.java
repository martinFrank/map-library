package de.elite.games.maplib.map;

import de.elite.games.maplib.Map;
import de.elite.games.maplib.MapStyle;
import de.elite.games.maplib.data.TestMapData;

public class TestMap extends Map<TestMapData, TestMapField, TestMapEdge, TestMapPoint, TestMapWalker> {


    TestMap(int width, int height, MapStyle style, TestMapData testMapData) {
        super(width, height, style, testMapData);
    }

    @Override
    public void draw(Object graphics) {
        for (TestMapField field : getFields()) {
            field.draw(graphics);
        }

    }

}
