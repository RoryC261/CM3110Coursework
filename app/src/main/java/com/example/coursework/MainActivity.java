package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private EditText mEnterArtistResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonParse = findViewById(R.id.button_parse);
        mQueue = Volley.newRequestQueue(this);

        mEnterArtistResult = (EditText)findViewById(R.id.enterArtist);

        Button buttonRec = findViewById(R.id.button_rec);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });

        buttonRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMovieRec();
            }
        });

    }

    public void openMovieRec() {
        Intent openMovieRec = new Intent(this, MovieRecPage.class);
        startActivity(openMovieRec);
    }

    private void jsonParse() {
        String key = "445164-RoryCame-WLB5WH2V";
        String url = "https://tastedive.com/api/similar?info=1&q=" + mEnterArtistResult.getText() + "&k=" + key;
        Log.println(Log.DEBUG, "debug", "=== API TEST 1 ===");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.println(Log.DEBUG, "debug", "=== API TEST 2 ===");
                        try {
                            Log.println(Log.DEBUG, "debug", "=== API TEST 3 ===");
                            JSONObject jsonObject = response.getJSONObject("Similar");
                            JSONArray jsonArray = jsonObject.getJSONArray("Results");
                            Log.println(Log.DEBUG, "debug", "=== API TEST 4 ===");

                            Intent searchResults = new Intent(MainActivity.this, MovieRecPage.class);
                            searchResults.putExtra("search_results", jsonArray.toString());
                            Log.println(Log.DEBUG, "debug", "=== API TEST 5 ===");
                            openMovieRec();

                        } catch (JSONException e) {
                            Log.println(Log.DEBUG, "debug", "=== API TEST 6 ===");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}