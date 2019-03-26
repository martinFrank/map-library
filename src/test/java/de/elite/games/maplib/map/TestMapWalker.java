package de.elite.games.maplib.map;

import de.elite.games.maplib.MapWalker;

import java.util.List;

public class TestMapWalker extends MapWalker<TestMapField, TestMapEdge, TestMapNode> {
    @Override
    public boolean canEnter(TestMapField from, TestMapField into) {
        return true;
    }

    @Override
    public int getEnterCosts(TestMapField from, TestMapField into) {
        return 10;
    }

    @Override
    public List<TestMapField> getNeighbours(TestMapField field) {
        return getNeighboursFromNodes(field);//this one can walk diagonal
    }
}
