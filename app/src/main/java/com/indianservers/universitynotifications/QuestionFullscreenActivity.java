package com.indianservers.universitynotifications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class QuestionFullscreenActivity extends AppCompatActivity {
    private WebView fullquestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_fullscreen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fullquestion = (WebView)findViewById(R.id.questionfullwebview);
        Intent intent = getIntent();
        String webviewlink = intent.getStringExtra("webviewid");
        fullquestion.getSettings().setBuiltInZoomControls(true);
        fullquestion.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        fullquestion.getSettings().setDisplayZoomControls(true);
        fullquestion.getSettings().setDomStorageEnabled(true);
        fullquestion.getSettings().setSupportZoom(true);
        fullquestion.getSettings().setAllowFileAccess(true);
        fullquestion.getSettings().setJavaScriptEnabled(true);
        fullquestion.getSettings().setAppCacheEnabled(true);
        fullquestion.getSettings().setLoadsImagesAutomatically(true);
        fullquestion.loadData("<html><body>" + webviewlink+ "</body></html>", "text/html", "utf-8");

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
