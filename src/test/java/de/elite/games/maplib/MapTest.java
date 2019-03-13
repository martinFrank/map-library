package de.elite.games.maplib;

import de.elite.games.maplib.map.TestMap;
import de.elite.games.maplib.map.TestMapFactory;
import de.elite.games.maplib.map.TestMapField;
import de.elite.games.maplib.map.TestMapPartFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class MapTest {

    @Test
    public void test() {
        TestMapPartFactory testMapPartFactory = new TestMapPartFactory();
        TestMapFactory mapFactory = new TestMapFactory(testMapPartFactory);
        final int width = 10;
        final int height = 10;
        TestMap testMap = mapFactory.createMap(width, height, MapStyle.SQUARE4);
        Set<TestMapField> fields = testMap.getFields();
        Assert.assertEquals(width * height, fields.size());
    }

}
