package com.example.citrainitiative.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.leisureactivity.Hotel_Activity_Data_Displayer;
import com.example.citrainitiative.models.General_Firebase_Data_Getter_and_Setter;
import com.example.citrainitiative.models.display_hotel_data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class hotelAdapter extends FirebaseRecyclerAdapter<display_hotel_data, hotelAdapter.myViewHolder> {

    Context context;
    String clickedPlace;
    DatabaseReference refHotel;
    ProgressDialog pd;

    public hotelAdapter(@NonNull Context context, FirebaseRecyclerOptions<display_hotel_data> options) {
        super(options);
        this.context = context;
    }

    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull display_hotel_data model) {
        holder.setData(model);
        display_hotel_data hotel = getSnapshots().get(holder.getAbsoluteAdapterPosition());
        pd = new ProgressDialog(context);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedPlace = hotel.getPlace();
                Toast.makeText(context, clickedPlace, Toast.LENGTH_SHORT).show();
                refHotel = FirebaseDatabase.getInstance().getReference().child("Leisure").child("Hotel").child(clickedPlace);
                pd.setTitle("Data Fetching");
                pd.setMessage("Data is fetching, please wait");
                pd.show();
                refHotel.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        General_Firebase_Data_Getter_and_Setter general = snapshot.getValue(General_Firebase_Data_Getter_and_Setter.class);
                        if (general != null) {
                            Intent a = new Intent(context, Hotel_Activity_Data_Displayer.class);
                            a.putExtra("placeName", general.getPlace());
                            a.putExtra("detailsDesc", general.getDetails());
                            a.putExtra("imageUrl", general.getImageUrl());
                            a.putExtra("mapLink", general.getGoogleMapLink());
                            context.startActivity(a);
                            pd.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pd.setTitle("Data fetching cancelled");
                        pd.setMessage("Data cannot be fetched due to user cancellation, or the system can't connect to the database.\nTry again later.");
                        pd.setCancelable(true);
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.a_leisure_container, parent, false));
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageLugar;
        TextView placeName, placePrice, shortDesc;

        ConstraintLayout parent;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.contentView);
            imageLugar = itemView.findViewById(R.id.imageLugar);
            placeName = itemView.findViewById(R.id.itemName);
            placePrice = itemView.findViewById(R.id.lugarPrice);
            shortDesc = itemView.findViewById(R.id.shortDesc);
        }

        void setData(display_hotel_data data) {

            Picasso.get().load(data.getImageUrl()).into(imageLugar);
            placeName.setText(data.getPlace());
            placePrice.setText(data.getstartPrice());
            shortDesc.setText(data.getShortDesc());

        }
    }


}
