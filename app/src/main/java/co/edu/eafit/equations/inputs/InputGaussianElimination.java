package co.edu.eafit.equations.inputs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.eafit.equations.R;

/**
 * Created by JDaniels on 09/11/2015.
 */
public class InputGaussianElimination extends Fragment {
    public static InputGaussianElimination newInstance(int sectionNumber) {
        InputGaussianElimination fragment = new InputGaussianElimination();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_input_gaussian_elimination, container, false);
        return rootView;
    }
}
