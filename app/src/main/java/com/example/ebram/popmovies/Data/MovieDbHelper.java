package com.example.ebram.popmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ebram.popmovies.Data.MovieContract.PopMovieEntry;
import com.example.ebram.popmovies.Data.MovieContract.RatedMovieEntry;

/**
 * Created by Ebram Samy on 9/15/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_POP_MOVIE_TABLE = "CREATE TABLE " + PopMovieEntry.TABLE_NAME + " (" +

                PopMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PopMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                PopMovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                PopMovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                PopMovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT, " +
                PopMovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                PopMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_RATED_MOVIE_TABLE = "CREATE TABLE " + RatedMovieEntry.TABLE_NAME + " (" +

                RatedMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RatedMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                RatedMovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                RatedMovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                RatedMovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT, " +
                RatedMovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                RatedMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_POP_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_RATED_MOVIE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RatedMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
