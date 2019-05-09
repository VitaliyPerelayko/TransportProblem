package by.vit.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * DTO for response and request entity CarModel
 */
public class CarModelDTO {

    private Long id;

    @NotNull(message = "carModel.name.notNull")
    @NotEmpty(message = "carModel.name.notEmpty")
    @Size(min = 1, max = 50, message = "carModel.name.size")
    private String name;

    @NotNull(message = "carModel.tonnage.notNull")
    @Positive(message = "carModel.tonnage.notPositive")
    private Double tonnage;

    @NotNull(message = "carModel.space.notNull")
    @Positive(message = "carModel.space.notPositive")
    private Double space;

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

    public Double getTonnage() {
        return tonnage;
    }

    public void setTonnage(Double tonnage) {
        this.tonnage = tonnage;
    }

    public Double getSpace() {
        return space;
    }

    public void setSpace(Double space) {
        this.space = space;
    }
}
