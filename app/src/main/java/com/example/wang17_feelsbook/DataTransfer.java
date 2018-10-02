package com.example.wang17_feelsbook;

import android.content.Context;

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

public class DataTransfer {
    protected static final String Records_Filename = "RECORDS.dat";
    protected DataTransferExceptionManager CallingClass;

    /* part of the "DataTransferExceptionManager" interface is letting each class that implements
     * it be able to call performDataExceptionAction() when something goes wrong to handle the
     * data exception in its own way.  The datamanager calls this function on the calling class
     * when something goes wrong, so naturally that calling class must be registered.
     */
    public void registerCallingClass(DataTransferExceptionManager Class) {
        this.CallingClass = Class;
    }

    /* save function for EmotionRecords arraylist */
    public void saveToDisk(Context context, ArrayList<Integer> array) {
        try {
            /* create buffered stream writer */
            FileOutputStream fos = context.openFileOutput(Records_Filename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            /* convert array list to Json and write to disk */
            Gson gson = new Gson();
            gson.toJson(array, writer);

            /* ensure writer has flushed all buffers, then close */
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* load function for EmotionRecords arraylist */
    public ArrayList<Integer> loadEmotionRecordsFromDisk(Context context) {
        /* contains each emotion record that is produced */
        ArrayList<Integer> EmotionRecords = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(Records_Filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            /* read the json data back from the file and assign it to EmotionRecords arrayList */
            Gson gson = new Gson();
            Type listEmotionRecords = new TypeToken<ArrayList<Integer>>(){}.getType();
            EmotionRecords = gson.fromJson(reader, listEmotionRecords);

            fis.close();

        } catch (FileNotFoundException e) {
            this.CallingClass.performDataExceptionAction();
            e.printStackTrace();
        } catch (IOException e) {
            this.CallingClass.performDataExceptionAction();
            e.printStackTrace();
        }
        return EmotionRecords;
    }
}