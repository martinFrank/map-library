package de.frank.martin.maplib;


class Node {
	
	int f;
	int g;
	int h;
	int x;
	int y;
	Node from;
	
	Node(Point point){
		this.x=point.x(); this.y=point.y();
	}
	
	boolean isSamePos(Node n){
		if (n != null && n.x==x && n.y==y) return true;
		return false;
	}

	@Override
	public String toString() {
		return ""+x+"/"+y+" g="+g+" h="+h+" f="+f;
	}
	
	
}
