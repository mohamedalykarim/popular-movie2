package udacity.popularmovie.pupularmovies.Models;

public class Favorite {
    String name, movieID;

    public Favorite(String name, String movieID) {
        this.name = name;
        this.movieID = movieID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
}
