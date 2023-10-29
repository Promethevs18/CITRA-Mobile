package com.example.citrainitiative.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.models.gallery_display_data;
import com.example.citrainitiative.zoomed_image;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class galleryAdapter extends FirebaseRecyclerAdapter<gallery_display_data, galleryAdapter.myViewHolder> {

    Context context;
    int shortAnimationDuration;

    public galleryAdapter(Context context, @NonNull FirebaseRecyclerOptions<gallery_display_data> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull gallery_display_data model) {
        holder.setData(model);
        gallery_display_data gal = getSnapshots().get(holder.getAbsoluteAdapterPosition());
        String clickedImage = gal.getImagePiece();

        holder.imahe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, zoomed_image.class);
                in.putExtra("Image Link", clickedImage);
                context.startActivity(in);

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_format, parent, false));
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageButton imahe;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imahe = itemView.findViewById(R.id.imageDisplayer);
        }

        void setData(gallery_display_data data) {
            Picasso.get().load(data.getImagePiece()).into(imahe);

        }
    }
}
