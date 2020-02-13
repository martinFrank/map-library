package com.github.martinfrank.maplib;

import com.github.martinfrank.maplib.map.TestMap;
import com.github.martinfrank.maplib.map.TestMapFactory;
import com.github.martinfrank.maplib.map.TestMapPartFactory;
import org.junit.Test;

public class MapPrinterTest {

    @Test
    public void testPrinter(){

        TestMapPartFactory mapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(mapPartFactory);
        TestMap demoMap = mapFactory.createMap(16, 16, MapStyle.HEX_HORIZONTAL);


        MapPrinter.print(demoMap);
    }
}
