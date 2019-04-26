package by.vit.service.transportproblemsolve.impl;


import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.SolverOfTP;
import com.quantego.clp.CLP;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * CLP implementation of SolverOfTP.
 * It uses CLP java solver of liner programming
 */
public class CLPSolverImpl implements SolverOfTP {

    private Double[][] matrixOfSolve;
    private Map<Long, List<String>> interpretation;

    public CLPSolverImpl(ConditionTP conditionTP) {
        solveTP(conditionTP);
        getInterpretation(conditionTP);
    }

    public Double[][] getMatrixOfSolve() {
        return matrixOfSolve;
    }

    /**
     * @return Map < Long carId, List< Long pointId,Double mass, BigDecimal cost > >
     */
    @Override
    public Map<Long, List<String>> getInterpretation() {
        return interpretation;
    }

    private void getInterpretation(ConditionTP conditionTP) {
        Long[] carsId = conditionTP.getCarsId();
        Long[] pointsId = conditionTP.getPointsId();
        BigDecimal[][] costMatrix = conditionTP.getCostMatrix();

        int n = pointsId.length + 1;
        int s = carsId.length * pointsId.length;
        Double zero = 0D;

        interpretation = new TreeMap<>();

        for (int a = 0; a < carsId.length; a++) {
            for (int i = a * n; i < (a + 1) * n; i++) {
                for (int j = 0; j < n - 1; j++) {
                    Double tonne = matrixOfSolve[i][j + s];
                    BigDecimal cost = null;
                    if (costMatrix[i][j + s].equals(BigDecimal.ZERO)) {
                        cost = costMatrix[i][j + s];
                    } else {
                        cost = costMatrix[a * n][j * carsId.length + a];
                    }

                    if (!tonne.equals(zero)) {
                        if (interpretation.get((long) a) == null) {
                            interpretation.put((long) a, new ArrayList<>());
                        }
                        List<String> infoForCar = interpretation.get((long) a);
                        infoForCar.add(pointsId[j].toString());
                        infoForCar.add(tonne.toString());
                        infoForCar.add(cost.multiply(BigDecimal.valueOf(tonne)).toString());
                    }
                }
            }
        }

    }


    private void solveTP(ConditionTP conditionTP) {
        BigDecimal[][] costMatrix = conditionTP.getCostMatrix();
        Double[] restrictionOfCar = conditionTP.getRestrictionOfCars();
        Double[] restrictionOfOrder = conditionTP.getRestrictionOfOrder();
        int rowsLength = costMatrix[0].length;
        int linesLength = costMatrix.length;


        CLP solver = new CLP();

        //Create variable x[i][j]
        CLPVariable[][] variables = new CLPVariable[linesLength][rowsLength];
        // Create restriction for cars
        CLPExpression[] constraintsCar = new CLPExpression[restrictionOfCar.length];
        // Create restriction for order
        CLPExpression[] constraintsOrder = new CLPExpression[restrictionOfOrder.length];

        for (int i = 0; i < linesLength; i++) {
            Double bound = restrictionOfCar[i];
            constraintsCar[i] = solver.createExpression();
            for (int j = 0; j < rowsLength; j++) {
                if (constraintsOrder[j] == null) {
                    constraintsOrder[j] = solver.createExpression();
                }
                //variable
                variables[i][j] = solver.addVariable().bounds(0D, bound);
                //coefficient
                solver.setObjectiveCoefficient(variables[i][j], costMatrix[i][j].doubleValue());
                //restriction
                constraintsCar[i].add(variables[i][j], 1);
                constraintsOrder[j].add(variables[i][j], 1);
            }
        }
        for (int i = 0; i < restrictionOfCar.length; i++) {
            Double tonnage = restrictionOfCar[i];
            constraintsCar[i].eq(tonnage);
        }

        for (int i = 0; i < restrictionOfOrder.length; i++) {
            Double order = restrictionOfOrder[i];
            constraintsOrder[i].eq(order);
        }

        solver.minimize();
        matrixOfSolve = new Double[linesLength][rowsLength];
        for (int i = 0; i < linesLength; i++) {
            for (int j = 0; j < rowsLength; j++) {
                matrixOfSolve[i][j] = variables[i][j].getSolution();
            }
        }
    }
}
