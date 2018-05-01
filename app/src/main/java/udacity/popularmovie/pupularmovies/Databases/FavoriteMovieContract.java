package udacity.popularmovie.pupularmovies.Databases;

import android.net.Uri;
import android.provider.BaseColumns;

public final class FavoriteMovieContract {

    public static final String AUTHORITY = "udacity.popularmovie.pupularmovies";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String FAVORITES_PATH = "favorites";

    private FavoriteMovieContract() {
    }

    public static class FMovies implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_URI.buildUpon()
                .appendPath(FAVORITES_PATH)
                .build();

        public static final String TABLE_NAME = "favorite_movies";
        public static final String TITLE = "title";
        public static final String MOVIE_ID = "movie_id";
    }
}
