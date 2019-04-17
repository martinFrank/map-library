package com.github.martinfrank.maplib;

import com.github.martinfrank.maplib.map.TestMap;
import com.github.martinfrank.maplib.map.TestMapFactory;
import com.github.martinfrank.maplib.map.TestMapPartFactory;
import com.github.martinfrank.maplib.map.TestMapWalker;
import org.junit.Test;

public class MapTest {

    @Test
    public void test() {
        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        TestMap demoMap = mapFactory.createMap(16, 16, MapStyle.SQUARE_DIAMOND);
        demoMap.scale(12f);
        demoMap.pan(10, 10);
        TestMapWalker walker = mapPartFactory.createWalker();
    }

}
