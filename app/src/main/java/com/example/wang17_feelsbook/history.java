package com.example.wang17_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class history extends AppCompatActivity {


    private static final String FILENAME = "feels.sav";
    private ListView history_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        history_list = findViewById(R.id.emotion_list);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        String[] feels = loadFromFile();
        final ArrayAdapter<String> ea = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feels);
        // set the adaptor to the listView to list the stored emotions
        history_list.setAdapter(ea);
    }

    public void home(View view){

        // launch an intent to return to the home screen
        finish();
    }

    private String[] loadFromFile() {
        ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
                tweets.add(line);
                line = in.readLine();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tweets.toArray(new String[tweets.size()]);
    }
}
