package com.example.ebram.popmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Ebram on 8/22/2015.
 */
public class ParseJson {
    public static Movie[] getMoviesFromJson(String moviesJsonStr) throws JSONException {
        final String LOG_TAG = ParseJson.class.getSimpleName();
        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "results";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(OWM_LIST);


        Movie[] resultStrs = new Movie[moviesArray.length()];
        JSONObject userJson;
        for (int i = 0; i < moviesArray.length(); i++) {
            resultStrs[i]=new Movie();
            resultStrs[i].setMovieTitle(moviesArray.getJSONObject(i).getString("title"));
            resultStrs[i].setPosterPath("http://image.tmdb.org/t/p/w185" + moviesArray.getJSONObject(i).getString("poster_path"));
            resultStrs[i].setOverview(moviesArray.getJSONObject(i).getString("overview"));
            resultStrs[i].setVoteAverage(moviesArray.getJSONObject(i).getString("vote_average"));
            resultStrs[i].setReleaseDate(moviesArray.getJSONObject(i).getString("release_date"));
        }
        for (Movie s : resultStrs) {
            Log.v(LOG_TAG, "Movie Text: " + s.getMovieTitle());
            Log.v(LOG_TAG, "Movie Poster: " + s.getPosterPath());
         }
        return resultStrs;
    }

}
