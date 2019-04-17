package com.github.martinfrank.maplib;

import com.github.martinfrank.drawlib.Line;
import com.github.martinfrank.drawlib.Point;
import com.github.martinfrank.maplib.data.TestMapEdgeData;
import com.github.martinfrank.maplib.map.TestMapEdge;
import org.junit.Assert;
import org.junit.Test;

public class EdgeTest {

    @Test
    public void edgeTest() {

        MapEdges edges = new MapEdges();

        Point pa = new Point(1, 1);
        Point pb = new Point(3, 4);


        MapEdge ab = new TestMapEdge(new TestMapEdgeData());
        ab.setLine(new Line(pa, pb));
        MapEdge ba = new TestMapEdge(new TestMapEdgeData());
        ba.setLine(new Line(pb, pa));

        Assert.assertEquals(ab, ba);

        MapEdge testee = edges.get(ab);
        Assert.assertEquals(testee, ab);

    }
}
