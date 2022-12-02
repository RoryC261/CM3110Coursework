package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieRecPage extends YouTubeBaseActivity {

    private TextView mTextViewResult;
    private TextView mtext_view_movie_name;
    private RequestQueue mQueue;
    private int counter = 0;
    private JSONArray jsonArray;

    YouTubePlayerView myouTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec_page);

        mQueue = Volley.newRequestQueue(this);
        mTextViewResult = findViewById(R.id.text_view_result);
        mtext_view_movie_name = findViewById(R.id.text_view_movie_name);
        Button buttonNext = findViewById(R.id.button_next);
        Button buttonBack = findViewById(R.id.button_back);
        Button buttonBackToSearch = findViewById(R.id.button_back_to_search);
        // myouTubePlayerView = findViewById(R.id.youtube_player_view);

        Bundle movieResults = getIntent().getExtras();
        String userInput = movieResults.getString("user_input");
        Log.i("====== DEBUG ", "LOG 2 - INTENT GET() IS NOT THROWING ERROR ======");

        String key = "445164-RoryCame-WLB5WH2V";
        String url = "https://tastedive.com/api/similar?info=1&q=" + userInput + "&k=" + key;

        buttonBackToSearch.setOnClickListener(new View.OnClickListener() {
            // STOP CONTROL Z HERE PLS
            @Override
            public void onClick(View view) {
                openBackToSearch();
            }
        });

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int firstCounter = 0;
                            buttonBack.setClickable(false);
                            JSONObject jsonObject = response.getJSONObject("Similar");
                            jsonArray = jsonObject.getJSONArray("Results");


                            JSONObject movie = jsonArray.getJSONObject(firstCounter);
                            String movieTrailer = movie.getString("yUrl");

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
                    if (getCounter() < jsonArray.length() - 1) {
                        Log.i("====== DEBUG ", "LOG 4 - WE ARE IN THE EVENT LISTENER ======");
                        setCounter(1);
                        nextSong();
                        Log.i("====== DEBUG ", "LOG 8 - GOT PAST INCREMENTATION ======");
                        buttonBack.setClickable(true);
                    } if (getCounter() >= jsonArray.length() - 1){
                        buttonNext.setClickable(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("====== DEBUG ", "LOG 11 - WE ARE IN BACK SONGTHE EVENT LISTENER ======");
                try {
                    if (getCounter() > 0) {
                        setCounter(-1);
                        nextSong();
                        buttonNext.setClickable(true);
                    } if (getCounter() <= 0) {
                        buttonBack.setClickable(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("====== DEBUG ", "LOG 12 - GOT PAST INCREMENTATION BACK SONG ======");
            }
        });



    }

    private int getCounter() {
        return counter;
    }

    private void setCounter(int value) {
        counter = counter + value;
    }

    public void nextSong() throws JSONException {
        Log.i("====== DEBUG ", "LOG 5 - WE ARE SETTING NEW RESULTS TO BE DISPLAYED AFTER BUTTON PRESSED ======");
        JSONObject movie = jsonArray.getJSONObject(getCounter());
        String movieName = movie.getString("Name");
        String movieDescription = movie.getString("wTeaser");
        String movieTrailer = movie.getString("yUrl");
        mtext_view_movie_name.setText(movieName);
        // mTextViewResult.setText(movieDescription + movieTrailer + "\n\n");
        Log.i("====== DEBUG ", "LOG 6 - RESULTS HOPEFULLY DISPLAYED ======");
    }

    public void openBackToSearch(){
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    /* // TRY FIX LATER THIS IS FUCKED WILL ATTEMPT LATER
    public void queueUrls(ArrayList<String> urls){
        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                for (String element : urls){
                    youTubePlayer.cueVideo(element.substring(element.lastIndexOf("/") + 1));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "INITIALIZATION FAILED", Toast.LENGTH_SHORT).show();
            }

        };
        myouTubePlayerView.initialize("AIzaSyCFJyQDYDHxlcdkdlDCCwhp7FjyFUivQGY", listener);
    }
    */
}