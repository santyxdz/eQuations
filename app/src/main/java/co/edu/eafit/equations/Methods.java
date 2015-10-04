package co.edu.eafit.equations;

import java.math.BigDecimal;
import java.util.ArrayList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Methods {
    public static Expression function;
    public static BigDecimal f(double x){
        double fx = function.setVariable("x", x).evaluate();
        return new BigDecimal(fx);
    }
    public static void IncrementalSearches(BigDecimal x0, BigDecimal  delta, long iter, Tabla tabla, String fx){
        function = new ExpressionBuilder(fx).variable("x").build();
        BigDecimal y0 = f(x0.doubleValue());
        BigDecimal cero = new BigDecimal("0.0");
        if (y0.compareTo(cero)==0) {
            tabla.setResult(x0.toString() + " Is a root");
        }else{
            BigDecimal x1 = x0.add(delta);
            BigDecimal y1 = f(x1.doubleValue());
            long cont = 1;
            while (((y0.multiply(y1)).compareTo(cero)==1) && (y1.compareTo(cero)!=0) && (cont < iter)){
                x0 = x1;
                y0 = y1;
                x1 = x0.add(delta);
                y1 = f(x1.doubleValue());
                ArrayList<String> newRow = new ArrayList<String>();
                newRow.add(Long.toString(cont));
                newRow.add("" + x1.doubleValue());
                newRow.add("" + y1.setScale(5, BigDecimal.ROUND_HALF_UP));
                tabla.addRow(newRow);
                cont++;
            }
            if(y1.compareTo(cero)==0){
                tabla.setResult(x1.toString() + " Is a root");
            }else{
                if((y0.multiply(y1)).compareTo(cero)==-1){//menor que cero
                    tabla.setResult(x1.toString()
                            +" There's a root between x0 and x1 in the interval ["
                            +x0.toString()+","+x1.toString()+"]");
                }else{
                    tabla.setResult(" No root found");
                }
            }
        }

    }

}
