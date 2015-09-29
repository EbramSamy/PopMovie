package com.example.ebram.popmovies.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ebram Samy on 9/14/2015.
 */
public class MovieContract {


    public static final String CONTENT_AUTHORITY = "com.example.ebram.popmovies.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_PopMOVIES = "popMovie";
    public static final String PATH_RatedMOVIES = "ratedMovie";

    public static final class PopMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PopMOVIES).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PopMOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PopMOVIES;

        public static final String TABLE_NAME = "popMovie";

        // movie id as returned by API, to identify the icon to be used
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

        public static final String COLUMN_MOVIE_TITLE = "movie_title";

        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";

        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";



        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class RatedMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RatedMOVIES).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RatedMOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RatedMOVIES;

        public static final String TABLE_NAME = "ratedMovie";

        // movie id as returned by API, to identify the icon to be used
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

        public static final String COLUMN_MOVIE_TITLE = "movie_title";

        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";

        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";



        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
