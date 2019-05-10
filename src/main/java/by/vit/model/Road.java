package by.vit.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Class for the entity Road. It's road table in database
 */
@Entity
@Table(name = "road")
public class Road {
    @EmbeddedId
    private RoadId id;

    @MapsId("point1Id")
    @ManyToOne
    @JoinColumn(name = "point1_id", referencedColumnName = "id")
    private Point point1;


    @MapsId("point2Id")
    @ManyToOne
    @JoinColumn(name = "point2_id", referencedColumnName = "id")
    private Point point2;

    @NotNull(message = "road.distance.notNull")
    @Positive(message = "road.distance.positive")
    private Double distance;

    public Road(Point point1, Point point2, Double distance) {
        id = new RoadId();
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
    }

    public Road() {
        id = new RoadId();
    }

    public Long getPoint1id(){
        return this.id.getPoint1Id();
    }

    public Long getPoint2id(){
        return this.id.getPoint1Id();
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
