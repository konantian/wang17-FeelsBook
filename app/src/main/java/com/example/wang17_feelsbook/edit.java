package com.example.wang17_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class edit extends AppCompatActivity {

    public TextView edit_text;
    public String stringToPassBack;
    public String content;
    public String position;
    public Integer location;
    public ArrayList<String> tweets;

    private static final String FILENAME = "feels.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent= getIntent();

        content=intent.getStringExtra("content");
        position=intent.getStringExtra("position");
        location = Integer.valueOf(position);

        edit_text=findViewById(R.id.content);
        edit_text.setText(content);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        tweets = new ArrayList<>();

        FileInputStream fis = openFileInput(FILENAME);

        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        String line = in.readLine();
        while (line != null) {
            tweets.add(line);
            line = in.readLine();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save(View view) throws IOException {
        stringToPassBack= edit_text.getText().toString();


        tweets.set(location,stringToPassBack);
        deleteFile(FILENAME);

        for(int i=0;i<tweets.size();i++){
            saveNew(tweets.get(i)+"\n");
        }
        finish();
    }

    public void delete(View view){
        deleteFile(FILENAME);

        for(int i=0;i<tweets.size();i++){
            if(i != location) {
                saveNew(tweets.get(i) + "\n");
            }
        }

        finish();
    }

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

}
