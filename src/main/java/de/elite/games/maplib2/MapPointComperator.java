package de.elite.games.maplib2;

import de.elite.games.drawlib.Point;

import java.util.Comparator;

/**
 * Comparator to sort List<GeoPoint> clockwise (requires a center)
 */
class MapPointComperator implements Comparator<Point> {

    private final Point center;

    //
    MapPointComperator(Point center) {
        this.center = center;
    }

    private static double distance(Point from, Point to) {
        return distance(from.getX(), from.getY(), to.getX(), to.getY());
    }

    private static double distance(double fromx, double fromy, double tox, double toy) {
        double dx = tox - fromx;
        double dy = toy - fromy;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    @Override
    public int compare(Point o1, Point o2) {
        GlPolarPoint p1 = new GlPolarPoint(o1, center);
        GlPolarPoint p2 = new GlPolarPoint(o2, center);
        return p1.compareTo(p2);
    }

    /**
     * polar points represent a point with angle/distance<br>
     * they are equal to Cartesian points(x/y)
     */
    private class GlPolarPoint implements Comparable<GlPolarPoint> {
        private double tetha;
        private double length;

        private GlPolarPoint(Point point, Point center) {
            double dx = point.getX() - center.getX();
            double dy = point.getY() - center.getY();
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