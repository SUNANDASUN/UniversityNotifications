package com.indianservers.universitynotifications;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

import static android.view.View.GONE;


public class QuestionFragment extends Fragment implements View.OnClickListener,View.OnTouchListener, Handler.Callback {

    private final Handler handler = new Handler(this);
    final ArrayList<Integer> optIndex = new ArrayList<>();
    TextView questionText, questionCount, indicator;
    WebView opt1, opt2, opt3, opt4,opt5, desc;
    Button choose, finish1,askfriend,fifty,rough;
    String question, option1, option2, option3, option4,option5, description;
    ScrollView parent;
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    private static final int CLICK_ON_OPT5 = 1;
    int correctAnswer, totalQuestions, questionID;
    int counter, favourite = 0;
    OnHeadlineSelectedListener mCallback;
    View itemView;
    private InterstitialAd mInterstitialAd;
    private WebView questionwebview;
    private ImageView questionimageview;
    private ImageView op1imageview,op2imageview,op3imageview,op4imageview,op5imageview;
    private String examMode;
    private WebViewClient client;
    private String count;
    private Tracker mTracker;
    public QuestionFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        itemView = inflater.inflate(R.layout.question_fragment, container, false);
        askfriend = (Button)itemView.findViewById(R.id.share);
        askfriend.setOnClickListener(this);
        fifty = (Button)itemView.findViewById(R.id.fifty);
        fifty.setOnClickListener(this);
        rough = (Button)itemView.findViewById(R.id.rough);
        rough.setOnClickListener(this);
        questionwebview = (WebView)itemView.findViewById(R.id.questionwebview);
        questionwebview.setOnTouchListener(this);
        questionimageview = (ImageView)itemView.findViewById(R.id.questionimageview);
        //getting Arguments from Fragment Adapter
        Bundle bundle = getArguments();
        try{
            count = Integer.toString(bundle.getInt("Count"));
            question = bundle.getString("question");
            option1 = bundle.getString("opt1");
            option2 = bundle.getString("opt2");
            option3 = bundle.getString("opt3");
            option4 = bundle.getString("opt4");
            option5 = bundle.getString("opt5");
            description = bundle.getString("desc");
            correctAnswer = bundle.getInt("correct");
            totalQuestions = bundle.getInt("totalQuestions");
            questionID = bundle.getInt("questionID");
            counter = bundle.getInt("Count");
            Quiz.strings.add(question);
            if(Quiz.strings.size()==16){
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();
                Quiz.strings.clear();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        examMode = sharedPrefs.getString("exammode","0");
        //getting id's from the layout
        questionCount = (TextView) itemView.findViewById(R.id.question_count);
        opt1 = (WebView) itemView.findViewById(R.id.opt1);
        opt1.setOnTouchListener(this);
        opt2 = (WebView) itemView.findViewById(R.id.opt2);
        opt2.setOnTouchListener(this);
        opt3 = (WebView) itemView.findViewById(R.id.opt3);
        opt3.setOnTouchListener(this);
        opt4 = (WebView) itemView.findViewById(R.id.opt4);
        opt4.setOnTouchListener(this);
        opt5 = (WebView) itemView.findViewById(R.id.opt5);
        opt5.setOnTouchListener(this);
        finish1 = (Button) itemView.findViewById(R.id.finish);
        desc = (WebView) itemView.findViewById(R.id.descc);
        op1imageview = (ImageView) itemView.findViewById(R.id.opt1imageview);
        op2imageview = (ImageView)itemView.findViewById(R.id.opt2imageview);
        op3imageview = (ImageView)itemView.findViewById(R.id.opt3imageview);
        op4imageview = (ImageView)itemView.findViewById(R.id.opt4imageview);
        op5imageview = (ImageView)itemView.findViewById(R.id.opt5imageview);
        parent = (ScrollView)itemView.findViewById(R.id.parent_scroll);


        //copying the option id's to array
        final Integer[] options = {0, R.id.opt1, R.id.opt2, R.id.opt3, R.id.opt4,R.id.opt5};
        optIndex.add(Arrays.asList(options).indexOf(0));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt1));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt2));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt3));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt4));
        try
        {
            if (option5.equals("null")||option5.equals("")||option5.equals(null)) {

            }else {
                optIndex.add(Arrays.asList(options).indexOf(R.id.opt5));
            }
            opt5.setOnClickListener(this);
        }catch (NullPointerException e){

        }


        //doing action when the button is clicked
        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
        opt4.setOnClickListener(this);


        finish1.setOnClickListener(this);

        //Setting data to the elements in the layout
        if(Quiz.type.equals("Offline")){

        }else if(Quiz.type.equals("online")){

        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1800,800);

                questionwebview.setVisibility(View.VISIBLE);

                questionwebview.getSettings().setTextSize(WebSettings.TextSize.LARGER);
                questionwebview.getSettings().setBuiltInZoomControls(true);
                questionwebview.getSettings().setDisplayZoomControls(true);
                questionwebview.getSettings().setDomStorageEnabled(true);
                questionwebview.getSettings().setSupportZoom(true);
                questionwebview.getSettings().setAllowFileAccess(true);
                questionwebview.getSettings().setJavaScriptEnabled(true);
                questionwebview.getSettings().setAppCacheEnabled(true);
                questionwebview.getSettings().setLoadsImagesAutomatically(true);
                questionwebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                if(question.contains("img src")){

                    questionwebview.loadData("<html><body>" + question+ "</body></html>", "text/html", "utf-8");

                }else {
                    questionwebview.loadData("<html><body>" + question+ "</body></html>", "text/html", "utf-8");

                }
        questionCount.setText(count + "/" + totalQuestions);
        questionCount.setTextColor(Color.BLACK);
        if(option1.contains("img src"))
        {
            int start = question.indexOf("src=\"") + 5;
            int end = question.indexOf("\"", start);

            String src = question.substring(start, end);
            opt1.setVisibility(View.VISIBLE);
            opt1.loadData("A) " + option1,"text/html", "utf-8");

        }else {
            opt1.setVisibility(View.VISIBLE);
            opt1.loadData("A) " + option1,"text/html", "utf-8");
        }

        opt2.setVisibility(View.VISIBLE);
        opt2.loadData("B) " + option2,"text/html", "utf-8");
        opt3.setVisibility(View.VISIBLE);
        opt3.loadData("C) " + option3,"text/html", "utf-8");
        opt4.setVisibility(View.VISIBLE);
        opt4.loadData("D) " + option4,"text/html", "utf-8");
        try
        {
            if(option5.equals("null")||option5.equals("")||option5.equals(null)){
                opt5.setVisibility(GONE);
            }else {
                if(option5.contains("img src")){
                    opt5.setVisibility(View.VISIBLE);
                    opt5.loadData("E) " + option5,"text/html", "utf-8");
                    opt5.setInitialScale(8*5);
                }else {
                    opt5.setVisibility(View.VISIBLE);
                    opt5.loadData("E) " + option5,"text/html", "utf-8");
                }
            }
        }catch (NullPointerException e){

        }


        if (counter == totalQuestions) {

            finish1.setVisibility(View.VISIBLE);
        }
        client = new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                handler.sendEmptyMessage(CLICK_ON_URL);
                return false;
            }
        };
        return itemView;

    }
    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.InterstitialAd));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {

            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

        }
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opt1:

                break;
            case R.id.opt2:


                break;
            case R.id.opt3:

                break;
            case R.id.opt4:

                break;
            case R.id.opt5:
                if(examMode.equals("practice")){
                    if (correctAnswer == optIndex.get(5)) {
                        opt5.setBackgroundColor(Color.parseColor("#4CAF50"));
                        opt2.setVisibility(View.INVISIBLE);
                        opt3.setVisibility(View.INVISIBLE);
                        opt1.setVisibility(View.INVISIBLE);
                        opt4.setVisibility(View.INVISIBLE);
                        desc.setVisibility(View.VISIBLE);
                    } else {
                        opt5.setBackgroundColor(Color.parseColor("#F44336"));
                    }
                }else if(examMode.equals("test")){
                    Quiz.totlaque.put(Integer.valueOf(count),optIndex.get(5));
                    opt5.setBackgroundDrawable( getResources().getDrawable(R.drawable.custombackground));
                    opt2.setBackgroundColor(Color.WHITE);
                    opt3.setBackgroundColor(Color.WHITE);
                    opt1.setBackgroundColor(Color.WHITE);
                    opt4.setBackgroundColor(Color.WHITE);
                    if (correctAnswer == optIndex.get(5)) {
                        Quiz.answers.put(Integer.valueOf(count),correctAnswer);

                    } else {

                    }
                }
                break;

            case R.id.finish:
                ArrayList<String> list = new ArrayList<>();
                for(int i=0;i<Quiz.questionsClassList.size();i++){
                    list.add(Quiz.questionsClassList.get(i).getDesc());
                }
                String time = Quiz.questionTimer.getText().toString();
                if(examMode.equals("practice")){
                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else if(examMode.equals("test")){
                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putExtra("rightanswers",Quiz.answers.size());
                    intent.putExtra("totalqu",totalQuestions);
                    intent.putStringArrayListExtra("listdesc",list);
                    intent.putExtra("overalltime",time);
                    startActivity(intent);
                    getActivity().finish();

                }

                break;
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                //currentQID
                String shareBody = "";
                shareBody += "Q) " + Html.fromHtml(question) + "\n";
                shareBody += "A) " + Html.fromHtml(option1) + "\n";
                shareBody += "B) " + Html.fromHtml(option2) + "\n";
                shareBody += "C) " + Html.fromHtml(option3) + "\n";
                shareBody += "D) " + Html.fromHtml(option4) + "\n";
                // shareBody+=" Download app from "+getResources().getString(R.string.appURL);

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Questions");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.fifty:
                Random random = new Random();
                int randomnum = random.nextInt(4);
                if (randomnum == 0)
                    randomnum = 1;
                if (randomnum == correctAnswer) {
                    if (randomnum == 4)
                        randomnum--;
                    else
                        randomnum++;
                }
                opt1.setVisibility(View.GONE);
                opt2.setVisibility(View.GONE);
                opt3.setVisibility(View.GONE);
                opt4.setVisibility(View.GONE);


                if (randomnum == 1) {
                    opt1.setVisibility(View.VISIBLE);
                }
                if (randomnum == 2) {
                    opt2.setVisibility(View.VISIBLE);
                }
                if (randomnum == 3) {
                    opt3.setVisibility(View.VISIBLE);
                }
                if (randomnum == 4) {
                    opt4.setVisibility(View.VISIBLE);
                }
                if (correctAnswer == 1) {
                    opt1.setVisibility(View.VISIBLE);
                }
                if (correctAnswer == 2) {
                    opt2.setVisibility(View.VISIBLE);
                }
                if (correctAnswer == 3) {
                    opt3.setVisibility(View.VISIBLE);
                }
                if (correctAnswer == 4) {
                    opt4.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rough:
                Intent rough = new Intent(getActivity(), Gestures.class);
                rough.putExtra("question",question);
                startActivity(rough);
                break;
//                 case R.id.question:
//                Intent intent = new Intent(getContext(),QuestionFullscreenActivity.class);
//                intent.putExtra("webviewid",question);
//                startActivity(intent);
//                break;


        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.questionwebview && event.getAction() == MotionEvent.ACTION_DOWN){
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        else if(v.getId()==R.id.opt5&&event.getAction() == MotionEvent.ACTION_DOWN){
            if(examMode.equals("practice")){
                if (correctAnswer == optIndex.get(5)) {
                    opt5.getSettings();
                    opt5.setBackgroundColor(Color.parseColor("#4CAF50"));
                    opt2.setVisibility(View.INVISIBLE);
                    opt3.setVisibility(View.INVISIBLE);
                    opt1.setVisibility(View.INVISIBLE);
                    opt4.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.VISIBLE);
                } else {
                    opt5.getSettings();
                    opt5.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }else if(examMode.equals("test")){
                opt5.getSettings();
                opt5.setBackgroundColor(Color.GREEN);
                Quiz.totlaque.put(Integer.valueOf(count),optIndex.get(5));
                opt2.setBackgroundColor(Color.WHITE);
                opt3.setBackgroundColor(Color.WHITE);
                opt1.setBackgroundColor(Color.WHITE);
                opt4.setBackgroundColor(Color.WHITE);
                if (correctAnswer == optIndex.get(5)) {
                    Quiz.answers.put(Integer.valueOf(count),correctAnswer);

                } else {

                }
            }
        }
        else if(v.getId()==R.id.opt1&&event.getAction() == MotionEvent.ACTION_DOWN){
            if(examMode.equals("practice")){
                if (correctAnswer == optIndex.get(1)) {
                    opt1.setBackgroundColor(Color.parseColor("#4CAF50"));
                    op1imageview.setBackgroundColor(Color.parseColor("#4CAF50"));
                    opt2.setVisibility(View.INVISIBLE);
                    opt3.setVisibility(View.INVISIBLE);
                    opt4.setVisibility(View.INVISIBLE);
                    opt5.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.VISIBLE);
                } else {
                    opt1.setBackgroundColor(Color.parseColor("#F44336"));
                    op1imageview.setBackgroundColor(Color.parseColor("#4CAF50"));
                }
            }else if(examMode.equals("test")){
                Quiz.totlaque.put(Integer.valueOf(count),optIndex.get(1));
                opt1.setBackgroundColor(Color.GREEN);
                opt2.setBackgroundColor(Color.WHITE);
                opt3.setBackgroundColor(Color.WHITE);
                opt4.setBackgroundColor(Color.WHITE);
                opt5.setBackgroundColor(Color.WHITE);
                if (correctAnswer == optIndex.get(1)) {
                    Quiz.answers.put(Integer.valueOf(count),correctAnswer);


                } else {

                }
            }
        }
        else if(v.getId()==R.id.opt2&&event.getAction() == MotionEvent.ACTION_DOWN){
            if(examMode.equals("practice")){
                if (correctAnswer == optIndex.get(2)) {

                    opt2.setBackgroundColor(Color.parseColor("#4CAF50"));
                    opt1.setVisibility(View.INVISIBLE);
                    opt3.setVisibility(View.INVISIBLE);
                    opt4.setVisibility(View.INVISIBLE);
                    opt5.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.VISIBLE);
                } else {
                    opt2.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }else if(examMode.equals("test")){
                Quiz.totlaque.put(Integer.valueOf(count),optIndex.get(2));
                opt2.setBackgroundColor(Color.GREEN);
                opt1.setBackgroundColor(Color.WHITE);
                opt3.setBackgroundColor(Color.WHITE);
                opt4.setBackgroundColor(Color.WHITE);
                opt5.setBackgroundColor(Color.WHITE);
                if (correctAnswer == optIndex.get(2)) {
                    Quiz.answers.put(Integer.valueOf(count),correctAnswer);
                } else {

                }

            }
        }
        else if(v.getId()==R.id.opt3&&event.getAction() == MotionEvent.ACTION_DOWN){
            if(examMode.equals("practice")){
                if (correctAnswer == optIndex.get(3)) {

                    opt3.setBackgroundColor(Color.parseColor("#4CAF50"));
                    opt2.setVisibility(View.INVISIBLE);
                    opt1.setVisibility(View.INVISIBLE);
                    opt4.setVisibility(View.INVISIBLE);
                    opt5.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.VISIBLE);
                } else {
                    opt3.setBackgroundColor(Color.parseColor("#F44336"));

                }
            }else if(examMode.equals("test")){
                Quiz.totlaque.put(Integer.valueOf(count),optIndex.get(3));
                opt3.setBackgroundColor(Color.GREEN);
                opt2.setBackgroundColor(Color.WHITE);
                opt1.setBackgroundColor(Color.WHITE);
                opt4.setBackgroundColor(Color.WHITE);
                opt5.setBackgroundColor(Color.WHITE);
                if (correctAnswer == optIndex.get(3)) {
                    Quiz.answers.put(Integer.valueOf(count),correctAnswer);


                } else {

                }
            }

        }
        else if(v.getId()==R.id.opt4&&event.getAction() == MotionEvent.ACTION_DOWN){
            if(examMode.equals("practice")){
                if (correctAnswer == optIndex.get(4)) {

                    opt4.setBackgroundColor(Color.parseColor("#4CAF50"));
                    opt2.setVisibility(View.INVISIBLE);
                    opt3.setVisibility(View.INVISIBLE);
                    opt1.setVisibility(View.INVISIBLE);
                    opt5.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.VISIBLE);
                } else {
                    opt4.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }else if(examMode.equals("test")){
                Quiz.totlaque.put(Integer.valueOf(count),optIndex.get(4));
                opt4.setBackgroundColor(Color.GREEN);
                opt2.setBackgroundColor(Color.WHITE);
                opt3.setBackgroundColor(Color.WHITE);
                opt1.setBackgroundColor(Color.WHITE);
                opt5.setBackgroundColor(Color.WHITE);
                if (correctAnswer == optIndex.get(4)) {
                    Quiz.answers.put(Integer.valueOf(count),correctAnswer);


                } else {

                }
            }

        }

        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL){
            handler.removeMessages(CLICK_ON_WEBVIEW);
            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW){
            Intent intent = new Intent(getContext(),QuestionFullscreenActivity.class);
            intent.putExtra("webviewid",question);
            startActivity(intent);
            return true;
        }
        if (msg.what == CLICK_ON_OPT5){

            return true;
        }
        return false;
    }


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int qid, int favValue);
    }
}
