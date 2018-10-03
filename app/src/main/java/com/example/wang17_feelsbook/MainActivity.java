package com.example.wang17_feelsbook;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final String FILENAME = "file.sav";
    protected static ArrayList<Integer> counter = new ArrayList<>();
    /* initialize a data transfer manager */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* load all records from disk */
        counter=loadFromFile();
        init();
    }


    public void init(){

        TextView joy_text = findViewById(R.id.joy_count);
        TextView love_text = findViewById(R.id.love_count);
        TextView anger_text = findViewById(R.id.anger_count);
        TextView fear_text = findViewById(R.id.fear_count);
        TextView surprise_text = findViewById(R.id.surprise_count);
        TextView sadness_text = findViewById(R.id.sadness_count);

        joy_text.setText(String.format("%s",counter.get(0)));
        love_text.setText(String.format("%s",counter.get(1)));
        anger_text.setText(String.format("%s",counter.get(2)));
        fear_text.setText(String.format("%s",counter.get(3)));
        surprise_text.setText(String.format("%s",counter.get(4)));
        sadness_text.setText(String.format("%s",counter.get(5)));

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                update_count("joy");
            }
        }

        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                update_count("love");
            }
        }

        if(requestCode == 3){
            if(resultCode == RESULT_OK){
                update_count("fear");
            }
        }

        if(requestCode == 4){
            if(resultCode == RESULT_OK){
                update_count("anger");
            }
        }

        if(requestCode == 5){
            if(resultCode == RESULT_OK){
                update_count("sadness");
            }
        }

        if(requestCode == 6){
            if(resultCode == RESULT_OK){
                update_count("surprise");
            }
        }
    }
    public void record_feels(View view){
        switch (view.getId()) {
            // in the case of the joy button being clicked

            case R.id.joy:

                // the clicking of this button creates a new intent which takes the user to the emotion entry activity
                Intent joy_intent = new Intent(MainActivity.this, joyEmotion.class);
                // pass the joy value designated to the button to the emotion entry activity via its key "emotion"
                joy_intent.putExtra("emotion","joy");
                // start the activity
                startActivityForResult(joy_intent,1);
                // break from the case so the sequence does not continue onto other cases
                break;

            // each case will perform almost identical methods but will differ in their count IDs & values and the exported message
            case R.id.sadness:
                Intent sadness_intent = new Intent(MainActivity.this, joyEmotion.class);
                sadness_intent.putExtra("emotion","sadness");
                startActivityForResult(sadness_intent,5);
                break;

            case R.id.love:
                // the clicking of this button creates a new intent which takes the user to the emotion entry activity
                Intent love_intent = new Intent(MainActivity.this, joyEmotion.class);
                // pass the joy value designated to the button to the emotion entry activity via its key "emotion"
                love_intent.putExtra("emotion","love");
                // start the activity
                startActivityForResult(love_intent,2);
                // break from the case so the sequence does not continue onto other cases
                break;

            case R.id.anger:
                // the clicking of this button creates a new intent which takes the user to the emotion entry activity
                Intent anger_intent = new Intent(MainActivity.this, joyEmotion.class);
                // pass the joy value designated to the button to the emotion entry activity via its key "emotion"
                anger_intent.putExtra("emotion","anger");
                // start the activity
                startActivityForResult(anger_intent,4);
                // break from the case so the sequence does not continue onto other cases
                break;

            case R.id.surprise:
                // the clicking of this button creates a new intent which takes the user to the emotion entry activity
                Intent surprise_intent = new Intent(MainActivity.this, joyEmotion.class);
                // pass the joy value designated to the button to the emotion entry activity via its key "emotion"
                surprise_intent.putExtra("emotion","surprise");
                // start the activity
                startActivityForResult(surprise_intent,6);
                // break from the case so the sequence does not continue onto other cases
                break;

            case R.id.fear:
                // the clicking of this button creates a new intent which takes the user to the emotion entry activity
                Intent fear_intent = new Intent(MainActivity.this, joyEmotion.class);
                // pass the joy value designated to the button to the emotion entry activity via its key "emotion"
                fear_intent.putExtra("emotion","fear");
                // start the activity
                startActivityForResult(fear_intent,3);
                // break from the case so the sequence does not continue onto other cases
                break;

        }
        saveInFile(counter);

    }

    public void update_count(String feel){
        TextView joy_text = findViewById(R.id.joy_count);
        TextView love_text = findViewById(R.id.love_count);
        TextView anger_text = findViewById(R.id.anger_count);
        TextView fear_text = findViewById(R.id.fear_count);
        TextView surprise_text = findViewById(R.id.surprise_count);
        TextView sadness_text = findViewById(R.id.sadness_count);


        switch(feel){
            case "joy":
                counter.set(0,counter.get(0)+1);
                joy_text.setText(String.format("%s",counter.get(0)));

                break;

            case "love":
                counter.set(1,counter.get(1)+1);

                love_text.setText(String.format("%s",counter.get(1)));

                break;

            case "anger":
                counter.set(2,counter.get(2)+1);

                anger_text.setText(String.format("%s",counter.get(2)));
                break;

            case "fear":
                counter.set(3,counter.get(3)+1);

                fear_text.setText(String.format("%s",counter.get(3)));

                break;

            case "surprise":
                counter.set(4,counter.get(4)+1);

                surprise_text.setText(String.format("%s",counter.get(4)));

                break;

            case "sadness":
                counter.set(5,counter.get(5)+1);
                sadness_text.setText(String.format("%s",counter.get(5)));

                break;
        }
        //setResult(RESULT_OK);
        saveInFile(counter);
        //finish();

    }

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

}
