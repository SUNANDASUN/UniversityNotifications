package com.indianservers.universitynotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.indianservers.universitynotifications.app.Config;
import com.indianservers.universitynotifications.util.NotificationUtils;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        LinearLayout notifications = (LinearLayout)findViewById(R.id.notifications);
        notifications.setOnClickListener(this);
        LinearLayout results = (LinearLayout)findViewById(R.id.results);
        results.setOnClickListener(this);
        LinearLayout academiccalender = (LinearLayout)findViewById(R.id.calender);
        academiccalender.setOnClickListener(this);
        LinearLayout syllabus = (LinearLayout)findViewById(R.id.syllabus);
        syllabus.setOnClickListener(this);
        LinearLayout timetable = (LinearLayout)findViewById(R.id.timetables);
        timetable.setOnClickListener(this);
        LinearLayout oldquepapers = (LinearLayout)findViewById(R.id.oldquestionpapers);
        oldquepapers.setOnClickListener(this);
        LinearLayout jobupdates = (LinearLayout)findViewById(R.id.jobupdates);
        jobupdates.setOnClickListener(this);
        LinearLayout rrb = (LinearLayout)findViewById(R.id.rrb);
        rrb.setOnClickListener(this);
        LinearLayout events = (LinearLayout)findViewById(R.id.jntuevents);
        events.setOnClickListener(this);
        LinearLayout offcampus = (LinearLayout)findViewById(R.id.jntuoffcampusdrives);
        offcampus.setOnClickListener(this);
        LinearLayout rrbonline = (LinearLayout)findViewById(R.id.rrbonlineexam);
        rrbonline.setOnClickListener(this);
        LinearLayout gate = (LinearLayout)findViewById(R.id.gateexam);
        gate.setOnClickListener(this);
        LinearLayout ibps = (LinearLayout)findViewById(R.id.ibpsexams);
        ibps.setOnClickListener(this);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notifications:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JNTUNOTIFICATIONS");
                editor.commit();
                Intent notification = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(notification);
                break;
            case R.id.results:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JNTURESULTS");
                editor.commit();
                Intent results = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(results);
                break;
            case R.id.calender:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JNTUACADEMICCALENDER");
                editor.commit();
                Intent calender = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(calender);
                break;
            case R.id.syllabus:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JNTUSYLLABUS");
                editor.commit();
                Intent syllabus = new Intent(DashboardActivity.this,OtherActivity.class);
                startActivity(syllabus);
                break;
            case R.id.timetables:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JNTUTIMETABLE");
                editor.commit();
                Intent timetable = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(timetable);
                break;
            case R.id.oldquestionpapers:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JNTUOLDQUESTIONPAPERS");
                editor.commit();
                Intent oldquepapers = new Intent(DashboardActivity.this,OtherActivity.class);
                startActivity(oldquepapers);
                break;
            case R.id.jobupdates:
                editor = settings.edit();
                editor.putString("JNTUTYPE","JOBUPDATES");
                editor.commit();
                Intent jobupdates = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(jobupdates);
                break;
            case R.id.jntuevents:
                editor = settings.edit();
                editor.putString("JNTUTYPE","EVENTS");
                editor.commit();
                Intent events = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(events);
                break;
            case R.id.jntuoffcampusdrives:
                editor = settings.edit();
                editor.putString("JNTUTYPE","OFFCAMPUSDRIVES");
                editor.commit();
                Intent offcampus = new Intent(DashboardActivity.this,NotificationsActivity.class);
                startActivity(offcampus);
                break;
            case R.id.rrb:
                editor = settings.edit();
                editor.putString("RRBTYPE","Offline");
                editor.commit();
                Intent rrb = new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(rrb);
                break;
            case R.id.rrbonlineexam:
                editor = settings.edit();
                editor.putString("RRBTYPE","online");
                editor.commit();
                Intent rrbonline = new Intent(DashboardActivity.this,RRBExamsActivity.class);
                startActivity(rrbonline);
                break;
            case R.id.gateexam:
                Intent gate = new Intent(DashboardActivity.this,GateActivity.class);
                startActivity(gate);
                break;
            case R.id.ibpsexams:
                editor = settings.edit();
                editor.putString("RRBTYPE","ibps");
                editor.commit();
                Intent ibps = new Intent(DashboardActivity.this,IBPSActivity.class);
                startActivity(ibps);
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_resume, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.jntuk:
                editor = settings.edit();
                editor.putString("JNTU", "JNTUK");
                editor.commit();
                finish();
                startActivity(getIntent());
                break;
            case R.id.jntua:
                editor = settings.edit();
                editor.putString("JNTU", "JNTUA");
                editor.commit();
                finish();
                startActivity(getIntent());
                break;
            case R.id.jntuh:
                editor = settings.edit();
                editor.putString("JNTU", "JNTUH");
                editor.commit();
                finish();
                startActivity(getIntent());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                })
                .show();

    }
}
