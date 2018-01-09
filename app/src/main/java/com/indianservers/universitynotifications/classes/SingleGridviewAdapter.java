package com.indianservers.universitynotifications.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indianservers.universitynotifications.Quiz;
import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.adapters.SingleGridview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu on 26-12-2017.
 */

public class SingleGridviewAdapter extends BaseAdapter {
    private int PositionSelected = 0;
    Context context;
    List<SingleGridview> mQuestionsSet = new ArrayList<>();

    public SingleGridviewAdapter(Context context, List<SingleGridview> mQuestionsSet) {
        this.context = context;
        this.mQuestionsSet = mQuestionsSet;
    }
    @Override
    public int getCount() {
        return mQuestionsSet.size();
    }

    @Override
    public Object getItem(int position) {
        return Quiz.positionitem;
    }

    @Override
    public long getItemId(int position) {
        return Quiz.positionitem;
    }
    public void setPositionSelected(int position)
    {
        PositionSelected = position;
        this.notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.single_gridview_choose, null);
        TextView textView = (TextView) rowView.findViewById(R.id.single);
        textView.setText(String.valueOf(mQuestionsSet.get(position).getQno()));
        if(position==PositionSelected){
            rowView.setBackgroundColor(Color.GREEN);
            notifyDataSetChanged();
        }else{
            rowView.setBackgroundColor(Color.WHITE);
            notifyDataSetChanged();
        }
        return rowView;
    }
}
