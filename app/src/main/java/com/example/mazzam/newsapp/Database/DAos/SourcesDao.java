package com.example.mazzam.newsapp.Database.DAos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesItem;

import java.util.List;

@Dao
public interface SourcesDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    public void Addsources(List<SourcesItem> sourcesItems);

    @Query("select *from SourcesItem")
    public List<SourcesItem> GetAllSources();

}
