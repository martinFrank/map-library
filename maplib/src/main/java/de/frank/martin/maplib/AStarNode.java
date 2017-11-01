package de.frank.martin.maplib;

final class  AStarNode {

	int f;
	int g;
	int h;
	int x;
	int y;
	AStarNode from;

	AStarNode(MapPoint<?> point) {
		this.x = point.getX();
		this.y = point.getY();
	}

	boolean isSamePos(AStarNode n) {
		if (n != null && n.x == x && n.y == y)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return Integer.toString(x) + "/" + Integer.toString(y) + " g=" + Integer.toString(g) + " h="
				+ Integer.toString(h) + " f=" + Integer.toString(f);
	}

}
