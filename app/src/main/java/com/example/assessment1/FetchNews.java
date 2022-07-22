package com.example.assessment1;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetchNews extends AsyncTask<String, Void, String> {

    //Labels logcat output for easy filtering
    private static final String TAG = FetchNews.class.getSimpleName();

    List<Article> articles;
    MainActivity mainActivity;

    public FetchNews(MainActivity ma) {
        mainActivity = ma;
    }

    //Calls NetworkUtils to query the NewsAPI with the search term
    @Override
    protected String doInBackground(String... url) {
        Log.i(TAG,"querying");
        return NetworkUtils.getNews(url[0]);
    }

    //After the query, the JSON is parsed here
    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        Log.i(TAG,"OnPostExecute");
        try {
            //Reads jsonString as a JSON object to be parsed
            JSONObject jsonObject = new JSONObject(jsonString);
            //Looks for items contained under the "articles" section
            JSONArray itemsArray = jsonObject.getJSONArray("articles");

            //Max 2 articles loaded into the app - to limit excessive strain while testing the database
            int cap = Math.min(itemsArray.length(),2);
            articles = new ArrayList<Article>();

            for(int i = 0; i < cap; i++) {
                try {
                    //Parses the specific data from the ith item in the array
                    JSONObject article = itemsArray.getJSONObject(i);
                    String title = article.getString("title");
                    String author = article.getString("author");
                    String description = article.getString("description");
                    String url = article.getString("url");

                    //if there is not a problem, put it in the articles list
                    if(title != null && author != null && description != null && url != null && !title.isEmpty() && !author.isEmpty() && !description.isEmpty() && !url.isEmpty()) {
                        articles.add(new Article(title,author,description,url));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, articles.toString());
        mainActivity.saveData(articles);
    }
}
