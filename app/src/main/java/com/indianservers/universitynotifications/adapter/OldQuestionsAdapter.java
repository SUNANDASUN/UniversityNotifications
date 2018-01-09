package com.indianservers.universitynotifications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.models.OldQuestionsModel;

import java.util.ArrayList;

/**
 * Created by JNTUH on 18-11-2017.
 */

public class OldQuestionsAdapter extends BaseAdapter {
    Context c;
    ArrayList<OldQuestionsModel> commonModels;
    LayoutInflater inflater;

    public OldQuestionsAdapter(Context c, ArrayList<OldQuestionsModel> commonModels) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater== null)
        {
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(view==null)
        {
            view = inflater.inflate(R.layout.singleitem,viewGroup,false);

        }
        TextView subject = (TextView)view.findViewById(R.id.qsubject);
        TextView subjectdesc = (TextView)view.findViewById(R.id.year);
        TextView examname = (TextView)view.findViewById(R.id.examname);
        TextView examtype = (TextView)view.findViewById(R.id.examtype);
        TextView pdflink = (TextView)view.findViewById(R.id.pdflink);

        subject.setText(commonModels.get(i).getSubject());
        subjectdesc.setText(commonModels.get(i).getYear());
        examname.setText(commonModels.get(i).getExamname());
        examtype.setText(commonModels.get(i).getExamtype());
        pdflink.setText(commonModels.get(i).getPdflink());
        return view;
    }
}
