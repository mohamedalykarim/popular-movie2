package udacity.popularmovie.pupularmovies.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udacity.popularmovie.pupularmovies.Databases.FavoriteMovieContract;
import udacity.popularmovie.pupularmovies.Models.Favorite;

import static udacity.popularmovie.pupularmovies.Databases.FavoriteMovieContract.FMovies.CONTENT_URI;

public class FavoriteMovieUtils {

    public static boolean isCheckedAsFavorite(Context context, String movieID){
        Uri uri = CONTENT_URI.buildUpon()
                .appendPath(movieID)
                .build();

        boolean ischecked;

        Cursor cursor = context.getContentResolver().query(uri,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }


    }

    public static int insertFavoriteMovie(Context context, ContentValues values){
        String returnedInt;

        Uri uri = context.getContentResolver().insert(CONTENT_URI,values);

        returnedInt = uri.getPathSegments().get(1);

        return Integer.valueOf(returnedInt);
    }

    public static int deleteFavoriteMovie(Context context, String movieID){
        int returnedInt;

        Uri uri = CONTENT_URI.buildUpon()
                .appendPath(movieID)
                .build();

        returnedInt = context.getContentResolver().delete(uri,null,null);

        return returnedInt;

    }

    public static List<Favorite> getALLFavoriteMovie(Context context){
        List<Favorite> favoriteMovies = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        favoriteMovies.add(new Favorite(
                cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FMovies.TITLE)),
                cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FMovies.MOVIE_ID))
        ));

        while (cursor.moveToNext()){
            favoriteMovies.add(new Favorite(
                    cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FMovies.TITLE)),
                    cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FMovies.MOVIE_ID))
            ));
        }

        cursor.close();
        return favoriteMovies;

    }

}
