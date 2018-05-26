package np.com.bpb.popularmovies;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.net.URL;

public class Movie implements Serializable{
    public int id;
    public String original_title;
    public  String poster_path;
    public  String overview;
    public  int vote_average;
    public  String release_date;
    public String backdrop_path;//poster image thumbnail

    public Movie(int id, String original_title, String poster_path, String overview, int vote_average, String release_date, String backdrop_path) {
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
    }

    public void setImage(URL fromURL, ImageView toImageView){
        Picasso.get()
                .load(String.valueOf(fromURL))
                .placeholder(R.drawable.heart)
                .error(R.drawable.star)
                .fit()
                .into(toImageView);
    }
}
