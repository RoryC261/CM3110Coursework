package com.example.coursework;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {

    MovieDao movieDao;

    MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        movieDao = db.movieDao();
    }

    LiveData<List<Movie>> getMovie(){
        return movieDao.getAllMovies();
    }

    void insert(Movie movie){
        new insertAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie movie) {
        new deleteMovieAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao taskDao;

        insertAsyncTask(MovieDao movieDao){
            taskDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            taskDao.insertAll(movies[0]);
            return null;
        }
    }

    private static class deleteMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        private  deleteMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.delete(movies[0]);
            return null;
        }
    }

}
