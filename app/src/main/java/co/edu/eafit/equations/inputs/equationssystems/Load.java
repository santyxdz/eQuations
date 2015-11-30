package co.edu.eafit.equations.inputs.equationssystems;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.gson.Gson;

import org.ejml.simple.SimpleMatrix;

import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;

public class Load {
    public static void load(SharedPreferences preferences, Tabs rootView){
        try{
            String n =  preferences.getString("n","4");
            loadN(rootView, n);
        }catch (Exception e){}
        try{
            String matrixString = preferences.getString("MatrixA","");
            loadMatrixA(rootView, matrixString);
        }catch (Exception e){}
        try{
            String vectorBString = preferences.getString("VectorB","");
            loadVectorB(rootView,vectorBString);
        }catch (Exception e){}
    }
    public static void loadN(Tabs rootView, String n){
        EditText inputMatrixSize = (EditText)rootView.findViewById(R.id.input_matrix_size);
        inputMatrixSize.setText(n);
        Button buttonMatrixSize = (Button)rootView.findViewById(R.id.btn_matrix_size);
        buttonMatrixSize.performClick();
    }
    public static void loadMatrixA(Tabs rootView,String matrixString){
        Gson gson = new Gson();
        SimpleMatrix matrixA = gson.fromJson(matrixString,SimpleMatrix.class);
        TableLayout matrixinput = (TableLayout)rootView.findViewById(R.id.MatrixA);
        matrixinput.removeAllViews();
        for (int i=0;i<matrixA.numCols();i++){
            TableRow row = new TableRow(rootView.returnthis().getApplicationContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for(int j=0;j<matrixA.numRows();j++){
                EditText input = new EditText(rootView.returnthis().getApplicationContext());
                row.addView(input);
                input.getLayoutParams().width=100;
                input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
                input.setTextColor(Color.BLACK);
                input.setText(Double.toString(matrixA.get(i,j)));
            }
            matrixinput.addView(row);
        }

    }
    public static void loadVectorB(Tabs rootView,String vectorString){
        Gson gson = new Gson();
        SimpleMatrix vectorB = gson.fromJson(vectorString,SimpleMatrix.class);
        TableLayout vectorbinput = (TableLayout)rootView.findViewById(R.id.VectorB);
        vectorbinput.removeAllViews();
        TableRow row = new TableRow(rootView.returnthis().getApplicationContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        for(int j=0;j< vectorB.numRows();j++){
            EditText input = new EditText(rootView.returnthis().getApplicationContext());
            row.addView(input);
            input.getLayoutParams().width=100;
            input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
            input.setTextColor(Color.BLACK);
            input.setText(Double.toString(vectorB.get(j, 0)));
        }
        vectorbinput.addView(row);

    }
    public static void saveN(SharedPreferences preferences, String n){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("n",n);
        editor.apply();
    }
    public static void saveMatrixA(SharedPreferences preferences,SimpleMatrix MatrixA){
        Gson gson = new Gson();
        String matrixString = gson.toJson(MatrixA,SimpleMatrix.class);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("MatrixA",matrixString);
        editor.apply();
    }
    public static void saveVectorB(SharedPreferences preferences,SimpleMatrix VectorB){
        Gson gson = new Gson();
        String matrixString = gson.toJson(VectorB,SimpleMatrix.class);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("VectorB",matrixString);
        editor.apply();
    }
    public static void saveVectorX(SharedPreferences preferences,SimpleMatrix VectorX){
        Gson gson = new Gson();
        String matrixString = gson.toJson(VectorX,SimpleMatrix.class);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("VectorX",matrixString);
        editor.apply();
    }
}
