package co.edu.eafit.equations;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Methods {
    public static final String[] methods = {
            "Incremental Searches", //0
            "Bisection", //1
            "False Position", //2
            "Fixed Point", //3
            "Newton", //4
            "Secant", // 5
            "Multiple Roots", //6
            "Gaussian Elimination", //7
            "Cholesky LU", //8
            "Crout LU", //9
            "Doolittle", //10
            "Gauss Seidel", //11
            "Jacobi", //12
    };
    public static Expression function;
    public static Expression function_gx;
    public static Expression function_ddf;

    public static BigDecimal f(double x){
        double fx = function.setVariable("x", x).evaluate();
        return new BigDecimal(fx);
    }
    public static BigDecimal g(double x){
        double gx = function_gx.setVariable("x", x).evaluate();
        return new BigDecimal(gx);
    }
    public static BigDecimal ddf(double x){
        double ddf = function_ddf.setVariable("x", x).evaluate();
        return new BigDecimal(ddf);
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
                newRow.add("" + x1.setScale(10, BigDecimal.ROUND_HALF_UP));
                newRow.add("" + y1.setScale(10, BigDecimal.ROUND_HALF_UP));
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

    public static void Bisection(BigDecimal xinf,BigDecimal xsup,BigDecimal tol,long iter,int TipeE, Tabla tabla, String fx){
        DecimalFormat format = new DecimalFormat("#.###E0");
        function = new ExpressionBuilder(fx).variable("x").build();
        String answer = ">";
        BigDecimal yinf = f(xinf.doubleValue());
        BigDecimal ysup = f(xsup.doubleValue());
        BigDecimal zero = new BigDecimal("0.0");
        BigDecimal two = new BigDecimal("2.0");
        if(yinf.compareTo(zero)==0){
            tabla.setResult(xinf.toEngineeringString()+" IS a root");
        }else{
            if(ysup.compareTo(zero)==0){
                tabla.setResult(xsup.toEngineeringString() +" IS a root");
            }else{
                if(yinf.multiply(ysup).compareTo(zero)==1){
                    tabla.setResult("["+xinf.toString()+" , "+xsup.toString()+" ] is not a vlid interval");
                }else{
                    BigDecimal xmed = (xinf.add(xsup)).divide(two);
                    BigDecimal ymed = f(xmed.doubleValue());
                    long cont = 1;
                    BigDecimal error = tol.add(two);
                    while (ymed.compareTo(zero)!=0 && error.compareTo(tol) ==1 && cont < iter) {
                        if((yinf.multiply(ymed)).compareTo(zero) == -1){
                            xsup = xmed;
                            ysup = ymed;
                        }else{
                            xinf = xmed;
                            yinf = ymed;
                        }
                        BigDecimal aux = xmed;
                        xmed=(xinf.add(xsup)).divide(two);
                        ymed = f(xmed.doubleValue());
                        cont++;
                        if (TipeE == 1) { //Absolute Error
                            error = (xmed.subtract(aux)).abs();
                        }else{//Relative Error
                            error = new BigDecimal(((xmed.subtract(aux)).doubleValue())/xmed.doubleValue());
                            error = error.abs();
                        }
                        ArrayList<String> newRow = new ArrayList<String>();
                        newRow.add(Long.toString(cont));
                        newRow.add("" + xinf.setScale(10, BigDecimal.ROUND_HALF_UP));
                        newRow.add("" + xsup.setScale(10, BigDecimal.ROUND_HALF_UP));
                        newRow.add(""+ format.format(xmed));
                        newRow.add(""+ format.format(ymed));
                        newRow.add(""+ format.format(error));
                        tabla.addRow(newRow);
                    }
                    if(ymed.compareTo(zero)==0){
                        tabla.setResult("Xmed :"+xmed.toEngineeringString()+" IS a root");
                    }else {
                        if(error.compareTo(tol)==-1){
                            tabla.setResult("["+xmed.toEngineeringString()+"] Is an aproximation to a root with a " +
                                    "a tolerance of ["+tol.doubleValue()+"]");
                        }else{
                            tabla.setResult ("There weren't enough iterations");
                        }
                    }
                }
            }
        }
    }


    public static void falsePosition(BigDecimal xinf,BigDecimal xsup,BigDecimal tol,long iter,int TipeE, Tabla tabla, String fx){
        function = new ExpressionBuilder(fx).variable("x").build();
        DecimalFormat format = new DecimalFormat("#.###E0");
        String answer = ">";
        BigDecimal yinf = f(xinf.doubleValue());
        BigDecimal ysup = f(xsup.doubleValue());
        BigDecimal zero = new BigDecimal("0.0");
        BigDecimal two = new BigDecimal("2.0");
        if(yinf.compareTo(zero)==0){
            tabla.setResult(xinf.toString()+" IS a root");
        }else {
            if (ysup.compareTo(zero) == 0) {
                tabla.setResult(xsup.toString() + " IS a root");
            } else {
                if (yinf.multiply(ysup).compareTo(zero) == 1) {
                    tabla.setResult("[" + xinf.toString() + " , " + xsup.toString() + " ] is not a vlid interval");
                } else {
                    // BigDecimal xmed = xsup.subtract(ysup.multiply((xsup.subtract(xinf)).divide(ysup.subtract(yinf))));
                    BigDecimal xmed = new BigDecimal(xsup.doubleValue()-(ysup.doubleValue()*((xsup.doubleValue()-xinf.doubleValue())/
                            (ysup.doubleValue()-yinf.doubleValue()))));
                    BigDecimal ymed = f(xmed.doubleValue());
                    long cont = 1;
                    BigDecimal error = tol.add(two);
                    while (ymed.compareTo(zero) != 0 && error.compareTo(tol) == 1 && cont < iter) {
                        if ((yinf.multiply(ymed)).compareTo(zero) == -1) {
                            xsup =xmed;
                            ysup = ymed;
                        } else {
                            xinf =xmed;
                            yinf = ymed;
                        }
                        BigDecimal aux = xmed;
                        // BigDecimal xmed = xsup.subtract(ysup.multiply((xsup.subtract(xinf)).divide(ysup.subtract(yinf))));
                        xmed = new BigDecimal(xsup.doubleValue()-(ysup.doubleValue()*((xsup.doubleValue()-xinf.doubleValue())/
                                (ysup.doubleValue()-yinf.doubleValue()))));
                        ymed = f(xmed.doubleValue());
                        cont++;
                        if (TipeE == 1) { //Absolute Error
                            error = (xmed.subtract(aux)).abs();
                        } else {//Relative Error
                            error = new BigDecimal(((xmed.subtract(aux)).doubleValue())/xmed.doubleValue());
                            error = error.abs();
                        }
                        ArrayList<String> newRow = new ArrayList<String>();
                        newRow.add(Long.toString(cont));
                        newRow.add("" + xinf.setScale(10, BigDecimal.ROUND_HALF_UP));
                        newRow.add("" + xsup.setScale(10, BigDecimal.ROUND_HALF_UP));
                        newRow.add("" + format.format(xmed));
                        newRow.add("" + format.format(ymed));
                        newRow.add("" + format.format(error));
                        tabla.addRow(newRow);
                    }
                    if (ymed.compareTo(zero) == 0) {
                        tabla.setResult("Xmed :" + xmed.toString() + " IS a root");
                    } else {
                        if (error.compareTo(tol) == -1) {
                            tabla.setResult("[" + xmed.doubleValue() + "] Is an aproximation to a root with a " +
                                    "a tolerance of [" + tol.doubleValue() + "]");
                        } else {
                            tabla.setResult("There weren't enough iterations");
                        }
                    }
                }
            }
        }
    }

    public static void FixedPoint(BigDecimal xinf,BigDecimal tol,long iter, int TipeE, String fx,String gx,Tabla tabla){
        function = new ExpressionBuilder(fx).variable("x").build();
        function_gx = new ExpressionBuilder(gx).variable("x").build();
        DecimalFormat format = new DecimalFormat("#.###E0");
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal yx = f(xinf.doubleValue());
        long cont = 0;
        BigDecimal error = tol.add(new BigDecimal("1"));
        while (yx.compareTo(zero)!=0 && error.compareTo(tol)==1 && cont < iter){
            BigDecimal xn = g(xinf.doubleValue());
            yx = f(xn.doubleValue());
            if (TipeE == 1) { //Absolute Error
                error = (xn.subtract(xinf)).abs();
            } else {//Relative Error
                error = new BigDecimal(((xn.subtract(xinf)).doubleValue())/xn.doubleValue());
                error = error.abs();
            }
            ArrayList<String> newRow = new ArrayList<String>();
            newRow.add(Long.toString(cont));
            newRow.add("" + xn.setScale(10, BigDecimal.ROUND_HALF_UP));
            newRow.add("" + yx.setScale(10, BigDecimal.ROUND_HALF_UP));
            newRow.add("" + format.format(error));
            tabla.addRow(newRow);
            xinf = xn;
            cont++;
        }
        if (yx.compareTo(zero) == 0) {
            tabla.setResult("[" + xinf.doubleValue() + " IS a root");
        } else {
            if (error.compareTo(tol) == -1) {
                tabla.setResult("[" + xinf.doubleValue() + "] Is an aproximation to a root with a " +
                        "a tolerance of [" + tol.doubleValue() + "]");
            } else {
                tabla.setResult("There weren't enough iterations");
            }
        }

    }
    public static void Newton(BigDecimal xinf,BigDecimal tol,long iter, int TipeE, String fx,String dfx,Tabla tabla){
        function = new ExpressionBuilder(fx).variable("x").build();
        function_gx = new ExpressionBuilder(dfx).variable("x").build();
        DecimalFormat format = new DecimalFormat("#.###E0");
        BigDecimal zero = new BigDecimal("0.000");
        BigDecimal yx = f(xinf.doubleValue());
        BigDecimal dyx = g(xinf.doubleValue());
        long cont = 0;
        BigDecimal error = tol.add(new BigDecimal("1"));
        while (yx.compareTo(zero)!=0 && error.compareTo(tol)==1 && dyx.compareTo(zero)!=0 && cont < iter){
            BigDecimal xn = new BigDecimal(xinf.doubleValue()-yx.doubleValue()/dyx.doubleValue());
            yx = f(xn.doubleValue());
            dyx = g(xn.doubleValue());
            if (TipeE == 1) { //Absolute Error
                error = (xn.subtract(xinf)).abs();
            } else {//Relative Error
                error = new BigDecimal(((xn.subtract(xinf)).doubleValue())/xn.doubleValue());
                error = error.abs();
            }
            ArrayList<String> newRow = new ArrayList<String>();
            newRow.add(Long.toString(cont));
            newRow.add("" + xn.setScale(10, BigDecimal.ROUND_HALF_UP));
            newRow.add("" + yx.setScale(10, BigDecimal.ROUND_HALF_UP));
            newRow.add("" + format.format(dyx));
            newRow.add("" + format.format(error));
            tabla.addRow(newRow);
            xinf = xn;
            cont++;
        }
        if (yx.compareTo(zero) == 0) {
            tabla.setResult("[" + xinf.doubleValue() + " IS a root");
        } else {
            if (error.compareTo(tol) == -1) {
                tabla.setResult("[" + xinf.doubleValue() + "] Is an aproximation to a root with a " +
                        "a tolerance of [" + tol.doubleValue() + "]");
            } else {
                if(dyx.compareTo(zero) == 0){
                    tabla.setResult("The derivate is zero");
                }else {
                    tabla.setResult("There weren't enough iterations");
                }
            }
        }

    }

    public static void Secant(BigDecimal x0,BigDecimal x1,BigDecimal tol, long iter, int TipeE, String fx,Tabla tabla){
        function = new ExpressionBuilder(fx).variable("x").build();
        DecimalFormat format = new DecimalFormat("#.###E0");
        BigDecimal fx0 = f(x0.doubleValue());
        BigDecimal zero = new BigDecimal("0.000");
        if (fx0.compareTo(zero)==0){
            tabla.setResult(x0.doubleValue()+" Is a root");
        }else{
            BigDecimal fx1 = f(x1.doubleValue());
            long cont = 0;
            BigDecimal error = tol.add(new BigDecimal("1.0"));
            BigDecimal d = fx1.subtract(fx0);
            while (fx1.compareTo(zero)!=0 && error.compareTo(tol)==1 && d.compareTo(zero)!=0 && cont < iter){
                BigDecimal xn = new BigDecimal(x1.doubleValue()-(fx1.doubleValue()*
                        (x1.doubleValue()-x0.doubleValue())/d.doubleValue()));
                if (TipeE == 1) { //Absolute Error
                    error = (x1.subtract(x0)).abs();
                } else {//Relative Error
                    error = new BigDecimal(((x1.subtract(x0)).doubleValue())/x1.doubleValue());
                    error = error.abs();
                }
                ArrayList<String> newRow = new ArrayList<String>();
                newRow.add(Long.toString(cont));
                newRow.add("" + xn.setScale(10, BigDecimal.ROUND_HALF_UP));
                newRow.add("" + format.format(fx1));
                newRow.add("" + format.format(error));
                tabla.addRow(newRow);
                x0=x1;
                x1=xn;
                fx0=fx1;
                fx1 = f(x1.doubleValue());
                d = fx1.subtract(fx0);
                cont++;
            }
            if (fx1.compareTo(zero) == 0) {
                tabla.setResult("[" + x1.doubleValue() + " IS a root");
            } else {
                if (error.compareTo(tol) == -1) {
                    tabla.setResult("[" + x1.doubleValue() + "] Is an aproximation to a root with a " +
                            "a tolerance of [" + tol.doubleValue() + "]");
                } else {
                    if(d.compareTo(zero) == 0){
                        tabla.setResult("There is a possible multiple root");
                    }else {
                        tabla.setResult("There weren't enough iterations");
                    }
                }
            }

        }
    }

    public static void MultipleRoots(BigDecimal x0, BigDecimal tol, long iter,
                                     int TipeE, String fx, String dfx, String ddfx, Tabla tabla ){
        function = new ExpressionBuilder(fx).variable("x").build();
        function_gx = new ExpressionBuilder(dfx).variable("x").build();
        function_ddf = new ExpressionBuilder(ddfx).variable("x").build();
        DecimalFormat format = new DecimalFormat("#.###E0");
        BigDecimal zero = new BigDecimal("0.0");
        BigDecimal yx = f(x0.doubleValue());
        BigDecimal dyx = g(x0.doubleValue());
        BigDecimal ddyx= ddf(x0.doubleValue());
        BigDecimal den = new BigDecimal(Math.pow(dyx.doubleValue(), 2)-(yx.doubleValue()*ddyx.doubleValue()));
        BigDecimal error = tol.add(new BigDecimal("1"));
        int cont=0;
        while (yx.compareTo(zero)!=0 && error.compareTo(tol)==1 && den.compareTo(zero)!=0 && cont < iter){
            BigDecimal mul = yx.multiply(dyx);
            BigDecimal xn = new BigDecimal(x0.doubleValue()-(mul.doubleValue()/den.doubleValue()));
            yx = f(xn.doubleValue());
            dyx = g(xn.doubleValue());
            ddyx= ddf(xn.doubleValue());
            ArrayList<String> newRow = new ArrayList<String>();
            newRow.add(Long.toString(cont));
            newRow.add("" + xn.setScale(10, BigDecimal.ROUND_HALF_UP));
            newRow.add("" + format.format(yx));
            newRow.add("" + format.format(dyx));
            newRow.add("" + format.format(ddyx));
            newRow.add("" + format.format(error));
            tabla.addRow(newRow);
            if (TipeE == 1) { //Absolute Error
                error = (xn.subtract(x0)).abs();
            } else {//Relative Error
                error = new BigDecimal(((xn.subtract(x0)).doubleValue())/xn.doubleValue());
                error = error.abs();
            }
            x0 = xn;
            den = new BigDecimal(Math.pow(dyx.doubleValue(), 2)-(yx.doubleValue()*ddyx.doubleValue()));
            cont++;
        }
        if (yx.compareTo(zero) == 0) {
            tabla.setResult("[" + x0.doubleValue() + " IS a root");
        } else {
            if (error.compareTo(tol) == -1) {
                tabla.setResult("[" + x0.doubleValue() + "] Is an aproximation to a root with a " +
                        "a tolerance of [" + tol.doubleValue() + "]");
            } else {
                if(den.compareTo(zero) == 0){
                    tabla.setResult("Deneominator is zero");
                }else {
                    tabla.setResult("There weren't enough iterations");
                }
            }
        }
    }


}