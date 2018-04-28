package udacity.popularmovie.pupularmovies.Models;

import android.widget.TextView;

public class Trailer {
    String title, key;


    public Trailer(String title, String key) {
        this.title = title;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
