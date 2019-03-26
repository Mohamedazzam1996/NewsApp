package com.example.mazzam.newsapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesItem;
import com.example.mazzam.newsapp.Database.DAos.ArticlesDao;
import com.example.mazzam.newsapp.Database.DAos.FavouriteDao;
import com.example.mazzam.newsapp.Database.DAos.SourcesDao;
import com.example.mazzam.newsapp.Database.Model.Favourite;

@Database(entities = {SourcesItem.class,ArticlesItem.class,Favourite.class},version = 6,exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {

    private static MyDataBase myDataBase;
    public abstract SourcesDao sourcesDao();
    public abstract FavouriteDao favouriteDao();
    public abstract ArticlesDao articlesDao();
    public static void init(Context context){
        if(myDataBase==null) {
            myDataBase = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "News-database")
                    //.allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }
    public static MyDataBase GetInstance()
    {
        return myDataBase;
    }


}
