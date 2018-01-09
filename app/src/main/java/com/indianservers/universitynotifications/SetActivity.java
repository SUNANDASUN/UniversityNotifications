package com.indianservers.universitynotifications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indianservers.universitynotifications.adapters.GridViewAdapter;
import com.indianservers.universitynotifications.adapters.SetNameAdapter;
import com.indianservers.universitynotifications.classes.SetClass;
import com.indianservers.universitynotifications.classes.SubjectClass;
import com.indianservers.universitynotifications.database.DatabaseAssertHelper;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SetActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    int topicID, subjectId;
    String topicName;
    List<SetClass> setNames = new ArrayList<>();
    ListView topicsList;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    DatabaseAssertHelper assertHelper;
    String selection = assertHelper.KEY_STID + "=?";
    private Firebase firebase;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String mode ,adapterposition;
    private AlertDialog alertDialog;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private GridView setName;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String type = settings.getString("RRBTYPE", "0");
        //assigning gridview id to the class
        setName = (GridView) findViewById(R.id.setgridview);
        Firebase.setAndroidContext(SetActivity.this);
        if(type.equals("ibps")){
            Intent intent = getIntent();
            final String examid = intent.getStringExtra("liveexam");
            firebase = new Firebase("https://universitesnotifications.firebaseio.com/JNTUK/IBPS/modelsets");
            firebase.child(examid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            SetClass setClass = new SetClass();
                            setClass.setSetName((String) ds.getKey());
                            setNames.add(setClass);
                        }
                        SetNameAdapter setNameAdapter = new SetNameAdapter(SetActivity.this, setNames);
                        setName.setAdapter(setNameAdapter);
                        setName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final String type = ((TextView)view.findViewById(R.id.setname)).getText().toString();

                                final AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
                                LayoutInflater inflater = SetActivity.this.getLayoutInflater();
                                final View dialogView = inflater.inflate(R.layout.modeofexam, null);
                                builder.setView(dialogView);
                                alertDialog = builder.create();
                                alertDialog.show();
                                Button practice = (Button)dialogView.findViewById(R.id.practice);
                                practice.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mode = "practice";
                                        editor = settings.edit();
                                        editor.putString("exammode",mode);
                                        editor.commit();
                                        Intent intent1 = new Intent(SetActivity.this,Quiz.class);
                                        intent1.putExtra("subname",type);
                                        intent1.putExtra("examid",examid);
                                        startActivity(intent1);
                                        alertDialog.dismiss();
                                    }
                                });
                                Button test =(Button)dialogView.findViewById(R.id.test);
                                test.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mode = "test";
                                        editor = settings.edit();
                                        editor.putString("exammode",mode);
                                        editor.commit();
                                        Intent intent1 = new Intent(SetActivity.this,Quiz.class);
                                        intent1.putExtra("subname",type);
                                        intent1.putExtra("examid",examid);
                                        startActivity(intent1);
                                        alertDialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }else {
            //getting data from the topics intent
            Intent intent = getIntent();
            topicID = intent.getIntExtra("TopicID", 0);
            subjectId = intent.getIntExtra("SubjectID", 0);
            topicName = intent.getStringExtra("TopicName");
            // selection=selection+ "="+ topicID;
            Log.v("Sai", "selection===========" + assertHelper.KEY_STID);
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            setupWindowAnimations();
            setTitle(topicName);

            // Log.d("SetActivity.class", "topicName-----" + topicName);

            //database connection
            databaseConnection();

            //getting all the sets table info
            setNames = getSets();
            SetNameAdapter setNameAdapter = new SetNameAdapter(this, setNames);
            setName.setAdapter(setNameAdapter);
        }



            //doing action when the item on girdview is clicked
            setName.setOnItemClickListener(this);
                 }
    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Re-enter transition is executed when returning back to this activity
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.LEFT); // Use START if using right - to - left locale
            slideTransition.setDuration(1000);

            getWindow().setReenterTransition(slideTransition);  // When MainActivity Re-enter the Screen
            getWindow().setExitTransition(slideTransition);     // When MainActivity Exits the Screen

            // For overlap of Re Entering Activity - MainActivity.java and Exiting TransitionActivity.java

            getWindow().setAllowReturnTransitionOverlap(false);
        }
    }
    /**
     * Connecting to the database
     */
    public void databaseConnection() {

        assertHelper = new DatabaseAssertHelper(this);

        String[] selectionArgs = {String.valueOf(topicID)};
        Log.v("sai", "SetActivity.class0 Selection -------" + selection);
        sqLiteDatabase = assertHelper.getReadableDatabase();

        Log.v("sai", "No Error 1");
        //selectionArgs //selection
        cursor = sqLiteDatabase.query(assertHelper.TABLE_SETS, null, selection, selectionArgs, null, null, null);
        Log.v("sai", "No Error 2");
        cursor.moveToFirst();
        Log.v("sai", "No Error 3");


    }

    /**
     * geting the sets table info
     *
     * @return Setclass object contains all the data related to sets table
     */
    public List<SetClass> getSets() {

        List<SetClass> sets = new ArrayList<>();


        Log.v("sai", "No Error 4");
        //getting column index from the database table
        int prmKeyIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_PRMKEY);
        int sTIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_STID);
        int setIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_SETID);
        int setNameColumnIndex = cursor.getColumnIndex(assertHelper.KEY_SETNAME);
        //    int sStatusColumnIndex = cursor.getColumnIndex(assertHelper.KEY_SSTATUS);
        //    int sTQColumnIndex = cursor.getColumnIndex(assertHelper.KEY_STQ);


        Log.v("sai", "No Error 5");
//        Log.v("sai","No Error -----------prmKey IDColumnIndex------"+prmKeyIDColumnIndex);
//        Log.v("sai","No Error -------------Topic IDColumnIndex--------"+sTIDColumnIndex);
//        Log.v("sai","No Error -------------set IDColumnIndex-------"+setIDColumnIndex);
//        Log.v("sai","No Error -------------setName ColumnIndex----------"+setNameColumnIndex);

        //getting the values in the table by using column index
        for (int i = 0; i < cursor.getCount(); i++) {


            SetClass setClassObj = new SetClass(cursor.getInt(prmKeyIDColumnIndex), cursor.getInt(sTIDColumnIndex),
                    cursor.getInt(setIDColumnIndex), cursor.getString(setNameColumnIndex));
            sets.add(setClassObj);
            cursor.moveToNext();
            Log.v("sai", "No Error in LOOP");


        }

        cursor.close();
        Log.v("sai", "No Error 7");
        return sets;

    }

    //banner ad code

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.modeofexam, null);
        builder.setView(dialogView);
        alertDialog = builder.create();
        Button practice = (Button)dialogView.findViewById(R.id.practice);
        practice.setOnClickListener(this);
        Button test =(Button)dialogView.findViewById(R.id.test);
        test.setOnClickListener(this);
        adapterposition = String.valueOf(position);
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.practice:
                mode = "practice";
                startExam(mode);
                alertDialog.dismiss();
                break;
            case R.id.test:
                mode = "test";
                startExam(mode);
                alertDialog.dismiss();
                break;
        }
    }

    private void startExam(String mode) {
        SetClass setClass = setNames.get(Integer.parseInt(adapterposition));
        editor = settings.edit();
        editor.putString("exammode",mode);
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), Quiz.class);
        intent.putExtra("SetID", setClass.getSetId());
        intent.putExtra("TopicId", setClass.getTopicId());
        intent.putExtra("SubjectId", setClass.getSubjectID());
        intent.putExtra("SetName", setClass.getSetName());
        intent.putExtra("TotalQuestions", setClass.getSettotalQuestions());

        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, setClass.getSetId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, setClass.getSetName());

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //Sets whether analytics collection is enabled for this app on this device.
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setMinimumSessionDuration(2000);
        mFirebaseAnalytics.setSessionTimeoutDuration(300000);

        //Sets the user ID property.
        mFirebaseAnalytics.setUserId(String.valueOf(setClass.getSetId()));

        //Sets a user property to a given value.
        mFirebaseAnalytics.setUserProperty("Topics", setClass.getSetName());

//        Log.d("sai","set id intent------"+setClass.getSetId());
//        Log.d("sai","topic  id intent ------"+setClass.getTopicId());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
