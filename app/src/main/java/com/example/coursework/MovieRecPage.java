package com.example.coursework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import java.util.ArrayList;
import java.util.List;

public class MovieRecPage extends AppCompatActivity {

    private TextView mTextViewResult;
    private TextView mtext_view_movie_name;
    private RequestQueue mQueue;
    private int counter = 0;
    private JSONArray jsonArray;
    private WebView mWebView;
    private Button buttonFavourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec_page);

        MovieViewmodel viewmodel = ViewModelProviders.of(this).get(MovieViewmodel.class);

        mQueue = Volley.newRequestQueue(this);
        mTextViewResult = findViewById(R.id.text_view_result);
        mtext_view_movie_name = findViewById(R.id.text_view_movie_name);
        Button buttonNext = findViewById(R.id.button_next);
        Button buttonBack = findViewById(R.id.button_back);
        Button buttonBackToSearch = findViewById(R.id.button_back_to_search);
        buttonFavourite = findViewById(R.id.button_favourite);

        Bundle movieResults = getIntent().getExtras();
        String userInput = movieResults.getString("user_input");
        Log.i("====== DEBUG ", "LOG 2 - INTENT GET() IS NOT THROWING ERROR ======");

        String key = "445164-RoryCame-WLB5WH2V";
        String url = "https://tastedive.com/api/similar?info=1&q=" + userInput + "&k=" + key;

        mWebView =(WebView)findViewById(R.id.videoview);

        buttonBackToSearch.setOnClickListener(new View.OnClickListener() {
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

        buttonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject movie = jsonArray.getJSONObject(getCounter());
                    String movieName = movie.getString("Name");
                    String movieDescription = movie.getString("wTeaser");
                    String movieTrailer = movie.getString("yUrl");
                    viewmodel.insertMovie(new Movie(movieName, movieDescription, movieTrailer));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        viewmodel.getAllMovies().observe((LifecycleOwner) this, movieList -> {
            Log.d("Movies", String.valueOf(movieList.size()));

            for (Movie movies: movieList){
                Log.d("Movies", movies.movieName + ", " + movies.movieDescription + ", " + movies.movieUrl);
            }
        });

    }

    private int getCounter() {
        return counter;
    }

    private void setCounter(int value) {
        counter = counter + value;
    }

    private Button getButtonFavourite(){
        return buttonFavourite;
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
        playVideo(movieTrailer);

    }

    public void openBackToSearch(){
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    public void playVideo(String movieTrailer){
        String videoStr = "<html><body>Promo video<br><iframe width=\"420\" height=\"315\" src=\"" + movieTrailer + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings ws = mWebView.getSettings();
        ((WebSettings) ws).setJavaScriptEnabled(true);
        mWebView.loadData(videoStr, "text/html", "utf-8");
    }
}