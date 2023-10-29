package com.example.citrainitiative.storeactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.adapters.galleryAdapter;
import com.example.citrainitiative.lugaractivity.ExplorePangantucan_Data_Displayer;
import com.example.citrainitiative.models.gallery_display_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//calculates rate

public class Store_Activity_Data_Displayer extends AppCompatActivity {

    ImageView back;
    KenBurnsView kbproduct;
    ConstraintLayout backImage;
    Button checkLocation, buy;
    ConstraintLayout buongCost;
    TextView bookCost, dayStay, totalCost, costAround;
    EditText piraso, name, deets;
    int total;
    int cost = 150;
    int ilan;
    CharSequence phoneNum;
    String finalNum,product;
    String imageUrl;
    TextView productName;
    RecyclerView galleryRecycler;
    galleryAdapter galleryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_kapenapalit);

        Intent p = getIntent();

        product = p.getExtras().getString("productName");

        //for partial calculator
        bookCost = findViewById(R.id.bookCost);
        dayStay = findViewById(R.id.dayStay);
        totalCost = findViewById(R.id.totalCost);
        costAround = findViewById(R.id.costAround);
        buongCost = findViewById(R.id.buongCost);

        //for resources
        kbproduct = (KenBurnsView) findViewById(R.id.imageMain);
        back = findViewById(R.id.backArrow);
        backImage = (ConstraintLayout) findViewById(R.id.backExpanded);
        buy = findViewById(R.id.book);

        //mga editTexts
        name = findViewById(R.id.nameHere);
        deets = findViewById(R.id.detailsHere);
        piraso = findViewById(R.id.quantity);

        //main image implementation
        imageUrl = p.getExtras().getString("imageUrl");
        Picasso.get().load(imageUrl).into(kbproduct);

        //texts implementation
        productName = (TextView) findViewById(R.id.productName);
        productName.setText(product);



        //changing pieces listener
        piraso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!piraso.getText().toString().equalsIgnoreCase("")) {
                    ilan = Integer.parseInt(piraso.getText().toString());
                    calc();
                    dayStay.setText(ilan + " piece/s");
                    bookCost.setText(cost + " pesos");
                    buongCost.setVisibility(View.VISIBLE);
                }
            }
        });

        //phone number spinner
        Spinner phones = (Spinner) findViewById(R.id.phoneNum);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generalPhone, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phones.setAdapter(adapter);

        setImageGallery();

        phones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phoneNum = adapter.getItem(position);
                finalNum = phoneNum.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //buy button
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(Store_Activity_Data_Displayer.this);
                build.setTitle("SMS Content");
                build.setMessage("The message will show as follows: \n" + name.getText().toString() + " would like to order " + ilan + " piece/s of "+ product + "\nDelivery instructions are as follows: \n" + deets.getText().toString() + "\n\nWould you like to send the message now?");
                build.setIcon(R.drawable.sms);
                build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Store_Activity_Data_Displayer.this, "The message has been sent to " + finalNum, Toast.LENGTH_SHORT).show();
                    }
                });
                build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                build.show();
            }
        });

        //back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Store_Activity_Data_Displayer.this.finish();
            }
        });
    }
    //cost calc
    public void calc() {
        total = cost * ilan;
        totalCost.setText(total + " pesos");
        totalCost.setVisibility(View.VISIBLE);

    }

    public void setImageGallery(){
        galleryRecycler = findViewById(R.id.gallery);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        galleryRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions galleryQueue = new FirebaseRecyclerOptions.Builder<gallery_display_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Gallery").child("Store").child(product).orderByValue(), gallery_display_data.class)
                .build();
        galleryAdapter = new galleryAdapter(Store_Activity_Data_Displayer.this,galleryQueue);
        galleryRecycler.setAdapter(galleryAdapter);
        galleryAdapter.startListening();

    }
}