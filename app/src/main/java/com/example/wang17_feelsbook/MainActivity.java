package com.example.wang17_feelsbook;

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


    public void record(View view){
        TextView joy_text = findViewById(R.id.joy_count);
        TextView love_text = findViewById(R.id.love_count);
        TextView anger_text = findViewById(R.id.anger_count);
        TextView fear_text = findViewById(R.id.fear_count);
        TextView surprise_text = findViewById(R.id.surprise_count);
        TextView sadness_text = findViewById(R.id.sadness_count);


        switch(view.getId()){
            case R.id.joy:
                counter.set(0,counter.get(0)+1);
                joy_text.setText(String.format("%s",counter.get(0)));

                break;

            case R.id.love:
                counter.set(1,counter.get(1)+1);

                love_text.setText(String.format("%s",counter.get(1)));

                break;

            case R.id.anger:
                counter.set(2,counter.get(2)+1);

                anger_text.setText(String.format("%s",counter.get(2)));
                break;

            case R.id.fear:
                counter.set(3,counter.get(3)+1);

                fear_text.setText(String.format("%s",counter.get(3)));

                break;

            case R.id.surprise:
                counter.set(4,counter.get(4)+1);

                surprise_text.setText(String.format("%s",counter.get(4)));

                break;

            case R.id.sadness:
                counter.set(5,counter.get(5)+1);
                sadness_text.setText(String.format("%s",counter.get(5)));

                break;
        }
        setResult(RESULT_OK);
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
