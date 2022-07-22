package com.example.assessment1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Converts a list of Articles into recycler view friendly format
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static String TAG = NewsAdapter.class.getSimpleName();

    //Article[] articles;
    List<Article> articles;
    MainActivity mainActivity;

    public NewsAdapter(MainActivity ma) {
        mainActivity = ma;
    }

    //Creates a row of the recycler view
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Grabs the location to inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //inflates the row of our recycler view with the right context and layout
        View row = layoutInflater.inflate(R.layout.news_item,parent,false);
        return new NewsViewHolder(row);
    }

    //Sets the display and click interaction of the rows of the recycler view
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.newsTextView.setText(articles.get(position).title);
        holder.newsTextView.setOnClickListener(new RecyclerViewOnClick(articles.get(position), mainActivity));
    }

    //Returns the number of items to be displayed
    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    //Updates the list of articles to display
    public void setTasks(List<Article> articleList) {
        articles = articleList;
        notifyDataSetChanged();
    }

    //Returns list of articles from the database
    public List<Article> getTasks() {
        return articles;
    }

    //The display of each row
    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView newsTextView;

        public NewsViewHolder(@NonNull View newsItem) {
            super(newsItem);
            newsTextView = newsItem.findViewById(R.id.tvNewsTitle);
        }
    }
}
