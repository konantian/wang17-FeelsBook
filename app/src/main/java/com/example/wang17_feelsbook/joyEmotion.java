package com.example.wang17_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

        // set the Edittext and TextViews to the appropriate layout objects via the findViewId method
        //TextView tv = findViewById(R.id.emotion);
        EditText editText= findViewById(R.id.joy_comment);
        //TextView edate=findViewById(R.id.date);

        // set the textView's character sequence to the value passed into the activity
        //tv.setText(emotion);

        // create a new datetime object with the specified assignment format and set the date objects text
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        String strdate=dateFormat.format(date);
        //edate.setText(strdate);
        // merge the emotion, date, and comment into a single entry which will later be user to compare changes
        //initial_entry=tv.getText().toString()+" -- "+edate.getText().toString()+"\n"+editText.getText().toString();

    }

    public void cancel(View view){

        // launch an intent to return to the home screen
        Intent intent = new Intent(joyEmotion.this, MainActivity.class);
        startActivity(intent);
    }

    public void post(View view){

        // launch an intent to return to the home screen
        Intent intent = new Intent(joyEmotion.this, MainActivity.class);
        startActivityForResult(intent,1);
        startActivity(intent);
    }

}
