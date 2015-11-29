package co.edu.eafit.equations.tables.equiationssystems;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.eafit.equations.R;
import co.edu.eafit.equations.Tabs;


public class TableDoolittle extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private TableLayout tableLayout;
    public TextView text;
    public static TableDoolittle newInstance() {
        TableDoolittle fragment = new TableDoolittle();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TableDoolittle() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_table_doolittle, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        text = (TextView)rootView.findViewById(R.id.gau_text);
        return rootView;
    }

    private int getTextWidth(String text){
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);
        p.getTextBounds(text,0,text.length(),bounds);
        return bounds.width();
    }

    public TextView getText() {
        return text;
    }
}
