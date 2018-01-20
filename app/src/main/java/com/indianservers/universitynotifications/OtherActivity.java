package com.indianservers.universitynotifications;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.indianservers.universitynotifications.adapter.OldQuestionsAdapter;
import com.indianservers.universitynotifications.models.OldQuestionsModel;
import com.indianservers.universitynotifications.service.ConnectionDetector;

import java.util.ArrayList;
import java.util.Collections;

public class OtherActivity extends AppCompatActivity {
    private Spinner course,branch,semister;
    private String type, category;
    private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<String> branches = new ArrayList<>();
    private ArrayList<String> semisters = new ArrayList<>();
    private ArrayList<String> regulations = new ArrayList<>();
    private ArrayList<OldQuestionsModel> commonModels = new ArrayList<>();
    private Button save;
    private OldQuestionsAdapter adapter;
    private ProgressDialog mProgressDialog;
    private String lastRegulations, lastYears;
    Firebase firebase;
    private ListView listView;
    public TabLayout Regulations;
    private ConnectionDetector detector;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        type = sharedPrefs.getString("JNTU","1");
        category = sharedPrefs.getString("JNTUTYPE","1");
        layout = (LinearLayout) findViewById(R.id.nn);
        detector = new ConnectionDetector(this);
        if(detector.isNetworkOn(this)){
            if(category.equals("JNTUOLDQUESTIONPAPERS")){
                getSupportActionBar().setTitle(type+" Old Question Papers");

            }else if(category.equals("JNTUSYLLABUS")){
                getSupportActionBar().setTitle(type+" Syllabus");
            }

            Firebase.setAndroidContext(this);
            listView = (ListView)findViewById(R.id.listvieww);
            Regulations = (TabLayout)findViewById(R.id.regulation);
            Regulations.setVisibility(View.GONE);
            showAlertDialog();
        }
        else {
            setSnackBar(layout,"Check Internet Connection");
        }


    }
    public void timerDelayRemoveDialog(long time, final Dialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
    private void getSemisters(String jntuType) {
        semisters.clear();
        semisters.add("Select Semester");
        String coursetype = course.getSelectedItem().toString();
        String branchtype = branch.getSelectedItem().toString();
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofsemesters"+"/"+coursetype+"/"+branchtype);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdatasemisters();
    }
    private void getRegulations(String jntuType) {
        regulations.clear();
        String coursetype = course.getSelectedItem().toString();
        String branchtype = branch.getSelectedItem().toString();
        String semestertype = semister.getSelectedItem().toString();
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofregulations"+"/"+coursetype+"/"+branchtype+"/"+semestertype);        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdataregulations();
    }
    private void getBranches(String jntuType) {
        branches.clear();
        branches.add("Select Branch");
        String coursetype = course.getSelectedItem().toString();
        if(coursetype.equals(null)){
            setSnackBar(layout,"Choose One Branch");
        }else {
            firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofbranches"+"/"+coursetype);
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            refreshdatabranches();
        }
    }
    private void getCourses(String jntuType) {
        courses.clear();
        courses.add("Select Course");
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofcourses");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdatacourses();
    }

    public  void refreshdatacourses() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatescourses(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatescourses(dataSnapshot);
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
    public  void refreshdatabranches() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatesbranches(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatesbranches(dataSnapshot);
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
    public  void refreshdatasemisters() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatessemisters(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatessemisters(dataSnapshot);
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
    public  void refreshdataregulations() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatesregulations(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatesregulations(dataSnapshot);
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

    public void getupdatescourses(DataSnapshot dataSnapshot){
        courses.add((String) dataSnapshot.getValue());
        if(courses.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, courses);
            course.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void getupdatesbranches(DataSnapshot dataSnapshot){
        branches.add((String) dataSnapshot.getValue());
        if(branches.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, branches);
            branch.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void getupdatessemisters(DataSnapshot dataSnapshot){
        semisters.add((String) dataSnapshot.getValue());
        if(semisters.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, semisters);
            semister.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void getupdatesregulations(DataSnapshot dataSnapshot){

        regulations.add((String) dataSnapshot.getValue());
        if(regulations.size()>0)
        {

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void getdata() {
        Regulations.setVisibility(View.VISIBLE);
        for(int i=0;i<regulations.size();i++){
            Regulations.addTab(Regulations.newTab().setText(regulations.get(i)));
        }
        saveData();
    }

    private void saveData() {

        Regulations.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                lastRegulations = tab.getText().toString();
                commonModels = new ArrayList<>();
                listView.setAdapter(null);
                String courses = course.getSelectedItem().toString();
                String branches = branch.getSelectedItem().toString();
                String semisters = semister.getSelectedItem().toString();
                if(courses.equals(null)||branches.equals(null)||semisters.equals(null)){
                    setSnackBar(layout,"Choose All Fields");
                }else{
                    String url = "https://universitesnotifications.firebaseio.com/"+type+"/"+category+"/"+courses+"/"+branches+"/"+semisters+"/"+lastRegulations;
                    firebase=new Firebase(url);
                    commonModels = new ArrayList<>();
                    listView.setAdapter(null);
                    refreshdata();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try{
            String courses = course.getSelectedItem().toString();
            String branches = branch.getSelectedItem().toString();
            String semisters = semister.getSelectedItem().toString();
            if(courses.equals(null)|branches.equals(null)||semisters.equals(null)){
                setSnackBar(layout,"Choose All Fields");
            }else{
                lastRegulations = Regulations.getTabAt(0).getText().toString();
                String url = "https://universitesnotifications.firebaseio.com/"+type+"/"+category+"/"+courses+"/"+branches+"/"+semisters+"/"+lastRegulations;
                firebase=new Firebase(url);
                commonModels = new ArrayList<>();
                listView.setAdapter(null);
                refreshdata();
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }


    }

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
            OldQuestionsModel d= new OldQuestionsModel();
            d.setExamname(ds.getValue(OldQuestionsModel.class).getSubject());
            d.setExamtype(ds.getValue(OldQuestionsModel.class).getExamtype());
            d.setPdflink(ds.getValue(OldQuestionsModel.class).getPdflink());
            d.setSubject(ds.getValue(OldQuestionsModel.class).getSubject());
            d.setYear(ds.getValue(OldQuestionsModel.class).getYear());
            commonModels.add(d);

        }
        if(commonModels.size()>0)
        {
            Collections.reverse(commonModels);
            adapter = new OldQuestionsAdapter(this, commonModels);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String qsubject = ((TextView) view.findViewById(R.id.qsubject)).getText().toString();
                    String subjectdesc = ((TextView) view.findViewById(R.id.year)).getText().toString();
                    String examtype = ((TextView) view.findViewById(R.id.examtype)).getText().toString();
                    String examname = ((TextView) view.findViewById(R.id.examname)).getText().toString();
                    String pdflink = ((TextView) view.findViewById(R.id.pdflink)).getText().toString();

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdflink));
                    startActivity(browserIntent);
                }
            });
        }else
        {
            mProgressDialog.dismiss();
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.filter:
                showAlertDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.showfilter, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        regulations.clear();
        Regulations.removeAllTabs();
        course = (Spinner)dialogView.findViewById(R.id.listofcourses);
        branch = (Spinner)dialogView.findViewById(R.id.listofbranches);
        semister = (Spinner)dialogView.findViewById(R.id.listofsemisters);
        getCourses(type);
        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getBranches(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSemisters(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        semister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getRegulations(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save = (Button)dialogView.findViewById(R.id.getdtata);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    public static void setSnackBar(View coordinatorLayout, String snackTitle) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, Snackbar.LENGTH_LONG);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
}
