package com.indianservers.universitynotifications;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
        private ArrayList<String > list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);

        Intent intent = getIntent();
        int tonum = intent.getIntExtra("totalqu",0);
        int rightansw=intent.getIntExtra("rightanswers",0);
        list = intent.getStringArrayListExtra("listdesc");
        String examtime = intent.getStringExtra("overalltime");

        TextView totalqu = (TextView)findViewById(R.id.totalQues);
        TextView examTime = (TextView)findViewById(R.id.examtime);
        totalqu.setText(String.valueOf(tonum).toString());
        examTime.setText(String.valueOf(examtime).toString());
        int wrongansw = tonum-rightansw;
        mPieChart.addPieSlice(new PieModel("RighrAnswers", rightansw, Color.parseColor("#388E3C")));
        mPieChart.addPieSlice(new PieModel("WrongAnswers", wrongansw, Color.parseColor("#F44336")));

        mPieChart.startAnimation();

        ListView listView = (ListView)findViewById(R.id.descriptionslist);
        try{
            if(Quiz.type.equals("Offline")){
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list){
                    public String getItem(int position)
                    {
                        int po = position+1;
                        return String.valueOf(Html.fromHtml("\n"+"Question " + po +" Correct Answer"+"<br>"+ list.get(position)));
                    }
                };
                listView.setAdapter(arrayAdapter);
            }else{
                listView.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }




        Button fi = (Button)findViewById(R.id.submit);
        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(Quiz.type.equals("Offline")){
                        startActivity(new Intent(ResultActivity.this,MainActivity.class));
                        finish();
                    }else if(Quiz.type.equals("online")){
                        startActivity(new Intent(ResultActivity.this,RRBExamsActivity.class));
                        finish();
                    }
                    else if(Quiz.type.equals("ibps")){
                        startActivity(new Intent(ResultActivity.this,IBPSActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(ResultActivity.this,GateActivity.class));
                        finish();
                    }
                }catch (NullPointerException e){

                }


            }
        });
    }
}
