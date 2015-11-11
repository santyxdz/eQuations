package co.edu.eafit.equations.helps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import co.edu.eafit.equations.R;

public class Help extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static Help newInstance(int sectionNumber) {
        Help fragment = new Help();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Help() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_help, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.textView_help);
        ImageView img = (ImageView) rootView.findViewById(R.id.imageView_help);
        ScrollView s = (ScrollView) rootView.findViewById(R.id.scrollView3);
        String type = getActivity().getIntent().getExtras().getString("type");
        switch (type) {
            case "Incremental Searches":
                textView.setText(getResources().getString(R.string.help_searchincre));
                img.setImageResource(R.drawable.searincre);
                break;
            case "Bisection":
                textView.setText(getResources().getString(R.string.help_bis));
                img.setImageResource(R.drawable.bis);
                break;
            case "False Position":
                textView.setText(getResources().getString(R.string.help_falposi));
                img.setImageResource(R.drawable.reglafalsa);
                break;
            case "Fixed Point":
                textView.setText(getResources().getString(R.string.help_fixpoint));
                img.setImageResource(R.drawable.fixpoi);
                break;
            case "Newton":
                textView.setText(getResources().getString(R.string.help_newton));
                img.setImageResource(R.drawable.newton);
                break;
            case "Secant":
                textView.setText(getResources().getString(R.string.help_secant));
                img.setImageResource(R.drawable.secant);
                break;
            case "Multiple Roots":
                textView.setText(getResources().getString(R.string.help_multiroot));
                img.setImageResource(R.drawable.multipleroots);
                break;
            case "Gaussian Elimination":
            case "Cholesky LU":
            case "Crout LU":
            case "Doolittle":
            case "Gauss Seidel":
            case "Jacobi":
            default:
                textView.setText("Not Defined");
                img.setImageResource(R.drawable.searincre);
                break;
        }
        return rootView;
    }
}
