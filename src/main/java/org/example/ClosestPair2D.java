package org.example;

import java.util.*;

public class ClosestPair2D {
    public static double solve(List<Point2D> points) {
        List<Point2D> px = new ArrayList<>(points);
        px.sort(Comparator.comparingDouble(Point2D::x));
        List<Point2D> py = new ArrayList<>(points);
        py.sort(Comparator.comparingDouble(Point2D::y));
        return closest(px, py);
    }

    private static double closest(List<Point2D> px, List<Point2D> py) {
        int n = px.size();
        if (n <= 3) return brute(px);

        int mid = n / 2;
        double midX = px.get(mid).x();

        List<Point2D> Lx = px.subList(0, mid);
        List<Point2D> Rx = px.subList(mid, n);

        List<Point2D> Ly = new ArrayList<>(), Ry = new ArrayList<>();
        for (Point2D p : py) {
            if (p.x() < midX) Ly.add(p);
            else Ry.add(p);
        }

        double d1 = closest(new ArrayList<>(Lx), Ly);
        double d2 = closest(new ArrayList<>(Rx), Ry);
        double d = Math.min(d1, d2);

        List<Point2D> strip = new ArrayList<>();
        for (Point2D p : py) {
            if (Math.abs(p.x() - midX) < d) strip.add(p);
        }

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y() - strip.get(i).y()) < d; j++) {
                d = Math.min(d, distance(strip.get(i), strip.get(j)));
            }
        }

        return d;
    }

    private static double brute(List<Point2D> pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.size(); i++) {
            for (int j = i + 1; j < pts.size(); j++) {
                best = Math.min(best, distance(pts.get(i), pts.get(j)));
            }
        }
        return best;
    }

    private static double distance(Point2D a, Point2D b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}