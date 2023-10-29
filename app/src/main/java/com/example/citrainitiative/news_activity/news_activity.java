package com.example.citrainitiative.news_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citrainitiative.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

public class news_activity extends AppCompatActivity {

    ImageView kbNews;
    TextView headline, datePosted, full_Details;
    String headLine, date, newsDetails, imageLink;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //assign all widgets to respective layout
        setContentView(R.layout.news_feed_full_details_container);
        kbNews = (ImageView) findViewById(R.id.newsImage);
        headline = (TextView) findViewById(R.id.main_Headline);
        datePosted = (TextView) findViewById(R.id.main_Date);
        full_Details = (TextView) findViewById(R.id.main_Details);

        //calling the intent and passing the string value to each keys
        Intent n = getIntent();
        headLine = n.getExtras().getString("Headline");
        date = n.getExtras().getString("Date Posted");
        newsDetails = n.getExtras().getString("News Details");
        imageLink = n.getExtras().getString("News Image");

        //imageSetter
        Picasso.get().load(imageLink).into(kbNews);

        //text setters
        headline.setText(headLine);
        datePosted.setText(date);
        full_Details.setText(newsDetails);

    }
}
