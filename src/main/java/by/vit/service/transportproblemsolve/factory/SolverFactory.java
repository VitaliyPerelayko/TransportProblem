package by.vit.service.transportproblemsolve.factory;

import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.impl.CLPSolverImpl;
import by.vit.service.transportproblemsolve.SolverOfTP;

public class SolverFactory {
    public enum Type {CLP}



    public static SolverOfTP createSolver(Type type, ConditionTP conditionTP){
        SolverOfTP solver = new CLPSolverImpl(conditionTP);
        return solver;
    }
}
