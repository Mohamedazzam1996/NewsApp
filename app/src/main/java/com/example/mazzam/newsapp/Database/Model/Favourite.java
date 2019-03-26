package com.example.mazzam.newsapp.Database.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesItem;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = ArticlesItem.class,
        parentColumns = "url",
        childColumns = "articleUrl",
        onDelete = CASCADE),indices = {@Index(value = "articleUrl", unique = false)})
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String articleUrl;

    public Favourite() {
    }

@Ignore
    public Favourite(String articleUrl) {
        this.articleUrl = articleUrl;
    }


    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}
