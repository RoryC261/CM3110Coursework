package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieFavourites extends AppCompatActivity{

    private MovieViewmodel viewmodel;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favourites);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Button buttonBackToSearch = findViewById(R.id.button_back_to_search);
        // Button buttonBackToRec = findViewById(R.id.button_back_to_rec);

        MovieAdapter adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        // When pressed, Movie search activity is opened
        buttonBackToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBackToSearch();
            }
        });

        /*
        buttonBackToRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBackToRec();
            }
        });

         */

        viewmodel = ViewModelProviders.of(this).get(MovieViewmodel.class);
        viewmodel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
               adapter.setMovies(movies);
            }
        });

        // Android Gesture, detects if user has swiped either left or right, in this case as it is set to 0, user can swipe left or right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Deletes item from recylcer view and from database when swiped away
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodel.delete(adapter.getMovieAt(viewHolder.getAdapterPosition()));
                Log.d("TEST", viewmodel.getAllMovies().toString());
                Log.d("TEST", "MOVIE DELETED");
            }

        }).attachToRecyclerView(recyclerView);

        // Detects when an item in the recycler view has been pressed
        adapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Log.d("DEBUG", "ITEM CLICKED");
                currentMovie = adapter.getMovie(); // Gets info on movie that was clicked on
                openMovieInfo();
            }
        });
    }

    public void openBackToSearch(){
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    // Opens new info activity, sends data of the selected movie to new activity through an intent
    public void openMovieInfo(){
        Intent openInfo = new Intent(this, MovieInfo.class);
        openInfo.putExtra("MOVIE_NAME", currentMovie.movieName);
        openInfo.putExtra("MOVIE_DESCRIPTION", currentMovie.movieDescription);
        openInfo.putExtra("MOVIE_URL", currentMovie.movieUrl);
        startActivity(openInfo);
    }
}