package udacity.popularmovie.pupularmovies.Adapters;

import android.content.Context;
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

import udacity.popularmovie.pupularmovies.Models.Favorite;
import udacity.popularmovie.pupularmovies.Models.Movie;
import udacity.popularmovie.pupularmovies.R;

/**
 * Created by Mohamed ALi on 4/1/2018.
 */

public class FavoriteAdapter extends ArrayAdapter<Favorite> {
    TextView name;


    public FavoriteAdapter(Context context, List<Favorite> favorites){
        super(context,0,favorites);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.favorite_movie_list_item,parent,false);
        Favorite favorite = getItem(position);

        name = view.findViewById(R.id.favorite_movie_item_name);

        name.setText(favorite.getName());

        return view;

    }
}
