package udacity.popularmovie.pupularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import udacity.popularmovie.pupularmovies.Adapters.FavoriteAdapter;
import udacity.popularmovie.pupularmovies.Adapters.MovieArrayAdapter;
import udacity.popularmovie.pupularmovies.Models.Favorite;
import udacity.popularmovie.pupularmovies.Models.Movie;
import udacity.popularmovie.pupularmovies.Utils.FavoriteMovieUtils;
import udacity.popularmovie.pupularmovies.Utils.MovieJSONUtils;
import udacity.popularmovie.pupularmovies.Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    /*
     * Constant that will use in the sort type of movies
     * (Most Popular sort and Top rated sort)
     */
    private static final String SORT_TYPE_EXTRA = "sort_ype";
    private static final String MOST_POPULAR_MOVIE = "popular";
    private static final String TOP_RATED_MOVIE = "top_rated";

    /*
     * The Constant for loader id
     */
    private static final int LOADER_ID = 13;
    Bundle loaderBundle;


    GridView grid;
    ListView favoritesListView;
    MovieArrayAdapter movieArrayAdapter;
    FavoriteAdapter favoriteAdapter;
    List<Movie> movies;
    List<Favorite> favorites;
    RadioButton topRatedRadio, mostPopularRadio, favoriteRadio;
    RelativeLayout offlineMode;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.grid);
        favoritesListView = findViewById(R.id.favorite_list_view);

        mostPopularRadio = findViewById(R.id.most_popular_rb);
        topRatedRadio = findViewById(R.id.top_rated_rb);

        favoriteRadio = findViewById(R.id.favorite);
        offlineMode = findViewById(R.id.offline);

        /*
         * The default movie sort type.
         * You can change it to top rated by changing the next string inside the bundle
         */

        loaderBundle = new Bundle();


        if (savedInstanceState != null && savedInstanceState.containsKey(SORT_TYPE_EXTRA)){
            loaderBundle.putString(SORT_TYPE_EXTRA, savedInstanceState.getString(SORT_TYPE_EXTRA));

        }else{
            loaderBundle.putString(SORT_TYPE_EXTRA, MOST_POPULAR_MOVIE);
        }



        final LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);


        // Check if the internet available

        if (isOnline()){

            if (loader == null){

                loaderManager.initLoader(LOADER_ID,loaderBundle,this).forceLoad();
            }else{
                loaderManager.restartLoader(LOADER_ID,loaderBundle, this).forceLoad();
            }

        }else{
            offlineMode.setVisibility(View.VISIBLE);
            topRatedRadio.setVisibility(View.INVISIBLE);
            mostPopularRadio.setVisibility(View.INVISIBLE);
        }



        /*
         * handle the top rated and most popular radio buttons
         */

        topRatedRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesListView.setVisibility(View.GONE);
                grid.setVisibility(View.VISIBLE);

                loaderBundle.clear();
                loaderBundle.putString(SORT_TYPE_EXTRA, TOP_RATED_MOVIE);
                getSupportLoaderManager().restartLoader(LOADER_ID,loaderBundle, MainActivity.this).forceLoad();

            }
        });

        mostPopularRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesListView.setVisibility(View.GONE);
                grid.setVisibility(View.VISIBLE);

                loaderBundle.clear();
                loaderBundle.putString(SORT_TYPE_EXTRA, MOST_POPULAR_MOVIE);
                getSupportLoaderManager().restartLoader(LOADER_ID,loaderBundle, MainActivity.this).forceLoad();

            }
        });


        favoriteRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (favorites != null){
                    favorites.clear();
                }

                favoritesListView.setVisibility(View.VISIBLE);
                grid.setVisibility(View.GONE);

                /**
                 * Handle adapter
                 *
                 */

                if (FavoriteMovieUtils.isThereFavoriteMovies(getApplicationContext())){
                    favorites = FavoriteMovieUtils.getALLFavoriteMovie(getApplicationContext());
                    favoriteAdapter = new FavoriteAdapter(getApplicationContext(),favorites);
                    favoritesListView.setAdapter(favoriteAdapter);
                    favoriteAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "There is no added favorites movies", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    /*
     *
     * @param sort_type you can use one of two sort type that
     *                  defined as constatns
     *                  SORT_TYPE_EXTRA & MOST_POPULAR_MOVIE
     * @return the movies url depending on its sort type;
     */
    public URL getURL(String sort_type)
    {
        return  NetworkUtils.getMoviesURL(sort_type);
    }


    /*
     * Loader Methods that will retrieve data;
     */

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {


         return new AsyncTaskLoader<String>(this) {
             @Override
             protected void onStartLoading() {
                 super.onStartLoading();
                 if (args == null) return;

             }

             @Override
             public String loadInBackground() {

                 String json;
                 try {
                     if (!isOnline()) return null;
                     json = NetworkUtils.getTheResponse(getURL(args.getString(SORT_TYPE_EXTRA)));

                 } catch (IOException e) {
                     e.printStackTrace();
                     return null;
                 }


                 return json;
             }
         };

    }


    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        movies = new ArrayList<>();
        movieArrayAdapter = new MovieArrayAdapter(this,movies);

        grid.setAdapter(movieArrayAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentMainToDetails = new Intent(MainActivity.this,DetailsActivity.class);
                intentMainToDetails.putExtra(Intent.EXTRA_COMPONENT_NAME,movies.get(i));
                startActivity(intentMainToDetails);
            }
        });

        if (data == null){
            return;
        }

        movies.addAll(MovieJSONUtils.getMovies(data));
        movieArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    /**
     * check if there is internet connection
     * source: https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     * @return is there is connection
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (loaderBundle.containsKey(SORT_TYPE_EXTRA)){
            outState.putString(SORT_TYPE_EXTRA,loaderBundle.getString(SORT_TYPE_EXTRA));
        }

        super.onSaveInstanceState(outState);
    }
}
