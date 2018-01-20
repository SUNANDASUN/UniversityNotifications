package com.indianservers.universitynotifications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.indianservers.universitynotifications.adapters.IBPSAdpter;
import com.indianservers.universitynotifications.models.IbpsCommonClas;

import java.util.ArrayList;

public class GateActivity extends AppCompatActivity {
    private GridView branchgridview;
    private Firebase firebase;
    private ArrayList<IbpsCommonClas> ibpsCommonClass = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        branchgridview = (GridView)findViewById(R.id.gaterecycler);
        mProgressDialog = new ProgressDialog(GateActivity.this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://universitesnotifications.firebaseio.com/JNTUK/GATE/ListofBranches");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        IbpsCommonClas ibpsCommonClas = new IbpsCommonClas();
                        ibpsCommonClas.setExamName(ds.getValue(IbpsCommonClas.class).getExamName());
                        ibpsCommonClass.add(ibpsCommonClas);
                    }
                    IBPSAdpter ibpsAdpter = new IBPSAdpter(GateActivity.this,ibpsCommonClass);
                    branchgridview.setAdapter(ibpsAdpter);
                    mProgressDialog.dismiss();
                    branchgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String examname = ((TextView)view.findViewById(R.id.examnameibps)).getText().toString();

                            Intent intent = new Intent(GateActivity.this,GateExamsActivity.class);
                            intent.putExtra("branchname",examname);
                            startActivity(intent);
                        }
                    });

                }else {
                    mProgressDialog.dismiss();
                    Toast.makeText(GateActivity.this,"Server went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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
