package com.example.wang17_feelsbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class joyEmotion extends AppCompatActivity {


    // create an initial entry string for comparison purposes when user tries to cancel entry
    private String initial_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_emotion);

        // get emotion passed from the MainActivity
        Intent intent= getIntent();
        String emotion=intent.getStringExtra("emotion");
        display_emoji(emotion);

        // set the Edittext and TextViews to the appropriate layout objects via the findViewId method
        EditText editText= findViewById(R.id.comment);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        String strdate=dateFormat.format(date);
        //edate.setText(strdate);
        // merge the emotion, date, and comment into a single entry which will later be user to compare changes
        //initial_entry=emotion+" -- "+strdate+editText.getText().toString();


    }

    public void display_emoji(String emotion){
        ImageView image= (ImageView) findViewById(R.id.emoji);
        if(emotion.equals("joy")){
            image.setImageResource(R.drawable.joy);
        }

        if(emotion.equals("love")){
            image.setImageResource(R.drawable.love);
        }

        if(emotion.equals("fear")){
            image.setImageResource(R.drawable.fear);
        }

        if(emotion.equals("anger")){
            image.setImageResource(R.drawable.anger);
        }

        if(emotion.equals("surprise")){
            image.setImageResource(R.drawable.surprise);
        }

        if(emotion.equals("sadness")){
            image.setImageResource(R.drawable.sadness);
        }
    }
    public void cancel(View view){

        finish();
    }

    public void post(View view){
        setResult(RESULT_OK);
        TextView test=findViewById(R.id.test);
        EditText editText= findViewById(R.id.comment);
        initial_entry=editText.getText().toString();

        test.setText(initial_entry);
        //finish();
    }

}
