package co.edu.eafit.equations;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Methods {
    public static BigDecimal f(double datoX){
        double x = Math.sin(datoX);
        return new BigDecimal(x);
    }
    public static void IncrementalSearches(BigDecimal x0, BigDecimal  delta, long iter, Tabla tabla){
        BigDecimal y0 = f(x0.doubleValue());
        BigDecimal cero = new BigDecimal("0.0");
        if (y0.compareTo(cero)==0) {
            tabla.setResult(x0.toString() + "Is a root");
            //txt_push.setText(""+ x0 + "Is a root");
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
            //Log.i("Matrix Size", tabla.getColumnas() + ":" + tabla.getFilas());
            if(y1.compareTo(cero)==0){
                tabla.setResult(x1.toString() + "Is a root");
                //txt_push.setText(""+ x1 + "Is a root");
            }else{
                if((y0.multiply(y1)).compareTo(cero)==-1){//menor que cero
                    tabla.setResult(x1.toString()
                            +"There's a root between x0 and x1 in the interval ["
                            +x0.toString()+","+x1.toString()+"]");
                    /*txt_push.setText(""+ x1 + "There's a root between x0 and x1 in the interval ["+
                            x0+" , "+x1+" ]");*/
                }else{
                    tabla.setResult("No root found");
                    //txt_push.setText(" Not root found");
                }
            }
        }

    }

}
