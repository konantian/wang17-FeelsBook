package com.example.wang17_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class recordEmotion extends AppCompatActivity {


    //set some variables and the filename
    private String message;
    private static final String FILENAME = "history.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_emotion);

        //get the message from the main page to know which emotion user clicked
        Intent intent= getIntent();
        String emotion=intent.getStringExtra("emotion");
        display_emoji(emotion);
    }

    //based on the clicked emotion to display the emoji picture
    public void display_emoji(String emotion){
        ImageView image= findViewById(R.id.emoji);
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

    //cancel button to back to the main page and save ang changes to local file
    public void cancel(View view){

        message="\n";
        saveFeels(message);
        finish();
    }


    //post button to add user's comment to the history data
    public void post(View view){

        //promopt to user that comments saved already
        Toast.makeText(this,"Successful saved the comments",Toast.LENGTH_SHORT).show();
        //setResult(RESULT_OK);
        //locate the edittext area and get the user's input

        EditText editText= findViewById(R.id.comment);
        message=editText.getText().toString()+"\n";
        saveFeels(message);
        finish();
    }

    //save the comment to local file, code from
    //https://github.com/joshua2ua/lonelyTwitter
    private void saveFeels(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_APPEND);
            fos.write(text.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
