package com.example.coursework;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Creates an instance of the database
@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase{
    public abstract MovieDao movieDao();

    public static MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie-database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
