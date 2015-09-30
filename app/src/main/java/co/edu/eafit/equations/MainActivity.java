package co.edu.eafit.equations;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private XYPlot plot;
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
        plot = (XYPlot) findViewById(R.id.Grafica);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 0.5);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.rgb(33,33,33)); //BG
        plot.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT); //Y lines
        plot.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT); //X lines
        plot.getBackgroundPaint().setColor(Color.rgb(33,33,33)); //masExterno
        plot.getGraphWidget().getBackgroundPaint().setColor(Color.rgb(33,33,33)); //Numbers-medio
        plot.setRangeBoundaries(-2,2, BoundaryMode.FIXED);
        plot.setBorderPaint(null);
        plot.setPlotMargins(0,0,0,0);

        for(int i=0;i<40;i++){
            datoX = datoX+0.25;
            vector.add(datoX);
            datoY = Math.sin(datoX);
            vector.add(datoY);
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refrescar();
            }
        },0,150);
        XYSeries series = new SimpleXYSeries(vector,SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED,"Seno");
        LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.MAGENTA,0x000000,0x000000,null);
        plot.clear();
        plot.addSeries(series,seriesFormat);
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
