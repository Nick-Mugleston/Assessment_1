package com.example.assessment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etNewsQuery;
    Button btnSearchNews;
    RecyclerView rvNewsArticles;

    Article[] articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNewsQuery = findViewById(R.id.etNewsQuery);
        btnSearchNews = findViewById(R.id.btnSearchNews);
        rvNewsArticles = findViewById(R.id.rvNewsArticles);
        rvNewsArticles.setLayoutManager(new LinearLayoutManager(this));
    }

    public void clickHandler(View view) {
        switch(view.getId()) {
            case R.id.btnSearchNews:
                queryNews();
                break;
        }
    }

    //Calls the NewsAPI to search popular results with the search string
    private void queryNews() {
        //User entered string in the edit text
        //Param for API query
        String queryString = etNewsQuery.getText().toString();
        //Async class to perform the query
        //this MainActivity instance is passed to allow a callback with the API data
        new FetchNews(this).execute(queryString);
    }

    //Called by FetchNews and given query data
    //Populates the recycler view
    protected void fillRecyclerView(Article[] a) {
        articles = a;
        //Formats the data and handles the recycler view display and click action assignment
        //this MainActivity instance is passed to give the context the click action should open a dialog on
        NewsAdapter adapter = new NewsAdapter(articles, this);
        rvNewsArticles.setAdapter(adapter);
    }
}