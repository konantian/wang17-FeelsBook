/*

https://stackoverflow.com/questions/42957906/cannot-resolve-symbol-onitemclicklistener
https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android

 */

package com.example.wang17_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class history extends AppCompatActivity {


    private static final String FILENAME = "feels.sav";
    private ListView history_list;
    public String new_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        history_list = findViewById(R.id.emotion_list);

        final String[] feels = loadFromFile();
        final ArrayAdapter<String> ea = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feels);
        // set the adaptor to the listView to list the stored emotions
        history_list.setAdapter(ea);

        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextActivity = new Intent(history.this, edit.class);
                String selectedFromList = (String) history_list.getItemAtPosition(position);
                nextActivity.putExtra("content",selectedFromList);
                nextActivity.putExtra("position",Integer.toString(position));
;               startActivity(nextActivity);
            }
        });

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                new_message = data.getStringExtra("back_message");

            }
        }
    }
    */


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
