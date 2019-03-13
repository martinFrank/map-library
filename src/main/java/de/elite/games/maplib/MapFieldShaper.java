package de.elite.games.maplib2;

import de.elite.games.geolib.GeoPoint;

class MapFieldShaper<F extends MapField<?, F, E, P>, E extends MapEdge<?, F, E, P>, P extends MapPoint<?, F, E, P>> {

    private final MapPartFactory<?, F, E, P, ?> mapPartFactory;

    MapFieldShaper(MapPartFactory<?, F, E, P, ?> mapPartFactory) {
        this.mapPartFactory = mapPartFactory;
    }

    void createFieldShape(F field, MapStyle style) {
        createCenter(field, style);
        createShape(field, style);

    }

    private void createCenter(F field, MapStyle style) {
        GeoPoint index = field.getIndex();
        P center = mapPartFactory.createMapPoint(index.getX(), index.getY());
        adjustCenter(center, index, style);
        field.setCenter(center);
    }

    private void adjustCenter(P center, GeoPoint index, MapStyle style) {
        switch (style) {
            case SQUARE4:
            case SQUARE8:
                setCenterSquare(center, index);
                break;
            case HEX_VERTICAL:
                setCenterHexVertical(center, index);
                break;
            case HEX_HORIZONTAL:
                setCenterHexHorizontal(center, index);
                break;
            case TRIANGLE_HORIZONTAL:
                setCenterTriangleHorizontal(center, index);
                break;
            case TRIANGLE_VERTICAL:
                setCenterTriangleVertical(center, index);
                break;
        }
    }

    private void setCenterSquare(P center, GeoPoint index) {
        int x = 1 + (2 * index.getX());
        int y = 1 + (2 * index.getY());
        center.setPoint(x, y);
    }

    private void setCenterHexVertical(P center, GeoPoint index) {
        boolean isEvenLine = index.getY() % 2 == 0;
        int offset = isEvenLine ? 4 : 2;
        int x = offset + 4 * index.getX();
        int y = 2 + 3 * index.getY();
        center.setPoint(x, y);
    }

    private void setCenterHexHorizontal(P center, GeoPoint index) {
        boolean isEvenRow = index.getX() % 2 == 0;
        int offset = isEvenRow ? 2 : 4;
        int x = 2 + 3 * index.getX();
        int y = offset + 4 * index.getY();
        center.setPoint(x, y);
    }

    private void setCenterTriangleHorizontal(P center, GeoPoint index) {
        boolean isEvenLine = index.getY() % 2 == 0;
        boolean isEvenRow = index.getX() % 2 == 0;
        int x = 2 + 2 * index.getX();
        int y = 3 * index.getY();
        if (isEvenLine) {
            y = y + (isEvenRow ? 2 : 1);
        } else {
            y = y + (isEvenRow ? 1 : 2);
        }
        center.setPoint(x, y);
    }

    private void setCenterTriangleVertical(P center, GeoPoint index) {
        boolean isEvenLine = index.getY() % 2 == 0;
        boolean isEvenRow = index.getX() % 2 == 0;
        int x = 3 * index.getX();
        int y = 2 + 2 * index.getY();

        if (isEvenLine) {
            x = x + (isEvenRow ? 1 : 2);
        } else {
            x = x + (isEvenRow ? 2 : 1);
        }
        center.setPoint(x, y);
    }

    private void createShape(F field, MapStyle style) {
        switch (style) {
            case SQUARE4:
            case SQUARE8:
                createSquares(field);
                break;
            case HEX_VERTICAL:
                createHexesVertical(field);
                break;
            case HEX_HORIZONTAL:
                createHexesHorizontal(field);
                break;
            case TRIANGLE_HORIZONTAL:
                createTriangleHorizontal(field);
                break;
            case TRIANGLE_VERTICAL:
                createTriangleVertical(field);
                break;
        }
    }

    private void createTriangleVertical(F field) {
        GeoPoint index = field.getIndex();
        boolean isPointingLeft;
        if (index.getY() % 2 == 0) {
            isPointingLeft = index.getX() % 2 != 0;
        } else {
            isPointingLeft = index.getX() % 2 == 0;
        }
        P a;
        P b;
        P c;
        if (isPointingLeft) {
            a = createDeltaMapPointFromCenter(field, -2, 0);
            b = createDeltaMapPointFromCenter(field, 1, -2);
            c = createDeltaMapPointFromCenter(field, 1, 2);
        } else {
            a = createDeltaMapPointFromCenter(field, -1, -2);
            b = createDeltaMapPointFromCenter(field, 2, 0);
            c = createDeltaMapPointFromCenter(field, -1, 2);
        }
        field.addEdge(mapPartFactory.createMapEdge(a, b));
        field.addEdge(mapPartFactory.createMapEdge(b, c));
        field.addEdge(mapPartFactory.createMapEdge(c, a));
    }

    private void createTriangleHorizontal(F field) {
        GeoPoint index = field.getIndex();
        boolean isUpside;
        if (index.getY() % 2 == 0) {
            isUpside = index.getX() % 2 == 0;
        } else {
            isUpside = index.getX() % 2 != 0;
        }
        P a;
        P b;
        P c;
        if (isUpside) {
            a = createDeltaMapPointFromCenter(field, -2, 1);
            b = createDeltaMapPointFromCenter(field, 2, 1);
            c = createDeltaMapPointFromCenter(field, 0, -2);
        } else {
            a = createDeltaMapPointFromCenter(field, -2, -1);
            b = createDeltaMapPointFromCenter(field, 2, -1);
            c = createDeltaMapPointFromCenter(field, 0, 2);
        }
        field.addEdge(mapPartFactory.createMapEdge(a, b));
        field.addEdge(mapPartFactory.createMapEdge(b, c));
        field.addEdge(mapPartFactory.createMapEdge(c, a));
    }

    private void createHexesVertical(F field) {
        P a = createDeltaMapPointFromCenter(field, -2, -1);
        P b = createDeltaMapPointFromCenter(field, 0, -2);
        P c = createDeltaMapPointFromCenter(field, 2, -1);
        P d = createDeltaMapPointFromCenter(field, 2, 1);
        P e = createDeltaMapPointFromCenter(field, 0, 2);
        P f = createDeltaMapPointFromCenter(field, -2, 1);
        field.addEdge(mapPartFactory.createMapEdge(a, b));
        field.addEdge(mapPartFactory.createMapEdge(b, c));
        field.addEdge(mapPartFactory.createMapEdge(c, d));
        field.addEdge(mapPartFactory.createMapEdge(d, e));
        field.addEdge(mapPartFactory.createMapEdge(e, f));
        field.addEdge(mapPartFactory.createMapEdge(f, a));
    }

    private void createHexesHorizontal(F field) {
        P a = createDeltaMapPointFromCenter(field, -2, 0);
        P b = createDeltaMapPointFromCenter(field, -1, -2);
        P c = createDeltaMapPointFromCenter(field, 1, -2);
        P d = createDeltaMapPointFromCenter(field, 2, 0);
        P e = createDeltaMapPointFromCenter(field, 1, 2);
        P f = createDeltaMapPointFromCenter(field, -1, 2);
        field.addEdge(mapPartFactory.createMapEdge(a, b));
        field.addEdge(mapPartFactory.createMapEdge(b, c));
        field.addEdge(mapPartFactory.createMapEdge(c, d));
        field.addEdge(mapPartFactory.createMapEdge(d, e));
        field.addEdge(mapPartFactory.createMapEdge(e, f));
        field.addEdge(mapPartFactory.createMapEdge(f, a));
    }

    private void createSquares(F field) {
        P a = createDeltaMapPointFromCenter(field, -1, -1);
        P b = createDeltaMapPointFromCenter(field, 1, -1);
        P c = createDeltaMapPointFromCenter(field, 1, 1);
        P d = createDeltaMapPointFromCenter(field, -1, 1);

        field.addEdge(mapPartFactory.createMapEdge(a, b));
        field.addEdge(mapPartFactory.createMapEdge(b, c));
        field.addEdge(mapPartFactory.createMapEdge(c, d));
        field.addEdge(mapPartFactory.createMapEdge(d, a));
    }


    private P createDeltaMapPointFromCenter(F field, int dx, int dy) {
        GeoPoint center = field.getCenter().getPoint();
        return mapPartFactory.createMapPoint(center.getX() + dx, center.getY() + dy);
    }


}
