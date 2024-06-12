package it.my.linesegment.model;

import java.util.ArrayList;
import java.util.List;

public class Segment {

    private List<Point> segment;

    public Segment() {
        this.segment = new ArrayList<>();
    }

    public void addPoint(Point point) {
        this.segment.add(point);
    }

    public int getLength() {
        return segment != null ? segment.size() : 0;
    }

    public List<Point> getPoints() {
        return this.segment;
    }

    public boolean contains(Point point) {
        return this.segment.contains(point);
    }

    @Override
    public String toString() {
        StringBuilder toStr = new StringBuilder();
        toStr.append("[");
        for (Point point : segment) {
            toStr.append("(").append(point.getX()).append(", ").append(point.getY()).append(")");
        }
        toStr.append("]").append(" --- size: ").append(segment.size());
        return toStr.toString();
    }
}
