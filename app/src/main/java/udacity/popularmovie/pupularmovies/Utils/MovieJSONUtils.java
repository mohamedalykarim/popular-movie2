package udacity.popularmovie.pupularmovies.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import udacity.popularmovie.pupularmovies.Models.Movie;

/**
 * Created by Mohamed ALi on 4/6/2018.
 * in this class I handled the JSON result that come from the API
 */

public class MovieJSONUtils {
    private static final String RESULTS = "results";

    private static final String VOTE_AVERAGE = "vote_average";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";


    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    /**
     * get the movies details from the json string and convert them to movies List
     * @param json the JSON string that comes from the api response
     * @return movies list
     */

    public static List<Movie> getMovies(String json){
        List<Movie> movies = new ArrayList<>();

        int voteAaverage, popularity;
        String posterPath, originalTitle, overview, releaseDate;

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

                movies.add(new Movie(voteAaverage, popularity, POSTER_BASE_URL + posterPath,
                        originalTitle, overview, releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }




}
