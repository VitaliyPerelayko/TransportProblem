package by.vit.repository;

import by.vit.model.Car;
import by.vit.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository layer for Cars
 */

public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Find car where carModel has tonnage bigger then mass
     *
     * @param mass tonnage of car
     * @return List<Car>
     */
    @Query("FROM Car car JOIN FETCH car.carModel WHERE car.carModel.tonnage > :mass")
    List<Car> findCarsByTonnageAfter(@Param("mass") Double mass);

    /**
     * find all cars in given point
     *
     * @param point point, in which we want find all cars
     * @return list of car
     */
    List<Car> findAllByPoint(Point point);
}
