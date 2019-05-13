package by.vit.service.transportproblemsolve;


import java.util.List;
import java.util.Map;

/**
 * Basic interface of Solver
 */
public interface SolverOfTP {

    /**
     * get interpretation of solution:
     * carId it's id of car which will make deliver
     * Each car has own route:
     *  Delivery point (pointId) 1st in List<String>
     *  Mass of cargo (mass) 2nd in List<String>
     *  Cost ot this route (cost) 3th in List<String>
     *
     * @return Map < Long carId, List< Long pointId, Double mass, BigDecimal cost > >
     */
    Map<Long, List<String>> getInterpretation();
}
