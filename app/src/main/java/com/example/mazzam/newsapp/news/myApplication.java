package com.example.mazzam.newsapp.news;

import android.app.Application;

import com.example.mazzam.newsapp.Database.MyDataBase;

public class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBase.init(this);
    }
}
