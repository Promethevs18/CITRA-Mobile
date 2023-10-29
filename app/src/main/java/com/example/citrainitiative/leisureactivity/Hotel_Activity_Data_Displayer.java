package com.example.citrainitiative.leisureactivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.example.citrainitiative.models.gallery_display_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Hotel_Activity_Data_Displayer extends AppCompatActivity {

    ImageView back;
    KenBurnsView kbMain;
    ConstraintLayout backImage;
    RadioGroup radioGroup;
    Button checkLocation, dateSetter;
    ConstraintLayout buongCost;
    Button book;
    TextView bookClass, dash, bookCost, mult, dayStay, totalCost, costAround;
    int total;
    int cost = 0;
    int days;

    public TextView name, deets;
    public String finalNum,  selectedDate;
    public String msg;
    DatePickerDialog datePickerDialog;
    String mapLink, imageUrl, place;
    TextView placeName, detailDesc;

    RecyclerView galleryRecycler;
    galleryAdapter galleryAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leisure_hotel_interface);
        AlertDialog.Builder build = new AlertDialog.Builder(Hotel_Activity_Data_Displayer.this);

        Intent a = getIntent();
        Spinner daySpinner = findViewById(R.id.daysList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dayStay, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        bookClass = findViewById(R.id.bookClass);
        dash = findViewById(R.id.dash);
        bookCost = findViewById(R.id.bookCost);
        mult = findViewById(R.id.mult);
        dayStay = findViewById(R.id.dayStay);
        totalCost = findViewById(R.id.totalCost);
        costAround = findViewById(R.id.costAround);
        buongCost = findViewById(R.id.buongCost);

        book = findViewById(R.id.book);
        radioGroup = findViewById(R.id.classes);
        kbMain = findViewById(R.id.imageMain);
        back = findViewById(R.id.backArrow);
        name = findViewById(R.id.nameHere);
        deets = findViewById(R.id.detailsHere);
        placeName = (TextView) findViewById(R.id.productName);
        detailDesc = (TextView) findViewById(R.id.detailDesc);

        //intents implementation
        imageUrl = a.getExtras().getString("imageUrl");
        mapLink = a.getExtras().getString("mapLink");

        place = a.getExtras().getString("placeName");
        placeName.setText(place);
        detailDesc.setText(a.getExtras().getString("detailsDesc"));
        Picasso.get().load(imageUrl).into(kbMain);


        //calendar
        dateSetter = (Button) findViewById(R.id.bookingDate);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //calendar button
        dateSetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(
                        Hotel_Activity_Data_Displayer.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                               selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
                                Toast.makeText(Hotel_Activity_Data_Displayer.this, selectedDate, Toast.LENGTH_SHORT).show();

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        //phone number spinner at button

        ArrayList<String> phoneFirebase = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Leisure").child("Hotel").child(place).child("phoneNums");
        Spinner phones = findViewById(R.id.phoneNum);
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<String>(Hotel_Activity_Data_Displayer.this, android.R.layout.simple_spinner_dropdown_item, phoneFirebase);
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
                Toast.makeText(Hotel_Activity_Data_Displayer.this, finalNum, Toast.LENGTH_SHORT).show();
            }
        });


        //pag pinindot ang book button, maglalabas ng Alert for confirmation, bago i send ang message.
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || deets.getText().toString().equals("")){
                    build.setTitle("Data cannot be null");
                    build.setMessage("Name or booking details are empty.\nInput required details and try again");
                    build.show();
                }
                else {
                    build.setTitle("SMS Content");
                    build.setMessage("The message will show as follows: \n" + name.getText().toString() + " would like to ask about " + bookClass.getText().toString() + " and interested to stay from " + selectedDate +" for "+ days + " day/s." + " \nAdditional instructions are as follows: \n" + deets.getText().toString() + "\n\nWould you like to send the message now?\n\n"+ finalNum);
                    build.setIcon(R.drawable.sms);
                    build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(finalNum,null,name.getText().toString() + " would like to ask about " + bookClass.getText().toString() + " and interested to stay for " + days + " day/s." + " \nAdditional instructions are as follows: \n" + deets.getText().toString() +"\n\n Sent from CITRA Initiative",null,null);
                            Toast.makeText(Hotel_Activity_Data_Displayer.this, "The message has been sent to " + finalNum, Toast.LENGTH_LONG).show();
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

        //days stay spinner
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        days = 1;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();

                        break;
                    case 2:
                        days = 2;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 3:
                        days = 3;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 4:
                        days = 4;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 5:
                        days = 5;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 6:
                        days = 6;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 7:
                        days = 7;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 8:
                        days = 8;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 9:
                        days = 9;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case 10:
                        days = 10;
                        dayStay.setVisibility(View.VISIBLE);
                        dayStay.setText(days + " days");
                        costAround.setVisibility(View.VISIBLE);
                        calc();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayStay.setVisibility(View.INVISIBLE);
                costAround.setVisibility(View.INVISIBLE);
                totalCost.setVisibility(View.INVISIBLE);
            }
        });

        //room number counter
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.room1:
                        bookClass.setVisibility(View.VISIBLE);
                        bookClass.setText("Room 1");
                        bookCost.setVisibility(View.VISIBLE);
                        bookCost.setText("450 pesos");
                        cost = 450;
                        dash.setVisibility(View.VISIBLE);
                        mult.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case R.id.room2:
                        bookClass.setVisibility(View.VISIBLE);
                        bookClass.setText("Room 2");
                        bookCost.setVisibility(View.VISIBLE);
                        bookCost.setText("550 pesos");
                        cost = 550;
                        dash.setVisibility(View.VISIBLE);
                        mult.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case R.id.room3:
                        bookClass.setVisibility(View.VISIBLE);
                        bookClass.setText("Room 3");
                        bookCost.setVisibility(View.VISIBLE);
                        bookCost.setText("650 pesos");
                        cost = 650;
                        dash.setVisibility(View.VISIBLE);
                        mult.setVisibility(View.VISIBLE);
                        calc();
                        break;
                    case R.id.room4:
                        bookClass.setVisibility(View.VISIBLE);
                        bookClass.setText("Room 4");
                        bookCost.setVisibility(View.VISIBLE);
                        bookCost.setText("750 pesos");
                        cost = 750;
                        dash.setVisibility(View.VISIBLE);
                        mult.setVisibility(View.VISIBLE);
                        calc();
                        break;

                    default:
                        Toast.makeText(Hotel_Activity_Data_Displayer.this, "Inputs are invalid. Try again", Toast.LENGTH_LONG).show();
                        break;
                }
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
                Hotel_Activity_Data_Displayer.this.finish();
            }
        });

        //gallery shower
        setImageGallery();

    }
    public void calc() {
        total = cost * days;
        totalCost.setText(total + " pesos");
        totalCost.setVisibility(View.VISIBLE);

    }

    public void setImageGallery(){
        galleryRecycler = findViewById(R.id.imageGallery);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        galleryRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions galleryQueue = new FirebaseRecyclerOptions.Builder<gallery_display_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Gallery").child("Leisure").child("Hotel").child(place).orderByValue(), gallery_display_data.class)
                .build();
        galleryAdapter = new galleryAdapter(Hotel_Activity_Data_Displayer.this,galleryQueue);
        galleryRecycler.setAdapter(galleryAdapter);
        galleryAdapter.startListening();

    }
}