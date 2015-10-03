package co.edu.eafit.equations;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.etsy.android.grid.util.DynamicHeightTextView;

import java.util.ArrayList;
import java.util.Random;

public class MenuAdapter extends ArrayAdapter<String>{
    public static final String TAG = "MenuAdapter";
    static class ViewHolder{
        DynamicHeightTextView txtLineOne;
        Button btn_go;
    }
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    public MenuAdapter(final Context context, final int textViewResourceId){
        super(context,textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<>();
        mBackgroundColors.add(R.color.orange);
        mBackgroundColors.add(R.color.green);
        mBackgroundColors.add(R.color.blue);
        mBackgroundColors.add(R.color.yellow);
        mBackgroundColors.add(R.color.grey);
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_sample, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView)convertView.findViewById(R.id.txt_line1);
            vh.btn_go = (Button) convertView.findViewById(R.id.btn_go);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));

        Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

        vh.txtLineOne.setHeightRatio(positionHeight);
        //vh.txtLineOne.setText(getItem(position) + position);
        vh.txtLineOne.setText(getItem(position));

        vh.btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), Tabs.class);
                /*Toast.makeText(getContext(), "Button Clicked Position " +
                        position, Toast.LENGTH_SHORT).show();*/
                switch (position){
                    case 0:
                    intent.putExtra("type","Incremental Searches");
                        break;
                    case 1:
                        intent.putExtra("type", "Bisection");
                        break;
                    case 2:
                        intent.putExtra("type", "False Position");
                        break;
                    case 3:
                        intent.putExtra("type", "Fixed Point");
                        break;
                    case 4:
                        intent.putExtra("type", "Newton");
                        break;
                    case 5:
                        intent.putExtra("type", "Secant");
                        break;
                    case 6:
                        intent.putExtra("type","Multiple Roots");
                        break;
                }
                intent.putExtra("id",position);
                intent.setClass(getContext(),Tabs.class);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

}
