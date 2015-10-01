package co.edu.eafit.equations;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

/**
 * Created by Santi on 30/09/2015.
 */
public class Graphic extends Fragment {
    private View rootView;
    private XYPlot plot;
    private Expression eval;
    private double datoX,datoY;
    private ArrayList<Double> values = new ArrayList<>();
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.lay_graphic, container, false);
            stylize();
            Button btn_graph = (Button)rootView.findViewById(R.id.btn_graph);
            final EditText inpt_f = (EditText)rootView.findViewById(R.id.inpt_f);
            btn_graph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plot.clear();
                    String function_text = inpt_f.getText().toString();
                    eval = new ExpressionBuilder(function_text).variable("x").build();
                    for (int i = 0; i < 40; i++) {
                        datoX = datoX + 0.25;
                        values.add(datoX);
                        datoY = eval.setVariable("x", datoX).evaluate();
                        values.add(datoY);
                    }
                    XYSeries series = new SimpleXYSeries(values,
                            SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, function_text);
                    LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.MAGENTA,
                            0x000000, 0x000000, null);
                    plot.addSeries(series, seriesFormat);
                    plot.redraw();
                    values.clear();
                }
            });
            return rootView;
        }
        public void stylize(){
            plot = (XYPlot) rootView.findViewById(R.id.Grafica);
            plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 0.5);
            plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
            plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.rgb(33, 33, 33)); //BG
            plot.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT); //Y lines
            plot.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT); //X lines
            plot.getBackgroundPaint().setColor(Color.rgb(33,33,33)); //masExterno
            plot.getGraphWidget().getBackgroundPaint().setColor(Color.rgb(33, 33, 33)); //Numbers-medio
            plot.setRangeBoundaries(-2, 2, BoundaryMode.FIXED);
            plot.setBorderPaint(null);
            plot.setPlotMargins(0, 0, 0, 0);
        }
}