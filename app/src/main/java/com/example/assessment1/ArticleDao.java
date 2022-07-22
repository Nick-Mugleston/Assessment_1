package com.example.assessment1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM ARTICLE ORDER BY ID")
    List<Article> loadAllArticles();

    @Insert
    void insertArticle(Article Article);

    @Update
    void updateArticle(Article article);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM ARTICLE WHERE id = :id")
    Article loadArticleById(int id);
}
