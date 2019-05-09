package by.vit.service.transportproblemsolve;


import java.util.List;
import java.util.Map;

/**
 * Basic interface of Solver
 */
public interface SolverOfTP {
    void setConditions(ConditionTP conditionTP);

    Map<Long, List<String>> getInterpretation();
}
