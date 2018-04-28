package udacity.popularmovie.pupularmovies.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import udacity.popularmovie.pupularmovies.Models.Movie;
import udacity.popularmovie.pupularmovies.Models.Review;
import udacity.popularmovie.pupularmovies.Models.Trailer;

/**
 * Created by Mohamed ALi on 4/6/2018.
 * in this class I handled the JSON result that come from the API
 */

public class MovieJSONUtils {

    private static final String NAME = "name";
    private static final String KEY = "key";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    private static final String RESULTS = "results";

    private static final String VOTE_AVERAGE = "vote_average";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String MOVIE_ID = "id";


    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";





    /**
     * get the movies details from the json string and convert them to movies List
     * @param json the JSON string that comes from the api response
     * @return movies list
     */

    public static List<Movie> getMovies(String json){
        List<Movie> movies = new ArrayList<>();

        int voteAaverage, popularity;
        String posterPath, originalTitle, overview, releaseDate, id;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultJsonArray = jsonObject.getJSONArray(RESULTS);

            for (int i = 0; i < resultJsonArray.length(); i++){
                voteAaverage = resultJsonArray.getJSONObject(i).getInt(VOTE_AVERAGE);
                popularity = resultJsonArray.getJSONObject(i).getInt(POPULARITY);
                posterPath = resultJsonArray.getJSONObject(i).getString(POSTER_PATH);
                originalTitle = resultJsonArray.getJSONObject(i).getString(ORIGINAL_TITLE);
                overview = resultJsonArray.getJSONObject(i).getString(OVERVIEW);
                releaseDate = resultJsonArray.getJSONObject(i).getString(RELEASE_DATE);
                id = resultJsonArray.getJSONObject(i).getString(MOVIE_ID);

                movies.add(new Movie(voteAaverage, popularity, POSTER_BASE_URL + posterPath,
                        originalTitle, overview, releaseDate, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }


    /**
     * Get the trailers details from the json that come from api
     * @param json is string that contain trailers json
     * @return List of trailers
     */

    public static List<Trailer> getTrailers(String json){
        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONObject trailersJson = new JSONObject(json);
            JSONArray results = trailersJson.getJSONArray(RESULTS);
            for (int i=0; i<results.length();i++){
                JSONObject result = results.getJSONObject(i);
                String name = result.getString(NAME);
                String key = result.getString(KEY);
                trailers.add(new Trailer(name,key));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }


    /**
     * Get the reviews details from the json that come from api
     * @param json is string that contain reviews json
     * @return List of reviews
     */

    public static List<Review> getReviews(String json){
        List<Review> reviews = new ArrayList<>();

        try {
            JSONObject reviewsJson = new JSONObject(json);
            JSONArray results = reviewsJson.getJSONArray(RESULTS);
            for (int i=0; i<results.length();i++){
                JSONObject result = results.getJSONObject(i);
                String author = result.getString(AUTHOR);
                String content = result.getString(CONTENT);
                reviews.add(new Review(author,content));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }




}
