package com.example.citrainitiative.adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.lugaractivity.ExplorePangantucan_Data_Displayer;
import com.example.citrainitiative.models.General_Firebase_Data_Getter_and_Setter;
import com.example.citrainitiative.models.display_water_data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class waterAdapter extends FirebaseRecyclerAdapter<display_water_data, waterAdapter.myViewHolder> {

    Context context;
    String clickedPlace;
    DatabaseReference refWater;
    ProgressDialog pd;

    public waterAdapter(@NonNull Context context, FirebaseRecyclerOptions<display_water_data> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull display_water_data model) {
        holder.setData(model);

        display_water_data water = getSnapshots().get(holder.getAbsoluteAdapterPosition());
        pd = new ProgressDialog(context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPlace = water.getPlace();
                refWater = FirebaseDatabase.getInstance().getReference().child("Explore").child("Water Masses").child(clickedPlace);
                pd.setTitle("Data Fetching");
                pd.setMessage("Data is fetching, please wait");
                pd.show();
                refWater.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        General_Firebase_Data_Getter_and_Setter kal = snapshot.getValue(General_Firebase_Data_Getter_and_Setter.class);
                        if (kal != null) {
                            Intent x = new Intent(context, ExplorePangantucan_Data_Displayer.class);
                            x.putExtra("placeName", kal.getPlace());
                            x.putExtra("locatedWithin", kal.getlocatedWithin());
                            x.putExtra("goodFor", kal.getGoodFor());
                            x.putExtra("details", kal.getDetails());
                            x.putExtra("imageUrl", kal.getImageUrl());
                            x.putExtra("mapLink", kal.getGoogleMapLink());
                            x.putExtra("category", "Water Masses");
                            context.startActivity(x);
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
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.b_locations_container, parent, false));
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        KenBurnsView kblocation;
        TextView placeName, baranggayName;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            kblocation = itemView.findViewById(R.id.kbvLocation);
            placeName = itemView.findViewById(R.id.itemName);
            baranggayName = itemView.findViewById(R.id.baranggayName);

        }

        void setData(display_water_data data) {
            Picasso.get().load(data.getImageUrl()).into(kblocation);
            placeName.setText(data.getPlace());
            baranggayName.setText(data.getlocatedWithin());


        }
    }
}
