package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.classes.SubjectClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GridViewAdapter extends BaseAdapter {


    Context context;
    List<SubjectClass> AllSubjects;
    private ArrayList<SubjectClass> arraylist;
    private ArrayList<String> strings;
    SharedPreferences sharedPreferences;

    public GridViewAdapter(Context context, List<SubjectClass> names) {
        this.AllSubjects = names;
        this.context = context;
        this.arraylist = new ArrayList<SubjectClass>();
        this.arraylist.addAll(names);
    }
    public GridViewAdapter(Context context, ArrayList<String> strings) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String type = type = sharedPreferences.getString("RRBTYPE", "0");
        if(type.equals("online")){
            return strings.size();
        }else {
            return AllSubjects.size();
        }

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String type = type = sharedPreferences.getString("RRBTYPE", "0");
        View rowView;

        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.grid_list_item, null);

        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        if(type.equals("online")){
            textView.setText(strings.get(position));
        }else {
            textView.setText(AllSubjects.get(position).getSName());
        }
        return rowView;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        AllSubjects.clear();
        if (charText.length() == 0) {
            AllSubjects.addAll(arraylist);
        } else {
            for (SubjectClass wp : arraylist) {
                if (wp.getSName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    AllSubjects.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
