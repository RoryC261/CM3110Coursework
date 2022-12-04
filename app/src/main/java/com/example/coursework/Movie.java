package com.example.coursework;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {

    @PrimaryKey(autoGenerate = true)
    public int movieID;

    @ColumnInfo(name = "movie_name")
    public String movieName;

    @ColumnInfo(name = "movie_description")
    public String movieDescription;

    @ColumnInfo(name = "movie_url")
    public String movieUrl;

    public Movie(String movieName, String movieDescription, String movieUrl) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieUrl = movieUrl;
    }
}
