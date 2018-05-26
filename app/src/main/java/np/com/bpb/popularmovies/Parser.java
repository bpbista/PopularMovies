package np.com.bpb.popularmovies;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser {
    public ArrayList<Movie> parseMovies(String jsonStr){//(JSONObject respone){
        int id;
        String original_title;
        String poster_path;
        String overview;
        int vote_average;
        String release_date;
        String backdrop_path;//p)
        ArrayList<Movie> popularMovies = new ArrayList<>();
        Movie movie;
        try {
            JSONObject respone = new JSONObject(jsonStr);
            JSONArray results = respone.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                 id = obj.getInt("id");
                 original_title = obj.getString("original_title");
                 poster_path = obj.getString("poster_path");
                 overview = obj.getString("overview");
                 vote_average = obj.getInt("vote_average");
                 release_date = obj.getString("release_date");
                 backdrop_path = obj.getString("backdrop_path");//poster image thumbnail
                movie = new Movie(id, original_title,poster_path,overview,vote_average,release_date,backdrop_path);
                popularMovies.add(movie);
            }
        } catch (JSONException  e) {
            e.printStackTrace();
        }
        return popularMovies;
    }

    public ArrayList<Movie> parseLatestMovies(String jsonStr){
        int id;
        String original_title;
        String poster_path;
        String overview;
        int vote_average;
        String release_date;
        String backdrop_path;//p)
        ArrayList<Movie> popularMovies = new ArrayList<>();
        Movie movie;
        JSONObject respone = null;
        try {
            respone = new JSONObject(jsonStr);
            id = respone.getInt("id");
            original_title = respone.getString("original_title");
            poster_path = respone.getString("poster_path");
            overview = respone.getString("overview");
            vote_average = respone.getInt("vote_average");
            release_date = respone.getString("release_date");
            backdrop_path = respone.getString("backdrop_path");
            movie = new Movie(id, original_title,poster_path,overview,vote_average,release_date,backdrop_path);
            popularMovies.add(movie);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return popularMovies;
    }
}
