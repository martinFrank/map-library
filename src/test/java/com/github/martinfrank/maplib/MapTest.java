package com.github.martinfrank.maplib;

import com.github.martinfrank.maplib.map.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MapTest {

    @Test
    public void testAStar() {
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        TestMap demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE_DIAMOND4);
        demoMap.scale(12f);
        demoMap.pan(10, 10);
        TestMapWalker walker = mapPartFactory.createWalker();
        TestMapField start = demoMap.getField(0, 0);
        TestMapField end = demoMap.getField(15, 15);
        List<TestMapField> path = demoMap.aStar(start, end, walker, 50);
        Assert.assertEquals(24, path.size());
    }

    @Test
    public void testFieldShaper() {
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        for (MapStyle style : MapStyle.values()) {
            TestMap demoMap = mapFactory.createMap(16, 16, style);
            TestMapWalker walker = mapPartFactory.createWalker();
            TestMapField start = demoMap.getField(0, 0);
            TestMapField end = demoMap.getField(15, 15);
            List<TestMapField> path = demoMap.aStar(start, end, walker, 50);
            int pathLength = getPathLengthForStyle(style);
            Assert.assertEquals(pathLength, path.size());
        }
    }

    private int getPathLengthForStyle(MapStyle style) {
        switch (style) {
            case HEX_VERTICAL: return 23;
            case SQUARE4:
                return 16;
            case TRIANGLE_HORIZONTAL: return 22;
            case TRIANGLE_VERTICAL: return 16;
            default: return 24;
        }
    }

}
