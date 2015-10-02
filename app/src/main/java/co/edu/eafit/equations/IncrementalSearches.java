package co.edu.eafit.equations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;

public class IncrementalSearches extends AppCompatActivity {

    private Expression equation;
    private EditText inpt_function;
    private EditText inpt_delta, inpt_init, inpt_iter;
    private TextView txt_push; // para mostrar las salidas
    private Tabla tabla;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incremental_searches);
        inpt_function = (EditText)findViewById(R.id.inpt_function);
        inpt_delta = (EditText)findViewById(R.id.inpt_delta);
        inpt_init = (EditText)findViewById(R.id.inpt_init);
        inpt_iter = (EditText)findViewById(R.id.inpt_iter);
        txt_push = (TextView) findViewById(R.id.txt_push);
        Button btn_run = (Button)findViewById(R.id.btn_run);
        tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla_BIncremental);
        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String function = inpt_function.getText().toString();
                equation = new ExpressionBuilder(function).variable("x").build();
                BigDecimal x0 = new BigDecimal(inpt_init.getText().toString());
                BigDecimal delta = new BigDecimal(inpt_delta.getText().toString());
                long iter = Integer.parseInt(inpt_iter.getText().toString());
                Method(x0,delta,iter);
            }
        });
    }
    public void Method(BigDecimal x0,BigDecimal  delta,long iter){
        //Toast.makeText(getApplicationContext(), "Valor " + f(x0.doubleValue()), Toast.LENGTH_LONG).show();1
        BigDecimal y0 = f(x0.doubleValue());
        BigDecimal cero = new BigDecimal("0.0");
        if (y0.compareTo(cero)==0) {
            txt_push.setText(""+ x0 + "Is a root");
        }else{
            BigDecimal x1 = x0.add(delta);
            BigDecimal y1 = f(x1.doubleValue());
            long cont = 1;
            while (((y0.multiply(y1)).compareTo(cero)==1) && (y1.compareTo(cero)!=0) && (cont < iter)){
                x0 = x1;
                y0 = y1;
                x1 = x0.add(delta);
                y1 = f(x1.doubleValue());
                ArrayList<String> newrow = new ArrayList<String>();
                newrow.add(Long.toString(cont));
                newrow.add(""+x1.doubleValue());
                newrow.add(""+y1.setScale(5, BigDecimal.ROUND_HALF_UP));
                tabla.agregarFilaTabla(newrow);
                cont++;
            }
            if(y1.compareTo(cero)==0){
                txt_push.setText(""+ x1 + "Is a root");
            }else{
                if((y0.multiply(y1)).compareTo(cero)==-1){//menor que cero
                    txt_push.setText(""+ x1 + "There's a root between x0 and x1 in the interval ["+
                    x0+" , "+x1+" ]");
                }else{
                    txt_push.setText(" Not root found");
                }
            }
        }

    }

    public BigDecimal f(double datoX){
        double x = equation.setVariable("x", datoX).evaluate();
        return new BigDecimal(x);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_incremental_searches, menu);
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
