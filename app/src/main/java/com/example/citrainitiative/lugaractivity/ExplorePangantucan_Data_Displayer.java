package com.example.citrainitiative.lugaractivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.adapters.galleryAdapter;
import com.example.citrainitiative.models.gallery_display_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class ExplorePangantucan_Data_Displayer extends AppCompatActivity {

    Button checkLocation;
    ImageView back;
    KenBurnsView kbkilakiron;
    RecyclerView galleryRecycler;
    galleryAdapter galleryAdapter;

    String mapLink, imageUrl, place,  category;
    TextView placeName, locatedWithin, goodFor, detailDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_interface);

        galleryRecycler = (RecyclerView) findViewById(R.id.imageGallery);
        Intent y = getIntent();

        //view implementations
        placeName = (TextView) findViewById(R.id.productName);
        locatedWithin = (TextView) findViewById(R.id.locatedWithin);
        goodFor = (TextView) findViewById(R.id.goodFor);
        detailDesc = (TextView) findViewById(R.id.detailDesc);

        //main image implementation
        imageUrl = y.getExtras().getString("imageUrl");
        kbkilakiron = (KenBurnsView) findViewById(R.id.kilakironMain);
        Picasso.get().load(imageUrl).into(kbkilakiron);


        //place implementation
        place = y.getExtras().getString("placeName");
        category = y.getExtras().getString("category");
        //placeName implementation
        placeName.setText(place);
        //locatedWithin implementation
        locatedWithin.setText(y.getExtras().getString("locatedWithin"));
        //goodFor implementation
        goodFor.setText(y.getExtras().getString("goodFor"));
        //details implementation
        detailDesc.setText(y.getExtras().getString("details"));
        //mapLink implementation
        mapLink = y.getExtras().getString("mapLink");






        //eto yung sa mapa
        checkLocation = findViewById(R.id.checkRoute);
        checkLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mapLink);
                Intent j = new Intent(Intent.ACTION_VIEW, uri);
                j.setPackage("com.google.android.apps.maps");
                startActivity(j);
            }
        });

        setImageGallery();
        //eto yung back arrow
        back = (ImageView) findViewById(R.id.backArrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExplorePangantucan_Data_Displayer.this.finish();
            }
        });
    }

    public void setImageGallery(){
        galleryRecycler = findViewById(R.id.imageGallery);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        galleryRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions galleryQueue = new FirebaseRecyclerOptions.Builder<gallery_display_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Gallery").child("Explore").child(category).child(place).orderByValue(), gallery_display_data.class)
                .build();
        galleryAdapter = new galleryAdapter(ExplorePangantucan_Data_Displayer.this,galleryQueue);
        galleryRecycler.setAdapter(galleryAdapter);
        galleryAdapter.startListening();

    }

}