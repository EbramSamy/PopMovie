package com.example.ebram.popmovies;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ebram on 8/22/2015.
 */
public class MovieAdapter extends BaseAdapter {
    ArrayList<Movie> movies;
    Context context;


    public MovieAdapter(ArrayList<Movie> movies, Context context) {
        for (Movie s : movies) {
            Log.d("MyApp","Here 1"+s.getMovieTitle());
        }
        this.movies = movies;
        for (Movie s : this.movies) {
            Log.d("MyApp","Here 2"+s.getMovieTitle());
        }
        this.context = context;

    }

    public int getCount() {
        return movies.size();
    }

    public Movie getItem(int pos) {

        return movies.get(pos);
    }

    public long getItemId(int position) {
        return position;
    }

    public void add( Movie item) {
        movies.add(item);
        notifyDataSetChanged();
    }
    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        ViewHolder holder = new ViewHolder();

        // First let's verify the convertView is not null

        if (convertView == null) {
            Log.d("MyApp","Here 3");
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.d("MyApp", "Here -4");
            v = inflater.inflate(R.layout.grid_item, null);

            // Now we can fill the layout with the right values

            TextView movieTitle = (TextView) v.findViewById(R.id.movieTitle);


            ImageView moviePost = (ImageView) v.findViewById(R.id.moviePoster);

            holder.movieTit = movieTitle;


            holder.moviePost=moviePost;

            v.setTag(holder);

        } else

        {
            holder = (ViewHolder) v.getTag();
        }

        Log.d("MyApp", "Here 16");
        Movie t = movies.get(position);


        holder.movieTit.setText(t.getMovieTitle());






        URL url= null;
        try {
            Log.d("MyApp", "Here 15");
            url = new URL(t.getPosterPath());
           // new DownloadImageTask(holder.moviePost).execute(url.toString());
            Picasso.with(context).load(url.toString()).into(holder.moviePost);
            Log.d("MyApp", "Here 11");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return v;

    }

    private static class ViewHolder {

        public TextView movieTit;

        public ImageView moviePost;
    }


}
