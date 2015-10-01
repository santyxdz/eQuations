package co.edu.eafit.equations;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AbsListView;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

/**
 * Created by Santi on 30/09/2015.
 */
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
        txtv_footer.setText("El Footer");
        txtv_header.setText("El Header");
        gridView.addHeaderView(header);
        gridView.addFooterView(footer);
        MenuAdapter mAdapter = new MenuAdapter(this.getActivity(),R.id.txt_line1);
        ArrayList<String> mData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mData.add("SAMPLE #");}
//        }
//        if (mAdapter == null) {
//            mAdapter = new MenuAdapter(getActivity(), R.id.txt_line1);
//        }
//
//        if (mData == null) {
//            mData = generateSampleData();
//        }
        for(String data:mData) {
            mAdapter.add(data);
        }
        gridView.setAdapter(mAdapter);
        return rootView;
    }
}
