package de.elite.games.maplib.map;

import de.elite.games.maplib.MapWalker;

public class TestMapWalker extends MapWalker<TestMapField, TestMapEdge, TestMapPoint> {
    @Override
    public boolean canEnter(TestMapField from, TestMapField into) {
        return true;
    }

    @Override
    public int getEnterCosts(TestMapField from, TestMapField into) {
        return 10;
    }
}
