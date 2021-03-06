package com.github.martinfrank.maplib;


import com.github.martinfrank.drawlib.Line;
import com.github.martinfrank.drawlib.Point;
import com.github.martinfrank.drawlib.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MapFieldShaper<F extends MapField<?, F, E, N>,
        E extends MapEdge<?, F, E, N>,
        N extends MapNode<?, F, E, N>> {

    private final MapPartFactory<?, F, E, N, ?> mapPartFactory;

    MapFieldShaper(MapPartFactory<?, F, E, N, ?> mapPartFactory) {
        this.mapPartFactory = mapPartFactory;
    }

    Shape createFieldShape(F field, MapStyle style, int x, int y, MapNodes<F, E, N> nodes, MapEdges<F, E, N> edges) {
        Point center = createCenter(style, x, y);
        List<N> shapeNodes = createShape(center, x, y, style, nodes);
        List<E> shapeEdges = createEdges(shapeNodes, edges);
        for (N node : shapeNodes) {
            node.addField(field);
            field.addNode(node);
        }
        for (E edge : shapeEdges) {
            edge.addField(field);
            field.addEdge(edge);
        }
        return createShape(center, shapeNodes, shapeEdges);

    }

    private Point createCenter(MapStyle style, int x, int y) {
        switch (style) {
            case SQUARE4:
                return createCenterSquare(x, y);
            case SQUARE_DIAMOND4:
                return setCenterSquareDiamond(x, y);
            case SQUARE_ISOMETRIC4:
                return setCenterSquareIsometric(x, y);
            case HEX_VERTICAL:
                return setCenterHexVertical(x, y);
            case HEX_HORIZONTAL:
                return setCenterHexHorizontal(x, y);
            case TRIANGLE_HORIZONTAL:
                return setCenterTriangleHorizontal(x, y);
            case TRIANGLE_VERTICAL:
                return setCenterTriangleVertical(x, y);
            default:
                return createCenterSquare(x, y);
        }
    }

    private Point setCenterSquareDiamond(int x, int y) {
        boolean isEvenLine = y % 2 == 0;
        if (isEvenLine) {
            int hx = 2 * x + 1;
            int hy = y + 1;
            return new Point(hx, hy);
        } else {
            int hx = 2 * (x + 1);
            int hy = y + 1;
            return new Point(hx, hy);
        }
    }

    private Point setCenterSquareIsometric(int x, int y) {
        boolean isEvenLine = y % 2 == 0;
        if (isEvenLine) {
            int hx = 4 * x + 2;
            int hy = y + 1;
            return new Point(hx, hy);
        } else {
            int hx = 4 * (x + 1);
            int hy = y + 1;
            return new Point(hx, hy);
        }
    }

    private Point createCenterSquare(int x, int y) {
        int cx = 1 + (2 * x);
        int cy = 1 + (2 * y);
        return new Point(cx, cy);
    }

    private Point setCenterHexVertical(int x, int y) {
        boolean isEvenLine = y % 2 == 0;
        int offset = isEvenLine ? 4 : 2;
        int hx = offset + 4 * x;
        int hy = 2 + 3 * y;
        return new Point(hx, hy);
    }

    private Point setCenterHexHorizontal(int x, int y) {
        boolean isEvenRow = x % 2 == 0;
        int offset = isEvenRow ? 2 : 4;
        int hx = 2 + 3 * x;
        int hy = offset + 4 * y;
        return new Point(hx, hy);
    }

    private Point setCenterTriangleHorizontal(int x, int y) {
        boolean isEvenLine = y % 2 == 0;
        boolean isEvenRow = x % 2 == 0;
        int hx = 2 + 2 * x;
        int hy = 3 * y;
        if (isEvenLine) {
            hy = hy + (isEvenRow ? 2 : 1);
        } else {
            hy = hy + (isEvenRow ? 1 : 2);
        }
        return new Point(hx, hy);
    }

    private Point setCenterTriangleVertical(int x, int y) {
        boolean isEvenLine = y % 2 == 0;
        boolean isEvenRow = x % 2 == 0;
        int hx = 3 * x;
        int hy = 2 + 2 * y;

        if (isEvenLine) {
            hx = hx + (isEvenRow ? 1 : 2);
        } else {
            hx = hx + (isEvenRow ? 2 : 1);
        }
        return new Point(hx, hy);
    }

    private List<N> createShape(Point center, int x, int y, MapStyle style, MapNodes<F, E, N> nodes) {
        switch (style) {
            case SQUARE4:
                return createSquares(center, nodes);
            case SQUARE_DIAMOND4:
                return createSquaresDiamond(center, nodes);
            case SQUARE_ISOMETRIC4:
                return createSquaresIsometric(center, nodes);
            case HEX_VERTICAL:
                return createHexesVertical(center, nodes);
            case HEX_HORIZONTAL:
                return createHexesHorizontal(center, nodes);
            case TRIANGLE_HORIZONTAL:
                return createTriangleHorizontal(center, x, y, nodes);
            case TRIANGLE_VERTICAL:
                return createTriangleVertical(center, x, y, nodes);
            default:
                return createSquares(center, nodes);
        }
    }

    private List<N> createTriangleVertical(Point center, int x, int y, MapNodes<F, E, N> nodes) {
        boolean isPointingLeft;
        if (y % 2 == 0) {
            isPointingLeft = x % 2 != 0;
        } else {
            isPointingLeft = x % 2 == 0;
        }
        N a;
        N b;
        N c;
        if (isPointingLeft) {
            a = getDeltaNode(center, -2, 0, nodes);
            b = getDeltaNode(center, 1, -2, nodes);
            c = getDeltaNode(center, 1, 2, nodes);
        } else {
            a = getDeltaNode(center, -1, -2, nodes);
            b = getDeltaNode(center, 2, 0, nodes);
            c = getDeltaNode(center, -1, 2, nodes);
        }
        return Arrays.asList(a, b, c);
    }

    private List<N> createTriangleHorizontal(Point center, int x, int y, MapNodes<F, E, N> nodes) {
        boolean isUpside;
        if (y % 2 == 0) {
            isUpside = x % 2 == 0;
        } else {
            isUpside = x % 2 != 0;
        }
        N a;
        N b;
        N c;
        if (isUpside) {
            a = getDeltaNode(center, -2, 1, nodes);
            b = getDeltaNode(center, 0, -2, nodes);
            c = getDeltaNode(center, 2, 1, nodes);
        } else {
            a = getDeltaNode(center, -2, -1, nodes);
            b = getDeltaNode(center, 2, -1, nodes);
            c = getDeltaNode(center, 0, 2, nodes);
        }
        return Arrays.asList(a, b, c);
    }

    private List<N> createHexesVertical(Point center, MapNodes<F, E, N> nodes) {
        N a = getDeltaNode(center, -2, -1, nodes);
        N b = getDeltaNode(center, 0, -2, nodes);
        N c = getDeltaNode(center, 2, -1, nodes);
        N d = getDeltaNode(center, 2, 1, nodes);
        N e = getDeltaNode(center, 0, 2, nodes);
        N f = getDeltaNode(center, -2, 1, nodes);
        return Arrays.asList(a, b, c, d, e, f);
    }

    private List<N> createHexesHorizontal(Point center, MapNodes<F, E, N> nodes) {
        N a = getDeltaNode(center, -2, 0, nodes);
        N b = getDeltaNode(center, -1, -2, nodes);
        N c = getDeltaNode(center, 1, -2, nodes);
        N d = getDeltaNode(center, 2, 0, nodes);
        N e = getDeltaNode(center, 1, 2, nodes);
        N f = getDeltaNode(center, -1, 2, nodes);
        return Arrays.asList(a, b, c, d, e, f);
    }

    private List<N> createSquares(Point center, MapNodes<F, E, N> nodes) {
        N a = getDeltaNode(center, -1, -1, nodes);
        N b = getDeltaNode(center, 1, -1, nodes);
        N c = getDeltaNode(center, 1, 1, nodes);
        N d = getDeltaNode(center, -1, 1, nodes);
        return Arrays.asList(a, b, c, d);
    }


    private List<N> createSquaresDiamond(Point center, MapNodes<F, E, N> nodes) {
        N a = getDeltaNode(center, 0, -1, nodes);
        N b = getDeltaNode(center, 1, 0, nodes);
        N c = getDeltaNode(center, 0, 1, nodes);
        N d = getDeltaNode(center, -1, 0, nodes);
        return Arrays.asList(a, b, c, d);
    }

    private List<N> createSquaresIsometric(Point center, MapNodes<F, E, N> nodes) {
        N a = getDeltaNode(center, 0, -1, nodes);
        N b = getDeltaNode(center, 2, 0, nodes);
        N c = getDeltaNode(center, 0, 1, nodes);
        N d = getDeltaNode(center, -2, 0, nodes);
        return Arrays.asList(a, b, c, d);
    }

    private List<E> createEdges(List<N> shapeNodes, MapEdges<F, E, N> edges) {
        List<E> edgeShapes = new ArrayList<>();
        for (int index = 0; index < shapeNodes.size(); index++) {
            N a = shapeNodes.get(index);
            int nextIndex = index + 1 == shapeNodes.size() ? 0 : index + 1;
            N b = shapeNodes.get(nextIndex);
            E edge = createEdge(a, b, edges);
            edgeShapes.add(edge);
            a.addEdge(edge);
            b.addEdge(edge);
            edge.addNode(a);
            edge.addNode(b);


        }
        return edgeShapes;
    }

    private Shape createShape(Point center, List<N> nodes, List<E> edges) {
        List<Point> points = new ArrayList<>();
        for (N node : nodes) {
            points.add(node.getPoint());
        }
        List<Line> lines = new ArrayList<>();
        for (E edge : edges) {
            lines.add(edge.getLine());
        }
        return new Shape(center, points, lines);
    }

    private E createEdge(N a, N b, MapEdges<F, E, N> edges) {
        Line line = new Line(a.getPoint(), b.getPoint());
        E edge = mapPartFactory.createMapEdge();
        edge.setLine(line);

        return edges.get(edge);
    }

    private N getDeltaNode(Point center, int dx, int dy, MapNodes<F, E, N> nodes) {
        N node = mapPartFactory.createMapNode();
        node.setPoint(new Point(center.getX() + dx, center.getY() + dy));
        return nodes.get(node);
    }
}
