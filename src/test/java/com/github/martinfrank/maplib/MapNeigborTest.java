package com.github.martinfrank.maplib;

import com.github.martinfrank.maplib.map.TestMap;
import com.github.martinfrank.maplib.map.TestMapFactory;
import com.github.martinfrank.maplib.map.TestMapField;
import com.github.martinfrank.maplib.map.TestMapPartFactory;
import org.junit.Assert;
import org.junit.Test;

public class MapNeigborTest {

    @Test
    public void testGetTarget() {
        //given
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        TestMap demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE4);
        demoMap.scale(12f);
        demoMap.pan(10, 10);
        TestMapField start = demoMap.getField(8, 8);

        //when
        TestMapField targetN = demoMap.getTarget(start, Direction.N);

        //then
        Assert.assertEquals(start.getIndex().getY() - 1, targetN.getIndex().getY());
        Assert.assertEquals(start.getIndex().getX(), targetN.getIndex().getX());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTargetInvalidParameter() {
        //given
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        TestMap demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE4);
        demoMap.scale(12f);
        demoMap.pan(10, 10);
        TestMapField start = demoMap.getField(8, 8);

        //when
        demoMap.getTarget(start, Direction.NE);

        //then throws
    }

}
