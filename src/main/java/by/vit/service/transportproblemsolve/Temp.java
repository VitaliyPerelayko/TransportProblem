package by.vit.service.transportproblemsolve;

import com.quantego.clp.CLP;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;

public class Temp {
    public static void main(String[] args) {
        CLP solver = new CLP();
        CLPVariable x = solver.addVariable();
        CLPVariable y = solver.addVariable();

        solver.setObjectiveCoefficient(x,1);
        solver.setObjectiveCoefficient(y,1);

        CLPExpression ex1 = solver.createExpression();
        CLPExpression ex2 = solver.createExpression();
        ex1.add(x,1);
        ex2.add(y,1);
        ex1.geq(0);
        ex2.geq(0);
        ex1.leq(4);
        ex2.leq(5);

        solver.maximize();

        System.out.println(x.getSolution());
        System.out.println(y.getSolution());
    }
}
