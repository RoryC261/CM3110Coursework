package com.example.coursework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie currentMovie = movies.get(position);
        holder.textViewMovieName.setText(currentMovie.movieName);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public Movie getMovieAt(int position) {
        return movies.get(position);
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private TextView textViewMovieName;
        Button deleteButton;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            textViewMovieName = itemView.findViewById(R.id.text_view_movie_name);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}
