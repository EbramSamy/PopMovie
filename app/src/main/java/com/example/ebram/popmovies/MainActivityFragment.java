package com.example.ebram.popmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {

    }

    @Override
    public void onStart() {
        updateMovies();
        super.onStart();
    }

    private void updateMovies() {
        FetchMovies fetchMovies = new FetchMovies();
        MyParams params = new MyParams();
        params.movieAdapter = movieAdapter;
        params.sortType = getSortingType();
        fetchMovies.execute(params);
    }

    MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        movieAdapter = new MovieAdapter(new ArrayList<Movie>(), getActivity());
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), MovieDetails.class).putExtra("Movie", movie);
                startActivity(intent);
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

    public static class MyParams {
        MovieAdapter movieAdapter;
        String sortType;
    }
}
