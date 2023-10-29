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
import com.example.citrainitiative.models.General_Firebase_Data_Getter_and_Setter;
import com.example.citrainitiative.models.display_store_data;
import com.example.citrainitiative.storeactivity.Store_Activity_Data_Displayer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class storeAdapter extends FirebaseRecyclerAdapter<display_store_data, storeAdapter.myViewHolder> {

    Context context;
    String clickedPlace;
    DatabaseReference refstore;
    ProgressDialog pd;


    public storeAdapter(@NonNull Context context, FirebaseRecyclerOptions<display_store_data> options) {
        super(options);
        this.context = context;
    }

    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull display_store_data model) {
        holder.setData(model);
        display_store_data store = getSnapshots().get(holder.getAbsoluteAdapterPosition());
        pd = new ProgressDialog(context);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(context, Store_Activity_Data_Displayer.class);
                clickedPlace = store.getproductName();
                refstore = FirebaseDatabase.getInstance().getReference().child("Store").child(clickedPlace);
                pd.setTitle("Data Fetching");
                pd.setMessage("Data is fetching, please wait");
                pd.show();
                refstore.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        General_Firebase_Data_Getter_and_Setter general = snapshot.getValue(General_Firebase_Data_Getter_and_Setter.class);
                        if (general != null) {
                            s.putExtra("productName", general.getProductName());
                            s.putExtra("imageUrl", general.getImageHeadline());
                            context.startActivity(s);
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
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.c_store_container, parent, false));

    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageLugar;
        TextView placeName, placePrice, shortDesc;

        ConstraintLayout parent;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.buongLayout);
            imageLugar = itemView.findViewById(R.id.imageLugar);
            placeName = itemView.findViewById(R.id.itemName);
            placePrice = itemView.findViewById(R.id.itemPrice);
            shortDesc = itemView.findViewById(R.id.shortDesc);
        }


        void setData(display_store_data data) {

            Picasso.get().load(data.getimageHeadline()).into(imageLugar);
            placeName.setText(data.getproductName());
            placePrice.setText("₱" + data.getstartPrice());
            shortDesc.setText(data.getShortDesc());


        }
    }


}
