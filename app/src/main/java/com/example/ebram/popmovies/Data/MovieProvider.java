package com.example.ebram.popmovies.Data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Ebram Samy on 9/15/2015.
 */
public class MovieProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    static final int POP_MOVIE = 100;
    static final int RATED_MOVIE = 101;
    static final int POP_MOVIE_ID = 102;
    static final int RATED_MOVIE_ID = 103;

    private static final String popMovieByIDSelection =
            MovieContract.PopMovieEntry.TABLE_NAME +
                    "." + MovieContract.PopMovieEntry._ID + " = ?";

    private static final String ratedMovieByIDSelection =
            MovieContract.RatedMovieEntry.TABLE_NAME +
                    "." + MovieContract.RatedMovieEntry._ID + " = ?";

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_PopMOVIES, POP_MOVIE);
        matcher.addURI(authority, MovieContract.PATH_RatedMOVIES, RATED_MOVIE);
        matcher.addURI(authority, MovieContract.PATH_PopMOVIES + "/#", POP_MOVIE_ID);
        matcher.addURI(authority, MovieContract.PATH_RatedMOVIES + "/#", RATED_MOVIE_ID);
        return matcher;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case POP_MOVIE:
                return MovieContract.PopMovieEntry.CONTENT_TYPE;

            case RATED_MOVIE:
                return MovieContract.RatedMovieEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {


            case POP_MOVIE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.PopMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                Log.d("Que", "POP_MOVIE");
                break;
            }

            case RATED_MOVIE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.RatedMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                Log.d("Que", "RATED_MOVIE" );
                break;
            }
            case POP_MOVIE_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.PopMovieEntry.TABLE_NAME,
                        projection,
                        popMovieByIDSelection,
                        new String[]{ uri.getLastPathSegment().toString()},
                        null,
                        null,
                        sortOrder
                );
                Log.d("Que", "POP_MOVIE_ID" + uri.getLastPathSegment().toString());
                break;
            }
            case RATED_MOVIE_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.RatedMovieEntry.TABLE_NAME,
                        projection,
                        ratedMovieByIDSelection,
                        new String[]{ uri.getLastPathSegment().toString()},
                        null,
                        null,
                        sortOrder
                );
                Log.d("Que", "RATED_MOVIE_ID" + uri.getLastPathSegment().toString());
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case POP_MOVIE: {
                long _id = db.insert(MovieContract.PopMovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.PopMovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case RATED_MOVIE: {
                long _id = db.insert(MovieContract.RatedMovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.RatedMovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case POP_MOVIE:
                rowsDeleted = db.delete(
                        MovieContract.PopMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case RATED_MOVIE:
                rowsDeleted = db.delete(
                        MovieContract.RatedMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case POP_MOVIE:
                rowsUpdated = db.update(MovieContract.PopMovieEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case RATED_MOVIE:
                rowsUpdated = db.update(MovieContract.RatedMovieEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {
            case POP_MOVIE:
                db.beginTransaction();
                 returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.PopMovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case RATED_MOVIE:
                db.beginTransaction();
                 returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.RatedMovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
