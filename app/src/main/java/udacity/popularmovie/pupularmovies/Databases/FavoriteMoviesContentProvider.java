package udacity.popularmovie.pupularmovies.Databases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static udacity.popularmovie.pupularmovies.Databases.FavoriteMovieContract.FMovies.CONTENT_URI;
import static udacity.popularmovie.pupularmovies.Databases.FavoriteMovieContract.FMovies.TABLE_NAME;

public class FavoriteMoviesContentProvider extends ContentProvider {
    private FavoriteMovieDBHelper favoriteMovieDBHelper;
    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_MOVIE_ID = 101;

    public static final UriMatcher favoritesUriMatcher = buildUriMatcher();


    @Override
    public boolean onCreate() {
        Context context = getContext();
        favoriteMovieDBHelper = new FavoriteMovieDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnedCursor;
        int match = favoritesUriMatcher.match(uri);

        SQLiteDatabase db =favoriteMovieDBHelper.getReadableDatabase();

        switch (match){
            case FAVORITES:
                returnedCursor = db.query(
                  TABLE_NAME,
                  projection,
                  selection,
                  selectionArgs,
                  null,
                  null,
                  sortOrder
                );

                break;

            case FAVORITE_WITH_MOVIE_ID:
                String id = uri.getPathSegments().get(1);

                String mSelection = FavoriteMovieContract.FMovies.MOVIE_ID
                        +" = ?";

                String[] mSelectionArgs = new String[]{id};

                returnedCursor = db.query(
                  TABLE_NAME,
                  projection,
                  mSelection,
                  mSelectionArgs,
                  null,
                  null,
                  sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Can't read: " + uri);
        }

        returnedCursor.setNotificationUri(getContext().getContentResolver(), uri);


        return returnedCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri returnedUri;

        final SQLiteDatabase db = favoriteMovieDBHelper.getWritableDatabase();
        int match = favoritesUriMatcher.match(uri);

        switch (match) {
            case FAVORITES:
                long id = db.insert(
                        FavoriteMovieContract.FMovies.TABLE_NAME,
                        null,
                        values
                );

                if (id >0){
                    returnedUri = ContentUris.withAppendedId(CONTENT_URI,id);

                }else{
                    throw new SQLException("Faild to insert: "+ uri);
                }

                break;


            default:
                throw new UnsupportedOperationException("Unknown: "+ uri);
        }




        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int returnedInt;
        int match = favoritesUriMatcher.match(uri);
        SQLiteDatabase db =favoriteMovieDBHelper.getWritableDatabase();

        switch (match){
            case FAVORITES:
                returnedInt = db.delete(
                        TABLE_NAME,
                        selection,
                        selectionArgs
                );

                break;

            case FAVORITE_WITH_MOVIE_ID:
                String id = uri.getPathSegments().get(1);

                String mSelection = FavoriteMovieContract.FMovies.MOVIE_ID
                        +" = ?";

                String[] mSelectionArgs = new String[]{id};

                returnedInt = db.delete(
                        TABLE_NAME,
                        mSelection,
                        mSelectionArgs
                );
                break;

            default:
                throw new UnsupportedOperationException("Can't delete: " + uri);
        }

        return returnedInt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(
                FavoriteMovieContract.AUTHORITY,
                FavoriteMovieContract.FAVORITES_PATH,
                FAVORITES);
        uriMatcher.addURI(
                FavoriteMovieContract.AUTHORITY,
                FavoriteMovieContract.FAVORITES_PATH +"/#",
                FAVORITE_WITH_MOVIE_ID
        );

        return uriMatcher;
    }
}
