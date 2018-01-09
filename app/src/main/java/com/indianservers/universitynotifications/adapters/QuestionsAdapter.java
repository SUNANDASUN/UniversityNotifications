package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.indianservers.universitynotifications.R;
import com.indianservers.universitynotifications.classes.QuestionsClass;

import java.util.List;

/**
 * Created by hp on 30-May-17.
 */

public class QuestionsAdapter extends PagerAdapter {

    Context context;
    List<QuestionsClass> allQuestions ;
    LayoutInflater inflater;

    public QuestionsAdapter() {
    }

    public QuestionsAdapter(Context context, List<QuestionsClass> allQuestions) {
        this.context = context;
        this.allQuestions = allQuestions;
    }

    @Override
    public int getCount() {
//        Log.d("QuestionAdapter","question size-----"+allQuestions.size());
        return allQuestions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView question;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.question_fragment, container, false);

        question = (TextView)itemView.findViewById(R.id.question);
       question.setText(allQuestions.get(position).getQuestion());
        Log.d("QuestionAdapter","question-----"+allQuestions.get(position).getQuestion());
        ((ViewPager) container).addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}
