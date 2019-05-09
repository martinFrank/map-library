package com.github.martinfrank.maplib;

import java.util.ArrayList;
import java.util.List;

/**
 * this is an astar shortestpath implemenetation for the map library. you must
 * define a walker who's able to walk into fields and determines the costs for
 * walking.
 *
 * @author martinFrank
 */
class Astar<M extends Map<?, F, E, P, W>,
        F extends MapField<?, F, E, P>,
        E extends MapEdge<?, F, E, P>,
        P extends MapNode<?, F, E, P>,
        W extends MapWalker<F, E, P>> {

    private final M map;

    public Astar(M map) {
        this.map = map;
    }

    /**
     * open list
     */
    private ArrayList<AStarNode> oList = new ArrayList<>();

    /**
     * closed list
     */
    private ArrayList<AStarNode> cList = new ArrayList<>();

    ArrayList<F> getShortestPath(F startField, F targetField, W walker, int maxPathLength) {
        ArrayList<F> path = new ArrayList<>();
        if (walker == null || startField == null || targetField == null) {
            return path;
        }

        // 1) Fuege das Startquadrat der offenen Liste hinzu.
        // 2) Wiederhole das Folgende:
        // a) Suche in der offenen Liste nach dem Quadrat mit dem niedrigsten
        //    F-Wert. Wir bezeichnen dieses Quadrat im Folgenden als das aktuelle Quadrat.
        // b) Verschiebe es in die geschlossene Liste.
        // c) Fuer jedes der 8 an das aktuelle Quadrat angrenzenden Quadrate:
        //    Wenn es nicht begehbar ist oder sich bereits in der geschlossenen
        //    Liste befindet, ignoriere es; andernfalls mach das Folgende:
        //      Wenn es nicht in der offenen Liste ist, fuege es der offenen Liste
        //      hinzu. Trage das aktuelle Quadrat als Vorgaengerquadrat dieses
        //      Quadrats ein. Trage zusaetzlich die Werte fuer die F-, G- und H-Kosten
        //      dieses Quadrates ein.
        //      Falls es bereits in der offenen Liste ist, pruefe, ob der Pfad vom
        //      aktuellen Quadrat zu ihm - gemessen am G-Wert -, besser ist, als der
        //      Pfad von seinem eingetragenen Vorgaengerquadrat (ein geringerer
        //      g-Wert bedeutet einen besseren Pfad). Falls dem so ist, aendere sein
        //      Vorgaengerquadrat auf das aktuelle Quadrat und berechne seine Werte fuer
        //      G und F neu. Sofern Du Deine offene Liste nach dem F-Wert sortiert hast,
        //      ist moeglicherweise eine Neusortierung dieser Liste erforderlich, um
        //      dieser Veraenderung Rechnung zu tragen.
        // d) Beende den Prozess, falls:
        //    Du das Zielquadrat in die geschlossene Liste verschoben hast; in diesem Fall
        //    hast Du den Pfad ermittelt
        //    kein Zielquadrat gefunden werden konnte und die offene Liste leer ist;
        //    in diesem Fall gibt es keinen Pfad.
        // 3) Sichere den Pfad. Der Pfad erschliesst sich, indem Du vom Zielquadrat aus
        //   Quadrat fuer Quadrat rueckwaerts schreitend das Startquadrat erreichst.

        oList.clear();
        cList.clear();

        AStarNode start = new AStarNode(startField.getIndex());
        AStarNode end = new AStarNode(targetField.getIndex());

        oList.add(start);

        boolean noWayFound = false;
        while (true) {
            AStarNode current = getLeastF(oList);
            if (current == null || current.g > maxPathLength * 10) {
                noWayFound = true;
                break;
            }
            if (current.isSamePos(end)) {
                end.from = current.from;
                break;
            }
            oList.remove(current);
            cList.add(current);
            expandNode(current, map, walker, end);
        }

        if (!noWayFound) {
            AStarNode n = end;
            while (n != null) {
                F wayPoint = map.getField(n.x, n.y);
                if (wayPoint != null) {
                    path.add(wayPoint);
                    n = n.from;
                }
            }
        }
        return path;
    }

    private void expandNode(AStarNode current, M map, W walker, AStarNode end) {
        F center = map.getField(current.x, current.y);
        if (center != null) {
            List<AStarNode> nodeList = getNeigbors(walker, center);
            for (AStarNode n : nodeList) {
                if (checkIsPassable(center, n, walker, map)) {
                    F to = map.getField(n.x, n.y);
                    if (to != null) {
                        int distance = walker.getEnterCosts(center, to);
                        addIfRequired(n, current, end, distance);
                    }
                }
            }
        }
    }

    private List<AStarNode> getNeigbors(W walker, F field) {
        List<AStarNode> nodeList = new ArrayList<>();
        for (F nbgField : walker.getNeighbours(field)) {
            nodeList.add(new AStarNode(nbgField.getIndex()));
        }
        return nodeList;
    }

    private void addIfRequired(AStarNode nNode, AStarNode current, AStarNode end, int distance) {
        if (!isPosInList(nNode, cList)) {
            if (isPosInList(nNode, oList)) {
                AStarNode can = getPos(nNode, oList);
                if (can != null && can.g < nNode.g) {
                    can.from = current;
                    can.g = current.g + distance;
                    can.f = can.h + can.g;
                }
            } else {
                nNode.from = current;
                nNode.h = 10 * (Math.abs(end.x - nNode.x) + Math.abs(end.y - nNode.y));
                nNode.g = current.g + distance;
                nNode.f = nNode.h + nNode.g;
                oList.add(nNode);
            }
        }
    }

    private boolean checkIsPassable(F from, AStarNode n, W walker, M map) {
        F into = map.getField(n.x, n.y);
        return into != null && walker.canEnter(from, into);
    }

    private AStarNode getPos(AStarNode n, ArrayList<AStarNode> list) {
        for (AStarNode node : list) {
            if (node.isSamePos(n)) {
                return node;
            }
        }
        return null;
    }

    private boolean isPosInList(AStarNode n, ArrayList<AStarNode> list) {
        for (AStarNode node : list) {
            if (node.isSamePos(n)) {
                return true;
            }
        }
        return false;
    }

    private AStarNode getLeastF(ArrayList<AStarNode> list) {
        int cf = Integer.MAX_VALUE;
        AStarNode ret = null;
        for (AStarNode n : list) {
            if (n.f < cf) {
                cf = n.f;
                ret = n;
            }
        }
        return ret;
    }

}
