package com.example.ebram.popmovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebram.popmovies.Data.MovieContract;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MOVIE_LOADER = 0;


    private static final String[] POP_MOVIE_COLUMNS = {
            MovieContract.PopMovieEntry.TABLE_NAME + "." + MovieContract.PopMovieEntry._ID,
            MovieContract.PopMovieEntry.COLUMN_MOVIE_OVERVIEW,
            MovieContract.PopMovieEntry.COLUMN_MOVIE_TITLE,
            MovieContract.PopMovieEntry.COLUMN_MOVIE_POSTER_PATH,
            MovieContract.PopMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
            MovieContract.PopMovieEntry.COLUMN_MOVIE_RELEASE_DATE,

    };
    private static final String[] RATED_MOVIE_COLUMNS = {
            MovieContract.RatedMovieEntry.TABLE_NAME + "." + MovieContract.RatedMovieEntry._ID,
            MovieContract.RatedMovieEntry.COLUMN_MOVIE_OVERVIEW,
            MovieContract.RatedMovieEntry.COLUMN_MOVIE_TITLE,
            MovieContract.RatedMovieEntry.COLUMN_MOVIE_POSTER_PATH,
            MovieContract.RatedMovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
            MovieContract.RatedMovieEntry.COLUMN_MOVIE_RELEASE_DATE,

    };

    static final int COLUMN_MOVIE_ID = 0;
    static final int COLUMN_MOVIE_OVERVIEW = 1;
    static final int COLUMN_MOVIE_TITLE = 2;
    static final int COLUMN_MOVIE_POSTER_PATH = 3;
    static final int COLUMN_MOVIE_VOTE_AVERAGE = 4;
    static final int COLUMN_MOVIE_RELEASE_DATE = 5;

    //private MovieAdapter movieAdapter;
    private MovieCursorAdapter mMovieCursorAdapter;

    public MainActivityFragment() {

    }

    @Override
    public void onStart() {
        updateMovies();
        super.onStart();
    }

    private void updateMovies() {
        FetchMovies fetchMovies = new FetchMovies(getActivity());
        // MyParams params = new MyParams();
        //params.movieAdapter = movieAdapter;
        //params.movieAdapter=mMovieCursorAdapter;
        // params.sortType = getSortingType();
        //fetchMovies.execute(params);
        fetchMovies.execute(getSortingType());

        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //movieAdapter = new MovieAdapter(new ArrayList<Movie>(), getActivity());
        mMovieCursorAdapter = new MovieCursorAdapter(getActivity(), null, 0);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
       // gridView.setAdapter(movieAdapter);
         gridView.setAdapter(mMovieCursorAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Movie movie = movieAdapter.getItem(position);

                //Intent intent = new Intent(getActivity(), MovieDetails.class).putExtra("Movie", movie);
                // startActivity(intent);
            }
        });
        return rootView;
    }

    private String getSortingType() {
        SharedPreferences prf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String unitType = prf.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popular));
        if (unitType.equals(getString(R.string.pref_highest_rated))) {
            return "vote_average.desc";
        } else {
            return "popularity.desc";
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sort=getSortingType();
        Uri movieLocationUri;
        if(sort.equals("vote_average.desc")) {
            movieLocationUri = MovieContract.RatedMovieEntry.CONTENT_URI;

            return new CursorLoader(getActivity(),
                    movieLocationUri,
                    RATED_MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        }
        else
        {
            movieLocationUri = MovieContract.PopMovieEntry.CONTENT_URI;

            return new CursorLoader(getActivity(),
                    movieLocationUri,
                    POP_MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mMovieCursorAdapter.swapCursor(cursor);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mMovieCursorAdapter.swapCursor(null);
    }


   /* public static class MyParams {
        //MovieAdapter movieAdapter;
        MovieCursorAdapter movieAdapter;
        String sortType;
    }*/
}
