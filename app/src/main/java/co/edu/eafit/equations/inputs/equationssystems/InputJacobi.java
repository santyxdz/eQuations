package co.edu.eafit.equations.inputs.equationssystems;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ejml.simple.SimpleMatrix;

import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;
import co.edu.eafit.equations.sections.Methods;
import co.edu.eafit.equations.tables.singlevariable.TableJacobi;

/**
 * Created by JDaniels on 09/11/2015.
 */
public class InputJacobi extends Fragment {
    TableLayout matrixinput;
    TableLayout vectorbinput;
    TableLayout vectorinit;
    int size;
    public static InputJacobi newInstance() {
        InputJacobi fragment = new InputJacobi();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_jacobi, container, false);
        final EditText inputMatrixSize = (EditText)rootView.findViewById(R.id.input_matrix_size);
        final EditText inpt_iter = (EditText)rootView.findViewById(R.id.inpt_iter);
        final EditText inpt_tol = (EditText)rootView.findViewById(R.id.inpt_tol);
        final EditText inpt_lambda = (EditText)rootView.findViewById(R.id.inpt_lambda);
        final TextView txt_push = (TextView)rootView.findViewById(R.id.txt_push);
        Button btnMatrixSize = (Button)rootView.findViewById(R.id.btn_matrix_size);
        matrixinput = (TableLayout)rootView.findViewById(R.id.MatrixA);
        vectorbinput = (TableLayout)rootView.findViewById(R.id.VectorB);
        vectorinit = (TableLayout)rootView.findViewById(R.id.VectorX);
        final Button btnCalculate = (Button)rootView.findViewById(R.id.btn_calculate);
        btnMatrixSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrixinput.removeAllViews();
                vectorbinput.removeAllViews();
                vectorinit.removeAllViews();
                String sSize = inputMatrixSize.getText().toString();

                if(sSize.isEmpty()||sSize==null||sSize.equals("")){
                    size=4;
                }else{
                    size = Integer.parseInt(sSize);
                }
                for (int i=0;i<size;i++){
                    TableRow row = new TableRow(rootView.getContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    for(int j=0;j<size;j++){
                        EditText input = new EditText(rootView.getContext());
                        row.addView(input);
                        input.getLayoutParams().width=100;
                        //input.setHint("A("+(i+1)+","+(j+1)+")");
                        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
                    }
                    matrixinput.addView(row);
                }
                TableRow vectorb = new TableRow(rootView.getContext());
                TableRow.LayoutParams vectorblp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                vectorb.setLayoutParams(vectorblp);
                for(int i=0;i<size;i++){
                    EditText input = new EditText(rootView.getContext());
                    vectorb.addView(input);
                    input.getLayoutParams().width=100;
                    input.setHint("X"+(i+1));
                    input.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                vectorbinput.addView(vectorb);

                TableRow vectorx = new TableRow(rootView.getContext());
                TableRow.LayoutParams vectorxlb = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                for(int i=0;i<size;i++){
                    EditText inputx = new EditText(rootView.getContext());
                    vectorx.addView(inputx);
                    inputx.getLayoutParams().width=100;
                    inputx.setHint("X"+(i+1));
                    inputx.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
                vectorinit.addView(vectorx);

                btnCalculate.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                btnCalculate.setVisibility(View.VISIBLE);
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Tabs) getActivity()).getTabla().getArray().clear();
                SimpleMatrix matrix = new SimpleMatrix(matrixinput.getChildCount(),matrixinput.getChildCount());
                SimpleMatrix vectorb = new SimpleMatrix(matrixinput.getChildCount(),1);
                SimpleMatrix vectorx = new SimpleMatrix(matrixinput.getChildCount(),1);
                for(int i = 0; i < matrixinput.getChildCount();i++){
                    TableRow row = (TableRow)matrixinput.getChildAt(i);
                    for(int j = 0; j < row.getChildCount(); j++){
                        EditText input = (EditText)row.getChildAt(j);
                        matrix.set(i,j,Double.parseDouble(input.getText().toString()));
                    }
                }
                TableRow rowx = (TableRow)vectorbinput.getChildAt(0);
                for(int i = 0; i < rowx.getChildCount() ; i++){
                    EditText input = (EditText)rowx.getChildAt(i);
                    vectorb.set(i, 0, Double.parseDouble(input.getText().toString()));
                }
                TableRow rowxx = (TableRow)vectorinit.getChildAt(0);
                for(int i = 0; i < rowxx.getChildCount() ; i++){
                    EditText input = (EditText)rowxx.getChildAt(i);
                    vectorx.set(i, 0, Double.parseDouble(input.getText().toString()));
                }

                Tabs activity = (Tabs)getActivity();


                //tabgauelm.getText().setText(matrix.toString()+"\n"+vectorb.toString());
                //ArrayList<String> head = new ArrayList<String>();
                long iter = Integer.parseInt(inpt_iter.getText().toString());
                double lambda = Double.parseDouble(inpt_lambda.getText().toString());
                double tol = Double.parseDouble(inpt_tol.getText().toString());

                String head[] = new String[size+2];
                head[0] = " Iter ";
                for (int i=1;i<size+1;i++){
                    head[i] ="  X"+i+" ";
                }
                head[size+1] = " Error ";
                ((Tabs) getActivity()).getTabla().addHead(head);
                Methods.jacobi(matrix,vectorb,vectorx,iter,tol,lambda, size, ((Tabs) getActivity()).getTabla());
                ((TableJacobi)((Tabs) getActivity()).getFragmentTable()).load_tables();
                txt_push.setText(((Tabs) getActivity()).getTabla().getResult());



            }
        });
        return rootView;
    }
}
