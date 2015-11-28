package co.edu.eafit.equations.sections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

import co.edu.eafit.equations.R;
import co.edu.eafit.equations.menuadapters.MenuAdapterES;

public class EquationSystem extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_equationsystem, container, false);
        StaggeredGridView gridView = (StaggeredGridView)rootView.findViewById(R.id.grid_view);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View header = layoutInflater.inflate(R.layout.list_item_header_footer, null);
        View footer = layoutInflater.inflate(R.layout.list_item_header_footer,null);
        TextView txtv_header = (TextView)header.findViewById(R.id.txt_title);
        TextView txtv_footer = (TextView)footer.findViewById(R.id.txt_title);
        txtv_footer.setText("Implemented");
        txtv_header.setText(getString(R.string.equation_system));
        gridView.addHeaderView(header);
        gridView.addFooterView(footer);
        gridView.setBackgroundColor(getResources().getColor(R.color.white));
        MenuAdapterES mAdapter = new MenuAdapterES(this.getActivity(),R.id.txt_line1);
        mAdapter.add("Gaussian Elimination");
        mAdapter.add("Cholesky");
        mAdapter.add("Crout");
        mAdapter.add("Doolittle");
        mAdapter.add("Gauss Seidel");
        mAdapter.add("Jacobi");
        gridView.setAdapter(mAdapter);
        //gridView.setBackgroundColor(getResources().getColor(R.color.soft_teal));
        return rootView;
    }

}
