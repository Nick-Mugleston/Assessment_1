package com.example.assessment1;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

//Handles the click action of each item in the recycler view
public class RecyclerViewOnClick extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RecyclerView.class.getSimpleName();

    //The article associated with the clicked row
    Article article;
    //The context for the dialog
    MainActivity mainActivity;

    public RecyclerViewOnClick(Article a, MainActivity ma) {
        article = a;
        mainActivity = ma;
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, article.author);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setPositiveButton("OK", null);
        alertDialogBuilder.setTitle(article.title);
        alertDialogBuilder.setMessage("--"+article.author+"\n"+article.description+"\n"+article.url);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
