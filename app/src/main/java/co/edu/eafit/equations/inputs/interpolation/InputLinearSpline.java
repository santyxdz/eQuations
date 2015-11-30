package co.edu.eafit.equations.inputs.interpolation;

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

import co.edu.eafit.equations.Methods;
import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;
import co.edu.eafit.equations.tables.interpolation.TableLinearSpline;
import co.edu.eafit.equations.tables.interpolation.TableNewtonPolynomial;

public class InputLinearSpline extends Fragment {
    TableLayout matrixinput;
    TableLayout vectorbinput;
    int size;
    int numPoints;
    public static InputLinearSpline newInstance() {
        InputLinearSpline fragment = new InputLinearSpline();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_linear_spline, container, false);
        final EditText inputPointsSize = (EditText)rootView.findViewById(R.id.input_points_size);
        final Button btnPointSize = (Button)rootView.findViewById(R.id.btn_points_size);
        final TableLayout pointsLayout = (TableLayout)rootView.findViewById(R.id.PointsTable);
        final LinearLayout layinput = (LinearLayout)rootView.findViewById(R.id.lay_input_function);
        final Button btnCalculate = (Button)rootView.findViewById(R.id.btn_calculate);
        final EditText inputValue = (EditText)rootView.findViewById(R.id.input_value);
        btnPointSize.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputValue.setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_FLAG_DECIMAL
                | InputType.TYPE_NUMBER_FLAG_SIGNED);
        //OTROS
        btnPointSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsLayout.removeAllViews();
                numPoints = Integer.parseInt(inputPointsSize.getText().toString());
                for(int i=0;i<numPoints;i++){
                    TableRow fila = new TableRow(rootView.getContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    fila.setLayoutParams(lp);
                    EditText editx = new EditText(rootView.getContext());
                    EditText edity = new EditText(rootView.getContext());
                    fila.addView(editx);
                    fila.addView(edity);
                    editx.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    edity.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL
                            | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    TableRow.LayoutParams editparams = new TableRow.LayoutParams(
                            0,ViewGroup.LayoutParams.WRAP_CONTENT,0.5f);
                    editx.setLayoutParams(editparams);
                    edity.setLayoutParams(editparams);
                    pointsLayout.addView(fila);
                    layinput.setVisibility(LinearLayout.VISIBLE);
                }
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double []vectorx = new double[numPoints];
                double []vectory = new double[numPoints];
                for(int i = 0; i < numPoints;i++){
                    TableRow row = (TableRow)pointsLayout.getChildAt(i);
                    EditText  x = (EditText)row.getChildAt(0);
                    EditText  y = (EditText)row.getChildAt(1);
                    vectorx[i] = Double.parseDouble(x.getText().toString());
                    vectory[i] = Double.parseDouble(y.getText().toString());
                }
                double value = Double.parseDouble(inputValue.getText().toString());

                Tabs activity = (Tabs)getActivity();
                TableLinearSpline table = (TableLinearSpline)activity.getFragmentTable();
                String res = Methods.interpolacionLinearSpline(numPoints, value, vectorx, vectory);
                table.getText().setText(res);
            }
        });
        return rootView;
    }
}
