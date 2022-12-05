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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieFavourites extends AppCompatActivity {

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

        MovieViewmodel viewmodel = ViewModelProviders.of(this).get(MovieViewmodel.class);
        viewmodel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
               adapter.setMovies(movies);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodel.delete(adapter.getMovieAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

    }

    public void openBackToSearch(){
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    public void openBackToRec(){
        Intent openRec = new Intent(this, MovieRecPage.class);
        startActivity(openRec);
    }

}