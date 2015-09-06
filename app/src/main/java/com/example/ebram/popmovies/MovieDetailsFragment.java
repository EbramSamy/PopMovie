package com.example.ebram.popmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_movie_details, container, false);
        Intent i = getActivity().getIntent();
        Movie movie = (Movie) i.getSerializableExtra("Movie");
        TextView releaseDate = (TextView) rootView.findViewById(R.id.dateText);

        TextView title = (TextView) rootView.findViewById(R.id.titleText);

        TextView overView = (TextView) rootView.findViewById(R.id.overviewText);
        TextView rate = (TextView) rootView.findViewById(R.id.rateTextView);

        ImageView postImg = (ImageView) rootView.findViewById(R.id.posterImg);

        releaseDate.setText(movie.getReleaseDate());
        title.setText(movie.getMovieTitle());
        rate.setText(movie.getVoteAverage());
        overView.setText(movie.getOverview());
        URL url = null;
        try {
            url = new URL(movie.getPosterPath());
            Picasso.with(getActivity()).load(url.toString()).into(postImg);
        } catch (MalformedURLException e) {
            Toast toast = Toast.makeText(getActivity(), "No Poster", Toast.LENGTH_SHORT);
            toast.show();
        }
        return rootView;
    }
}
