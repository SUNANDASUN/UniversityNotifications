package com.indianservers.universitynotifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.indianservers.universitynotifications.adapters.QuestionsFragmentAdapter;
import com.indianservers.universitynotifications.adapters.SingleGridview;
import com.indianservers.universitynotifications.classes.QuestionsClass;
import com.indianservers.universitynotifications.classes.SingleGridviewAdapter;
import com.indianservers.universitynotifications.database.DatabaseAssertHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Quiz extends AppCompatActivity implements QuestionFragment.OnHeadlineSelectedListener,View.OnClickListener {

    public int setId, topicId, subjectId, totalQuestions;
    public static List<QuestionsClass> questionsClassList = new ArrayList<>();
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    DatabaseAssertHelper assertHelper;
    ImageView left,right;
    String setName;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1, fav, QID;
    int secs = 0;
    int mins = 0;
    public static String type;
    private SharedPreferences settings;
    Handler handler = new Handler();
    public static TextView questionTimer;
    private Firebase firebase;
    private ImageView comeout,comein;
    private GridView gridView;
    public static int positionitem ;
    ViewPager viewPager;
    public  static ArrayList<String> strings = new ArrayList<>();
    private ArrayList<SingleGridview> singleGridviews = new ArrayList<>();
    private LinearLayout linearLayout;
    public static HashMap<Integer,Integer> answers = new HashMap<>();
    public static HashMap<Integer,Integer> totlaque = new HashMap<>();
    QuestionsFragmentAdapter questionsAdapter;
    SingleGridviewAdapter singleGridviewAdapter;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView,mAdViw;
    public Runnable updateTimer = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;

            updatedtime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;

            questionTimer.setText("" + mins + ":" + String.format("%02d", secs));
            questionTimer.setTextColor(Color.WHITE);
            handler.postDelayed(this, 0);
        }

    };
    String selection = assertHelper.KEY_QSETID + " =? " + " AND " + assertHelper.KEY_QTID + " =? ";

    public Quiz() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        questionTimer = (TextView) findViewById(R.id.question_timer);
        comeout = (ImageView)findViewById(R.id.come);
        comeout.setOnClickListener(this);
        comein = (ImageView)findViewById(R.id.out);
        comein.setOnClickListener(this);
        left = (ImageView)findViewById(R.id.left);
        left.setOnClickListener(this);
        right = (ImageView)findViewById(R.id.right);
        right.setOnClickListener(this);
        mobileAd();
        gridView = (GridView)findViewById(R.id.selectoptiongridview);
        linearLayout = (LinearLayout)findViewById(R.id.chooseoption);
        viewPager = (ViewPager) findViewById(R.id.pager);
        Firebase.setAndroidContext(Quiz.this);
        if(positionitem==15||positionitem==30||positionitem==45||positionitem==70||positionitem==85){
            interstitialAd();
        }
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        type = settings.getString("RRBTYPE", "0");
        if(type.equals("Offline")){
            Intent intent = getIntent();
            setId = intent.getIntExtra("SetID", 0);
            topicId = intent.getIntExtra("TopicId", 0);

            //subjectId = intent.getIntExtra("SubjectId", 0);
            setName = intent.getStringExtra("SetName");
            Log.v("sai", "set Name------------" + setName);
            totalQuestions = intent.getIntExtra("TotalQuestions", 0);

            databaseConnection();
            questionsClassList = getQuestions();
            QuestionsFragmentAdapter questionsAdapter = new QuestionsFragmentAdapter(getSupportFragmentManager(), this, questionsClassList);
            viewPager.setAdapter(questionsAdapter);
            Log.v("sai", "fav value-in on create------------" + fav);
        }
        else if(type.equals("online"))
        {
            questionsClassList.clear();
                Intent intent = getIntent();
                String subname = intent.getStringExtra("subname");
                firebase = new Firebase("https://universitesnotifications.firebaseio.com/OnlineExam/Subject"+"/"+subname+"/"+"0");
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int total = (int) dataSnapshot.getChildrenCount();
                        // let's say userB is actually 2nd in the list
                        int i = 1;
                        for(DataSnapshot ds:dataSnapshot.getChildren()){

                            QuestionsClass questionsClass = new QuestionsClass();
                            questionsClass.setQ( String.valueOf(ds.child("Q").getValue()));
                            questionsClass.setCA( String.valueOf(ds.child("CA").getValue()));
                            questionsClass.setOpt1( String.valueOf(ds.child("Op1").getValue()));
                            questionsClass.setOpt2( String.valueOf(ds.child("Op2").getValue()));
                            questionsClass.setOpt3( String.valueOf(ds.child("Op3").getValue()));
                            questionsClass.setOpt4( String.valueOf(ds.child("Op4").getValue()));
                            questionsClass.setOpt5(String.valueOf(ds.child("Op5").getValue()));
                            SingleGridview singleGridview = new SingleGridview();
                            singleGridview.setQno(String.valueOf(i++));
                            singleGridviews.add(singleGridview);
                            questionsClassList.add(questionsClass);
                        }
                        questionsAdapter = new QuestionsFragmentAdapter(getSupportFragmentManager(), Quiz.this, questionsClassList);
                        viewPager.setAdapter(questionsAdapter);
                        questionsAdapter.getItem(0);
                        singleGridviewAdapter = new SingleGridviewAdapter(Quiz.this,singleGridviews);
                        gridView.setAdapter(singleGridviewAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                positionitem = position;
                                questionsAdapter.getItem(positionitem);
                                viewPager.setCurrentItem(positionitem);
                                int op= positionitem;
                                singleGridviewAdapter.setPositionSelected(op);
                                questionsAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
        }
        else if(type.equals("ibps")){
            questionsClassList.clear();
            Intent intent = getIntent();
            String subname = intent.getStringExtra("subname");
            String subid = intent.getStringExtra("examid");
            firebase = new Firebase("https://universitesnotifications.firebaseio.com/JNTUK/IBPS/modelsets"+"/"+subid+"/"+subname+"/");
            firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int total = (int) dataSnapshot.getChildrenCount();
                    // let's say userB is actually 2nd in the list
                    int i = 1;
                    for(DataSnapshot ds:dataSnapshot.getChildren()){

                        QuestionsClass questionsClass = new QuestionsClass();
                        questionsClass.setQ( String.valueOf(ds.child("question").getValue()));
                        questionsClass.setCA( String.valueOf(ds.child("answer").getValue()));
                        questionsClass.setOpt1( String.valueOf(ds.child("opt1").getValue()));
                        questionsClass.setOpt2( String.valueOf(ds.child("opt2").getValue()));
                        questionsClass.setOpt3( String.valueOf(ds.child("opt3").getValue()));
                        questionsClass.setOpt4( String.valueOf(ds.child("opt4").getValue()));
                        questionsClass.setOpt5(String.valueOf(ds.child("opt5").getValue()));
                        SingleGridview singleGridview = new SingleGridview();
                        singleGridview.setQno(String.valueOf(i++));
                        singleGridviews.add(singleGridview);
                        questionsClassList.add(questionsClass);
                    }
                    questionsAdapter = new QuestionsFragmentAdapter(getSupportFragmentManager(), Quiz.this, questionsClassList);
                    viewPager.setAdapter(questionsAdapter);
                    questionsAdapter.getItem(0);
                    singleGridviewAdapter = new SingleGridviewAdapter(Quiz.this,singleGridviews);
                    gridView.setAdapter(singleGridviewAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            positionitem = position;
                            questionsAdapter.getItem(positionitem);
                            viewPager.setCurrentItem(positionitem);
                            int op= positionitem;
                            singleGridviewAdapter.setPositionSelected(op);
                            questionsAdapter.notifyDataSetChanged();
                        }
                    });

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        //getting data from the sets intent
    }

    public void setFavQuestion() {

        assertHelper = new DatabaseAssertHelper(this);
        assertHelper.updateFavValue(fav, QID);
    }
//    public void updateFavValue(){
//        assertHelper = new DatabaseAssertHelper(this);
//
//        sqLiteDatabase = assertHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        Log.v("sai","fav value in update function-1111------------"+fav);
//        values.put(assertHelper.KEY_FAV,fav);
//
//        String selection1 = assertHelper.KEY_QID+"= ?"+QID;
//        Log.v("sai","fav value in update function-------------"+fav);
//       // String[] selectionArgs1 = {String.valueOf(QID)};
//        Log.v("sai","Values value in update function-----2525--------"+values.get(assertHelper.KEY_FAV));
//        if (QID!=0&&fav!=0) {
//            int rowId = sqLiteDatabase.update(assertHelper.TABLE_QUESTIONS, values, selection1, null);
//            Log.v("sai","Row Id in update function-------------"+rowId);
//        }
//
//
//
//
//    }
public void interstitialAd() {
    mInterstitialAd = newInterstitialAd();
    loadInterstitial();
}
private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.InterstitialAd));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {

            }
        });
        return interstitialAd;
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

        }
    }
    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }
    private void mobileAd() {
        MobileAds.initialize(this, getString(R.string.banner_ad));

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.adView);
        mAdViw = (AdView)findViewById(R.id.adView1);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        mAdViw.loadAd(adRequest);
    }
    public void databaseConnection() {

        assertHelper = new DatabaseAssertHelper(this);

        sqLiteDatabase = assertHelper.getReadableDatabase();

        String[] selectionArgs = {String.valueOf(setId), String.valueOf(topicId)};
        cursor = sqLiteDatabase.query(assertHelper.TABLE_QUESTIONS, null, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to Quit Exam???");


        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        alertDialogBuilder.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        starttime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimer, 0);
        t = 0;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        starttime = 0L;

        timeSwapBuff = 0L;
        updatedtime = 0L;
        t = 1;
        secs = 0;
        mins = 0;


        handler.removeCallbacks(updateTimer);
        questionTimer.setText("00:00");
    }

    @Override
    public void onArticleSelected(int quesID, int favourite) {

        this.QID = quesID;
        this.fav = favourite;

//        Log.v("sai","fav value-in selected------------"+fav);
//        assertHelper = new DatabaseAssertHelper(this);
//
//        sqLiteDatabase = assertHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        Log.v("sai","fav value in update function-1111------------"+fav);
//        values.put(assertHelper.KEY_FAV,fav);
//
//        String selection1 = assertHelper.KEY_QID+"= ?"+QID;
//        Log.v("sai","fav value in update function-------------"+fav);
//        // String[] selectionArgs1 = {String.valueOf(QID)};
//        Log.v("sai","Values value in update function-----2525--------"+values.get(assertHelper.KEY_FAV));
//        if (QID!=0&&fav!=0) {
//            int rowId = sqLiteDatabase.update(assertHelper.TABLE_QUESTIONS, values, selection1, null);
//            Log.v("sai","Row Id in update function-------------"+rowId);
//        }


    }

    public List<QuestionsClass> getQuestions() {

        Log.d("Quiz.class", "cursor count-------" + cursor.getCount());
        List<QuestionsClass> questionsClasses = new ArrayList<QuestionsClass>();
        int qIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_QID);
        int setIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_QSETID);
        int tIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_QTID);
        // int sIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_QSID);
        int correctColumnIndex = cursor.getColumnIndex(assertHelper.KEY_CORRECT);
        int favColumnIndex = cursor.getColumnIndex(assertHelper.KEY_FAV);
        int questionColumnIndex = cursor.getColumnIndex(assertHelper.KEY_QUES);
        int opt1ColumnIndex = cursor.getColumnIndex(assertHelper.KEY_OPT1);
        int opt2ColumnIndex = cursor.getColumnIndex(assertHelper.KEY_OPT2);
        int opt3ColumnIndex = cursor.getColumnIndex(assertHelper.KEY_OPT3);
        int opt4ColumnIndex = cursor.getColumnIndex(assertHelper.KEY_OPT4);
        int descriptionColumIndex = cursor.getColumnIndex(assertHelper.KEY_DESC);
        Log.d("sai", "cursor count-------" + cursor.getCount());

        for (int i = 0; i < cursor.getCount(); i++) {

            QuestionsClass obj = new QuestionsClass(
                    cursor.getInt(qIDColumnIndex),
                    cursor.getInt(setIDColumnIndex),
                    cursor.getInt(tIDColumnIndex),
                    // cursor.getInt(sIDColumnIndex),
                    cursor.getInt(correctColumnIndex),
                    cursor.getInt(favColumnIndex),
                    cursor.getString(questionColumnIndex).trim(),
                    cursor.getString(opt1ColumnIndex).trim(),
                    cursor.getString(opt2ColumnIndex).trim(),
                    cursor.getString(opt3ColumnIndex).trim(),
                    cursor.getString(opt4ColumnIndex).trim(),
                    cursor.getString(descriptionColumIndex));
            questionsClasses.add(obj);
            cursor.moveToNext();
        }
        cursor.close();
        return questionsClasses;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.come:
                comein.setVisibility(View.VISIBLE);
                comeout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

                break;
            case R.id.out:
                comein.setVisibility(View.GONE);
                comeout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                break;

            case R.id.left:
                int i = positionitem;
                i--;
                viewPager.setCurrentItem(i);
                positionitem = i;
                break;

            case R.id.right:
                int j = positionitem;
                j++;
                viewPager.setCurrentItem(j);
                positionitem = j;
                break;
        }
    }
}
