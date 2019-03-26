package com.example.mazzam.newsapp.Database.DAos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.Database.Model.Favourite;

import java.sql.Array;
import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addArticleUrl(Favourite url);

    @Query("Select *from ArticlesItem where url=:url")
    public ArticlesItem getArticle(String url);

    @Query("select articleUrl from favourite")
    public List<String> getUrl();

    @Query("delete from Favourite where articleUrl=:url")
    public void deleteArticle(String url);
}
