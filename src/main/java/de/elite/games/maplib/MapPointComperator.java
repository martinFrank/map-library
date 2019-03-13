package de.elite.games.maplib;

import java.util.Comparator;

/**
 * Comparator to sort List<GeoPoint> clockwise (requires a center)
 *
 * @author martinFrank
 */
class MapPointComperator implements Comparator<MapPoint> {

    private final MapPoint center;

    MapPointComperator(MapPoint center) {
        this.center = center;
    }

    private static double distance(MapPoint from, MapPoint to) {
        return distance(from.getPoint().getX(), from.getPoint().getY(), to.getPoint().getX(), to.getPoint().getY());
    }

    private static double distance(int fromx, int fromy, int tox, int toy) {
        double dx = tox - fromx;
        double dy = toy - fromy;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    @Override
    public int compare(MapPoint o1, MapPoint o2) {
        GlPolarPoint p1 = new GlPolarPoint(o1, center);
        GlPolarPoint p2 = new GlPolarPoint(o2, center);
        return p1.compareTo(p2);
    }

    /**
     * polar points represent a point with angle/distance<br>
     * they are equal to Cartesian points(x/y)
     *
     * @author martinFrank
     */
    private class GlPolarPoint implements Comparable<GlPolarPoint> {
        private double tetha;
        private double length;

        private GlPolarPoint(MapPoint point, MapPoint center) {
            int dx = point.getPoint().getX() - center.getPoint().getX();
            int dy = point.getPoint().getY() - center.getPoint().getY();
            tetha = Math.atan2(dy, dx);
            length = distance(point, center);
        }

        @Override
        public int compareTo(GlPolarPoint o) {
            int d = Double.compare(tetha, o.tetha);
            if (d == 0) {
                return Double.compare(length, o.length);
            } else {
                return d;
            }
        }
    }
}