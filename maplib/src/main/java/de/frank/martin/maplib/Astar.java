package de.frank.martin.maplib;

import java.util.ArrayList;
import java.util.List;


class Astar<T> {
	
	private ArrayList<Node> oList = new ArrayList<>();
	private ArrayList<Node> cList = new ArrayList<>();	
	
	ArrayList<Field<T>> getShortestPath(Field<T> startPoint, Field<T> targetPoint, Walker<T> walker, Map< T> map,  int maxPathLength) {
		ArrayList<Field<T>> path = new ArrayList<>();
		if(walker == null || startPoint == null || targetPoint == null ||map==null){
			return path;
		}
		
//		1)  Fuege das Startquadrat der offenen Liste hinzu.  
//		2)  Wiederhole das Folgende:  
//		  a)  Suche in der offenen Liste nach dem Quadrat mit dem niedrigsten F-Wert. Wir
//		  bezeichnen dieses Quadrat im Folgenden als das aktuelle Quadrat.  
//		  b)  Verschiebe es in die geschlossene Liste.  
//		  c)  Fuer jedes der 8 an das aktuelle Quadrat angrenzenden Quadrate:  
//		    Wenn es nicht begehbar ist oder sich bereits in der geschlossenen Liste befindet,
//		    ignoriere es; andernfalls mach das Folgende: 
//		Wenn es nicht in der offenen Liste ist, fuege es der offenen Liste hinzu. Trage das 
//		aktuelle Quadrat als Vorgaengerquadrat dieses Quadrats ein. Trage zusaetzlich die Werte 
//		fuer die F-, G- und H-Kosten dieses Quadrates ein. 
//		Falls es bereits in der offenen Liste ist, pruefe, ob der Pfad vom aktuellen Quadrat zu 
//		ihm - gemessen am G-Wert -, besser ist, als der Pfad von seinem eingetragenen 
//		Vorgaengerquadrat (ein geringerer G-Wert bedeutet einen besseren Pfad). Falls dem so 
//		ist, aendere sein Vorgaengerquadrat auf das aktuelle Quadrat und berechne seine Werte 
//		fuer G und F neu. Sofern Du Deine offene Liste nach dem F-Wert sortiert hast, ist 
//		moeglicherweise eine Neusortierung dieser Liste erforderlich, um dieser Veraenderung 
//		Rechnung zu tragen. 
//		 
//		  d)  Beende den Prozess, falls: 
//		Du das Zielquadrat in die geschlossene Liste verschoben hast; in diesem Fall hast Du 
//		den Pfad ermittelt 
//		kein Zielquadrat gefunden werden konnte und die offene Liste leer ist; in diesem Fall 
//		gibt es keinen Pfad. 
//		 
//		3)  Sichere den Pfad. Der Pfad erschliesst sich, indem Du vom Zielquadrat aus Quadrat 
//		fuer Quadrat rueckwaerts schreitend das Startquadrat erreichst.  
		
		oList.clear();
		cList.clear();
		
		Node start = new Node(startPoint.index());
		Node end = new Node(targetPoint.index());
		
		oList.add(start);
		
		boolean noWayFound = false;
		while(true){
			Node current = getLeastF(oList);			
			if (current == null || current.g > maxPathLength*10){
				noWayFound = true;
				break;
			}
			if (current.isSamePos(end) ){
				noWayFound = false;
				end.from = current.from;
				break;
			}			
			oList.remove(current);
			cList.add(current);
			expandNode(current, map, walker, end);			
		}
				
		if (!noWayFound){
			Node n = end;
			while(n != null){
				Field<T> wayPoint = map.getFieldByIndex(n.x, n.y);
				path.add(wayPoint);
				n = n.from;
			}
		}		
		return path;
	}	
	
	private void expandNode(Node current, Map<T> map, Walker<T> walker, Node end) {		
		Field<T> center =  map.getFieldByIndex(current.x, current.y);
		List<Node> nodeList = getNeigbours(center);				
		for (Node n: nodeList){
			if (checkIsPassable(center, n, walker, map) ){
				Field<T> to = map.getFieldByIndex(n.x, n.y);
				int distance = walker.getDistance(center, to, map.getMapStyle());				
				addIfRequired(n, current, end, distance);
			}			
		}
	}

	private List<Node> getNeigbours(Field<? extends T> center) {
		List<Node> nodeList = new ArrayList<>();
		//center.getNeigbourList().stream().forEach(e -> nodeList.add(new Node(e.ix(),e.iy())))
		for(Field<? extends T> nbgField: center.getNeigbourList() ){
			nodeList.add(new Node(nbgField.index()));
		}
		return nodeList;
	}

	private void addIfRequired(Node nNode, Node current, Node end, int distance) {
		if ( !isPosInList(nNode, cList) ){				
			if ( isPosInList(nNode, oList)  ){
				Node can = getPos(nNode, oList);
				if (can != null && can.g < nNode.g){
					can.from = current;
					can.g = current.g + distance;
					can.f = can.h + can.g;
				}
			}else{
				nNode.from = current;
				nNode.h = 10*  ( Math.abs(end.x-nNode.x)+Math.abs(end.y-nNode.y)   );
				nNode.g = current.g + distance;
				nNode.f = nNode.h + nNode.g;
				oList.add(nNode);
			}
		}
	}

	private boolean checkIsPassable(Field<T> from, Node n, Walker<T> walker, Map<T> map) {		
		Field<T> into = map.getFieldByIndex(n.x, n.y);		
		return walker.canEnter(from, into);
	}

	private Node getPos(Node n, ArrayList<Node> list) {
//		return list.stream().filter(e -> e.isSamePos(n)).findFirst().orElse(null)
		for(Node node: list){
			if (node.isSamePos(n)){
				return node;
			}
		}
		return null;
	}

	private boolean isPosInList(Node n, ArrayList<Node> list) {
//		return list.stream().filter(e -> e.isSamePos(n)).findAny().isPresent()
		for(Node node: list){
			if(node.isSamePos(n)){
				return true;
			}
		}
		return false;
	}

	private Node getLeastF(ArrayList<Node> list) {		
		int cf = Integer.MAX_VALUE;
		Node ret = null;
		for (Node n: list){
			if (n.f < cf){
				cf = n.f;
				ret = n;
			}	
		}
		return ret;
	}

}

