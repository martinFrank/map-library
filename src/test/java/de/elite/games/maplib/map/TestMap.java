package de.elite.games.maplib.map;

import de.elite.games.maplib.data.TestMapData;
import de.elite.games.maplib2.Map;
import de.elite.games.maplib2.MapStyle;

public class TestMap extends Map<TestMapData, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> {


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
