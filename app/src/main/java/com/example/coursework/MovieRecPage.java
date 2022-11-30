package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieRecPage extends AppCompatActivity {

    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec_page);

        mTextViewResult = findViewById(R.id.text_view_result);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("search_results");
        JSONArray searchResults = new JSONArray(jsonArray);
        Log.println(Log.DEBUG, "debug", "=== API TEST 7 ===");
        // mTextViewResult.append("HELLO WORLD");


        try{
            for (int i = 0; i < searchResults.length(); i++) {
                JSONObject movie = searchResults.getJSONObject(i);

                String name = movie.getString("Name");
                String type = movie.getString("Type");

                mTextViewResult.append(name + ", " + type + "\n\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}