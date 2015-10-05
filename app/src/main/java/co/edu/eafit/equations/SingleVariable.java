package co.edu.eafit.equations;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AbsListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
public class SingleVariable extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_singlevariable, container, false);
        StaggeredGridView gridView = (StaggeredGridView)rootView.findViewById(R.id.grid_view);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View header = layoutInflater.inflate(R.layout.list_item_header_footer, null);
        View footer = layoutInflater.inflate(R.layout.list_item_header_footer,null);
        TextView txtv_header = (TextView)header.findViewById(R.id.txt_title);
        TextView txtv_footer = (TextView)header.findViewById(R.id.txt_title);
        txtv_footer.setText("x");
        txtv_header.setText(getString(R.string.single_variables_equations));
        gridView.addHeaderView(header);
        gridView.addFooterView(footer);
        gridView.setBackgroundColor(getResources().getColor(R.color.white));
        MenuAdapter mAdapter = new MenuAdapter(this.getActivity(),R.id.txt_line1);
        mAdapter.add("Incremental Searches");
        mAdapter.add("Bisection");
        mAdapter.add("False Position");
        mAdapter.add("Fixed Point");
        mAdapter.add("Newton");
        mAdapter.add("Secant");
        mAdapter.add("Multiple Roots");
        gridView.setAdapter(mAdapter);
        return rootView;
    }
}
