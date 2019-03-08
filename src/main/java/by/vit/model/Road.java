package by.vit.model;

import by.vit.model.compositekey.RoadId;

import javax.persistence.*;

@Entity
@Table(name = "road")
public class Road {
    @EmbeddedId
    private RoadId id;

    @MapsId("point1Id")
    @ManyToOne
    @JoinColumn(name = "point1_id",referencedColumnName = "id")
    private Point point1;


    @MapsId("point2Id")
    @ManyToOne
    @JoinColumn(name = "point2_id",referencedColumnName = "id")
    private Point point2;

    private Double distance;

    public Road(Point point1, Point point2, Double distance) {
        id = new RoadId(point1.getId(),point2.getId());
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
    }

    public Road() {
    }

    public RoadId getId() {
        return id;
    }

    public void setId(RoadId id) {
        this.id = id;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
