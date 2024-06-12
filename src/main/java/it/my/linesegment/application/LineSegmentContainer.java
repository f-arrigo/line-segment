package it.my.linesegment.application;

import it.my.linesegment.model.Point;
import it.my.linesegment.model.Segment;

import java.util.ArrayList;
import java.util.List;

public class LineSegmentContainer {

    private final static double TOLERANCE = 0.000000000001d;

    private List<Point> pointSet;

    private List<Segment> segments;

    private static LineSegmentContainer instance;

    private LineSegmentContainer() {
        this.pointSet = new ArrayList<>();
        this.segments = new ArrayList<>();
    }

    public static LineSegmentContainer getInstance() {
        if (instance != null) {
            return instance;
        }
        else {
            instance = new LineSegmentContainer();
            return instance;
        }
    }

    public List<Point> getPointSet() {
        return this.pointSet;
    }

    /**
     * Reset the Point set and the related segments
     */
    public void resetPointSet() {
        this.pointSet = new ArrayList<>();
        this.segments = new ArrayList<>();
    }

    /**
     * Add the new point to the point set and also add it to every already
     * existing segment, if it is on the same line as the segment itself.
     * The singleton LineSegmentContainer maintains a cache of all the possible segment
     * that can be composed by the whole point set.
     *
     * @param point
     */
    public void addPoint(Point point) {
        if (point == null) {
            return;
        }

        // check if it belongs to the point set already
        boolean isIn = false;
        for (Point elem : this.pointSet) {
            if (point.equals(elem)) {
                isIn = true;
                break;
            }
        }
        if (!isIn) {
            this.pointSet.add(point);
        }
        else {
            return;
        }

        // add the point to every existing segment, if it is in the same line as the segment itself, increasing its dimension
        for (Segment segment : this.segments) {
            if (isOnSameLine(segment, point)) {
                segment.addPoint(point);
            }
        }

        // add the point to every point in the whole set, creating every possible new two-points segments that is not subsegment of other existing segments
        for (Point elem : pointSet) {
            if (!point.equals(elem)) {

                // Check if the new two points segment is not a subsegment of an already existing one
                boolean addNew = true;
                for (Segment segment : this.segments) {
                    if (segment.contains(point) && segment.contains(elem)) {
                        addNew = false;
                        break;
                    }
                }
                if (addNew) {
                    Segment segment = new Segment();
                    segment.addPoint(elem);
                    segment.addPoint(point);
                    this.segments.add(segment);
                }
            }
        }

    }

    public List<Segment> getAllSegments() {
        return this.segments;
    }

    /**
     * Get all segments in the point set being at least <b>size<b/> points long
     * @param size
     * @return
     */
    public List<List<Point>> getAllSegmentsAtLeastNLong(int size) {
        List<List<Point>> retVal = new ArrayList<>();
        if (size > 1) {
            for (Segment segment : segments) {
                if (segment.getLength() >= size) {
                    retVal.add(segment.getPoints());
                }
            }
        }
        return retVal;
    }

    /**
     * Check if the given point is on the same line as the segment
     * @param segment
     * @param point
     * @return
     */
    private boolean isOnSameLine(Segment segment, Point point) {
        boolean retVal = false;

        Point pointOne = segment.getPoints().get(0);
        Point pointTwo = segment.getPoints().get(1);

        if (pointOne.getX().doubleValue() == pointTwo.getX().doubleValue()) {
            retVal = point.getX().doubleValue() == pointOne.getX().doubleValue();
        }
        else if (pointOne.getY().doubleValue() == pointTwo.getY().doubleValue()) {
            retVal = point.getY().doubleValue() == pointOne.getY().doubleValue();
        }
        else {
            Double ys = (point.getY() - pointOne.getY()) / (pointTwo.getY() - pointOne.getY());
            Double xs = (point.getX() - pointOne.getX()) / (pointTwo.getX() - pointOne.getX());
            // Using a little tolerance due to possible numeric calculation limits. Single point can be on the segment
            // but in some cases machine calculation can produce little computational error that put it outside.
            retVal = Math.abs(ys - xs) < TOLERANCE;
        }

        return retVal;
    }

}
