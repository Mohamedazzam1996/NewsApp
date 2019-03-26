package com.example.mazzam.newsapp.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mazzam.newsapp.Adapter.ArticleFavouriteRecyclerViewAdapter;
import com.example.mazzam.newsapp.Adapter.ArticleRecyclerViewAdapter;
import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.Base.BaseActivity;
import com.example.mazzam.newsapp.Database.MyDataBase;
import com.example.mazzam.newsapp.R;
import com.example.mazzam.newsapp.news.Repo.NewsRepository;

import java.util.List;

public class FavouriteActivity extends BaseActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<ArticlesItem>articlesItems;
    ArticleFavouriteRecyclerViewAdapter adapter;
 NewsRepository.GetFavArticlesFromDBThread getFavArticlesFromDBThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(activity);
        adapter=new ArticleFavouriteRecyclerViewAdapter(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

       getFavArticlesFromDBThread=new NewsRepository.GetFavArticlesFromDBThread(new NewsRepository.OnArticlePreparedListner() {
           @Override
           public void onArticlePrepared(final List<ArticlesItem> articlesItem) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       adapter.changeData(articlesItem);
                   }
               });

           }

       });
        getFavArticlesFromDBThread.start();


    }
}
