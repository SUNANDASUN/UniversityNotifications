package com.indianservers.universitynotifications;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Gestures extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {


    ImageButton clear, close, blue, red, black, green;
    GestureOverlayView gestures;
    TextView question;
    GestureOverlayView.OnGesturePerformedListener mOnGesturePerformedListeners;

    private Paint drawPaint;

    @Override
    public void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

        clear = (ImageButton) findViewById(R.id.clear);
        close = (ImageButton) findViewById(R.id.close);
        question = (TextView) findViewById(R.id.question_scribble);
       /*  blue=(Button)findViewById(R.id.blue);
        red=(Button)findViewById(R.id.red);
        black=(Button)findViewById(R.id.black);
        green=(Button)findViewById(R.id.green);


*/
        Intent intent = getIntent();
        String questionScribble = intent.getStringExtra("question");
        question.setText(Html.fromHtml(questionScribble));

        gestures = (GestureOverlayView) findViewById(R.id.gestures);


        gestures.setGestureColor(getResources().getColor(R.color.colorPrimary));


        gestures.addOnGesturePerformedListener(Gestures.this);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gestures.cancelClearAnimation();
                Log.d("Akhill", "Clicked1");
                gestures.clear(true);
                Log.d("Akhill", "Clicked2");
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                finish();

            }
        });


       /* blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                gestures.setGestureColor(getResources().getColor(R.color.blue));

                Toast.makeText(Gestures.this,"Now Pen Color is Blue",Toast.LENGTH_SHORT).show();

            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gestures.setGestureColor(getResources().getColor(R.color.black));



                Toast.makeText(Gestures.this,"Now Pen Color is Black",Toast.LENGTH_SHORT).show();

            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                gestures.setGestureColor(getResources().getColor(R.color.green));


                Toast.makeText(Gestures.this,"Now Pen Color is green",Toast.LENGTH_SHORT).show();

            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                gestures.setGestureColor(getResources().getColor(R.color.red));




                Toast.makeText(Gestures.this,"Now Pen Color is Red",Toast.LENGTH_SHORT).show();

            }
        });
*/
    }


    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

    }

}
