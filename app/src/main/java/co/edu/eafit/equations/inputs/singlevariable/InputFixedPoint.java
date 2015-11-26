package co.edu.eafit.equations.inputs.singlevariable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import co.edu.eafit.equations.sections.Methods;
import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;
import co.edu.eafit.equations.tables.singlevariable.TableFixedPoint;

public class InputFixedPoint extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    public static InputFixedPoint newInstance() {
        InputFixedPoint fragment = new InputFixedPoint();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InputFixedPoint() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_fixed_point, null);
        final EditText inpt_function = (EditText)rootView.findViewById(R.id.inpt_function);
        final EditText inpt_function_gx = (EditText)rootView.findViewById(R.id.inpt_function_gx);
        final EditText inpt_tol = (EditText)rootView.findViewById(R.id.inpt_tol);
        final EditText inpt_xlower = (EditText)rootView.findViewById(R.id.inpt_xlower);
        final EditText inpt_iter = (EditText)rootView.findViewById(R.id.inpt_iter);
        final TextView txt_push = (TextView) rootView.findViewById(R.id.txt_push);
        final RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.RG);
        final int[] tipeError = new int[1];
        Button btn_run = (Button)rootView.findViewById(R.id.btn_run);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdb_errorAb) {
                    tipeError[0] = 1;
                    txt_push.setText(R.string.absolute_error);
                } else if(checkedId == R.id.rdb_errorRe){
                    tipeError[0] = 0;
                    txt_push.setText(R.string.relative_error);
                }


            }
        });
        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Tabs) getActivity()).getTabla().getArray().clear();
                BigDecimal x0 = new BigDecimal(inpt_xlower.getText().toString());
                BigDecimal tol = new BigDecimal(inpt_tol.getText().toString());
                long iter = Integer.parseInt(inpt_iter.getText().toString());
                ((Tabs) getActivity()).getTabla().addHead(
                        getActivity().getResources().getStringArray(
                                R.array.cabecera_tabla_FixedPoint
                        )
                );
                Methods.FixedPoint(x0, tol, iter, tipeError[0], inpt_function.getText().toString(),
                        inpt_function_gx.getText().toString(),((Tabs) getActivity()).getTabla()

                );
                ((TableFixedPoint)((Tabs) getActivity()).getFragmentTable()).load_tables();
                txt_push.setText(((Tabs)getActivity()).getTabla().getResult());
            }
        });
        return rootView;
    }
}
