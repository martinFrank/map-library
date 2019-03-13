package de.elite.games.maplib;

import de.elite.games.maplib.data.TestEdgeData;
import de.elite.games.maplib.data.TestPointData;
import de.elite.games.maplib.map.TestMapEdge;
import de.elite.games.maplib.map.TestMapPoint;
import org.junit.Assert;
import org.junit.Test;

public class EdgeTest {

    @Test
    public void edgeTest() {
        TestMapPoint a = new TestMapPoint(1, 1, new TestPointData());
        TestMapPoint b = new TestMapPoint(3, 2, new TestPointData());

        MapEdge ab = new TestMapEdge(a, b, new TestEdgeData());
        MapEdge ba = new TestMapEdge(b, a, new TestEdgeData());

        Assert.assertTrue(ab.equalLocation(ba));

    }
}
