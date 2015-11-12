package co.edu.eafit.equations.inputs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import co.edu.eafit.equations.R;

/**
 * Created by JDaniels on 09/11/2015.
 */
public class InputGaussianElimination extends Fragment {
    TableLayout matrixinput;
    public static InputGaussianElimination newInstance(int sectionNumber) {
        InputGaussianElimination fragment = new InputGaussianElimination();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_gaussian_elimination, container, false);
        final EditText inputMatrixSize = (EditText)rootView.findViewById(R.id.input_matrix_size);
        Button btnMatrixSize = (Button)rootView.findViewById(R.id.btn_matrix_size);
        matrixinput = (TableLayout)rootView.findViewById(R.id.MatrixA);
        btnMatrixSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrixinput.removeAllViews();
                String sSize = inputMatrixSize.getText().toString();
                int size;
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
                    }
                    matrixinput.addView(row);
                }
            }
        });
        return rootView;
    }
}
