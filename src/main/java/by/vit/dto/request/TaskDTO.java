package by.vit.dto.request;

import by.vit.dto.PointDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class TaskDTO {
    @NotNull(message = "{task.pointDTOList.notNull}")
    private List<
            @NotNull(message = "{task.pointDTO.notNull}")
            PointDTO> pointDTOList;
    @NotNull(message = "{task.orderList.notNull}")
    private List<
            @NotNull(message = "{task.order.notNull}")
            @Positive(message = "{task.order.positive}")
            Double> orderList;

    public List<PointDTO> getPointDTOList() {
        return pointDTOList;
    }

    public void setPointDTOList(List<PointDTO> pointDTOList) {
        this.pointDTOList = pointDTOList;
    }

    public List<Double> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Double> orderList) {
        this.orderList = orderList;
    }
}
