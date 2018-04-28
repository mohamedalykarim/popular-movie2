package udacity.popularmovie.pupularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ALi on 4/1/2018.
 * Movie Model
 */

public class Movie implements Parcelable {
    int voteAverage, popularity ;
    String posterPath, originalTitle, overview, releaseDate, id;

    public Movie(int voteAverage, int popularity, String posterPath, String originalTitle, String overview, String releaseDate, String id) {
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    protected Movie(Parcel in) {
        voteAverage = in.readInt();
        popularity = in.readInt();
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getVoteAverage() {
        return voteAverage;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(voteAverage);
        parcel.writeInt(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(id);
    }
}

