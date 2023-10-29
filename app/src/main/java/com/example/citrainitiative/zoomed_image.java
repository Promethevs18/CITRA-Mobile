package com.example.citrainitiative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class zoomed_image extends AppCompatActivity {
    PhotoView imageView;
    Button returnNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomed_image);

        imageView = (PhotoView) findViewById(R.id.image_viewer);
        returnNow = (Button) findViewById(R.id.return_to_previous);
        Intent im = getIntent();


        Picasso.get().load(im.getExtras().getString("Image Link")).into(imageView);

        returnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomed_image.this.finish();
            }
        });

    }
}