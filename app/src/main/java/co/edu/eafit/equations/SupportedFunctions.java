package co.edu.eafit.equations;

import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Santi on 30/09/2015.
 */
public class SupportedFunctions extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_supported_functions, container, false);
        TableLayout tableLayout = (TableLayout)rootView.findViewById(R.id.tabla);
        String[][] functions = {{"abs","absolute value"},
                {"acos","arc cosine"},
                {"asin","arc sine"},
                {"atan","arc tangent"},
                {"cbrt","cubic root"},
                {"ceil","nearest upper integer"},
                {"cos","cosine"},
                {"cosh","hyperbolic cosine"},
                {"exp","euler's number raised to the power (e^x)"},
                {"floor","nearest lower integer"},
                {"log","logarithmus naturalis (base e)"},
                {"sin","sine"},
                {"sinh","hyperbolic sine"},
                {"sqrt","square root"},
                {"tan","tangent"},
                {"tanh","hyperbolic tangent"}};
        load_tables(tableLayout, functions);
        return rootView;
    }
    public void load_tables(TableLayout tableLayout, String[][] table){
        tableLayout.removeAllViews();
        for(String[] row:table){
            TableRow.LayoutParams layoutCelda;
            TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
            );
            TableRow fila = new TableRow(getActivity());
            fila.setLayoutParams(layoutFila);
            for(String item:row){
                TextView text = new TextView(getActivity());
                text.setText(item);
                text.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);
                text.setBackgroundResource(R.drawable.tabla_celda);
                layoutCelda = new TableRow.LayoutParams(
                        getTextWidth(item),
                        TableRow.LayoutParams.WRAP_CONTENT
                );
                text.setLayoutParams(layoutCelda);
                fila.addView(text);
            }
            tableLayout.addView(fila);
        }
    }
    private int getTextWidth(String text){
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);
        p.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }
}
