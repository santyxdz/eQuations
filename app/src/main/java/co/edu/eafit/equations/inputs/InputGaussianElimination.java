package co.edu.eafit.equations.inputs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.eafit.equations.R;

/**
 * Created by JDaniels on 09/11/2015.
 */
public class InputGaussianElimination extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    public static InputFixedPoint newInstance(int sectionNumber) {
        InputFixedPoint fragment = new InputFixedPoint();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_input_gaussian_elimination, null);

        return rootView;
    }
}
