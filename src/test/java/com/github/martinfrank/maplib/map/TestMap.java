package com.github.martinfrank.maplib.map;

import com.github.martinfrank.maplib.Map;
import com.github.martinfrank.maplib.MapStyle;
import com.github.martinfrank.maplib.data.TestMapData;

public class TestMap extends Map<TestMapData, TestMapField, TestMapEdge, TestMapNode, TestMapWalker> {


    TestMap(int width, int height, MapStyle style, TestMapData testMapData) {
        super(width, height, style, testMapData);
    }

    public void draw(Object graphics) {
        for (TestMapField field : getFields()) {

//            field.draw(graphics);
        }

    }

}
