package de.elite.games.maplib;

import de.elite.games.maplib.map.TestMapEdge;
import de.elite.games.maplib.map.TestMapPoint;
import org.junit.Assert;
import org.junit.Test;

public class EdgeTest {

    @Test
    public void edgeTest() {
        TestMapPoint a = new TestMapPoint(1, 1);
        TestMapPoint b = new TestMapPoint(3, 2);

        MapEdge ab = new TestMapEdge(a, b);
        MapEdge ba = new TestMapEdge(b, a);

        Assert.assertTrue(ab.equalLocation(ba));

    }
}
