package com.example.assessment1;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Holds the data for each item parsed from the queried JSON
@Entity (tableName = "article")
public class Article {

    @PrimaryKey (autoGenerate = true)
    int id;
    String author, title, description, url;

    @Ignore
    public Article(String title, String author, String description, String url) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.url = url;
    }

    public Article(int id, String title, String author, String description, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return title + " - " + author + " - " + description + " - " + url;
    }
}
