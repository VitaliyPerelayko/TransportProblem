package by.vit.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Request DTO. Conditions for transport problem.
 */
public class TaskDTO {
    /**
     * List of points id.
     * First point is point from which deliver will start.
     * The others it's delivery points.
     */
    @NotNull(message = "task.points.notNull")
    private List<
            @NotNull(message = "task.pointName.notNull")
            @NotEmpty(message = "task.pointName.notEmpty")
                    String> pointName;

    /**
     * Mass of cargo, which we need to deliver to each delivery point.
     */
    @NotNull(message = "task.orderList.notNull")
    private List<
            @NotNull(message = "task.order.notNull")
            @Positive(message = "task.order.positive")
                    Double> orderList;

    /**
     * Username of user who has asked for solution
     */
    @NotNull(message = "task.username.notNull")
    @NotEmpty(message = "task.username.notEmpty")
    private String username;

    public List<String> getPointName() {
        return pointName;
    }

    public void setPointName(List<String> pointName) {
        this.pointName = pointName;
    }

    public List<Double> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Double> orderList) {
        this.orderList = orderList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
