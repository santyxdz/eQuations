package co.edu.eafit.equations.sections;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.ejml.simple.SimpleMatrix;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import co.edu.eafit.equations.Tabla;

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
            "Cholesky", //8
            "Crout", //9
            "Doolittle", //10
            "Gauss Seidel", //11
            "Jacobi", //12
    };
    public static Expression function;
    public static Expression function_gx;
    public static Expression function_ddf;
    public static int numchar = 10;
    public static int precision = 10;
    public static Boolean stop = Boolean.FALSE;

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

    public static SimpleMatrix sustitucionRegresiva(SimpleMatrix Ab){
        int n = Ab.numRows();
        SimpleMatrix x = new SimpleMatrix(n,1);
        n--;
        x.set(n, 0, Ab.get(n, n + 1) / Ab.get(n, n));
        for(int i=n; i>-1; i--){
            double sum = 0;
            for(int p=i+1; p<=n; p++){
                sum+=Ab.get(i, p)*x.get(p, 0);
            }
            x.set(i, 0, (Ab.get(i, n+1)-sum)/Ab.get(i,i));
        }
        return x;
    }

    public static SimpleMatrix sustitucionRegresiva(SimpleMatrix U,
                                                    SimpleMatrix z){
        int n = U.numCols();
        SimpleMatrix x = new SimpleMatrix(n,1);
        for(int i = n-1; i>=0; i--){
            double suma = 0;
            for(int j = i+1; j<n; j++){
                suma+=U.get(i,j)*x.get(j,0);
            }
            x.set(i,0,(z.get(i,0)-suma)/U.get(i,i));
        }
        return x;
    }

    public static SimpleMatrix sustitucionProgresiva(SimpleMatrix L,
                                                     SimpleMatrix b){
        int n = L.numCols();
        SimpleMatrix x = new SimpleMatrix(n,1);
        for(int i = 1; i < n+1; i++){
            double suma = 0;
            for(int p = i-1; p > 0; p--){
                suma+=L.get(i-1,p-1) * x.get(p-1,0);
            }
            x.set(i-1,0,(b.get(i-1,0)-suma)/L.get(i-1,i-1));
        }
        return x;
    }

    public static SimpleMatrix eliminacionGaussiana(SimpleMatrix A,
                                                    SimpleMatrix b){
        int n = b.numRows();
        SimpleMatrix Ab = new SimpleMatrix(A.numRows(),A.numCols()+1);
        Ab.insertIntoThis(0, 0, A);
        Ab.insertIntoThis(0, A.numCols(), b);
        Ab.print(numchar, precision);
        for(int k = 0; k < n-1; k++){
            for(int i = k+1; i < n; i++){
                double m = Ab.get(i, k)/Ab.get(k, k);
                for(int j = k; j < n+1; j++){
                    Ab.set(i, j, Ab.get(i, j)-(m*Ab.get(k,j)));
                }
            }
        }
        return Ab;
    }
    //----------------------------------------------------------------------------------

    public static String factorizacionLUCrout(SimpleMatrix A, SimpleMatrix b){
        int n= A.numCols();
        SimpleMatrix L = SimpleMatrix.identity(n);
        SimpleMatrix U = SimpleMatrix.identity(n);
        for(int k = 1; k < n+1; k++){
            double suma = 0;
            for(int p = 0; p < k-1; p++){
                suma += L.get(k-1,p)*U.get(p,k-1);
            }
            L.set(k-1,k-1, A.get(k-1,k-1)-suma);
            for(int i = k+1; i < n+1; i++){
                suma = 0;
                for(int p = 0; p < k-1; p++){
                    suma += L.get(i - 1, p) * U.get(p,k-1);
                }
                L.set(i - 1, k - 1, (A.get(i - 1, k - 1)-suma)/U.get(k - 1, k - 1));
            }
            for (int j = k+1; j < n+1; j++){
                suma = 0;
                for (int p = 0; p < k-1; p++){
                    suma += L.get(k-1,p)*U.get(p,j-1);
                }
                U.set(k-1,j- 1, (A.get(k - 1, j -1)-suma)/L.get(k-1,k-1));
            }
        }
        String res = "";
        res += "Matrix L:\n";

        L.print(numchar, precision);
        res += L.toString();
        res += "\nMatrix U:\n";
        U.print(numchar,precision);
        res+= U.toString();
        SimpleMatrix z = sustitucionProgresiva(L, b);
        res+= "\nVector z:\n";
        z.print(numchar,precision);
        String xx = "";
        for(int k=0;k<z.getNumElements();k++)
          xx +=  "z"+(k+1)+" = "+z.get(k)+"\n";
        res += xx;//z.toString();
        SimpleMatrix x = sustitucionRegresiva(U, z);
        res += "\nVector x:\n";
        x.print(numchar, precision);
        //res += x.toString();
        String vecre = "";
        for(int k=0;k<x.getNumElements();k++)
          vecre +=  "X"+(k+1)+" = "+x.get(k)+"\n";
        res+= vecre;
        return res;
    }
    //----------------------------------------------------------------------------------
    public static String factorizacionLUDoolittle(SimpleMatrix A, SimpleMatrix b){
        int n = A.numCols();
        String res = "";
        SimpleMatrix L = SimpleMatrix.identity(n);
        SimpleMatrix U = SimpleMatrix.identity(n);
        for(int k = 1; k < n+1; k++){
            double suma = 0;
            for(int p = 0; p < k-1; p++){
                suma += L.get(k-1,p)*U.get(p,k-1);
            }
            U.set(k-1,k-1,(A.get(k-1,k-1)-suma)/L.get(k-1,k-1));
            for(int j = k+1; j < n+1; j++){
                suma = 0;
                for(int p = 0; p < k-1; p++){
                    suma += L.get(k-1,p)*U.get(p,j-1);
                }
                U.set(k-1,j-1,(A.get(k-1,j-1)-suma)/L.get(k-1,k-1));
            }
            for(int i = k+1; i < n+1; i++){
                suma = 0;
                for(int p = 0; p < k-1; p++){
                    suma += L.get(i-1,p)*U.get(p,k-1);
                }
                L.set(i-1,k-1,(A.get(i-1,k-1)-suma)/U.get(k-1,k-1));
            }
        }
        System.out.println("\nMatrix L:");
        L.print(numchar, precision);
        System.out.println("\nMatrix U:");
        U.print(numchar, precision);
        SimpleMatrix z = sustitucionProgresiva(L, b);
        System.out.println("\nVector z:");
        z.print(numchar, precision);
        SimpleMatrix x = sustitucionRegresiva(U, z);
        System.out.println("\nVector x:");
        x.print(numchar, precision);
        return res;
    }


    //----------------------------------------------------------------------------------
    public static SimpleMatrix eliminacionGaussianaConPivoteoParcial(SimpleMatrix A,
                                                                     SimpleMatrix b){
        int n = A.numRows();
        SimpleMatrix Ab = new SimpleMatrix(A.numRows(),A.numCols()+1);
        Ab.insertIntoThis(0, 0, A);
        Ab.insertIntoThis(0, A.numCols(), b);
        for(int k = 1; k < n; k++){
            pivoteoParcial(Ab, k);
            for(int i = k+1; i < n+1; i++){
                double multiplicador = Ab.get(i-1,k-1)/Ab.get(k-1,k-1);
                for(int j = k ; j < n+2; j++){
                    Ab.set(i-1,j-1,Ab.get(i-1,j-1)-multiplicador*Ab.get(k-1,j-1));
                }
            }
        }
        SimpleMatrix x = new SimpleMatrix(n,1);
        for(int i = n; i>0;i--){
            double sumatoria = 0;
            for(int p = i+1; p <= n; p++){
                sumatoria = sumatoria + Ab.get(i-1,p-1)*x.get(p-1,0);
            }
            x.set(i-1,0,(Ab.get(i-1,n)-sumatoria)/Ab.get(i-1,i-1));
        }
        return Ab;
    }

    public static SimpleMatrix pivoteoParcial(SimpleMatrix A, int k){
        int n = A.numRows();
        double elementoMayor = Math.abs(A.get(k-1,k-1));
        int filaMayor = k-1;
        for(int s = k-1; s < n; s++){
            double nuevoElemento = Math.abs(A.get(s,k-1));
            if( nuevoElemento > elementoMayor){
                elementoMayor =  Math.abs(A.get(s,k-1));
                filaMayor = s;
            }
        }
        if(elementoMayor != 0){
            if(filaMayor != k-1){
                for(int i = 0; i < A.numCols(); i++){
                    double aux = A.get(k-1,i);
                    A.set(k-1,i,A.get(filaMayor,i));
                    A.set(filaMayor,i,aux);
                }
            }
        }
        return A;
    }
    //-----------------------------------------------------------------------------------
    public static SimpleMatrix crearMarcas(int n){
        SimpleMatrix marcas = new SimpleMatrix(n,1);
        for(int i = 0; i < n; i++){
            marcas.set(i,0,i+1);
        }
        return marcas;
    }
    public static SimpleMatrix pivoteoTotal(SimpleMatrix Ab,
                                            SimpleMatrix marcas, int k, int n){
        double mayor = 0;
        int filaMayor = k-1;
        int columnaMayor = k-1;
        for(int r = k-1; r < n; r++){
            for(int s = k-1; s < n; s++){
                if(Math.abs(Ab.get(r,s)) > mayor){
                    mayor = Math.abs(Ab.get(r,s));
                    filaMayor = r;
                    columnaMayor = s;
                }
            }
        }
        if(mayor == 0){
            stop = Boolean.TRUE;
        }else{
            stop = Boolean.FALSE;
            if(filaMayor != k-1){
                for(int i = 0; i < Ab.numCols(); i++){ //Warning Maybe NumRows
                    double aux = Ab.get(k-1,i);
                    Ab.set(k-1,i,Ab.get(filaMayor,i));
                    Ab.set(filaMayor,i,aux);
                }
            }
            if(columnaMayor != k-1){
                for(int i = 0; i < n; i++){
                    double aux = Ab.get(i,k-1);
                    Ab.set(i,k-1,Ab.get(i,columnaMayor));
                    Ab.set(i,columnaMayor,aux);
                }
                double aux2 = marcas.get(columnaMayor,0);
                marcas.set(columnaMayor,0,marcas.get(k-1,0));
                marcas.set(k-1,0,aux2);
            }
        }
        return Ab;
    }
    public static String eliminacionGaussianaConPivoteoTotal(SimpleMatrix A,
                                                           SimpleMatrix b,int size){
        int n = A.numRows();
        String res = "";
        SimpleMatrix Ab = new SimpleMatrix(A.numRows(),A.numCols()+1);
        Ab.insertIntoThis(0, 0, A);
        Ab.insertIntoThis(0, A.numCols(), b);
        SimpleMatrix marcas = crearMarcas(n);
        for(int k = 1; k < n; k++){
            pivoteoTotal(Ab, marcas, k, n);
            if (stop)
                return "El sistema no tiene solucion unica";
            for(int i = k+1; i < n+1; i++){
                double multiplicador = Ab.get(i-1,k-1)/Ab.get(k-1,k-1);
                for(int j = k ; j < n+2; j++){
                    Ab.set(i-1,j-1,Ab.get(i-1,j-1)-multiplicador*Ab.get(k-1,j-1));
                }
            }
        }
        SimpleMatrix x = new SimpleMatrix(n,1);
        for(int i = n; i>0;i--){
            double sumatoria = 0;
            for(int p = i+1; p <= n; p++){
                sumatoria = sumatoria + Ab.get(i-1,p-1)*x.get(p-1,0);
            }
            x.set(i-1,0,(Ab.get(i-1,n)-sumatoria)/Ab.get(i-1,i-1));
        }

        String xx = "";
        for (int k = 0; k < size; k++)
            xx += "X" + marcas.get(k) + " = " + x.get(k) + "\n";

        res= ""+Ab.toString()+"\n MASCARAS \n"+marcas.toString()+"\n ANSWERS \n"+xx;
        return res;
    }
}