package com.example.ebram.popmovies;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.example.ebram.popmovies.Data.MovieContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    private static final int MOVIE_LOADER = 1;


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

    public MoviesFragment() {

    }

    @Override
    public void onStart() {
        updateMovies();
        super.onStart();
    }

    private void updateMovies() {
        FetchMovies fetchMovies = new FetchMovies(getActivity());
        fetchMovies.execute(Utility.getSortingType(getActivity()));
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movies_fragment_layout, container, false);
        mMovieCursorAdapter = new MovieCursorAdapter(getActivity(), null, 0);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(mMovieCursorAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

                String sort = Utility.getSortingType(getActivity());
            /*    Intent intent = new Intent(getActivity(), MovieDetails.class);
                if (sort.equals("vote_average.desc")) {
                    intent.setData(MovieContract.RatedMovieEntry.buildMovieUri(cursor.getLong(COLUMN_MOVIE_ID)));
                } else {

                    intent.setData(MovieContract.PopMovieEntry.buildMovieUri(cursor.getLong(COLUMN_MOVIE_ID)));
                }*/
                Bundle arguments = new Bundle();
                if (sort.equals("vote_average.desc")) {
                    arguments.putParcelable(MovieDetailsFragment.DETAIL_URI, MovieContract.RatedMovieEntry.buildMovieUri(cursor.getLong(COLUMN_MOVIE_ID)));
                } else {
                    arguments.putParcelable(MovieDetailsFragment.DETAIL_URI, MovieContract.PopMovieEntry.buildMovieUri(cursor.getLong(COLUMN_MOVIE_ID)));
                }
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                MovieDetailsFragment fragment = new MovieDetailsFragment();
                int containerID = R.id.movie_detail_container;
                fragment.setArguments(arguments);
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.movie_detail_container, fragment);
                ft.commit();

                Log.d("Click", "MOVIE_ID" + cursor.getLong(COLUMN_MOVIE_ID));
                //startActivity(intent);

            }
        });
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sort = Utility.getSortingType(getActivity());
        Uri movieLocationUri;
        if (sort.equals("vote_average.desc")) {
            movieLocationUri = MovieContract.RatedMovieEntry.CONTENT_URI;

            return new CursorLoader(getActivity(),
                    movieLocationUri,
                    RATED_MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        } else {
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

}
