package com.indianservers.universitynotifications;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.analytics.FirebaseAnalytics;

import com.indianservers.rrbexams.database.DatabaseAssetHelperTest;
import com.indianservers.universitynotifications.adapters.GridViewAdapter;
import com.indianservers.universitynotifications.classes.SubjectClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<SubjectClass> Subjects = new ArrayList<>();
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    DatabaseAssetHelperTest assertHelper;
    //String[] projection = { assertHelper.KEY_SID,assertHelper.KEY_SNAME,};
    GridView gridView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridView = (GridView) findViewById(R.id.gridview);
        databaseConnection();
        Subjects = getSubjects();
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, Subjects);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupWindowAnimations();

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

    public void databaseConnection() {

        assertHelper = new DatabaseAssetHelperTest(this);

        sqLiteDatabase = assertHelper.getReadableDatabase();

        cursor = sqLiteDatabase.query(assertHelper.getTABLE_SUBJECTS(), null, null, null, null, null, null);
        cursor.moveToFirst();


    }

    public List<SubjectClass> getSubjects() {


        List<SubjectClass> subjectclassnames = new ArrayList<SubjectClass>();
        for (int i = 0; i < cursor.getCount(); i++) {

            int subID = cursor.getColumnIndex(assertHelper.getKEY_SID());
            int nameColumnIndex = cursor.getColumnIndex(assertHelper.getKEY_SNAME());
            SubjectClass obj = new SubjectClass(cursor.getInt(subID), cursor.getString(nameColumnIndex).trim());
            subjectclassnames.add(obj);
            cursor.moveToNext();

        }
        cursor.close();
        return subjectclassnames;

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                SubjectClass obj = Subjects.get(position);

                Intent intent = new Intent(this, Topics.class);
                intent.putExtra("SubjectID", obj.getId());
                intent.putExtra("SubjectName", obj.getSName());
                Bundle bundle = new Bundle();
                bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, obj.getId());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, obj.getSName());

                //Logs an app event.
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                //Sets whether analytics collection is enabled for this app on this device.
                mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
                mFirebaseAnalytics.setMinimumSessionDuration(2000);
                mFirebaseAnalytics.setSessionTimeoutDuration(300000);
                //Sets the user ID property.
                mFirebaseAnalytics.setUserId(String.valueOf(obj.getId()));

                //Sets a user property to a given value.
                mFirebaseAnalytics.setUserProperty("Subject", obj.getSName());
                startActivity(intent);

                // overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right);
                break;

            case 10:
//                Intent fav = new Intent(this,FormulaTest.class);
//                startActivity(fav);
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
//                else {
//                    Log.d("sai","Ad not loaded ");
//                }
                break;
            case 11:
                Intent moreApps = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/search?q=tejaprakash"));
                startActivity(moreApps);
        }



    }
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
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

