package co.edu.eafit.equations;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private XYPlot plot;
    private Expression eval;
    ArrayList<Double> vector = new ArrayList<>();
    public double datoX,datoY;

    private void refrescar(){
        this.runOnUiThread(Graf);
    }
    private Runnable Graf = new Runnable() {
        @Override
        public void run() {
            //Movimiento
            /*datoX = datoX+0.5;
            vector.remove(0);
            vector.add(datoX);
            datoY = Math.tan(datoX);
            vector.remove(0);
            vector.add(datoY);
            XYSeries series = new SimpleXYSeries(vector,SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED,"Seno");
            LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.MAGENTA,0x000000,0x000000,null);
            plot.clear();
            plot.addSeries(series, seriesFormat);
            plot.redraw();*/
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Grafica
        plot = (XYPlot) findViewById(R.id.Grafica);
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

        Button btn_graph = (Button) findViewById(R.id.btn_graph);
        final EditText inpt_f = (EditText) findViewById(R.id.inpt_f);
        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                String function_text = inpt_f.getText().toString();
                eval = new ExpressionBuilder(function_text).variable("x").build();
                for (int i = 0; i < 40; i++) {
                    datoX = datoX + 0.25;
                    vector.add(datoX);
                    datoY = eval.setVariable("x", datoX).evaluate();
                    vector.add(datoY);
                }
                XYSeries series = new SimpleXYSeries(vector,
                        SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, function_text);
                LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.MAGENTA,
                        0x000000, 0x000000, null);
                plot.addSeries(series, seriesFormat);
                plot.redraw();
                vector.clear();
            }
        });

        /*Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refrescar();
            }
        },0,150);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
