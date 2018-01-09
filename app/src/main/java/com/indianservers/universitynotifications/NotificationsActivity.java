package com.indianservers.universitynotifications;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.indianservers.universitynotifications.adapter.CustomAdapter;
import com.indianservers.universitynotifications.models.CommonModel;
import com.indianservers.universitynotifications.service.ConnectionDetector;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationsActivity extends AppCompatActivity {
    private ListView nlist;
    private Firebase firebase;
    private ArrayList<CommonModel> commonModels = new ArrayList<>();
    final static  String DB_URL= "https://universitesnotifications.firebaseio.com/";
    private CustomAdapter adapter;
    private String jntu;
    private String type;
        private ProgressDialog mProgressDialog;
    private ConnectionDetector detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        type = sharedPrefs.getString("JNTUTYPE","1");
        jntu = sharedPrefs.getString("JNTU","1");
        nlist = (ListView)findViewById(R.id.notificationslistview);
        adapter = new CustomAdapter(this, commonModels);
        nlist.setAdapter(adapter);

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.nnn);
        detector = new ConnectionDetector(this);
        if(detector.isNetworkOn(this)){
            mProgressDialog = new ProgressDialog(NotificationsActivity.this);
            mProgressDialog.setMessage("Please Wait...");
            timerDelayRemoveDialog(15*1000,mProgressDialog);
            mProgressDialog.show();
            if(type.equals("JNTUNOTIFICATIONS")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntu+"/"+type);
                getSupportActionBar().setTitle("Updated "+jntu+" Notifications");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }else if(type.equals("JNTURESULTS")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntu+"/"+type);
                getSupportActionBar().setTitle("Updated "+jntu+" Results");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);

            }else if(type.equals("JNTUACADEMICCALENDER")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntu+"/"+type);
                getSupportActionBar().setTitle( jntu+" Calender");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
            else if(type.equals("JOBUPDATES")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntu+"/"+type);
                getSupportActionBar().setTitle("Job Updates");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
            else if(type.equals("EVENTS")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntu+"/"+type);
                getSupportActionBar().setTitle(jntu +" Events");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
            else if(type.equals("OFFCAMPUSDRIVES")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+type);
                getSupportActionBar().setTitle(jntu+" Off Campus Drives");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
            else if(type.equals("JNTUTIMETABLE")){
                Firebase.setAndroidContext(getApplicationContext());
                firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntu+"/"+type);
                getSupportActionBar().setTitle(jntu+" Time Table");
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
            refreshdata();
        }else{
            setSnackBar(layout,"Check Internet Connection");
        }
        }

//    }
    public  void refreshdata() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public void getupdates(DataSnapshot dataSnapshot){

        commonModels.clear();

        for(DataSnapshot ds :dataSnapshot.getChildren()){
            CommonModel d= new CommonModel();
            d.setNotificationdate(ds.getValue(CommonModel.class).getNotificationdate());
            d.setNotificationname(ds.getValue(CommonModel.class).getNotificationname());
            d.setImage(ds.getValue(CommonModel.class).getImage());
            d.setNotificationdesc(ds.getValue(CommonModel.class).getNotificationdesc());
            d.setWeblink(ds.getValue(CommonModel.class).getWeblink());
            commonModels.add(d);

        }
        if(commonModels.size()>0)
        {
            mProgressDialog.dismiss();
            Collections.reverse(commonModels);
            adapter = new CustomAdapter(this, commonModels);
            nlist.setAdapter(adapter);

            nlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String subject = ((TextView) view.findViewById(R.id.subject)).getText().toString();
                    String date = ((TextView) view.findViewById(R.id.ndate)).getText().toString();
                    String desc = ((TextView) view.findViewById(R.id.desc)).getText().toString();
                    String imgurl = ((TextView) view.findViewById(R.id.imglist)).getText().toString();
                    String webUrl = ((TextView) view.findViewById(R.id.weblink)).getText().toString();
                    Intent intent = new Intent(NotificationsActivity.this,SingleNotificationActivity.class);
                    intent.putExtra("subject",subject);
                    intent.putExtra("date",date);
                    intent.putExtra("desc",desc);
                    intent.putExtra("imgurl",imgurl);
                    intent.putExtra("weburl",webUrl);
                    startActivity(intent);
                }
            });
        }else
        {
            mProgressDialog.dismiss();
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void timerDelayRemoveDialog(long time, final Dialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
//            case R.id.changeuni:
//                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = sharedPrefs.edit();
//                editor.remove("JNTU");
//                editor.commit();
//                Intent intent = new Intent(NotificationsActivity.this,UniversityChooseActivity.class);
//                startActivity(intent);
//                this.finish();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setSnackBar(View coordinatorLayout, String snackTitle) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, Snackbar.LENGTH_LONG);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
}
