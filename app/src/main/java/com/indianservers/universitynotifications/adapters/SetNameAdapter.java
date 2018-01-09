package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.classes.SetClass;

import java.util.List;


public class SetNameAdapter extends BaseAdapter {
    Context context;
    //String setNames[];
    List<SetClass> setNames;

    public SetNameAdapter(Context context, List<SetClass> setNames) {
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
        rowView = inflater.inflate(R.layout.set_list, null);

        TextView textView = (TextView) rowView.findViewById(R.id.setname);
        textView.setText(setNames.get(position).getSetName());

        return rowView;
    }
}

