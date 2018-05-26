package np.com.bpb.popularmovies;

import java.net.MalformedURLException;
import java.net.URL;

public class SimpleUrl {
    private  final String common= "https://api.themoviedb.org/3/movie/";
    protected final String apiKey =  null; // Please provide your api key here as "<your value>"


    private URL popularUrl = null;
    private URL topRatedUrl = null;
    private URL upcoming = null;
    private URL latest = null;
    private URL nowPlaying = null;

    public URL getUrl(Order selection){
        URL url = null;
       try {
            url = new URL(common);
            switch (selection){
                case  POPULAR:
                    url = new URL(url + "popular?api_key=" + apiKey);
                    popularUrl = url;
                    break;
                case  RATED:
                    url = new URL(url + "top_rated?api_key=" + apiKey);
                    topRatedUrl = url;
                    break;
                case UPCOMING:
                    url = new URL(url + "upcoming?api_key=" + apiKey);
                    upcoming = url;
                    break;
                case LATEST:
                    url = new URL(url + "latest?api_key=" + apiKey);
                    latest = url;
                    break;
                case PLAYING:
                    url = new URL(url + "now_playing?api_key=" + apiKey);
                    nowPlaying = url;
                    break;
                    default:
                        url = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+apiKey);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public URL getPosterUrl(String posterPath){
        URL url = null;
        String base = "http://image.tmdb.org/t/p/";
        String size = "w185";
        String urlStr = base+size+posterPath;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
