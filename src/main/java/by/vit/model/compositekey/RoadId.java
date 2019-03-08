package by.vit.model.compositekey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class RoadId implements Serializable {

    @Column
    private Long point1Id;
    @Column
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

    public Long getPoint2Id() {
        return point2Id;
    }

    public void setPoint1Id(Long point) {
        point1Id = point;
    }

    public void setPoint2Id(Long point){
        point2Id = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoadId)) return false;
        RoadId roadId = (RoadId) o;
        return Objects.equals(getPoint1Id(), roadId.getPoint1Id()) &&
                Objects.equals(getPoint2Id(), roadId.getPoint2Id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPoint1Id(), getPoint2Id());
    }
}
