package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.classes.TopicClass;

import java.util.List;

/**
 * Created by hp on 30-May-17.
 */

public class TopicsListAdapter extends BaseAdapter {

    Context context;
    List<TopicClass> allTopics;

    public TopicsListAdapter(Context context, List<TopicClass> allTopics) {
        this.context = context;
        this.allTopics = allTopics;
    }

    @Override
    public int getCount() {
        return allTopics.size();
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
        rowView = inflater.inflate(R.layout.topics_list_item, null);

        TextView topicName = (TextView) rowView.findViewById(R.id.topics);

        topicName.setText(allTopics.get(position).getTopicName());
        return rowView;
    }

}
