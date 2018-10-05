/*MIT License
        Copyright (c) 2018 Yuan Wang
        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:
        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.
        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.*/

package com.example.wang17_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class joyEmotion extends AppCompatActivity {


    //set some variables and the filename
    private String initial_entry;
    private static final String FILENAME = "history.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_emotion);

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

        initial_entry="\n";
        saveFeels(initial_entry);
        finish();
    }


    //post button to add user's comment to the history data
    public void post(View view){

        //setResult(RESULT_OK);
        //locate the edittext area and get the user's input

        EditText editText= findViewById(R.id.comment);
        initial_entry=editText.getText().toString()+"\n";
        saveFeels(initial_entry);
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
