package com.indianservers.universitynotifications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.indianservers.universitynotifications.MyHolder;
import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.models.CommonModel;

import java.util.ArrayList;

/**
 * Created by Admin on 5/26/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<CommonModel> commonModels;
    LayoutInflater inflater;


    public CustomAdapter(Context c, ArrayList<CommonModel> commonModels) {
        this.c = c;
        this.commonModels = commonModels;
    }


    @Override
    public int getCount() {
        return commonModels.size();
    }

    @Override
    public Object getItem(int i) {
        return commonModels.get(getCount()-i-1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {

        if (inflater== null)
        {
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(convertview==null)
        {
            convertview= inflater.inflate(R.layout.listviewitem,viewGroup,false);

        }
        MyHolder holder= new MyHolder(convertview);
        holder.notification.setText(commonModels.get(i).getNotificationname());
        holder.notdate.setText(commonModels.get(i).getNotificationdate());
        holder.desc.setText(commonModels.get(i).getNotificationdesc());
        holder.imgurl.setText(commonModels.get(i).getImage());
        holder.weblink.setText(commonModels.get(i).getWeblink());
        return convertview;
    }
}
