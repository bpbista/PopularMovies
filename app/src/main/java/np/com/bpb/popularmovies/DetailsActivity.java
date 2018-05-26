package np.com.bpb.popularmovies;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.tv_original_title)
    TextView originalTitle;
    @BindView(R.id.tv_release_date)
    TextView releaseDate;
    @BindView(R.id.iv_poster_thumbnail)
    ImageView posterThumbnail;
    @BindView(R.id.tv_user_rating)
    RatingBar userRating;
    @BindView(R.id.tv_plot_synopsis)
    TextView plotSynopsis;
    Movie movie;
    SimpleUrl simpleUrl;
    URL thumbnailUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent mIntent = getIntent();
        ButterKnife.bind(this);
        simpleUrl = new SimpleUrl();
//        int index = mIntent.getIntExtra("index", 0);
        movie = (Movie) getIntent().getSerializableExtra("Movie");
        thumbnailUrl = simpleUrl.getPosterUrl(movie.poster_path);
        movie.setImage(thumbnailUrl,posterThumbnail);
        Toast.makeText(this, movie.original_title, Toast.LENGTH_SHORT).show();
        originalTitle.setText(movie.original_title);
        releaseDate.setText("Release Date: " + movie.release_date);
        userRating.setNumStars(5);
        userRating.setRating(movie.vote_average/2);
        plotSynopsis.setText("Overview: " + movie.overview);
    }
}
