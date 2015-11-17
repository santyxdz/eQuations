package co.edu.eafit.equations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

public class Interpolation extends Fragment {
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
        txtv_footer.setText("Not Implemented Yet");
        txtv_header.setText(getString(R.string.title_section6));
        gridView.addHeaderView(header);
        gridView.addFooterView(footer);
        gridView.setBackgroundColor(getResources().getColor(R.color.white));
        MenuAdapterIN mAdapter = new MenuAdapterIN(this.getActivity(),R.id.txt_line1);
        mAdapter.add("Newton");
        mAdapter.add("LaGrange");
        gridView.setAdapter(mAdapter);
        //gridView.setBackgroundColor(getResources().getColor(R.color.soft_teal));
        return rootView;
    }

}
