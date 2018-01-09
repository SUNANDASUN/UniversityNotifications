package com.indianservers.universitynotifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UniversityChooseActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView jntuk, jntua, jntuh;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_choose);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String jntu = settings.getString("JNTU", "1");
        if(jntu.equals("JNTUK")||jntu.equals("JNTUA")||jntu.equals("JNTUH")){
            Intent na = new Intent(UniversityChooseActivity.this,DashboardActivity.class);
            startActivity(na);
            finish();
        }else{
            jntuk = (TextView)findViewById(R.id.jntuk);
            jntuk.setOnClickListener(this);
            jntua = (TextView)findViewById(R.id.jntua);
            jntua.setOnClickListener(this);
            jntuh = (TextView)findViewById(R.id.jntuh);
            jntuh.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.jntuk:
                editor = settings.edit();
                editor.putString("JNTU", jntuk.getText().toString());
                editor.commit();
                Intent nk = new Intent(UniversityChooseActivity.this,DashboardActivity.class);
                startActivity(nk);
                finish();
                break;
            case R.id.jntua:
                editor = settings.edit();
                editor.putString("JNTU", jntua.getText().toString());
                editor.commit();
                Intent na = new Intent(UniversityChooseActivity.this,DashboardActivity.class);
                startActivity(na);
                finish();
                break;
            case R.id.jntuh:
                editor = settings.edit();
                editor.putString("JNTU", jntuh.getText().toString());
                editor.commit();
                Intent nh = new Intent(UniversityChooseActivity.this,DashboardActivity.class);
                startActivity(nh);
                finish();
                break;
        }
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
