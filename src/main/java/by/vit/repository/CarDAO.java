package by.vit.repository;

import by.vit.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarDAO extends JpaRepository<Car,Long> {

    /**
     * Find car where carModel has tonnage bigger then mass
     * @param mass tonnage of car
     * @return
     */
    @Query("FROM Car car JOIN FETCH car.carModel WHERE car.carModel.tonage > :mass")
    List<Car> findCarsByTonnageAfter(Double mass);
}
