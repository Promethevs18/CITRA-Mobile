package com.example.citrainitiative.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citrainitiative.R;
import com.example.citrainitiative.adapters.news_Adapter;
import com.example.citrainitiative.models.newsFeed_Display_Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.database.FirebaseDatabase;

public class News_Fragment extends Fragment {

    RecyclerView newsRecycler;
    news_Adapter newsAdapter;
    FirebaseRecyclerOptions newsFeedRecycler;
    View view;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_, container, false);

        scrollView = (ScrollView) view.findViewById(R.id.defaultScroll);
        scrollView.setSmoothScrollingEnabled(true);
        setNewsRecycler();
        firstTimeChecker();
        return view;

    }

    private void setNewsRecycler() {
        newsRecycler = (RecyclerView) view.findViewById(R.id.newsFeed);
        RecyclerView.LayoutManager recManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        newsRecycler.setLayoutManager(recManager);
        newsFeedRecycler = new FirebaseRecyclerOptions.Builder<newsFeed_Display_Data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("News Feed").orderByValue(), newsFeed_Display_Data.class)
                .build();
        newsAdapter = new news_Adapter(getContext(), newsFeedRecycler);
        newsRecycler.setAdapter(newsAdapter);
        newsAdapter.startListening();
    }

    private void tapTargetSequence() {
        new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(view.findViewById(R.id.news)
                                        , "News Category", "Updates about Pangantucan are posted here\nClick on a panel to view its information, or swipe down in a window to show more.")
                                .outerCircleColor(android.R.color.holo_orange_dark)
                                .outerCircleAlpha(0.90f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(30)
                                .titleTextColor(R.color.white)
                                .descriptionTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .drawShadow(true)
                                .dimColor(R.color.black)
                                .targetRadius(60)
                                .cancelable(false)
                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(getContext(), "Leisure Tutorial Completed!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();
    }

    private void firstTimeChecker() {
        Boolean ranFirst = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).getBoolean("First run", true);
        if (ranFirst) {
            tapTargetSequence();
        }
        getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit().putBoolean("First run", false).commit();

    }
}