package com.example.coursework;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewmodel extends AndroidViewModel {

    MovieRepository repository;
    LiveData<List<Movie>> movieList;

    public MovieViewmodel(Application application){
        super(application);
        repository = new MovieRepository(application);
        movieList = repository.getMovie();
    }

    LiveData<List<Movie>> getAllMovies(){
        return movieList;
    }

    public void insertMovie(Movie movie){
        repository.insert(movie);
    }

}
