package com.example.assessment1;

//Holds the data for each item parsed from the queried JSON
public class Article {

    String author, title, description, url;

    public Article(String... strings) {
        title = strings[0];
        author = strings[1];
        description = strings[2];
        url = strings[3];
    }

    public String toString() {
        return title + " - " + author + " - " + description + " - " + url;
    }
}
