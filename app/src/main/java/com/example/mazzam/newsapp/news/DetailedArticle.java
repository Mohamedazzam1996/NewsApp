package com.example.mazzam.newsapp.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mazzam.newsapp.R;

public class DetailedArticle extends AppCompatActivity {
    TextView sourceName,date,title,content;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_article);
        sourceName=findViewById(R.id.source_name);
        date=findViewById(R.id.date);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        image=findViewById(R.id.image);
        String ssourceName=getIntent().getStringExtra("source_name");
        String sdate=getIntent().getStringExtra("date");
        String stitle=getIntent().getStringExtra("title");
        String scontent=getIntent().getStringExtra("content");
        String simageurl=getIntent().getStringExtra("image_url");
        sourceName.setText(ssourceName);
        date.setText(sdate);
        title.setText(stitle);
        content.setText(scontent);
        Glide.with(this).load(simageurl).into(image);

    }
}
