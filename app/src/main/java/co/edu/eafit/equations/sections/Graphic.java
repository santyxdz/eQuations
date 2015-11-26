package co.edu.eafit.equations.sections;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

import co.edu.eafit.equations.R;

/**
 * Created by Santi on 30/09/2015.
 */
public class Graphic extends Fragment {
    private View rootView;
    private XYPlot plot;
    private Expression eval;
    private ArrayList<Double> values = new ArrayList<>();
    Number xmin=0,xmax=0,ymin=0,ymax=0;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.lay_graphic, container, false);
            plot = (XYPlot) rootView.findViewById(R.id.Grafica);
            plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.rgb(33, 33, 33)); //BG
            plot.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT); //Y lines
            plot.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT); //X lines
            plot.getBackgroundPaint().setColor(Color.rgb(33,33,33)); //masExterno
            plot.getGraphWidget().getBackgroundPaint().setColor(Color.rgb(33, 33, 33)); //Numbers-medio
            plot.setBorderPaint(null);
            plot.setPlotMargins(0, 0, 0, 0);
            stylize(0.5,0.5,0,8,-4,4);
            Button btn_graph = (Button)rootView.findViewById(R.id.btn_graph);
            final EditText minx = (EditText)rootView.findViewById(R.id.edtxt_rangeinx_min);
            final EditText maxx = (EditText)rootView.findViewById(R.id.edtxt_rangeinx_max);
            final EditText miny = (EditText)rootView.findViewById(R.id.edtxt_rangeiny_min);
            final EditText maxy = (EditText)rootView.findViewById(R.id.edtxt_rangeiny_max);
            final EditText inpt_f = (EditText)rootView.findViewById(R.id.inpt_f);
            minx.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            maxx.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            miny.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            maxy.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            btn_graph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int steps = 50;
                    Double xb,xt,yb,yt;
                    try{
                        xb = Double.parseDouble(minx.getText().toString());
                        xt = Double.parseDouble(maxx.getText().toString());
                        yb = Double.parseDouble(miny.getText().toString());
                        yt = Double.parseDouble(maxy.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(rootView.getContext(),"Invalid Limits",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(xt<=xb || yt<=yb){
                        Toast.makeText(rootView.getContext(),"Invalid Limits",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.i("PASO","por las validaciones");
                    stylize(Math.abs(xt - xb) / steps, Math.abs(yt - yb) / steps, xb, xt, yb, yt);
                    plot.redraw();
                    Log.i("PASO", "por lel stylize");
                    double xstep = Math.abs(xt-xb)/steps;
                    plot.clear();
                    String function_text = inpt_f.getText().toString();
                    try{
                    eval = new ExpressionBuilder(function_text).variable("x").build();}
                    catch (Exception e){
                        Toast.makeText(rootView.getContext(),"Invalid Function",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double datoX = xb;
                    double datoY;
                    for (int i = 0; i < steps; i++) {
                        Log.i("ESTA"," en el ciclo");
                        datoX = datoX + xstep;
                        values.add(datoX);
                        datoY = eval.setVariable("x", datoX).evaluate();
                        values.add(datoY);
                    }
                    Log.i("PASO","el ciclo");
                    XYSeries series = new SimpleXYSeries(values,
                            SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, function_text);
                    LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.MAGENTA,
                            0x000000, 0x000000, null);
                    Log.i("PASO","las series");
                    plot.addSeries(series, seriesFormat);
                    plot.redraw();
                    Log.i("PASO", "el redraw");
                    values.clear();
                    Log.i("PASO", "el clear");
                }
            });
            return rootView;
        }
        public void stylize(double dom_step, double range_step,
                            Number xmin, Number xmax, Number ymin, Number ymax){
            plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, dom_step*10);
            plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, range_step*10);
            plot.setDomainBoundaries(xmin, xmax, BoundaryMode.FIXED);
            plot.setRangeBoundaries(ymin, ymax, BoundaryMode.FIXED);
        }
}