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
    public static Help newInstance() {
        Help fragment = new Help();
        Bundle args = new Bundle();
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
                textView.setText(getResources().getString(R.string.help_Gausssianelimination));
                img.setImageResource(R.drawable.gaussianelimination);
                break;
            case "Cholesky":
                textView.setText(getResources().getString(R.string.help_Cholesky));
                img.setImageResource(R.drawable.cholesky);
                break;
            case "Crout":
                textView.setText(getResources().getString(R.string.help_Crout));
                img.setImageResource(R.drawable.crout1);
                break;
            case "Doolittle":
                textView.setText(getResources().getString(R.string.help_Doolittle));
                img.setImageResource(R.drawable.doolittle);
                break;
            case "Gauss Seidel":
                textView.setText(getResources().getString(R.string.help_GaussSeidel));
                img.setImageResource(R.drawable.gaussseidel);
                break;
            case "Jacobi":
                textView.setText(getResources().getString(R.string.help_Jacobi));
                img.setImageResource(R.drawable.jacobi);
                break;
            default:
                textView.setText("Not Defined");
                img.setImageResource(R.drawable.searincre);
                break;
        }
        return rootView;
    }
}
