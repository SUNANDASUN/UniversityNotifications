package com.indianservers.universitynotifications.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.android.gms.ads.InterstitialAd;
import com.indianservers.universitynotifications.QuestionFragment;
import com.indianservers.universitynotifications.classes.QuestionsClass;

import java.util.List;

/**
 * Created by hp on 01-Jun-17.
 */

public class QuestionsFragmentAdapter extends FragmentStatePagerAdapter {
    private InterstitialAd mInterstitialAd;
    Context context;
    List<QuestionsClass> allQuestions;
    private SharedPreferences settings;
    String type;
    private int PositionSelected = 0;
    public QuestionsFragmentAdapter(FragmentManager fm, Context context, List<QuestionsClass> allQuestions) {
        super(fm);
        this.context = context;
        this.allQuestions = allQuestions;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
         type = settings.getString("RRBTYPE", "0");
    }

    public QuestionsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setPositionSelected(int position)
    {
        PositionSelected = position;
        this.notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        //creating a fragment object
        Fragment fragment = new QuestionFragment();
        if(type.equals("Offline")){
            //sending the data to the fragment
            Bundle bundle = new Bundle();
            bundle.putInt("Count", position + 1);
            bundle.putInt("questionID", allQuestions.get(position).getQid());
            bundle.putString("question", allQuestions.get(position).getQuestion());
            bundle.putString("opt1", allQuestions.get(position).getOpt1());
            bundle.putString("opt2", allQuestions.get(position).getOpt2());
            bundle.putString("opt3", allQuestions.get(position).getOpt3());
            bundle.putString("opt4", allQuestions.get(position).getOpt4());
            bundle.putInt("correct", allQuestions.get(position).getCorrectAnswer());
            bundle.putInt("totalQuestions", allQuestions.size());
            bundle.putString("desc", allQuestions.get(position).getDesc());
            fragment.setArguments(bundle);
        }else if(type.equals("online")){
            //sending the data to the fragment
            Bundle bundle = new Bundle();
            bundle.putInt("Count", position + 1);
            bundle.putString("question", allQuestions.get(position).getQ());
            bundle.putString("opt1", allQuestions.get(position).getOpt1());
            bundle.putString("opt2", allQuestions.get(position).getOpt2());
            bundle.putString("opt3", allQuestions.get(position).getOpt3());
            bundle.putString("opt4", allQuestions.get(position).getOpt4());
            bundle.putString("opt5",allQuestions.get(position).getOpt5());
            if(allQuestions.get(position).getCA().equals("a")||allQuestions.get(position).getCA().equals("A")){
                bundle.putInt("correct",1);
            }
            else if(allQuestions.get(position).getCA().equals("b")||allQuestions.get(position).getCA().equals("B")){
                bundle.putInt("correct",2);
            }
            else if(allQuestions.get(position).getCA().equals("c")||allQuestions.get(position).getCA().equals("C")){
                bundle.putInt("correct",3);
            }
            else if(allQuestions.get(position).getCA().equals("d")||allQuestions.get(position).getCA().equals("D")){
                bundle.putInt("correct",4);
            }
            else if(allQuestions.get(position).getCA().equals("e")||allQuestions.get(position).getCA().equals("E")){
                bundle.putInt("correct",5);
            }
            bundle.putInt("totalQuestions", allQuestions.size());
            fragment.setArguments(bundle);
        }
        else if(type.equals("ibps")){
            Bundle bundle = new Bundle();
            bundle.putInt("Count", position + 1);
            bundle.putString("question", allQuestions.get(position).getQ());
            bundle.putString("opt1", allQuestions.get(position).getOpt1());
            bundle.putString("opt2", allQuestions.get(position).getOpt2());
            bundle.putString("opt3", allQuestions.get(position).getOpt3());
            bundle.putString("opt4", allQuestions.get(position).getOpt4());
            bundle.putString("opt5",allQuestions.get(position).getOpt5());
            if(allQuestions.get(position).getCA().equals("a")||allQuestions.get(position).getCA().equals("A")){
                bundle.putInt("correct",1);
            }
            else if(allQuestions.get(position).getCA().equals("b")||allQuestions.get(position).getCA().equals("B")){
                bundle.putInt("correct",2);
            }
            else if(allQuestions.get(position).getCA().equals("c")||allQuestions.get(position).getCA().equals("C")){
                bundle.putInt("correct",3);
            }
            else if(allQuestions.get(position).getCA().equals("d")||allQuestions.get(position).getCA().equals("D")){
                bundle.putInt("correct",4);
            }
            else if(allQuestions.get(position).getCA().equals("e")||allQuestions.get(position).getCA().equals("E")){
                bundle.putInt("correct",5);
            }
            bundle.putInt("totalQuestions", allQuestions.size());
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return allQuestions.size();
    }
}
