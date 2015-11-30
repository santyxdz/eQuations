package co.edu.eafit.equations.inputs.interpolation;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.gson.Gson;
import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;

public class Load {
    public static void load(SharedPreferences preferences, Tabs rootView){
        try{
            String n =  preferences.getString("numPoints","4");
            loadNumPoints(rootView, n);
        }catch (Exception e){}
        try{
            String matrixString = preferences.getString("points","");
            loadPoints(rootView, matrixString);
        }catch (Exception e){}
        try{
            String vectorBString = preferences.getString("evalValue","");
            loadEvalValue(rootView, vectorBString);
        }catch (Exception e){}
    }
    public static void loadNumPoints(Tabs rootView, String n){
        EditText inputPointsSize = (EditText)rootView.findViewById(R.id.input_points_size);
        inputPointsSize.setText(n);
        Button buttonPointsSize = (Button)rootView.findViewById(R.id.btn_points_size);
        buttonPointsSize.performClick();
    }
    public static void loadPoints(Tabs rootView, String pointsString){
        Gson gson = new Gson();
        double[][] points = gson.fromJson(pointsString,double[][].class);
        TableLayout pointsinput = (TableLayout)rootView.findViewById(R.id.PointsTable);
        for(int i=0;i<pointsinput.getChildCount();i++){
            TableRow row = (TableRow)pointsinput.getChildAt(i);
            EditText editx = (EditText)row.getChildAt(0);
            EditText edity = (EditText)row.getChildAt(1);
            editx.setText(Double.toString(points[0][i]));
            edity.setText(Double.toString(points[1][i]));
        }
    }
    public static void loadEvalValue(Tabs rootView, String valueString){
        EditText inputValue = (EditText)rootView.findViewById(R.id.input_value);
        inputValue.setText(valueString);
    }
    public static void saveNumPoints(SharedPreferences preferences, String numPoints){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("numPoints", numPoints);
        editor.apply();
    }
    public static void savePoints(SharedPreferences preferences, double[] x,double[] y){
        Gson gson = new Gson();
        double[][] points = {x,y};
        String pointsString = gson.toJson(points,double[][].class);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("points", pointsString);
        editor.apply();
    }
    public static void saveEvalValue(SharedPreferences preferences, String evalvaluestring){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("evalValue", evalvaluestring);
        editor.apply();
    }
}

