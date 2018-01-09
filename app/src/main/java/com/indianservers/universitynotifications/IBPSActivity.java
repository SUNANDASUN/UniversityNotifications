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

public class IBPSActivity extends AppCompatActivity {
    private Firebase firebase;
    private GridView recyclerView;
    private ArrayList<IbpsCommonClas> ibpsCommonClass = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private IBPSAdpter ibpsAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibps);
        mProgressDialog = new ProgressDialog(IBPSActivity.this);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Firebase.setAndroidContext(this);
        recyclerView = (GridView) findViewById(R.id.ibpsrecycler);
        firebase = new Firebase("https://universitesnotifications.firebaseio.com/JNTUK/IBPS/examnames");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            IbpsCommonClas ibpsCommonClas = new IbpsCommonClas();
                            ibpsCommonClas.setExamId(ds.getValue(IbpsCommonClas.class).getExamId());
                            ibpsCommonClas.setExamName(ds.getValue(IbpsCommonClas.class).getExamName());
                            ibpsCommonClass.add(ibpsCommonClas);
                        }
                    ibpsAdpter = new IBPSAdpter(IBPSActivity.this,ibpsCommonClass);
                    recyclerView.setAdapter(ibpsAdpter);
                    mProgressDialog.dismiss();
                    recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String examname = ((TextView)view.findViewById(R.id.examidibps)).getText().toString();

                            Intent intent = new Intent(IBPSActivity.this,SetActivity.class);
                            intent.putExtra("liveexam",examname);
                            startActivity(intent);
                        }
                    });

                }else {
                    mProgressDialog.dismiss();
                    Toast.makeText(IBPSActivity.this,"Server Not Found",Toast.LENGTH_LONG).show();
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
