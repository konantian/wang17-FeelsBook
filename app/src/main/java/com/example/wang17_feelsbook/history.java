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

https://stackoverflow.com/questions/42957906/cannot-resolve-symbol-onitemclicklistener
https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android

 */

package com.example.wang17_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class history extends AppCompatActivity {


    //set some variables
    private static final String FILENAME = "history.sav";
    private ListView history_list;

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

        //set a string array to store all the history data,code from
        //https://github.com/joshua2ua/lonelyTwitter

        final String[] feels = loadFromFile();
        final ArrayAdapter<String> ea = new ArrayAdapter<>(this, R.layout.list_item, feels);

        // set the adaptor to the listView to list the stored emotions
        history_list.setAdapter(ea);

        //if user click an item in listview, jump to another activity,code from
        //https://stackoverflow.com/questions/20689357/how-can-i-go-to-an-other-activity-when-i-click-item-of-list-view

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


    //back to the main page
    public void home(View view){

        finish();
    }

    //load the history data from local file, this code from
    //https://github.com/joshua2ua/lonelyTwitter
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
