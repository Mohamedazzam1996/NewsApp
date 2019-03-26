package com.example.mazzam.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mazzam.newsapp.Adapter.ArticleRecyclerViewAdapter;
import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesItem;
import com.example.mazzam.newsapp.Base.BaseActivity;
import com.example.mazzam.newsapp.Database.MyDataBase;
import com.example.mazzam.newsapp.news.DetailedArticle;
import com.example.mazzam.newsapp.news.FavouriteActivity;
import com.example.mazzam.newsapp.news.Repo.NewsRepository;
import com.example.mazzam.newsapp.news.Repo.NewsRepository;

import java.util.List;

public class HomeActivity extends BaseActivity {
    ImageView favImage;
    RecyclerView recyclerView;
    ArticleRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TabLayout tabLayout;
    NewsRepository newsRepository;
    NewsRepository.InsertArticlesFavouriteThread insertArticlesFavouriteThread;
    String lang="en";
    String country="us";
    String category="sports";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerview);
        favImage=findViewById(R.id.favImage);
        favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity,FavouriteActivity.class));
            }
        });
        tabLayout=findViewById(R.id.tablayout);
        showprogressBar();
        newsRepository=new NewsRepository(country);
        newsRepository.getNewsSources(onSourcesPreparedListner);
        adapter=new ArticleRecyclerViewAdapter(null);
        adapter.setOnArticleclicklistner(new ArticleRecyclerViewAdapter.OnItemclicklistner() {
            @Override
            public void onItemClick(int pos, ArticlesItem articlesItem) {
                Intent intent=new Intent(activity,DetailedArticle.class);
                   intent.putExtra("source_name",articlesItem.getSourceName());
                intent.putExtra("date",ArticleRecyclerViewAdapter.parseDateToddMMyyyy(articlesItem.getPublishedAt()));
                intent.putExtra("title",articlesItem.getTitle());
                intent.putExtra("content",articlesItem.getContent());
                intent.putExtra("image_url",articlesItem.getUrlToImage());
                startActivity(intent);
            }

        });
        adapter.setOnImageclick(new ArticleRecyclerViewAdapter.OnItemclicklistner() {
            @Override
            public void onItemClick(int pos, ArticlesItem articlesItem) {
                Toast.makeText(activity, "Item Is Added to Favourite", Toast.LENGTH_SHORT).show();
                insertArticlesFavouriteThread=new NewsRepository.InsertArticlesFavouriteThread(articlesItem.getUrl());
                insertArticlesFavouriteThread.start();

            }
        });
        layoutManager=new LinearLayoutManager(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnDislikImageClick(new ArticleRecyclerViewAdapter.OnItemclicklistner() {
            @Override
            public void onItemClick(int pos, ArticlesItem articlesItem) {
                NewsRepository.DeleteFvouriteArticleThread deleteFvouriteArticleThread=new NewsRepository.DeleteFvouriteArticleThread(articlesItem.getUrl());
                deleteFvouriteArticleThread.start();
                Toast.makeText(activity, "Item Has been deleted from Favourite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    NewsRepository.OnSourcesPreparedListner onSourcesPreparedListner=new NewsRepository.OnSourcesPreparedListner() {
        @Override
        public void onSourcesPrepared(final List<SourcesItem> sourcesItems) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hidePrgressBar();
                    addSourcesToTablayout(sourcesItems);
                }
            });

        }
    };

    private void addSourcesToTablayout(List<SourcesItem> sourcesItems) {
        if (sourcesItems==null)
            return;
        tabLayout.removeAllTabs();
        for (int i=0;i<sourcesItems.size();i++)
        {
            SourcesItem sourcesItem=sourcesItems.get(i);
         TabLayout.Tab tab=tabLayout.newTab();
         tab.setText(sourcesItem.getName());
         tab.setTag(sourcesItem);
         tabLayout.addTab(tab);
        }
        tabLayout.getTabAt(0).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 SourcesItem sourcesItem= ((SourcesItem) tab.getTag());
                newsRepository.getNewsBySourceId(sourcesItem.getId(), new NewsRepository.OnNewsPreparedListner() {
                    @Override
                    public void onNewsPrepared(final List<ArticlesItem> articlesItems) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.changeData(articlesItems);

                            }
                        });
                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem sourcesItem= ((SourcesItem) tab.getTag());
                newsRepository.getNewsBySourceId(sourcesItem.getId(), new NewsRepository.OnNewsPreparedListner() {
                    @Override
                    public void onNewsPrepared(final List<ArticlesItem> articlesItems) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.changeData(articlesItems);
                            }
                        });
                    }
                });

            }
        });

    }

}
