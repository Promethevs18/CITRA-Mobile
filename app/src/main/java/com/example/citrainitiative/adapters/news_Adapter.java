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
import com.example.citrainitiative.models.newsFeed_Display_Data;
import com.example.citrainitiative.news_activity.news_activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class news_Adapter extends FirebaseRecyclerAdapter<newsFeed_Display_Data, news_Adapter.myViewHolder> {

    Context context;
    ProgressDialog progress;
    String clickedNews;
    DatabaseReference newsRef;

    public news_Adapter(Context context, @NonNull FirebaseRecyclerOptions<newsFeed_Display_Data> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull newsFeed_Display_Data model) {
        holder.setData(model);
        newsFeed_Display_Data news_data = getSnapshots().get(holder.getAbsoluteAdapterPosition());
        progress = new ProgressDialog(context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setTitle("Data Fetching");
                progress.setMessage("Data is Fetching, please wait");
                clickedNews = news_data.getHeadLine();
                newsRef = FirebaseDatabase.getInstance().getReference().child("News Feed").child(clickedNews);
                progress.show();

                newsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newsFeed_Display_Data newsFeed_Data = snapshot.getValue(newsFeed_Display_Data.class);
                        Intent news = new Intent(context, news_activity.class);
                        news.putExtra("Headline", newsFeed_Data.getHeadLine());
                        news.putExtra("Date Posted", newsFeed_Data.getDate());
                        news.putExtra("News Details", newsFeed_Data.getFull_details());
                        news.putExtra("News Image", newsFeed_Data.getImageHeadline());
                        context.startActivity(news);
                        progress.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progress.setTitle("Data fetching cancelled");
                        progress.setMessage("Data cannot be fetched due to user cancellation, or the system can't connect to the database.\nTry again later.");
                        progress.setCancelable(true);
                    }
                });

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_container, parent, false));
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView headline, date, shortDetails;
        KenBurnsView news_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.Headline);
            date = itemView.findViewById(R.id.Date_Text);
            shortDetails = itemView.findViewById(R.id.shortDetails);
            news_image = itemView.findViewById(R.id.news_image);
        }

        void setData(newsFeed_Display_Data data) {
            headline.setText(data.getHeadLine());
            date.setText(data.getDate());
            shortDetails.setText(data.getShortDesc());
            Picasso.get().load(data.getImageHeadline()).into(this.news_image);
        }
    }
}
