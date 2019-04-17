package com.github.martinfrank.maplib;

import com.github.martinfrank.geolib.GeoPoint;

final class AStarNode {

	int f;
	int g;
	int h;
	int x;
	int y;
	AStarNode from;

	AStarNode(GeoPoint point) {
		this.x = point.getX();
		this.y = point.getY();
	}

	boolean isSamePos(AStarNode n) {
		return n != null && n.x == x && n.y == y;
	}

	@Override
	public String toString() {
		return Integer.toString(x) + "/" + Integer.toString(y) + " g=" + Integer.toString(g) + " h="
				+ Integer.toString(h) + " f=" + Integer.toString(f);
	}

}
