package by.vit.service.transportproblemsolve;


import java.util.List;
import java.util.Map;

/**
 * Basic interface of Solver
 */
public interface SolverOfTP {
    Map<Long, List<String>> getInterpretation();
}
