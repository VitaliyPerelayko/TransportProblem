package by.vit.model.solution;

import by.vit.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class for the entity Solution. It's solution table in database
 */
@Entity
@Table(name = "solution")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    @NotNull(message = "solution.dateTime.notNull")
    @PastOrPresent(message = "solution.dateTime.notFuture")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "solution.supplier.notNull")
    private User supplier;

    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SolutionCar> carSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getSupplier() {
        return supplier;
    }

    public void setSupplier(User supplier) {
        this.supplier = supplier;
    }

    public Set<SolutionCar> getCarSet() {
        return carSet;
    }
}
