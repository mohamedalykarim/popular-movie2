package udacity.popularmovie.pupularmovies.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import udacity.popularmovie.pupularmovies.BuildConfig;

/**
 * Created by Mohamed ALi on 4/6/2018.
 */

public class NetworkUtils {

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/";



    private static final String MOVIE_FORM = "movie";



    private static final String API_PARAM = "api_key";

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com";
    private static final String WATCH_PATH = "watch";
    private static final String V_PARAM = "v";

    private static final String VIDEOS_PATH = "videos";
    private static final String REVIEWS_PATH = "reviews";



    /**
         * build the url that I will use in the movies query by sort type
    */

    public static URL getMoviesURL(String sortType){
        URL url = null;
        Uri movieUri = Uri.parse(BASE_MOVIE_URL)
                .buildUpon()
                .appendPath(MOVIE_FORM)
                .appendPath(sortType)
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
     * Build Trailers URL
     * @param id is movie id
     * @return trailers URL
     */
    public static URL getTrailersURL(String id){
        URL url = null;
        Uri trailersUri = Uri.parse(BASE_MOVIE_URL)
                .buildUpon()
                .appendPath(MOVIE_FORM)
                .appendPath(id)
                .appendPath(VIDEOS_PATH)
                .appendQueryParameter(API_PARAM,API_KEY)
                .build();
        try {
            url = new URL(trailersUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    /**
     * Get Youtube URL by the video key;
     * @param key is video key
     * @return youtube URL
     */

    public static URL getTrailerYoutubeURL(String key){
        URL url = null;
        Uri trailersUri = Uri.parse(BASE_YOUTUBE_URL)
                .buildUpon()
                .appendPath(WATCH_PATH)
                .appendQueryParameter(V_PARAM,key)
                .build();
        try {
            url = new URL(trailersUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return  url;


    }


    /**
     * Build reviews URL
     * @param id is movie id
     * @return reviews URL
     */

    public static URL getReviewsURL(String id){
        URL url = null;
        Uri trailersUri = Uri.parse(BASE_MOVIE_URL)
                .buildUpon()
                .appendPath(MOVIE_FORM)
                .appendPath(id)
                .appendPath(REVIEWS_PATH)
                .appendQueryParameter(API_PARAM,API_KEY)
                .build();
        try {
            url = new URL(trailersUri.toString());
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
