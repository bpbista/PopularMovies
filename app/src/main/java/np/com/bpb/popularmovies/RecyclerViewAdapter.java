package np.com.bpb.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.net.URL;
import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    public ArrayList<Movie> movies;
    //Step 2 ClickListener for RecyclerView
    Clickable listener;
    Context mcontext;
    public  RecyclerViewAdapter(ArrayList<Movie> movies){
        this.movies = movies;
    }

    public RecyclerViewAdapter(Context context, ArrayList<Movie> data, Clickable listener) {
        //Step 3 ClickListener for RecyclerView
        this.movies = data;
        this.listener = listener;
        this.mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        //(COMPLETED)TODO set onClickListener
        //Step 4 ClickListener for RecyclerView
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleUrl imageURL = new SimpleUrl();
        URL url = null;

        // holder.itemView.setBackgroundColor(android.R.color.holo_green_dark);
        //(COMPLETED)TODO image load here

        url = imageURL.getPosterUrl(movies.get(position).poster_path);
        Log.d(TAG, "onBindViewHolder: "+ url.toString());
        /*Picasso.get()
                .load(String.valueOf(url))
                .placeholder(R.drawable.star)
                .error(R.drawable.heart)
                .fit()
                .into(holder.image);*/
          movies.get(position).setImage(url,holder.image);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+movies.size());
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.poster_image);
        }
    }
}
