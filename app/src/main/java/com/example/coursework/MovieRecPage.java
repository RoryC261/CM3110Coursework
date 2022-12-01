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

public class MovieRecPage extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private int counter = 0;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec_page);

        mQueue = Volley.newRequestQueue(this);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonNext = findViewById(R.id.button_next);

        Bundle movieResults = getIntent().getExtras();
        String userInput = movieResults.getString("user_input");
        Log.i("====== DEBUG ", "LOG 2 - INTENT GET() IS NOT THROWING ERROR ======");

        String key = "445164-RoryCame-WLB5WH2V";
        String url = "https://tastedive.com/api/similar?info=1&q=" + userInput + "&k=" + key;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("Similar");
                            jsonArray = jsonObject.getJSONArray("Results");

                            JSONObject movie = jsonArray.getJSONObject(getCounter());

                            nextSong();
                            Log.i("====== DEBUG ", "LOG 7 - COUNTER INITIALLY INCREMENTED ======");
                            Log.i("====== DEBUG ", "LOG 3 - API RESULT IS INITIALLY DISPLAYED ======");
                        } catch (JSONException e) {
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

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.i("====== DEBUG ", "LOG 4 - WE ARE IN THE EVENT LISTENER ======");
                    nextSong();
                    Log.i("====== DEBUG ", "LOG 8 - GOT PAST INCREMENTATION ======");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private int getCounter(){
        return counter;
    }

    private void setCounter(int value){
        counter = counter + value;
    }

    public void nextSong() throws JSONException {
        Log.i("====== DEBUG ", "LOG 5 - WE ARE SETTING NEW RESULTS TO BE DISPLAYED AFTER BUTTON PRESSED ======");
        JSONObject movie = jsonArray.getJSONObject(getCounter());
        String movieName = movie.getString("Name");
        String movieDescription = movie.getString("wTeaser");
        String movieTrailer = movie.getString("yUrl");
        mTextViewResult.setText(movieName + ", " + movieDescription + movieTrailer + "\n\n");
        setCounter(1);
        Log.i("====== DEBUG ", "LOG 6 - RESULTS HOPEFULLY DISPLAYED ======");
    }
}