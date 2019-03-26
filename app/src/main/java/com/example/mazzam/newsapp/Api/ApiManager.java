package com.example.mazzam.newsapp.Api;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class ApiManager {
   private static Retrofit retrofitInstance;

   private static Retrofit getInstance(){
       if (retrofitInstance==null)
       {
           HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
               @Override
               public void log(String message) {
                   Log.e("api",message);
               }
           });
           logging.setLevel(HttpLoggingInterceptor.Level.BODY);
           OkHttpClient client = new OkHttpClient.Builder()
                   .addInterceptor(logging)
                   .build();
           retrofitInstance= new Retrofit.Builder()
                   .baseUrl("https://newsapi.org/v2/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(client)
                   .build();

       }
      return retrofitInstance;
   }

   public static Services getApis(){

       return getInstance().create(Services.class);
   }
}
