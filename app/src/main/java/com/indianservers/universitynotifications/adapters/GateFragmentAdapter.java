package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.android.gms.ads.InterstitialAd;
import com.indianservers.universitynotifications.GateFragment;
import com.indianservers.universitynotifications.models.GateQuestionModelClass;

import java.util.List;

/**
 * Created by Prabhu on 28-12-2017.
 */

public class GateFragmentAdapter extends FragmentStatePagerAdapter {
    private InterstitialAd mInterstitialAd;
    Context context;
    List<GateQuestionModelClass> allQuestions;
    private SharedPreferences settings;
    String type;
    private int PositionSelected = 0;

    public GateFragmentAdapter(FragmentManager fm, Context context, List<GateQuestionModelClass> allQuestions) {
        super(fm);
        this.context = context;
        this.allQuestions = allQuestions;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        type = settings.getString("RRBTYPE", "0");
    }
    public GateFragmentAdapter(FragmentManager manager){
        super(manager);
    }
    public void setPositionSelected(int position)
    {
        PositionSelected = position;
        this.notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new GateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Count", position + 1);
        bundle.putString("gateqno", allQuestions.get(position).getQno());
        bundle.putString("question", allQuestions.get(position).getQuestion());
        bundle.putString("qutype", allQuestions.get(position).getQtype());
        bundle.putString("gatepmark", allQuestions.get(position).getPoitive());
        bundle.putString("gatenmark", allQuestions.get(position).getNegative());
        bundle.putString("gateexamtype", allQuestions.get(position).getExamtype());
        bundle.putString("gateExplanation",allQuestions.get(position).getExplanation());
        bundle.putInt("totalQuestions", allQuestions.size());

        if(allQuestions.get(position).getAnswer().equals("a")||allQuestions.get(position).getAnswer().equals("A")){
            bundle.putString("correct",String.valueOf(1));
        }
        else if(allQuestions.get(position).getAnswer().equals("b")||allQuestions.get(position).getAnswer().equals("B")){
            bundle.putString("correct",String.valueOf(2));
        }
        else if(allQuestions.get(position).getAnswer().equals("c")||allQuestions.get(position).getAnswer().equals("C")){
            bundle.putString("correct",String.valueOf(3));
        }
        else if(allQuestions.get(position).getAnswer().equals("d")||allQuestions.get(position).getAnswer().equals("D")){
            bundle.putString("correct",String.valueOf(4));
        }
        else if(allQuestions.get(position).getAnswer().equals("e")||allQuestions.get(position).getAnswer().equals("E")){
            bundle.putString("correct",String.valueOf(5));
        }else {
            bundle.putString("correct", allQuestions.get(position).getAnswer());
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return allQuestions.size();
    }

    public interface OnHeadlineSelectedListener {
    }
}
