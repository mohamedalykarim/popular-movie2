package udacity.popularmovie.pupularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import udacity.popularmovie.pupularmovies.Databases.FavoriteMovieContract;
import udacity.popularmovie.pupularmovies.Databases.FavoriteMoviesContentProvider;
import udacity.popularmovie.pupularmovies.Models.Movie;
import udacity.popularmovie.pupularmovies.Models.Review;
import udacity.popularmovie.pupularmovies.Models.Trailer;
import udacity.popularmovie.pupularmovies.Utils.FavoriteMovieUtils;
import udacity.popularmovie.pupularmovies.Utils.MovieJSONUtils;
import udacity.popularmovie.pupularmovies.Utils.NetworkUtils;

import static udacity.popularmovie.pupularmovies.Databases.FavoriteMovieContract.FMovies.CONTENT_URI;

public class DetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<String>> {

    public static final int LOADER_ID = 14;
    private static final String MOVIE_ID = "movie_id";

    /**
     * In this Activity we recieve the movie data
     * from the intent and bind it to the layout
     */

    Intent recievedIntent;
    Movie movie;

    ImageView poster;

    TextView originalNameTV, overviewTV,
            popularityTV, userRatingTV, releaseDateTV;


    LinearLayout trailerLinear, reviewLinear;

    ImageView favoriteIMG;

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


        /**
         * Handle Favorite Button
         *
         */
        favoriteIMG = findViewById(R.id.favoriteImageView);


        final boolean isChecked = FavoriteMovieUtils.isCheckedAsFavorite(
                getApplicationContext(),
                movie.getId()
        );

        if (isChecked){
            favoriteIMG.setImageResource(R.drawable.favoriteon);
        }else{
            favoriteIMG.setImageResource(R.drawable.favoriteoff);

        }


        /**
         * Handle Favorite Button Clicks
         */

        favoriteIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isChecked){

                    FavoriteMovieUtils.deleteFavoriteMovie(
                      getApplicationContext(),
                      movie.getId()
                    );

                    favoriteIMG.setImageResource(R.drawable.favoriteoff);


                }else{

                    ContentValues values = new ContentValues();
                    values.put(FavoriteMovieContract.FMovies.TITLE,movie.getOriginalTitle());
                    values.put(FavoriteMovieContract.FMovies.MOVIE_ID,movie.getId());

                    FavoriteMovieUtils.insertFavoriteMovie(
                            getApplicationContext(),
                            values
                    );

                    favoriteIMG.setImageResource(R.drawable.favoriteon);

                }




            }
        });





        // Bind the data
        Picasso.with(this).load(movie.getPosterPath()).into(poster);
        originalNameTV.setText(movie.getOriginalTitle());
        overviewTV.setText(movie.getOverview());
        popularityTV.setText(Integer.toString(movie.getPopularity()));
        userRatingTV.setText(Integer.toString(movie.getVoteAverage()));
        releaseDateTV.setText(movie.getReleaseDate());


        final Bundle loaderBundle  = new Bundle();
        loaderBundle.putString(MOVIE_ID,movie.getId());


        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<String>> loader = loaderManager.getLoader(LOADER_ID);

        if (isOnline()){

            if (loader == null){

                loaderManager.initLoader(LOADER_ID,loaderBundle,this).forceLoad();
            }else{
                loaderManager.restartLoader(LOADER_ID,loaderBundle, this).forceLoad();
            }

        }else{

        }


    }


    @NonNull
    @Override
    public AsyncTaskLoader<List<String>>  onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<List<String>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) return;

            }




            @Override
            public List<String> loadInBackground() {
                if (!isOnline()) return null;

                String movieID = args.getString(MOVIE_ID);
                List<String> json = new ArrayList<>();
                try {

                    json.add(NetworkUtils.getTheResponse(NetworkUtils.getTrailersURL(movieID)));
                    json.add(NetworkUtils.getTheResponse(NetworkUtils.getReviewsURL(movieID)));
                } catch (IOException e) {
                    return null;
                }


                return json;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<String>> loader, List<String> data) {

        String trailersString = data.get(0);
        String reviewString = data.get(1);


        /**
         * The trailers and reviews shown in a list view
         * by using adapters
         *
         * Using TrailerAdapter to populate trailers into
         * the list view and ReviewAdapter to populate
         * reviews into list view         *
         */


        /**
         *   First TrailerAdapter
         */

        trailerLinear = findViewById(R.id.trailers_Linear);
        List<Trailer> trailers = new ArrayList<>();

        trailers.addAll(MovieJSONUtils.getTrailers(trailersString));
        addReviewsOrTrailersLayout(R.layout.trailer_layout, trailers, trailerLinear);




        /**
         *   Second ReviewAdapter
         */

        reviewLinear = findViewById(R.id.reviews_Linear);
        List<Review> reviews = new ArrayList<>();

        reviews.addAll(MovieJSONUtils.getReviews(reviewString));
        addReviewsOrTrailersLayout(R.layout.review_layout,reviews, reviewLinear);



    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<String>> loader) {

    }


    private <T> void addReviewsOrTrailersLayout(int layout, List<T> trailerOrReviewList, LinearLayout container){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i=0;i<trailerOrReviewList.size();i++){
            View view = inflater.inflate(layout,null);

            if (trailerOrReviewList.size() > 0){
                if (trailerOrReviewList.get(0) instanceof Review){
                    TextView author = view.findViewById(R.id.author);
                    TextView content = view.findViewById(R.id.content);

                    List<Review> reviews = (List<Review>) trailerOrReviewList;
                    author.setText(reviews.get(i).getUsername());
                    content.setText(reviews.get(i).getReview());


                }else if (trailerOrReviewList.get(0) instanceof Trailer){
                    TextView movieTitle = view.findViewById(R.id.movie_trailer_title);
                    final List<Trailer> trailers = (List<Trailer>) trailerOrReviewList;
                    movieTitle.setText(trailers.get(i).getTitle());

                    // Open web to play trailer on click
                    final String movieURL = NetworkUtils.getTrailerYoutubeURL(trailers.get(i).getKey()).toString();
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent openTrailerIntent = new Intent(Intent.ACTION_VIEW);
                            openTrailerIntent.setData(Uri.parse(movieURL));
                            startActivity(openTrailerIntent);
                        }
                    });

                }
            }



            container.addView(view);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
