package np.com.bpb.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //Vars
    @BindView(R.id.poster_image)
    ImageView poster;
    private ArrayList<Movie> movies = new ArrayList<>();
    Parser parser = new Parser();
    String jsonStr;
    public NetRequest netRequest = new NetRequest();
    SimpleUrl simpleUrl = new SimpleUrl();
    URL url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonStr = getResources().getString(R.string.dummyMovie);
        Log.d(TAG, "onCreate: Json Dummy Data:"+jsonStr);
        setPoster();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean selection =  false;
        if(simpleUrl.apiKey == null){
            Toast.makeText(this, "Please provide your api key in SimpleUrl class.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Handle item selection
        switch (item.getItemId()) {
            // (COMPLETED) TODO Method calls for sorting
            case R.id.most_popular:
//                selection = setPopular();
                selection = setOrder(Order.POPULAR);
                break;
            case R.id.top_rated:
                selection = setOrder(Order.RATED);
//                selection  = setTopRated();
                break;
            case R.id.upcoming:
                selection = setOrder(Order.UPCOMING);
//                selection = setUpcoming();
                break;
            case R.id.latest:
                selection = setOrder(Order.LATEST);
//                selection = setLatest();
                break;
            case R.id.now_playing:
                selection = setOrder(Order.PLAYING);
//                selection = setNowPlaying();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return selection;
    }
    private boolean setOrder(Order by){
        if (netRequest.isConnected(this)) {
            url = simpleUrl.getUrl(by);
            new MovieLoaderTask().execute(url.toString());
            Toast.makeText(this, "Selected "+by, Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
            movies = parser.parseMovies(jsonStr);
            return false;
        }
    }
    /*
    private boolean setPopular(){
            if (netRequest.isConnected(this)) {
                url = simpleUrl.getUrl(Order.POPULAR);
                new MovieLoaderTask().execute(url.toString());
                Toast.makeText(this, "Selected Most Popular", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
                movies = parser.parseMovies(jsonStr);
                return false;
            }
    }

    private boolean setTopRated(){
            if (netRequest.isConnected(this)) {
                url = simpleUrl.getUrl(Order.RATED);
                Toast.makeText(this, "Selected Top Rated", Toast.LENGTH_SHORT).show();
                new MovieLoaderTask().execute(url.toString());
                return true;
            }
            else{
                Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
                movies = parser.parseMovies(jsonStr);
                return false;
            }
    }

    private boolean setUpcoming(){
            if (netRequest.isConnected(this)) {
                url = simpleUrl.getUrl(Order.UPCOMING);
                Toast.makeText(this, "Selected Upcoming", Toast.LENGTH_SHORT).show();
                new MovieLoaderTask().execute(url.toString());
                return true;
            }
            else{
                Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
                movies = parser.parseMovies(jsonStr);
                return false;
            }
    }

    private boolean setNowPlaying(){
        if (netRequest.isConnected(this)) {
            url = simpleUrl.getUrl(Order.PLAYING);
            Toast.makeText(this, "Selected Now Playing", Toast.LENGTH_SHORT).show();
            new MovieLoaderTask().execute(url.toString());
            return true;
        }
        else{
            Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
            movies = parser.parseMovies(jsonStr);
            return false;
        }
    }
    private boolean setLatest(){
            if (netRequest.isConnected(this)) {
                url = simpleUrl.getUrl(Order.LATEST);
                Toast.makeText(this, "Selected Latest", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setLatest: "+url);
                new LatestMovieLoader().execute(url.toString());
                return true;
            }
            else{
                Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
                movies = parser.parseMovies(jsonStr);
                return false;
            }
    }*/

    private void setPoster() {
        if(simpleUrl.apiKey == null){
            movies = parser.parseMovies(jsonStr.toString());
            this.initRecyclerView();
            Toast.makeText(this, "API Key Required, please provide it in SimpleUrl", Toast.LENGTH_SHORT).show();
            movies = parser.parseMovies(jsonStr.toString());
            initRecyclerView();
            return;
        }
        if (netRequest.isConnected(MainActivity.this)) {
            SimpleUrl simpleUrl = new SimpleUrl();
            if(simpleUrl.apiKey == null){
                Toast.makeText(this, "Please provide your api key in SimpleUrl class.", Toast.LENGTH_SHORT).show();
                return;
            }
            URL url = null;
            url = simpleUrl.getUrl(Order.UPCOMING);
            new MovieLoaderTask().execute(url.toString());
            Toast.makeText(this, "Net Connected "+netRequest.isConnected(this), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Internet is not available. Please try again.", Toast.LENGTH_SHORT).show();
            movies = parser.parseMovies(jsonStr.toString());
            initRecyclerView();
        }
    }
    private void initRecyclerView(){
        int numberOfColumns = 2;
        RecyclerView recyclerView = findViewById(R.id.mv_recycler_view);
        RecyclerViewAdapter adapter;
        adapter = new RecyclerViewAdapter(this,movies, new Clickable() {
            //Step 5 ClickListener for RecyclerView
            @Override
            public void onItemClick(View v, int position) {
                onDetail(position);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
    }
    private void onDetail(int i){
        // start the SecondActivity
        Intent intent = new Intent(this, DetailsActivity.class);
//        intent.putExtra("index", i);
        intent.putExtra("Movie", (Serializable) movies.get(i));
        startActivity(intent);
    }
    class MovieLoaderTask extends AsyncTask<String, Void, String> {
        //        NetRequest netRequest = new NetRequest();
        @Override
        protected String doInBackground(final String... urlsStr) {
            String backURL = urlsStr[0];
            return netRequest.httpRequest(backURL);
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            if (jsonStr != null) {
                movies = parser.parseMovies(jsonStr);
            }
            initRecyclerView();
        }
    }
        class LatestMovieLoader extends AsyncTask<String, Void, String>{
        //This is for a live movie which is continuously changing in the Movie DB
            //        NetRequest netRequest = new NetRequest();
            @Override
            protected String doInBackground(final String... urlsStr) {
                String backURL = urlsStr[0];
                return netRequest.httpRequest(backURL);
            }
            @Override
            protected void onPostExecute(String jsonStr) {
                super.onPostExecute(jsonStr);
                if(jsonStr != null){
                    movies = parser.parseLatestMovies(jsonStr);
                }
                initRecyclerView();
            }
    }
}

