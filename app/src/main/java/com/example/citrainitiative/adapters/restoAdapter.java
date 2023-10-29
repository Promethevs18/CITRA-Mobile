package com.example.citrainitiative.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.leisureactivity.Resto_Activity_Data_Displayer;
import com.example.citrainitiative.models.General_Firebase_Data_Getter_and_Setter;
import com.example.citrainitiative.models.display_resto_data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class restoAdapter extends FirebaseRecyclerAdapter<display_resto_data, restoAdapter.myViewHolder> {

    Context context;
    String clickedPlace;
    DatabaseReference refResto;
    ProgressDialog pd;


    public restoAdapter(@NonNull Context context, FirebaseRecyclerOptions<display_resto_data> options) {
        super(options);
        this.context = context;
    }

    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull display_resto_data model) {
        holder.setData(model);
        display_resto_data resto = getSnapshots().get(holder.getAbsoluteAdapterPosition());
        pd = new ProgressDialog(context);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(context, Resto_Activity_Data_Displayer.class);
                clickedPlace = resto.getPlace();
                refResto = FirebaseDatabase.getInstance().getReference().child("Leisure").child("Food Hubs").child(clickedPlace);
                pd.setTitle("Data Fetching");
                pd.setMessage("Data is fetching, please wait");
                pd.show();
                refResto.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        General_Firebase_Data_Getter_and_Setter general = snapshot.getValue(General_Firebase_Data_Getter_and_Setter.class);
                        if (general != null) {
                            b.putExtra("placeName", general.getPlace());
                            b.putExtra("detailsDesc", general.getDetails());
                            b.putExtra("imageUrl", general.getImageUrl());
                            b.putExtra("mapLink", general.getGoogleMapLink());
                            context.startActivity(b);
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

    public static class myViewHolder extends RecyclerView.ViewHolder {

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

        void setData(display_resto_data data) {

            Picasso.get().load(data.getImageUrl()).into(imageLugar);
            placeName.setText(data.getPlace());
            placePrice.setText(data.getstartPrice());
            shortDesc.setText(data.getShortDesc());

        }
    }


}
