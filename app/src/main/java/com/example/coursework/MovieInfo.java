package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MovieInfo extends AppCompatActivity {

    private TextView mtext_view_movie_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        mtext_view_movie_name = findViewById(R.id.text_view_movie_name);

        Intent intent = getIntent();
        String movieName = intent.getStringExtra("MOVIE_NAME");
        String movieDescription = intent.getStringExtra("MOVIE_DESCRIPTION");
        String movieURl = intent.getStringExtra("MOVIE_URL");
        Log.d("DEBUG", movieName);
        Log.d("DEBUG", movieDescription);
        Log.d("DEBUG", movieURl);

    }
}