package com.example.ebram.popmovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebram.popmovies.Data.MovieContract;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    static final String DETAIL_URI = "URI";
    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

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

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(MovieDetailsFragment.DETAIL_URI);
        }
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sort = Utility.getSortingType(getActivity());
        Uri movieLocationUri;
        if (sort.equals("vote_average.desc")) {

            return new CursorLoader(getActivity(),
                    mUri,
                    RATED_MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        } else {
            return new CursorLoader(getActivity(),
                    mUri,
                    POP_MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        TextView releaseDate = (TextView) getView().findViewById(R.id.dateText);

        TextView title = (TextView) getView().findViewById(R.id.titleText);

        TextView overView = (TextView) getView().findViewById(R.id.overviewText);
        TextView rate = (TextView) getView().findViewById(R.id.rateTextView);

        ImageView postImg = (ImageView) getView().findViewById(R.id.posterImg);

        releaseDate.setText(data.getString(COLUMN_MOVIE_RELEASE_DATE));
        title.setText(data.getString(COLUMN_MOVIE_TITLE));
        rate.setText(data.getString(COLUMN_MOVIE_VOTE_AVERAGE));
        overView.setText(data.getString(COLUMN_MOVIE_OVERVIEW));
        URL url = null;
        try {
            url = new URL(data.getString(COLUMN_MOVIE_POSTER_PATH));
            Picasso.with(getActivity()).load(url.toString()).into(postImg);
        } catch (MalformedURLException e) {
            Toast toast = Toast.makeText(getActivity(), "No Poster", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
