package udacity.popularmovie.pupularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import udacity.popularmovie.pupularmovies.Models.Movie;

public class DetailsActivity extends AppCompatActivity {

    /**
     * In this Activity we recieve the movie data
     * from the intent and bind it to the layout
     */

    Intent recievedIntent;
    Movie movie;

    ImageView poster;

    TextView originalNameTV, overviewTV,
            popularityTV, userRatingTV, releaseDateTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        poster = findViewById(R.id.poster);
        originalNameTV = findViewById(R.id.original_name);
        overviewTV = findViewById(R.id.overview);
        popularityTV = findViewById(R.id.popularity);
        userRatingTV = findViewById(R.id.user_rating);
        releaseDateTV = findViewById(R.id.release_date);

        // Receive the data
        recievedIntent = getIntent();
        movie = recievedIntent.getParcelableExtra(Intent.EXTRA_COMPONENT_NAME);

        // Bind the data
        Picasso.with(this).load(movie.getPosterPath()).into(poster);
        originalNameTV.setText(movie.getOriginalTitle());
        overviewTV.setText(movie.getOverview());
        popularityTV.setText(Integer.toString(movie.getPopularity()));
        userRatingTV.setText(Integer.toString(movie.getVoteAverage()));
        releaseDateTV.setText(movie.getReleaseDate());


    }
}
