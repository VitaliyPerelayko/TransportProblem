package by.vit.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PointRequestDto {

    private Long id;

    @NotNull(message = "{point.name.notNull}")
    @NotEmpty(message = "{point.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.name.size}")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
