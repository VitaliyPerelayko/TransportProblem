package by.vit.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO for request entity Road
 */
public class RoadRequestDTO {

    @NotNull(message = "road.pointId.notNull")
    private Long point1id;

    @NotNull(message = "road.pointId.notNull")
    private Long point2id;

    @NotNull(message = "road.distance.notNull")
    @Positive(message = "road.distance.positive")
    private Double distance;

    public Long getPoint1id() {
        return point1id;
    }

    public void setPoint1id(Long point1id) {
        this.point1id = point1id;
    }

    public Long getPoint2id() {
        return point2id;
    }

    public void setPoint2id(Long point2id) {
        this.point2id = point2id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
