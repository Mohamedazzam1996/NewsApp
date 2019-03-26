package com.example.mazzam.newsapp.news.Repo;

import android.util.Log;

import com.example.mazzam.newsapp.Api.ApiManager;
import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.Api.NewsResponse.NewsResponse;
import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesItem;
import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesResponse;
import com.example.mazzam.newsapp.Database.Model.Favourite;
import com.example.mazzam.newsapp.Database.MyDataBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    List<SourcesItem>sourcesItems;
    String lang;
    String country;
    private static String apiKey="43d8a8fd8e344b87bc7ef3ef6b6f4759";

    public NewsRepository( String country) {
        this.country = country;
        sourcesItems=new ArrayList<>();
    }

    public void getNewsSources(final OnSourcesPreparedListner onSourcesPreparedListner){
        ApiManager.getApis()
                .getNewsSources(country,apiKey)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                        if(response.isSuccessful()&&"ok".equals(response.body().getStatus())) {
                            sourcesItems = response.body().getSources();

                            if (onSourcesPreparedListner != null)
                                onSourcesPreparedListner.onSourcesPrepared(sourcesItems);
                            InsertIntoDataBase insertIntoDataBase=new InsertIntoDataBase(sourcesItems);
                              insertIntoDataBase.start();
                        }
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                       //handleDataBase
                     GetDatafromDataBase getDatafromDataBase=new GetDatafromDataBase(onSourcesPreparedListner)  ;
                     getDatafromDataBase.start();
                    }
                });
          }
    public void getNewsBySourceId(final String sourceId , final OnNewsPreparedListner onNewsPreparedListner)
    {
        ApiManager.getApis()
                .getNewsFromSources(sourceId,apiKey)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                        List<ArticlesItem> articlesItems=response.body().getArticles();
                        InsertArticlesThread insertArticlesThread=new InsertArticlesThread(articlesItems);
                        insertArticlesThread.start();
                        onNewsPreparedListner.onNewsPrepared(articlesItems);
                    }
                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                     GetArticlesThread getArticlesThread=new GetArticlesThread(onNewsPreparedListner,sourceId);
                     getArticlesThread.start();
                    }
                });

    }

          public interface OnSourcesPreparedListner{
              public void onSourcesPrepared(List<SourcesItem> sourcesItems);
          }
          public interface OnNewsPreparedListner{
           public void onNewsPrepared(List<ArticlesItem> articlesItems);
          }
          public class InsertIntoDataBase extends Thread{

              List<SourcesItem> sourcesItems;
              public InsertIntoDataBase(List<SourcesItem> sourcesItems)
              {
                  this.sourcesItems=sourcesItems;
              }
              @Override
              public void run() {
                  MyDataBase.GetInstance()
                          .sourcesDao()
                          .Addsources(sourcesItems);
                  Log.e("databasethread","data is inserted");
              }
          }


    public class GetDatafromDataBase extends Thread{
        OnSourcesPreparedListner onSourcesPreparedListner;
        public GetDatafromDataBase(OnSourcesPreparedListner onSourcesPreparedListner){

            this.onSourcesPreparedListner=onSourcesPreparedListner;
        }

        List<SourcesItem> sourcesItems;
        @Override
        public void run() {
         sourcesItems= MyDataBase.GetInstance()
                    .sourcesDao()
                    .GetAllSources();
         onSourcesPreparedListner.onSourcesPrepared(sourcesItems);
            Log.e("databasethread","data is inserted");
        }
    }
    public class InsertArticlesThread extends Thread{
        List<ArticlesItem> articlesItems;
        public InsertArticlesThread(List<ArticlesItem> articlesItems) {
            this.articlesItems = articlesItems;
        }
        @Override
        public void run() {
            for (int i=0;i<articlesItems.size();i++)
            {
                ArticlesItem articlesItem=articlesItems.get(i);
                articlesItem.setSourceId(articlesItem.getSource().getId());
                articlesItem.setSourceName(articlesItem.getSource().getName());
            }
            MyDataBase.GetInstance()
                     .articlesDao()
                    .AddArticles(articlesItems);
            Log.e("articlesthread","articles is insertd into database");
        }
    }


    public class GetArticlesThread extends Thread{
          OnNewsPreparedListner onNewsPreparedListner;
          String sourceId;
          public GetArticlesThread(OnNewsPreparedListner onNewsPreparedListner,String sourceId)
          { this.onNewsPreparedListner=onNewsPreparedListner;
            this.sourceId=sourceId;
          }

        @Override
        public void run() {
            List <ArticlesItem> articlesItems=
                    MyDataBase.GetInstance()
                    .articlesDao()
                    .getAllArticles(sourceId);
            onNewsPreparedListner.onNewsPrepared(articlesItems);
        }
    }
    public static class InsertArticlesFavouriteThread extends Thread{
        String articleUrl;
        public InsertArticlesFavouriteThread(String articleUrl){
            this.articleUrl=articleUrl;
        }
        @Override
        public void run() {
            Favourite f=new Favourite();
            f.setArticleUrl(articleUrl);
            MyDataBase.GetInstance()
                    .favouriteDao()
                    .addArticleUrl(f);
        }
    }
    public interface OnArticlePreparedListner{
        public void onArticlePrepared(List<ArticlesItem> articlesItem);
    }

    public static class GetFavArticlesFromDBThread extends Thread{
      List<String> urls;
        OnArticlePreparedListner onArticlePreparedListner;
        public GetFavArticlesFromDBThread(OnArticlePreparedListner onArticlePreparedListner){
            this.onArticlePreparedListner=onArticlePreparedListner;
        }
        @Override
        public void run() {
            urls=MyDataBase.GetInstance()
                    .favouriteDao()
                    .getUrl();
            Log.e("favouritedatabase","getfavourite");
           List<ArticlesItem>articlesItems=new ArrayList<>();
                   for(int i=0;i<urls.size();i++) {
                    ArticlesItem articlesItem= MyDataBase.GetInstance()
                               .favouriteDao()
                               .getArticle(urls.get(i));
                        articlesItems.add(articlesItem);
                   }

            onArticlePreparedListner.onArticlePrepared(articlesItems);
        }
    }
    public static class DeleteFvouriteArticleThread extends Thread {
        String articleUrl;
        public DeleteFvouriteArticleThread(String articleUrl)
        {
            this.articleUrl=articleUrl;
        }
        @Override
        public void run() {
            MyDataBase.GetInstance()
                    .favouriteDao()
                    .deleteArticle(articleUrl);
        }
    }

}
