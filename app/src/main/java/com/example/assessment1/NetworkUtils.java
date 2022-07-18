package com.example.assessment1;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkUtils {

    //Tag to label logcat statements
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    //Base URI for the NewsAPI
    private static final String NEWS_BASE_URI = "https://newsapi.org/v2/everything/"; // Base URI for theBooks API
    // Parameter for the search string
    private static final String QUERY_PARAM = "q"; // Parameter for the search string
    // Parameter for beginning date range
    private static final String FROM = "from"; // Parameter that limits search results
    // Parameter for ending date range
    private static final String TO = "to"; // Parameter to filter by print type
    // Parameter for search sort filter
    private static final String SORTBY = "sortBy";
    // Parameter for the API key
    private static final String API_KEY_PARAM = "apiKey";
    // My API key
    private static final String API_KEY = "a33a509ea13844abb94ae3bb8544385d";

    //queries the NewsAPI given the query string
    static String getNews(String queryString){
        //To connect to the internet
        HttpURLConnection urlConnection = null;
        //To read the JSON urlConnection pulls
        BufferedReader reader = null;
        //Saves and returns the JSON string
        String newsJSONString = null;

        //The full address to request
        Uri builtURI = Uri.parse(NEWS_BASE_URI).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(FROM, "2022-07-01")
                .appendQueryParameter(TO, "2022-07-15")
                .appendQueryParameter(SORTBY, "popularity")
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        Log.i(LOG_TAG,"url="+builtURI.toString());
        try {
            //Uri to URL for the urlConnection
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            //Pulls the input stream from urlConnection
            InputStream inputStream = urlConnection.getInputStream();
            //Midstep to process the JSON string
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            newsJSONString = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Must disconnect urlConnection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            //Must close BufferedReader
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, newsJSONString);

        return newsJSONString;
    }
}