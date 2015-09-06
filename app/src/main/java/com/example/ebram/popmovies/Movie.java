package com.example.ebram.popmovies;

import java.io.Serializable;

/**
 * Created by Ebram on 8/22/2015.
 */
public class Movie implements Serializable {
    private String movieTitle;
    private String posterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;


    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieNme) {
        this.movieTitle = movieNme;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage+" / 10";
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
