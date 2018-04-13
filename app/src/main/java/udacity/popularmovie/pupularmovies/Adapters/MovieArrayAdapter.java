package udacity.popularmovie.pupularmovies.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import udacity.popularmovie.pupularmovies.Models.Movie;
import udacity.popularmovie.pupularmovies.R;

/**
 * Created by Mohamed ALi on 4/1/2018.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    ImageView image;
    TextView name;

    /*
     * Movie adapter that will handle the
     * @param context used to inflate the view
     * @param movies used to get the data that want to bind
     */

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context,0,movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.movie_poster,parent,false);
        Movie movie = getItem(position);

        image = view.findViewById(R.id.poster_image);
        name = view.findViewById(R.id.movie_name);

        name.setText(movie.getOriginalTitle());

        Picasso.with(getContext()).load(movie.getPosterPath()).into(image);
        return view;

    }
}
