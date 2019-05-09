package by.vit.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class for the composite key of entity Road.
 */
@Embeddable
public class RoadId implements Serializable {

    private Long point1Id;

    private Long point2Id;

    public RoadId() {
    }

    public RoadId(Long point1Id, Long point2Id) {
        this.point1Id = point1Id;
        this.point2Id = point2Id;
    }

    public Long getPoint1Id() {
        return point1Id;
    }

    public void setPoint1Id(Long point1Id) {
        this.point1Id = point1Id;
    }

    public Long getPoint2Id() {
        return point2Id;
    }

    public void setPoint2Id(Long point2Id) {
        this.point2Id = point2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoadId)) return false;
        RoadId roadId = (RoadId) o;
        return (Objects.equals(getPoint1Id(), roadId.getPoint1Id()) &&
                Objects.equals(getPoint2Id(), roadId.getPoint2Id()))||
                (Objects.equals(getPoint1Id(), roadId.getPoint2Id()) &&
                        Objects.equals(getPoint2Id(), roadId.getPoint1Id()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPoint1Id(), getPoint2Id());
    }

    @Override
    public String toString() {
        return "RoadId{" +
                "point1Id=" + point1Id +
                ", point2Id=" + point2Id +
                '}';
    }
}
