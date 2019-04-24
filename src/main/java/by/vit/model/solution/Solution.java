package by.vit.model.solution;

import by.vit.model.Car;
import by.vit.model.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DTO for solution of Transport problem
 */
public class Solution {

    private Map<Car, List<Route>> solution;

    public Map<Car, List<Route>> getSolution() {
        return solution;
    }

    public void setSolution(Map<Car, List<Route>> solution) {
        this.solution = solution;
    }

    /**
     * Put information to the field solution
     * @param car which will deliver the cargo
     * @param points the delivery points
     * @param masses the weight of cargo in tonnes
     * @param costs the cost of delivery
     */
    public void putCarRoute(Car car, List<Point> points, List<Double> masses, List<BigDecimal> costs) {
        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Route route = new Route();
            route.setPointFinish(points.get(i));
            route.setMass(masses.get(i));
            route.setCost(costs.get(i));
            routes.add(route);
        }
        solution.put(car,routes);
    }
}
