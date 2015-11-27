package co.edu.eafit.equations.inputs.singlevariable;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import co.edu.eafit.equations.sections.Methods;
import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;
import co.edu.eafit.equations.tables.singlevariable.TableIncrementalSearches;

public class InputIncrementalSearches extends Fragment {
    public interface OnRefreshListener {
        public void onRefresh();
    }
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    public static InputIncrementalSearches newInstance() {
        InputIncrementalSearches fragment = new InputIncrementalSearches();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InputIncrementalSearches() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_incremental_searches, null);
        final EditText inpt_function = (EditText)rootView.findViewById(R.id.inpt_function);
        final EditText inpt_delta = (EditText)rootView.findViewById(R.id.inpt_tol);
        final EditText inpt_init = (EditText)rootView.findViewById(R.id.inpt_init);
        final EditText inpt_iter = (EditText)rootView.findViewById(R.id.inpt_iter);
        final TextView txt_push = (TextView) rootView.findViewById(R.id.txt_push);
        Button btn_run = (Button)rootView.findViewById(R.id.btn_run);
        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Tabs) getActivity()).getTabla().getArray().clear();
                BigDecimal x0 = new BigDecimal(inpt_init.getText().toString());
                BigDecimal delta = new BigDecimal(inpt_delta.getText().toString());
                long iter = Integer.parseInt(inpt_iter.getText().toString());
                ((Tabs) getActivity()).getTabla().addHead(
                        getActivity().getResources().getStringArray(
                                R.array.cabecera_tabla_BIncremental
                        )
                );
                Methods.IncrementalSearches(x0, delta, iter,
                        ((Tabs) getActivity()).getTabla(),
                        inpt_function.getText().toString()
                );
                ((TableIncrementalSearches)((Tabs) getActivity()).getFragmentTable()).load_tables();
                txt_push.setText(((Tabs)getActivity()).getTabla().getResult());
            }
        });
        return rootView;
    }
}
