package com.indianservers.universitynotifications;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.indianservers.universitynotifications.adapters.TopicsListAdapter;
import com.indianservers.universitynotifications.classes.TopicClass;
import com.indianservers.universitynotifications.database.DatabaseAssertHelper;


import java.util.ArrayList;
import java.util.List;

public class Topics extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<TopicClass> topics = new ArrayList<>();
    ListView topicsList;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    DatabaseAssertHelper assertHelper;
    String[] projection = {assertHelper.KEY_TNAME, assertHelper.KEY_TSID, assertHelper.KEY_TID, assertHelper.KEY_TSTATUS, assertHelper.KEY_TQ};
    //String topics[];
    String selection = assertHelper.KEY_TSID + "=?";
    int subjectid;
    String subjectName;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        subjectid = intent.getIntExtra("SubjectID", 0);
        subjectName = intent.getStringExtra("SubjectName");
        setTitle(subjectName);
        //Log.d("Topics.class","SUbject ID-----"+subjectid);
        topicsList = (ListView) findViewById(R.id.topics_list);
        databaseConnection();
        //  topics = new String[cursor.getCount()];
        topics = getTopics();

        TopicsListAdapter adapter = new TopicsListAdapter(this, topics);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        topicsList.setAdapter(adapter);
        topicsList.setOnItemClickListener(this);


        setupWindowAnimations();
    }

    public void databaseConnection() {

        assertHelper = new DatabaseAssertHelper(this);

        String[] selectionArgs = {String.valueOf(subjectid)};
        sqLiteDatabase = assertHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query(assertHelper.TABLE_TOPICS, projection, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();


    }

    //banner Ads code


    public List<TopicClass> getTopics() {


        List<TopicClass> topicNames = new ArrayList<>();

        // int subjectid[] = new int[cursor.getCount()];

        int tIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_TID);
        int tSIDColumnIndex = cursor.getColumnIndex(assertHelper.KEY_TSID);
        int tNameColumnIndex = cursor.getColumnIndex(assertHelper.KEY_TNAME);
        int tStatusColumnIndex = cursor.getColumnIndex(assertHelper.KEY_TSTATUS);
        int TQColumnIndex = cursor.getColumnIndex(assertHelper.KEY_TQ);
        for (int i = 0; i < cursor.getCount(); i++) {


            //topicNmes[i] = cursor.getString(nameColumnIndex).trim();

            TopicClass tc = new TopicClass(cursor.getInt(tIDColumnIndex), cursor.getInt(tSIDColumnIndex),
                    cursor.getInt(tStatusColumnIndex), cursor.getInt(TQColumnIndex), cursor.getString(tNameColumnIndex));
            topicNames.add(tc);
            cursor.moveToNext();

        }

        cursor.close();
        return topicNames;

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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TopicClass topicClass = topics.get(position);
        Intent intent = new Intent(getApplicationContext(), SetActivity.class);

        intent.putExtra("TopicID", topicClass.getTopicId());
        intent.putExtra("SubjectID", topicClass.getSubjectId());
        intent.putExtra("TopicName", topicClass.getTopicName());



        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, topicClass.getTopicId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, topicClass.getTopicName());

        Log.v("sai","item id-----------"+topicClass.getTopicId());
        Log.v("sai","item name-----------"+topicClass.getTopicName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //Sets whether analytics collection is enabled for this app on this device.
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setMinimumSessionDuration(2000);
        mFirebaseAnalytics.setSessionTimeoutDuration(300000);
        //Sets the user ID property.
        mFirebaseAnalytics.setUserId(String.valueOf(topicClass.getTopicId()));

        //Sets a user property to a given value.
        mFirebaseAnalytics.setUserProperty("Topic", topicClass.getTopicName());

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
