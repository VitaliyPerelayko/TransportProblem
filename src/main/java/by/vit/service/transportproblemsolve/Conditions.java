package by.vit.service.transportproblemsolve;

import by.vit.model.Car;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class contains information to solve transport problem.
 * The initial data is processed for substitution in the classical transport problem.
 */
public interface Conditions {

    /**
     * Set parameters.
     *
     * @param cars list of cars from start point
     * @param order array of order: the weight of the cargo to be delivered to each point
     * @param flagOfPseudoPoint need whether fictitious delivery point
     * @param difference the weight of the cargo to be delivered to fictitious point
     */
    void setParam(List<Car> cars, Double[] order, boolean flagOfPseudoPoint, Double difference);

    /**
     * Restrictions for supplier in classical transport problem
     * How many cargo supplier can delivers
     *
     * @return Restrictions
     */
    Double[] getRestrictionOfCars();

    /**
     * Restrictions for consumer in classical transport problem
     * How many cargo consumer needs
     *
     * @return Restrictions
     */
    Double[] getRestrictionOfOrder();

    /**
     * The matrix contains the cost of delivery of goods from each point to each on each car.
     * Example (p point, c car):
     *     p2c1  p2c2  p2c3   P3c1  p3c2  p3c3   P4c1  p4c2  p4c3  p2   p3   p4
     * p1c1
     * p1c2
     * p1c3
     * p2c1
     * p2c2           SOME NUMBERS
     * p2c3
     * p3c1
     * p3c2
     * p3c3
     * p4c1
     * p4c2
     * p4c3
     *
     *
     * @return cost matrix
     */
    BigDecimal[][] getCostMatrix();

    /**
     * @return array of car's ids
     */
    Long[] getCarsId();

    /**
     * @return  array of point's ids
     */
    Long[] getPointsId();
}
