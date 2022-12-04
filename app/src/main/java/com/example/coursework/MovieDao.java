package com.example.coursework;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import java.util.ArrayList;

@Dao
public interface MovieDao {

    @Insert
    void insertAll(Movie... movies);

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAllMovies();

    @Delete
    void delete(Movie movie);
}
