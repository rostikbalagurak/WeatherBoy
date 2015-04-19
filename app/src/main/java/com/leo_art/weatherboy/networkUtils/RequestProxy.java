package com.leo_art.weatherboy.networkUtils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leo_art.weatherboy.MainActivity;

import org.apache.http.client.CookieStore;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

public class RequestProxy {

    // http client instance
    private DefaultHttpClient mHttpClient;

    private RequestQueue mRequestQueue;

    private Context context;

    // package access constructor
    RequestProxy(Context context) {

        // Create an instance of the Http client.
        // We need this in order to access the cookie store
        this.context = context;
        mHttpClient = getThreadSafeClient();
        CookieStore cookieStore = mHttpClient.getCookieStore();
        mHttpClient.setCookieStore(cookieStore);
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(), new HttpClientStack(mHttpClient));
    }

    private DefaultHttpClient getThreadSafeClient() {

        DefaultHttpClient client = new DefaultHttpClient();

        ClientConnectionManager mgr = client.getConnectionManager();

        HttpParams params = client.getParams();

        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,

                mgr.getSchemeRegistry()), params);

        return client;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    public void getWeatherJSON(String url, Response.Listener<JSONObject> response, Response.ErrorListener error) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, response, error);

        mRequestQueue.add(jsonObjectRequest);
    }
}