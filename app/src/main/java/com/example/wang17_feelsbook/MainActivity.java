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

/*
https://github.com/tylerwatson98/tpwatson-FeelsBook/blob/master/app/src/main/java/com/example/tpwatson_feelsbook/EmotionEntry.java
 */

package com.example.wang17_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    //Initialize some variables at the beginning
    private static final String FILENAME = "count.sav";
    private static final String FILENAME2 = "history.sav";
    protected static ArrayList<Integer> counter = new ArrayList<>();

    private TextView joy_text;
    private TextView love_text;
    private TextView anger_text;
    private TextView fear_text;
    private TextView surprise_text;
    private TextView sadness_text;


    //Load all the data that need to display on the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* load all records from disk */
        counter=loadFromFile();
        joy_text = findViewById(R.id.joy_count);
        love_text = findViewById(R.id.love_count);
        anger_text = findViewById(R.id.anger_count);
        fear_text = findViewById(R.id.fear_count);
        surprise_text = findViewById(R.id.surprise_count);
        sadness_text = findViewById(R.id.sadness_count);

        joy_text.setText(String.format("%s",counter.get(0)));
        love_text.setText(String.format("%s",counter.get(1)));
        anger_text.setText(String.format("%s",counter.get(2)));
        fear_text.setText(String.format("%s",counter.get(3)));
        surprise_text.setText(String.format("%s",counter.get(4)));
        sadness_text.setText(String.format("%s",counter.get(5)));

    }

    //Each time back to the main activity,update the data from the file
    @Override
    public void onResume(){

        super.onResume();
        counter=loadFromFile();
        joy_text = findViewById(R.id.joy_count);
        love_text = findViewById(R.id.love_count);
        anger_text = findViewById(R.id.anger_count);
        fear_text = findViewById(R.id.fear_count);
        surprise_text = findViewById(R.id.surprise_count);
        sadness_text = findViewById(R.id.sadness_count);

        joy_text.setText(String.format("%s",counter.get(0)));
        love_text.setText(String.format("%s",counter.get(1)));
        anger_text.setText(String.format("%s",counter.get(2)));
        fear_text.setText(String.format("%s",counter.get(3)));
        surprise_text.setText(String.format("%s",counter.get(4)));
        sadness_text.setText(String.format("%s",counter.get(5)));

    }

    //click this button will lead to the activity to list all the past posts
    public void check_history(View view){
        Intent history = new Intent(MainActivity.this, history.class);
        startActivity(history);
    }

    //click the emotion buttons will record the emotion and date first
    public void record_feels(View view){

        //create the date string
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        String current_time=dateFormat.format(date);

        //check which button was clicked
        switch (view.getId()) {

            //joy button clicked
            case R.id.joy:

                //jump to a new activity to ask user if they want comment
                Intent joy_intent = new Intent(MainActivity.this, joyEmotion.class);
                joy_intent.putExtra("emotion","joy");
                startActivity(joy_intent);

                //record the emotion and the date
                saveFeels("Joy  "+current_time+"  |  ");

                //update the count for each emotion
                update_count("joy");

                break;

            //sadness button clicked
            case R.id.sadness:

                //jump to a new activity to ask user if they want comment
                Intent sadness_intent = new Intent(MainActivity.this, joyEmotion.class);
                sadness_intent.putExtra("emotion","sadness");
                startActivity(sadness_intent);

                //record the emotion and the date
                saveFeels("Sadness   "+current_time+"  |  ");
                //update the count for each emotion
                update_count("sadness");
                break;

            //love button clicked
            case R.id.love:

                //jump to a new activity to ask user if they want comment
                Intent love_intent = new Intent(MainActivity.this, joyEmotion.class);
                love_intent.putExtra("emotion","love");
                startActivity(love_intent);

                //record the emotion and the date
                saveFeels("Love   "+current_time+"  |  ");
                //update the count for each emotion
                update_count("love");

                break;

            //anger button clicked
            case R.id.anger:

                //jump to a new activity to ask user if they want comment
                Intent anger_intent = new Intent(MainActivity.this, joyEmotion.class);
                anger_intent.putExtra("emotion","anger");
                startActivity(anger_intent);

                //record the emotion and the date
                saveFeels("Anger   "+current_time+"  |  ");
                //update the count for each emotion
                update_count("anger");

                break;

            //surprise button clicked
            case R.id.surprise:

                //jump to a new activity to ask user if they want comment
                Intent surprise_intent = new Intent(MainActivity.this, joyEmotion.class);
                surprise_intent.putExtra("emotion","surprise");
                startActivity(surprise_intent);

                //record the emotion and the date
                saveFeels("Surprise   "+current_time+"  |  ");
                //update the count for each emotion
                update_count("surprise");

                break;

            case R.id.fear:

                //jump to a new activity to ask user if they want comment
                Intent fear_intent = new Intent(MainActivity.this, joyEmotion.class);
                fear_intent.putExtra("emotion","fear");
                startActivity(fear_intent);

                //record the emotion and the date
                saveFeels("Fear   "+current_time+"  |  ");
                //update the count for each emotion
                update_count("fear");

                break;

        }

    }

    //given an emotion name and update the count for each emotion
    public void update_count(String feel){

        switch(feel){
            case "joy":

                //get the current count and update it by +1 and set it to display
                counter.set(0,counter.get(0)+1);
                joy_text.setText(String.format("%s",counter.get(0)));

                break;

            case "love":

                //get the current count and update it by +1 and set it to display
                counter.set(1,counter.get(1)+1);

                love_text.setText(String.format("%s",counter.get(1)));

                break;

            case "anger":

                //get the current count and update it by +1 and set it to display
                counter.set(2,counter.get(2)+1);

                anger_text.setText(String.format("%s",counter.get(2)));
                break;

            case "fear":

                //get the current count and update it by +1 and set it to display
                counter.set(3,counter.get(3)+1);

                fear_text.setText(String.format("%s",counter.get(3)));

                break;

            case "surprise":

                //get the current count and update it by +1 and set it to display
                counter.set(4,counter.get(4)+1);

                surprise_text.setText(String.format("%s",counter.get(4)));

                break;

            case "sadness":

                //get the current count and update it by +1 and set it to display
                counter.set(5,counter.get(5)+1);
                sadness_text.setText(String.format("%s",counter.get(5)));

                break;
        }

        //save the count data to the file
        //setResult(RESULT_OK);
        saveInFile(counter);

    }

    //load the count data from local file, this code from
    //https://github.com/joshua2ua/lonelyTwitter

    private ArrayList<Integer> loadFromFile() {

        ArrayList<Integer> counter = new ArrayList<Integer>();

        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            /* read the json data back from the file and assign it to EmotionRecords arrayList */
            Gson gson = new Gson();
            Type listEmotionRecords = new TypeToken<ArrayList<Integer>>(){}.getType();
            counter = gson.fromJson(reader, listEmotionRecords);

            fis.close();

        } catch (FileNotFoundException e) {
            for(int i=0;i<6;i++) counter.add(0);
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    //save the count data to local file, code from
    //https://github.com/joshua2ua/lonelyTwitter
    private void saveInFile(ArrayList<Integer> array) {
        try {
            /* create buffered stream writer */
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            /* convert array list to Json and write to disk */
            Gson gson = new Gson();
            gson.toJson(array, writer);

            /* ensure writer has flushed all buffers, then close */
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //save the emotion and date data to local file, code from
    //https://github.com/joshua2ua/lonelyTwitter
    private void saveFeels(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME2,
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
