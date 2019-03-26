package com.example.mazzam.newsapp.Database.DAos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;

import java.util.List;

@Dao
public interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void AddArticles(List<ArticlesItem> articlesItems);

    @Query("select *from articlesitem where sourceId=:sourceId")
    public List<ArticlesItem> getAllArticles(String sourceId);
}
