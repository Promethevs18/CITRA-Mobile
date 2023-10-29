package com.example.citrainitiative.leisureactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Resto_Activity_Data_Displayer extends AppCompatActivity {


    Context context;
    ImageView back;
    KenBurnsView kbResto;
    ConstraintLayout backImage;
    RadioGroup radioGroup;
    Button checkLocation;
    Button book;
    private Animator currentAnimator;
    private int shortAnimation;
    TextView name, deets;
    String inquire;
    RadioButton button;
    int id;
    CharSequence phoneNum;
    String finalNum;

    String mapLink, imageUrl, place;
    TextView placeName, detailDesc;
    RecyclerView galleryRecycler;
    galleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leisure_resto_interface);
        AlertDialog.Builder build = new AlertDialog.Builder(Resto_Activity_Data_Displayer.this);


        book = (Button) findViewById(R.id.book);
        radioGroup = (RadioGroup) findViewById(R.id.classes);
        kbResto = (KenBurnsView) findViewById(R.id.imageMain);
        back = findViewById(R.id.backArrow);



        //start your copy pasting here for message details
        name = findViewById(R.id.nameHere);
        deets = findViewById(R.id.detailsHere);

          /*eto yung pang database na implementation
        dito ka mag-start na iimplement para madali lang syang i copy paste
         */
        //main image implementation
        Intent r = getIntent();
        //widgets implementation
        placeName = (TextView) findViewById(R.id.productName);
        detailDesc = (TextView) findViewById(R.id.detailDesc);

        //main image implementation
        imageUrl = r.getExtras().getString("imageUrl");
        Picasso.get().load(imageUrl).into(kbResto);

        //placeName implementation
        place = r.getExtras().getString("placeName");
        placeName.setText(place);
        //details implementation
        detailDesc.setText(r.getExtras().getString("detailsDesc"));
        //mapLink implementation
        mapLink = r.getExtras().getString("mapLink");


        //phone number spinner at button
        ArrayList<String> phoneFirebase = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Leisure").child("Food Hubs").child(place).child("phoneNums");
        Spinner phones = findViewById(R.id.phoneNum);
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<String>(Resto_Activity_Data_Displayer.this, android.R.layout.simple_spinner_dropdown_item, phoneFirebase);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phones.setAdapter(numberAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren()){
                    phoneFirebase.add(item.getValue().toString());
                    numberAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        phones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalNum = numberAdapter.getItem(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Resto_Activity_Data_Displayer.this, finalNum, Toast.LENGTH_SHORT).show();
            }
        });


            //pag pinindot ang book button, maglalabas ng Alert for confirmation, bago i send ang message.
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (name.getText().toString().equals("") || deets.getText().toString().equals("")) {
                        build.setTitle("Data cannot be null");
                        build.setMessage("Name or booking details are empty.\nInput required details and try again");
                        build.show();
                    } else {
                        build.setTitle("SMS Content");
                        build.setMessage("The message will show as follows: \n" + name.getText().toString() + " would like to order to ask about " + inquire + " \nAdditional instructions are as follows: \n" + deets.getText().toString() + "\n\nWould you like to send the message now?");
                        build.setIcon(R.drawable.sms);
                        build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(finalNum,null,name.getText().toString() + " would like to ask about " + inquire + " and interested to stay." + " \nAdditional instructions are as follows: \n" + deets.getText().toString() +"\n\n Sent from CITRA Initiative",null,null);
                                Toast.makeText(Resto_Activity_Data_Displayer.this, "The message has been sent to " + finalNum, Toast.LENGTH_LONG).show();
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
                }
            });

            //kung magseselect ka sa radiobutton, lalabas ang text content
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    id = group.getCheckedRadioButtonId();
                    button = (RadioButton) findViewById(id);
                    inquire = button.getText().toString();
                }
            });


            checkLocation = findViewById(R.id.checkRoute);
            //eto yung sa mapa
            checkLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse(mapLink);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }

                }
            });

            //eto yung back arrow
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Resto_Activity_Data_Displayer.this.finish();
                }
            });

            setImageGallery();

    }

    public void setImageGallery(){
        galleryRecycler = findViewById(R.id.imageGallery);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        galleryRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions galleryQueue = new FirebaseRecyclerOptions.Builder<gallery_display_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Gallery").child("Leisure").child("Food Hubs").child(place).orderByValue(), gallery_display_data.class)
                .build();
        galleryAdapter = new galleryAdapter(Resto_Activity_Data_Displayer.this,galleryQueue);
        galleryRecycler.setAdapter(galleryAdapter);
        galleryAdapter.startListening();

    }

}