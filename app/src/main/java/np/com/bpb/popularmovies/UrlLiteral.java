package np.com.bpb.popularmovies;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UrlLiteral {
    /*
    This class is only for future reference
    It is not used in the project
    */
    public  static String scheme = "https";
    public  static String authority = "api.themoviedb.org";
    public  static ArrayList<String> path = new ArrayList<String>();
    public  static HashMap<String, String>  params;
     UrlLiteral( String scheme, String authority, ArrayList<String> path, HashMap<String, String>  params){
        this.scheme = scheme;
        this.authority = authority;
         for(int i=0;i<path.size();i++){
             this.path.add(path.get(i));
         }
         for (Map.Entry<String, String> entry : params.entrySet()) {
             String key = entry.getKey();
             String value = entry.getValue();
             this.params.put(key,value);
         }
    }
    private String getUrlStr() throws MalformedURLException {
         URL url;
        Uri.Builder builder = new Uri.Builder();
        builder.clearQuery();
        builder.scheme(scheme)
                .authority(authority);
        for(int i=0;i<path.size();i++){
            builder.appendPath(path.get(i));
        }

        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.appendQueryParameter(key,value);

        }
        String movieUrl = builder.build().toString();
        try{
        url = new URL(movieUrl);
        }catch (Exception e){
            url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=<default>");
        }
        return movieUrl;
    }
}
