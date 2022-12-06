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

    private EditText mEnterMovieResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonParse = findViewById(R.id.button_parse); // Button user presses to see recommended movies
        Button buttonMovieFav = findViewById(R.id.button_movie_fav);

        mEnterMovieResult = (EditText)findViewById(R.id.enterMovie); // Variable which holds user inputted movie

        // When button is pressed, Movie Recommendation page is opened
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checks if user has typed something into the search bar
                if (!mEnterMovieResult.getText().toString().equals("")) {
                    openMovieRec();
                }
            }
        });

        // When button is pressed, users saved movie favourites activity is opened
        buttonMovieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMovieFav();
            }
        });
    }

    // Method which starts activity, as well as sends the user input to new activity
    public void openMovieRec() {
        Intent openMovieRec = new Intent(this, MovieRecPage.class);
        openMovieRec.putExtra("user_input", mEnterMovieResult.getText().toString());
        startActivity(openMovieRec);
    }

    // Method which starts activity
    public void openMovieFav(){
        Intent openMovieFav= new Intent(this, MovieFavourites.class);
        startActivity(openMovieFav);
    }
}