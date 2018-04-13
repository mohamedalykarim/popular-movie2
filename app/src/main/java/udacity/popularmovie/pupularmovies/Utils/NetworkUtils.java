package udacity.popularmovie.pupularmovies.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Mohamed ALi on 4/6/2018.
 */

public class NetworkUtils {

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/";

    private static final String SEARCH_FORM = "search";
    private static final String DISCOVER_FORM = "discover";
    private static final String FIND_FORM = "find";

    private static final String END_OF_URL = "movie";





    private static final String SORT_PARAM = "sort_by";
    private static final String API_PARAM = "api_key";

    private static final String API_KEY = "[API_KEY]";


    /**
         * build the url that I will use in the movies query by sort type
         * "popularity.desc" for the most popular movies
         * "vote_average.desc" for the highest rated movies
         * this method based on discover way in the api
    */

    public static URL getMoviesURL(String sortType){
        URL url = null;
        Uri movieUri = Uri.parse(BASE_MOVIE_URL)
                .buildUpon()
                .appendPath(DISCOVER_FORM)
                .appendPath(END_OF_URL)
                .appendQueryParameter(SORT_PARAM, sortType)
                .appendQueryParameter(API_PARAM,API_KEY)
                .build();
        try {
            url = new URL(movieUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }



    /**
     * Get the response from http request
     * @NOTE: this method I learnt from Udacity.com in the course called
     * "Developing Android Apps"
     */

    public static String getTheResponse(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream inputStream = urlConnection.getInputStream();
            Scanner mScanner = new Scanner(inputStream);
            mScanner.useDelimiter("\\A");

            if (mScanner.hasNext()){
                return mScanner.next();
            }else
            {
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }

}
