package it.my.linesegment.http.rest;

import it.my.linesegment.logic.LineSegmentContainer;
import it.my.linesegment.model.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApi {

    @GetMapping("space")
    public ResponseEntity<List<Point>> space() {
        LineSegmentContainer base =LineSegmentContainer.getInstance();
        List<Point> result = base.getPointSet();
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("lines/{length}")
    public ResponseEntity<List<List<Point>>> getAllSegmentsAtLeastNLength(@PathVariable(name = "length") int length) {
        LineSegmentContainer base = LineSegmentContainer.getInstance();
        List<List<Point>> result = base.getAllSegmentsAtLeastNLong(length);
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("point")
    public ResponseEntity<Point> addPoint(@RequestBody Point point) {
        LineSegmentContainer base = LineSegmentContainer.getInstance();
        base.addPoint(point);
        return ResponseEntity.ok(point);
    }

    @DeleteMapping("space")
    public ResponseEntity<Object> deleteAllPoints() {
        LineSegmentContainer base = LineSegmentContainer.getInstance();
        base.resetPointSet();
        return ResponseEntity.noContent().build();
    }

}
