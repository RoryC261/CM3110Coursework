package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.webkit.WebViewClient;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

public class MovieRecPage extends AppCompatActivity {

    private TextView mTextViewResult;
    private TextView mtext_view_movie_name;
    private RequestQueue mQueue;
    private int counter = 0;
    private JSONArray jsonArray;
    private WebView mWebView;
    private Button buttonFavourite;
    private Button buttonGoToFavourites;
    private ArrayList<Boolean> isFavourited = new ArrayList<Boolean>();

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
        buttonGoToFavourites = findViewById(R.id.button_movie_fav);

        Bundle movieResults = getIntent().getExtras();
        String userInput = movieResults.getString("user_input");

        String key = "445164-RoryCame-WLB5WH2V";
        String url = "https://tastedive.com/api/similar?info=1&q=" + userInput + "&k=" + key;

        mWebView =(WebView)findViewById(R.id.videoview);
        // mWebView.setBackgroundColor(Color.TRANSPARENT);

        // returns user to Movie Search Page when button is pressed
        buttonBackToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBackToSearch();
            }
        });

        // sends user to favourites page when button is pressed
        buttonGoToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavourites();
            }
        });

        // JSON Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int firstCounter = 0; // Created a separate counter for first display so forwards and backwards buttons work correctly and to save duplication of code

                            buttonBack.setClickable(false); // Disables option to press button

                            // Gets JSON and creates and Array of dictionaries with movie data
                            JSONObject jsonObject = response.getJSONObject("Similar");
                            jsonArray = jsonObject.getJSONArray("Results");
                            Log.d("TEST", jsonArray.toString());

                            JSONObject movie = jsonArray.getJSONObject(firstCounter);
                            // Created an Arraylist with false values that will each corrospond to a movie
                            for (int i = 0; i< movie.length(); i++) {
                                isFavourited.add(Boolean.FALSE);
                                Log.d("DEBUG", String.valueOf(isFavourited.get(i)));
                            }

                            nextSong();
                        } catch (JSONException e) {
                            // If No Results are found display error message (input validation)
                            e.printStackTrace();
                            mtext_view_movie_name.setText(":( No results found, Are you sure that's a movie?");
                            buttonBack.setVisibility(View.GONE);
                            buttonNext.setVisibility(View.GONE);
                            buttonFavourite.setVisibility(View.GONE);


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
                // Back button will be disabled when at start or array and enabled for every other index
                try {
                    if (getCounter() < jsonArray.length() - 1) {
                        setCounter(1);
                        nextSong();
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
                try {
                    // Next button will be disabled at end of array and enabled at every other index
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
            }
        });

        // Adds movie data to Room database when favourite button is pressed
        buttonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject movie = jsonArray.getJSONObject(getCounter());
                    String movieName = movie.getString("Name");
                    String movieDescription = movie.getString("wTeaser");
                    String movieTrailer = movie.getString("yUrl");
                    viewmodel.insertMovie(new Movie(movieName, movieDescription, movieTrailer));
                    getIsFavourited().set(getCounter(), true);
                    buttonFavourite.setClickable(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        viewmodel.getAllMovies().observe((LifecycleOwner) this, movieList -> {

            for (Movie movies: movieList){
                Log.d("TEST", movies.movieName + ", " + movies.movieDescription + ", " + movies.movieUrl);
            }
        });

    }

    // Getters and Setters
    private int getCounter() {
        return counter;
    }

    private void setCounter(int value) {
        counter = counter + value;
    }

    private Button getButtonFavourite(){
        return buttonFavourite;
    }

    private ArrayList<Boolean> getIsFavourited(){
        return isFavourited;
    }

    // Displays next song reccomendation
    public void nextSong() throws JSONException {
        Log.d("DEBUG", String.valueOf(isFavourited.get(getCounter())));
        // Checks if corresponding element in isFavourited array to movie array is true, if its true this means movie has already been favourited
        if (getIsFavourited().get(getCounter()) == true) {
            getButtonFavourite().setClickable(false);
        } else {
            getButtonFavourite().setClickable(true);
        }

        JSONObject movie = jsonArray.getJSONObject(getCounter());
        String movieName = movie.getString("Name");
        String movieDescription = movie.getString("wTeaser");
        String movieTrailer = movie.getString("yUrl");
        mtext_view_movie_name.setText(movieName);
        // mTextViewResult.setText(movieDescription + movieTrailer + "\n\n");
        playVideo(movieTrailer);

    }

    public void openBackToSearch(){
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    // Displays Youtube video of movie trailer
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

    public void openFavourites(){
        Intent openFav = new Intent(this, MovieFavourites.class);
        startActivity(openFav);
    }
}