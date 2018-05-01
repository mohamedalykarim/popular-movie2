package udacity.popularmovie.pupularmovies.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "udacity.popularmovie.pupularmovies.favorite";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE =
            "CREATE TABLE "
            + FavoriteMovieContract.FMovies.TABLE_NAME
            + " ("
            + FavoriteMovieContract.FMovies._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoriteMovieContract.FMovies.TITLE
            + " TEXT NOT NULL, "
            + FavoriteMovieContract.FMovies.MOVIE_ID
            + " TEXT NOT NULL "
            + ");";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "
            + FavoriteMovieContract.FMovies.TABLE_NAME;

    public FavoriteMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
