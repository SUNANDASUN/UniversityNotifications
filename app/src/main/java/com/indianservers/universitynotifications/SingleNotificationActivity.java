package com.indianservers.universitynotifications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SingleNotificationActivity extends AppCompatActivity implements View.OnClickListener{
    private String type, webURL;
    private Button readmore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String sub = intent.getStringExtra("subject");
        String date = intent.getStringExtra("date");
        String desc = intent.getStringExtra("desc");
        String imageurl = intent.getStringExtra("imgurl");
        webURL = intent.getStringExtra("weburl");

        Intent qintent= getIntent();
        String qsubject = qintent.getStringExtra("qsubject");
        String qsubjectdesc = qintent.getStringExtra("subjectdesc");
        String qexamname = qintent.getStringExtra("examname");
        String qexamtype = qintent.getStringExtra("examtype");
        final String qpdflink = qintent.getStringExtra("pdflink");

        if(qsubject!=null){
            getSupportActionBar().setTitle("");
            View webviewlayout = findViewById(R.id.qlayout);
            webviewlayout.setAnimation(AnimationUtils.makeInChildBottomAnimation(getApplicationContext()));
            webviewlayout.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.normallayout).setVisibility(View.GONE);
                }
            }, 500);
            TextView subq = (TextView)findViewById(R.id.subjectq);
            TextView subdes = (TextView)findViewById(R.id.subjectDesc);
            TextView exname = (TextView)findViewById(R.id.examName);
            TextView extype = (TextView)findViewById(R.id.examType);
            final TextView pdf = (TextView)findViewById(R.id.pdfLink);
            subq.setText(qsubject);
            subdes.setText(qsubjectdesc);
            exname.setText(qexamname);
            extype.setText(qexamtype);
            pdf.setText(qpdflink);
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(qpdflink));
                    startActivity(browserIntent);
                }
            });

        }else if(sub!=null){
            View webviewlayout = findViewById(R.id.normallayout);
            webviewlayout.setAnimation(AnimationUtils.makeInChildBottomAnimation(getApplicationContext()));
            webviewlayout.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.qlayout).setVisibility(View.GONE);
                }
            }, 500);
            TextView textsubject = (TextView)findViewById(R.id.textsub);
            TextView textdate = (TextView)findViewById(R.id.textdate);
            TextView textdesc = (TextView)findViewById(R.id.textdesc);
            readmore = (Button)findViewById(R.id.readmore);
            ImageView imageView = (ImageView)findViewById(R.id.cancelwebview);
            ImageView imurl = (ImageView)findViewById(R.id.imge);
            imageView.setOnClickListener(this);
            if(imageurl.equals("")){
                imurl.setVisibility(View.GONE);
            }else{
                imurl.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageurl)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imurl);
            }
            textsubject.setText(sub);
            textdate.setText(date);
            textdesc.setText(desc);

            readmore.setOnClickListener(this);

        }


    }

    private void webview(String jntu) {
        final WebView mywebview = (WebView) findViewById(R.id.jntuwebview);
        mywebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setBuiltInZoomControls(true);
        mywebview.loadUrl(webURL);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        type = sharedPrefs.getString("JNTUTYPE","1");
        final ProgressDialog pd = ProgressDialog.show(SingleNotificationActivity.this, "", "Please wait .......", true);
        mywebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(SingleNotificationActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = mywebview.getUrl();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.readmore:
                View webviewlayout = findViewById(R.id.webviewlayout);
                webviewlayout.setAnimation(AnimationUtils.makeInChildBottomAnimation(getApplicationContext()));
                webviewlayout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.normallayout).setVisibility(View.GONE);
                    }
                }, 500);
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String jntu = sharedPrefs.getString("JNTU","1");
                webview(jntu);
                getSupportActionBar().hide();
                break;
            case R.id.cancelwebview:
                View forgotLayout = findViewById(R.id.webviewlayout);
                forgotLayout.setAnimation(AnimationUtils.makeInChildBottomAnimation(getApplicationContext()));
                forgotLayout.setVisibility(View.GONE);
                getSupportActionBar().show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.normallayout).setVisibility(View.VISIBLE);
                    }
                }, 500);

                break;
        }
    }
}
