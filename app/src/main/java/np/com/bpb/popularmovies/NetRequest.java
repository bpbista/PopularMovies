package np.com.bpb.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetRequest{
    //OkHttp Start
    public String httpRequest(String url) { // This method is used for network request
        OkHttpClient client = new OkHttpClient();
        String jsonResult = null;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jsonResult = response.body().string().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
    //OkHttp End

    public boolean isConnected(final Context context) {
        boolean status;
        final ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //To check the Intenet connectivity, but how hard I tried, it just sends back true even if my Internet is off
        //So, this ugly code is to check every point of failures
        if((conn.getActiveNetworkInfo() != null && conn.getActiveNetworkInfo().isAvailable() && conn.getActiveNetworkInfo().isConnected())
                &&(conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)){
            status = true;
        }else{
            status = false;
        }
        return status;
    }

   /*
   // This menthod is working correctly, only the problem is to deal with async task
   // response is nice as intended
   // and I removed volley library from gradle to optimize code

   public String jsonRequest(Context context, String url){
        final String[] result = new String[1];
        ///Volly Start
        RequestQueue queue = Volley.newRequestQueue(context); //Request queue
        // prepare the Request
        JsonObjectRequest request = new JsonObjectRequest
                (GET, url, null,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                result[0] = response.toString();
                                Log.d("Network Request", "onResponse: "+result[0]);
                            }
                        },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(request);
       //Volly END
        return String.valueOf(result[0]);
    }
    */

}
