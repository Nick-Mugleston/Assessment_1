package com.example.assessment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etNewsQuery;
    Button btnSearchNews;
    RecyclerView rvNewsArticles;

    //List of articles in the recycler view
    List<Article> articles;
    //The adapter to bridge the data and the display
    NewsAdapter adapter;
    //THe database access point
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNewsQuery = findViewById(R.id.etNewsQuery);
        btnSearchNews = findViewById(R.id.btnSearchNews);
        rvNewsArticles = findViewById(R.id.rvNewsArticles);
        rvNewsArticles.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this);
        rvNewsArticles.setAdapter(adapter);
        mDb = AppDatabase.getInstance(getApplicationContext());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Article> tasks = adapter.getTasks();
                        mDb.articleDao().delete(tasks.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(rvNewsArticles);
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

    //Called after the news is queried and parsed, so it can be saved in the database
    protected void saveData(List<Article> articles) {
        //saves each article
        for(Article a : articles) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.articleDao().insertArticle(a);
                }
            });
        }
        //Loads the new data into the recycler view
        retrieveTasks();
    }

    //If the app is minimized and come back to, reloads the data
    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    //Loads all data into the recycler view
    protected void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Article> persons = mDb.articleDao().loadAllArticles();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setTasks(persons);
                    }
                });
            }
        });
    }
}