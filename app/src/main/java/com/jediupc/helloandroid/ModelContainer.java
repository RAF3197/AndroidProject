package com.jediupc.helloandroid;



import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class ModelContainer implements Serializable {
   // public String username;
    public ArrayList<AudioModel> audios = new ArrayList<>();
    public int removed;


    public static ModelContainer load(Context c) {
        try {
            FileInputStream fis = c.openFileInput("model.json");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            ModelContainer mc = new Gson().fromJson(reader, ModelContainer.class);
            if (mc == null) return new ModelContainer();
            return mc;
        } catch (FileNotFoundException e) {
            return new ModelContainer();
        }
    }

    public void print() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d("com.jediupc.helloandorid.ModelContainer", gson.toJson(this));
    }

    public void save(Context c) {
        try {
            FileOutputStream fos = c.openFileOutput("model.json", Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            new Gson().toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            Log.d("com.jediupc.helloandorid.ModelContainer", "error saving file", e);
        } catch (IOException e) {
            Log.d("com.jediupc.helloandorid.ModelContainer", "error saving file", e);
        }
    }

}
