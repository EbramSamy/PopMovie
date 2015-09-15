package com.example.ebram.popmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ebram on 8/22/2015.
 */
public class FetchMovies extends AsyncTask<MainActivityFragment.MyParams, Void, Movie[]> {
    private final String LOG_TAG = FetchMovies.class.getSimpleName();
    MovieAdapter mAdapter;
    String SORT_TYPE;

    @Override
    protected Movie[] doInBackground(MainActivityFragment.MyParams... params) {
        mAdapter = params[0].movieAdapter;
        SORT_TYPE = params[0].sortType;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;
        try {

            final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String API_KEY = "api_key";
            final String SORT_BY = "sort_by";
            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY, SORT_TYPE)
                    .appendQueryParameter(API_KEY, "Private Key")
                    .build();
            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Movies URL :" + url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {

                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
            Log.v(LOG_TAG, "Tweets String :" + moviesJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return ParseJson.getMoviesFromJson(moviesJsonStr);
        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movie[] result) {

        if (result != null) {
            mAdapter.clear();
            for (Movie t : result) {
                mAdapter.add(t);
            }
        }
    }
}
