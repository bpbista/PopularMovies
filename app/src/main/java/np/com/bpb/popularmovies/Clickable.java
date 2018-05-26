package np.com.bpb.popularmovies;

import android.view.View;

//Step 1 ClickListener for RecyclerView
public interface Clickable {
     void onItemClick(View v, int position);
}

enum Order{
    POPULAR, RATED, UPCOMING, LATEST,PLAYING
}