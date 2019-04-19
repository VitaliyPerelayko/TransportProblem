package by.vit.dto.request;

import javax.validation.constraints.NotNull;

public class RoadRequestDTO {
    @NotNull()
    private Long point1id;

    private Long point2id;

    private Double distance;
}
