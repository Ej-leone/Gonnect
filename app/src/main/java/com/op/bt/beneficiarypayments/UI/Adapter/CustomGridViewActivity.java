package com.op.bt.beneficiarypayments.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.op.bt.beneficiarypayments.R;

public class CustomGridViewActivity extends BaseAdapter {


    private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImageId;

    public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
            RelativeLayout lly = gridViewAndroid.findViewById(R.id.grid_back);
            switch (i){
                case 0:
                    lly.setBackgroundColor(mContext.getResources().getColor(R.color.Blue));
                    break;
                case 1:
                    lly.setBackgroundColor(mContext.getResources().getColor(R.color.Red));
                    break;
                case 2:
                    lly.setBackgroundColor(mContext.getResources().getColor(R.color.Orange));
                    break;

                case 3:
                    lly.setBackgroundColor(mContext.getResources().getColor(R.color.Yellow));
                    break;

                case 5:
                    lly.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_purple));
                    break;
            }
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }

}

