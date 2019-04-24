package by.vit.dto.response;

import by.vit.dto.PointDTO;

/**
 * DTO for response entity Road
 */
public class RoadResponseDTO {
    private PointDTO point1;
    private PointDTO point2;
    private Double distance;

    public PointDTO getPoint1() {
        return point1;
    }

    public void setPoint1(PointDTO point1) {
        this.point1 = point1;
    }

    public PointDTO getPoint2() {
        return point2;
    }

    public void setPoint2(PointDTO point2) {
        this.point2 = point2;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
