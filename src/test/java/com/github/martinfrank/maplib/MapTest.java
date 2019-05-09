package com.github.martinfrank.maplib;

import com.github.martinfrank.maplib.map.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MapTest {

    @Test
    public void test() {
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        TestMap demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE_DIAMOND);
        demoMap.scale(12f);
        demoMap.pan(10, 10);
        TestMapWalker walker = mapPartFactory.createWalker();
        TestMapField start = demoMap.getField(0, 0);
        TestMapField end = demoMap.getField(15, 15);
        List<TestMapField> path = demoMap.aStar(start, end, walker, 50);
        Assert.assertEquals(24, path.size());
    }

}
