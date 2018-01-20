package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.models.IbpsCommonClas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu on 05-01-2018.
 */

public class IBPSAdpter extends BaseAdapter {
    Context context;
    //String setNames[];
    ArrayList<IbpsCommonClas> setNames;

    public IBPSAdpter(Context context, ArrayList<IbpsCommonClas> setNames) {
        this.context = context;
        this.setNames = setNames;
    }
    @Override
    public int getCount() {
        return setNames.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;

        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.single_ibps, null);

        TextView textView1 = (TextView) rowView.findViewById(R.id.examnameibps);
        TextView textView2 = (TextView) rowView.findViewById(R.id.examidibps);
        IbpsCommonClas ibpsCommonClas = setNames.get(position);
        textView1.setText(ibpsCommonClas.getExamName());
        try{
            String tv2 = ibpsCommonClas.getExamId().toString();
            if(tv2.equals(null)){
                textView2.setVisibility(View.GONE);
            }
            else {
                textView2.setVisibility(View.VISIBLE);
                textView2.setText(ibpsCommonClas.getExamId());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }




        return rowView;
    }
}
