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
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class edit extends AppCompatActivity {


    //set some variables and filename
    public TextView edit_text;
    public String stringToPassBack;
    public String content;
    public String position;
    public Integer location;
    public ArrayList<String> records;
    public ArrayList<Integer> counter = new ArrayList<>();

    private static final String FILENAME = "history.sav";
    private static final String COUNT = "count.sav";


    //once user clicked specific records we get the position and the records
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent= getIntent();

        //initialize the counter data
        counter=loadFromFile();

        //initialize the record and position
        content=intent.getStringExtra("content");
        position=intent.getStringExtra("position");
        location = Integer.valueOf(position);

        //display the record on the edit area
        edit_text=findViewById(R.id.content);
        edit_text.setText(content);

        //load all the records
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //function to load all records, code from
    //https://github.com/joshua2ua/lonelyTwitter

    public void init() throws IOException {
        records = new ArrayList<>();

        FileInputStream fis = openFileInput(FILENAME);

        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        String line = in.readLine();
        while (line != null) {
            records.add(line);
            line = in.readLine();
        }
    }

    //based on the emotion and the value to add count or minus count
    public void update_count(String feel,int value){

        switch(feel){
            case "Joy":
                counter.set(0,counter.get(0)+value);

                break;

            case "Love":
                counter.set(1,counter.get(1)+value);
                break;

            case "Anger":
                counter.set(2,counter.get(2)+value);

                break;

            case "Fear":
                counter.set(3,counter.get(3)+value);

                break;

            case "Surprise":
                counter.set(4,counter.get(4)+value);

                break;

            case "Sadness":
                counter.set(5,counter.get(5)+value);

                break;
        }

        //setResult(RESULT_OK);
        //save the count data to local file
        saveInFile(counter);

    }

    //when user changed the record, we detect if user changed the emotion
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save(View view) throws IOException, ParseException {
        stringToPassBack= edit_text.getText().toString();

        //get the substring to compare if two emtions are equal or not
        String stringA = content.substring(0,content.indexOf("[")).trim();
        String stringB = stringToPassBack.substring(0,stringToPassBack.indexOf("[")).trim();

        if(!stringA.equals(stringB)){
            update_count(stringA,-1);
            update_count(stringB,1);
        }

        //update the records
        records.set(location,stringToPassBack);
        deleteFile(FILENAME);

        //sort the records by date
        Collections.sort(records, new StringDateComparator());

        //write the updates to file
        for(int i=0;i<records.size();i++){
            saveNew(records.get(i)+"\n");
        }

        finish();
    }

    //sort all dates,code from
    //https://stackoverflow.com/questions/15462814/sort-list-with-string-dates
    class StringDateComparator implements Comparator<String>
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        public int compare(String lhs, String rhs)
        {
            try {
                return sdf.parse(lhs.substring(lhs.indexOf("[")+1,lhs.indexOf("]"))).compareTo(sdf.parse(rhs.substring(rhs.indexOf("[")+1,rhs.indexOf("]"))));
            } catch (ParseException e) {

                e.printStackTrace();
                return -1;
            }
        }
    }

    //delete the record and minus the count by 1
    public void delete(View view){
        deleteFile(FILENAME);

        String emotions[]={"Joy","Love","Fear","Sadness","Surprise","Anger"};

        //use for loop to check which emotion this record include
        for (String emotion : emotions) {
            if(content.contains(emotion)){
                update_count(emotion,-1);
                System.out.println(emotion);
                break;
            }
        }

        //update the records and save to file
        for(int i=0;i<records.size();i++){
            if(i != location) {
                saveNew(records.get(i) + "\n");
            }
        }

        finish();
    }

    //save the updated records to the file, code from
    //https://github.com/joshua2ua/lonelyTwitter

    private void saveNew(String text) {
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


    //load the count data from local file, this code from
    //https://github.com/joshua2ua/lonelyTwitter
    private ArrayList<Integer> loadFromFile() {

        ArrayList<Integer> counter = new ArrayList<Integer>();

        try {
            FileInputStream fis = openFileInput(COUNT);
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
            FileOutputStream fos = openFileOutput(COUNT, MODE_PRIVATE);
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
