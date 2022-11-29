package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "https://tastedive.com/api/similar?q=Drake&k=445164-RoryCame-WLB5WH2V";
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

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject movie = jsonArray.getJSONObject(i);

                                String name = movie.getString("Name");
                                String type = movie.getString("Type");

                                mTextViewResult.append(name + ", " + type + "\n\n");
                            }
                        } catch (JSONException e) {
                            Log.println(Log.DEBUG, "debug", "=== API TEST 5 ===");
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