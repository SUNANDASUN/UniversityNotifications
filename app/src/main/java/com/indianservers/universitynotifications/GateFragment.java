package com.indianservers.universitynotifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GateFragment extends Fragment implements View.OnClickListener,View.OnTouchListener, Handler.Callback {


    private OnFragmentInteractionListener mListener;
    private WebView questionwebview,explanationwebview;
    TextView qunumber;
    private final Handler handler = new Handler(this);
    final ArrayList<Integer> optIndex = new ArrayList<>();
    Button choose, finish,askfriend,fifty,rough;
    View itemView;
    int counter, favourite = 0;
    String correctAnswer;
    int  totalQuestions, questionID;
    private InterstitialAd mInterstitialAd;
    private String qutype;
    OnHeadlineSelectedListener mCallback;
    private WebViewClient client;
    private String count;
    private String examMode;
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_EXPL = 1;
    private static final int CLICK_ON_URL = 2;
    private static final int CLICK_ON_OPT5 = 1;
    private String question,qno,gateexamtype,gatequtype,gateanswer,gatepmark,gatenmark,gateexplanation;
    private Button opt1,opt2,opt3,opt4,opt5;
    private LinearLayout linearLayout;
    public EditText natanswer;
    public static String answer;
    public GateFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GateFragment newInstance(String param1, String param2) {
        GateFragment fragment = new GateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_gate, container, false);
        askfriend = (Button)itemView.findViewById(R.id.gateshare);
        askfriend.setOnClickListener(this);
        qunumber = (TextView)itemView.findViewById(R.id.gatequestion_count);
        fifty = (Button)itemView.findViewById(R.id.gatefifty);
        fifty.setOnClickListener(this);
        rough = (Button)itemView.findViewById(R.id.gaterough);
        rough.setOnClickListener(this);
        questionwebview = (WebView)itemView.findViewById(R.id.gateQuestion);
        questionwebview.setOnTouchListener(this);
        explanationwebview = (WebView)itemView.findViewById(R.id.gateExplanation);
        explanationwebview.setOnTouchListener(this);
        finish = (Button)itemView.findViewById(R.id.gatefinish);
        finish.setOnClickListener(this);
        opt1 = (Button)itemView.findViewById(R.id.gateopt1);
        opt1.setOnClickListener(this);
        opt2 = (Button)itemView.findViewById(R.id.gateopt2);
        opt2.setOnClickListener(this);
        opt3 = (Button)itemView.findViewById(R.id.gateopt3);
        opt3.setOnClickListener(this);
        opt4 = (Button)itemView.findViewById(R.id.gateopt4);
        opt4.setOnClickListener(this);
        opt5 = (Button)itemView.findViewById(R.id.gateopt5);
        opt5.setOnClickListener(this);
        linearLayout = (LinearLayout)itemView.findViewById(R.id.mcqlinear);
        natanswer = (EditText)itemView.findViewById(R.id.gatenatedit);
        final Integer[] options = {0, R.id.opt1, R.id.opt2, R.id.opt3, R.id.opt4,R.id.opt5};
        optIndex.add(Arrays.asList(options).indexOf(0));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt1));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt2));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt3));
        optIndex.add(Arrays.asList(options).indexOf(R.id.opt4));

        Bundle bundle = getArguments();
        try{
            count = Integer.toString(bundle.getInt("Count"));
            question = bundle.getString("question");
            qno = bundle.getString("gateqno");
            gatequtype = bundle.getString("qutype");
            correctAnswer = bundle.getString("correct");
            gateexamtype = bundle.getString("gateexamtype");
            gatepmark = bundle.getString("gatepmark");
            gatenmark = bundle.getString("gatenmark");
            totalQuestions = bundle.getInt("totalQuestions");
            gateexplanation = bundle.getString("gateExplanation");
            explanationwebview.setInitialScale(13*10);
            explanationwebview.getSettings().setTextSize(WebSettings.TextSize.LARGER);
            explanationwebview.loadData("<html><body>" + gateexplanation+ "</body></html>", "text/html", "utf-8");
            counter = bundle.getInt("Count");
            GateQuestionsActivity.strings.add(question);
            if(GateQuestionsActivity.strings.size()==16){
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();
                GateQuestionsActivity.strings.clear();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        examMode = sharedPrefs.getString("exammode","0");
        questionwebview.setVisibility(View.VISIBLE);
        questionwebview.setInitialScale(13*10);
        questionwebview.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        questionwebview.getSettings().setAllowFileAccess(true);
        questionwebview.getSettings().setBuiltInZoomControls(true);
        questionwebview.getSettings().setDisplayZoomControls(true);
        questionwebview.getSettings().setDomStorageEnabled(true);
        questionwebview.getSettings().setSupportZoom(true);
        questionwebview.getSettings().setJavaScriptEnabled(true);
        questionwebview.getSettings().setAppCacheEnabled(true);
        questionwebview.getSettings().setLoadsImagesAutomatically(true);
        questionwebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        questionwebview.loadData("<html><body>" + question+ "</body></html>", "text/html", "utf-8");
        qunumber.setText(count + "/" + totalQuestions);
        qunumber.setTextColor(Color.BLACK);

        if(gatequtype.equals("MCQ")){
            linearLayout.setVisibility(View.VISIBLE);
            natanswer.setVisibility(View.GONE);

        }else if(gatequtype.equals("NAT")){
            linearLayout.setVisibility(View.GONE);
            natanswer.setVisibility(View.VISIBLE);
        }
        if (counter == totalQuestions) {

            finish.setVisibility(View.VISIBLE);
        }
        client = new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                handler.sendEmptyMessage(CLICK_ON_URL);
                return false;
            }
        };
        natanswer.addTextChangedListener(new TextWatcher() {
            final int co = Integer.parseInt(count);
            @Override
            public void afterTextChanged(Editable s) {
                if(examMode.equals("practice")){
                    if(String.valueOf(s).equals(correctAnswer)){
                        explanationwebview.setVisibility(View.VISIBLE);
                    }else {
                        explanationwebview.setVisibility(View.GONE);
                        natanswer.setError("Wrong Answer");
                    }
                }else {

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(examMode.equals("practice")){
                    if(String.valueOf(s).equals(correctAnswer)){
                        explanationwebview.setVisibility(View.VISIBLE);
                    }else {
                        explanationwebview.setVisibility(View.GONE);
                        natanswer.setError("Wrong Answer");
                    }
                }else {
                    if(s.length() != 0) {
                        GateQuestionsActivity.answers.put(co,String.valueOf(s));
                    }
                    else{
                        GateQuestionsActivity.answers.put(co,String.valueOf(s));
                    }
                }

            }
        });
        return itemView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {

        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gatefinish:
                ArrayList<String> list = new ArrayList<>();
                for(int i=0;i<GateQuestionsActivity.questionsClassList.size();i++){
                    list.add(GateQuestionsActivity.questionsClassList.get(i).getExplanation());
                }
                String time = GateQuestionsActivity.questionTimer.getText().toString();
                if(examMode.equals("practice")){
                    Intent intent = new Intent(getActivity(), GateActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else if(examMode.equals("test")){
                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putExtra("rightanswers",GateQuestionsActivity.answers.size());
                    intent.putExtra("totalqu",totalQuestions);
                    intent.putStringArrayListExtra("listdesc",list);
                    intent.putExtra("overalltime",time);
                    startActivity(intent);
                    getActivity().finish();

                }
                break;
            case R.id.gateopt1:
                if(examMode.equals("practice")){
                    if (optIndex.get(1)==Integer.parseInt(correctAnswer)) {
                        opt1.setBackgroundColor(Color.parseColor("#4CAF50"));
                        opt2.setVisibility(View.INVISIBLE);
                        opt3.setVisibility(View.INVISIBLE);
                        opt4.setVisibility(View.INVISIBLE);
                        opt5.setVisibility(View.INVISIBLE);
                        explanationwebview.setVisibility(View.VISIBLE);
                    } else {
                        opt1.setBackgroundColor(Color.parseColor("#F44336"));
                    }
                }else if(examMode.equals("test")){
                    GateQuestionsActivity.totlaque.put(Integer.valueOf(count),optIndex.get(1));
                    opt1.setBackgroundColor(Color.GREEN);
                    opt2.setBackgroundColor(Color.WHITE);
                    opt3.setBackgroundColor(Color.WHITE);
                    opt4.setBackgroundColor(Color.WHITE);
                    opt5.setBackgroundColor(Color.WHITE);
                    if (optIndex.get(1)==Integer.parseInt(correctAnswer)) {
                        GateQuestionsActivity.answers.put(Integer.valueOf(count),correctAnswer);


                    } else {

                    }
                }
                break;
            case R.id.gateopt2:
                if(examMode.equals("practice")){
                    if (optIndex.get(2)==Integer.parseInt(correctAnswer)) {

                        opt2.setBackgroundColor(Color.parseColor("#4CAF50"));
                        opt1.setVisibility(View.INVISIBLE);
                        opt3.setVisibility(View.INVISIBLE);
                        opt4.setVisibility(View.INVISIBLE);
                        opt5.setVisibility(View.INVISIBLE);
                        explanationwebview.setVisibility(View.VISIBLE);
                    } else {
                        opt2.setBackgroundColor(Color.parseColor("#F44336"));
                    }
                }else if(examMode.equals("test")){
                    GateQuestionsActivity.totlaque.put(Integer.valueOf(count),optIndex.get(2));
                    opt2.setBackgroundColor(Color.GREEN);
                    opt1.setBackgroundColor(Color.WHITE);
                    opt3.setBackgroundColor(Color.WHITE);
                    opt4.setBackgroundColor(Color.WHITE);
                    opt5.setBackgroundColor(Color.WHITE);
                    if (optIndex.get(2)==Integer.parseInt(correctAnswer)) {
                        GateQuestionsActivity.answers.put(Integer.valueOf(count),correctAnswer);
                    } else {

                    }

                }
                break;
            case R.id.gateopt3:
                if(examMode.equals("practice")){
                    if (optIndex.get(3)==Integer.parseInt(correctAnswer)) {

                        opt3.setBackgroundColor(Color.parseColor("#4CAF50"));
                        opt2.setVisibility(View.INVISIBLE);
                        opt1.setVisibility(View.INVISIBLE);
                        opt4.setVisibility(View.INVISIBLE);
                        opt5.setVisibility(View.INVISIBLE);
                        explanationwebview.setVisibility(View.VISIBLE);
                    } else {
                        opt3.setBackgroundColor(Color.parseColor("#F44336"));

                    }
                }else if(examMode.equals("test")){
                    GateQuestionsActivity.totlaque.put(Integer.valueOf(count),optIndex.get(3));
                    opt3.setBackgroundColor(Color.GREEN);
                    opt2.setBackgroundColor(Color.WHITE);
                    opt1.setBackgroundColor(Color.WHITE);
                    opt4.setBackgroundColor(Color.WHITE);
                    opt5.setBackgroundColor(Color.WHITE);
                    if (optIndex.get(3)==Integer.parseInt(correctAnswer)) {
                        GateQuestionsActivity.answers.put(Integer.valueOf(count),correctAnswer);


                    } else {

                    }
                }
                break;
            case R.id.gateopt4:
                if(examMode.equals("practice")){
                    if (optIndex.get(4)==Integer.parseInt(correctAnswer)) {

                        opt4.setBackgroundColor(Color.parseColor("#4CAF50"));
                        opt2.setVisibility(View.INVISIBLE);
                        opt3.setVisibility(View.INVISIBLE);
                        opt1.setVisibility(View.INVISIBLE);
                        opt5.setVisibility(View.INVISIBLE);
                        explanationwebview.setVisibility(View.VISIBLE);
                    } else {
                        opt4.setBackgroundColor(Color.parseColor("#F44336"));
                    }
                }else if(examMode.equals("test")){
                    GateQuestionsActivity.totlaque.put(Integer.valueOf(count),optIndex.get(4));
                    opt4.setBackgroundColor(Color.GREEN);
                    opt2.setBackgroundColor(Color.WHITE);
                    opt3.setBackgroundColor(Color.WHITE);
                    opt1.setBackgroundColor(Color.WHITE);
                    opt5.setBackgroundColor(Color.WHITE);
                    if (optIndex.get(4)==Integer.parseInt(correctAnswer)) {
                        GateQuestionsActivity.answers.put(Integer.valueOf(count),correctAnswer);


                    } else {

                    }
                }
                break;
            case R.id.gateopt5:
                if(examMode.equals("practice")){
                    if (optIndex.get(5)==Integer.parseInt(correctAnswer)) {
                        opt5.setBackgroundColor(Color.parseColor("#4CAF50"));
                        opt2.setVisibility(View.INVISIBLE);
                        opt3.setVisibility(View.INVISIBLE);
                        opt1.setVisibility(View.INVISIBLE);
                        opt4.setVisibility(View.INVISIBLE);
                        explanationwebview.setVisibility(View.VISIBLE);
                    } else {
                        opt5.setBackgroundColor(Color.parseColor("#F44336"));
                    }
                }else if(examMode.equals("test")){
                    GateQuestionsActivity.totlaque.put(Integer.valueOf(count),optIndex.get(4));
                    opt5.setBackgroundColor(Color.GREEN);
                    opt2.setBackgroundColor(Color.WHITE);
                    opt3.setBackgroundColor(Color.WHITE);
                    opt1.setBackgroundColor(Color.WHITE);
                    opt4.setBackgroundColor(Color.WHITE);
                    if (optIndex.get(5)==Integer.parseInt(correctAnswer)) {
                        GateQuestionsActivity.answers.put(Integer.valueOf(count),correctAnswer);

                    } else {

                    }
                }
                break;
            case R.id.gateshare:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                //currentQID
                String shareBody = "";
                shareBody += "Q) " + Html.fromHtml(question) + "\n";
                // shareBody+=" Download app from "+getResources().getString(R.string.appURL);

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Questions");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.gatefifty:
                Random random = new Random();
                int randomnum = random.nextInt(4);
                if (randomnum == 0)
                    randomnum = 1;
                if (randomnum == Integer.parseInt(correctAnswer)) {
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
                if (Integer.parseInt(correctAnswer) == 1) {
                    opt1.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(correctAnswer) == 2) {
                    opt2.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(correctAnswer) == 3) {
                    opt3.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(correctAnswer) == 4) {
                    opt4.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.gaterough:
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
        if (v.getId() == R.id.gateQuestion && event.getAction() == MotionEvent.ACTION_DOWN){
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        else if(v.getId() == R.id.gateExplanation && event.getAction()== MotionEvent.ACTION_DOWN){
            handler.sendEmptyMessageDelayed(CLICK_ON_EXPL, 500);
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
        if(msg.what == CLICK_ON_EXPL){
            Intent intent = new Intent(getContext(),QuestionFullscreenActivity.class);
            intent.putExtra("webviewid",gateexplanation);
            startActivity(intent);
            return true;
        }
        if (msg.what == CLICK_ON_OPT5){

            return true;
        }
        return false;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int qid, int favValue);
    }
}
