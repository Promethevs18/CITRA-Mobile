package com.example.citrainitiative.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.citrainitiative.adapters.hotelAdapter;

import com.example.citrainitiative.R;
import com.example.citrainitiative.adapters.resortAdapter;
import com.example.citrainitiative.adapters.restoAdapter;
import com.example.citrainitiative.models.display_hotel_data;
import com.example.citrainitiative.models.display_resort_data;
import com.example.citrainitiative.models.display_resto_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.database.FirebaseDatabase;


public class Leisure_Fragment extends Fragment {

    RecyclerView hotelRecycler, resortRecycler, restoRecycler;
    hotelAdapter hotelAdapter;
    resortAdapter resortAdapter;
    restoAdapter restoAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_leisure_, container, false);


        sethotelRecycler();
        setrestoRecycler();
        setresortRecycler();

        firstTimeChecker();
        return view;

    }

    private void sethotelRecycler() {
        hotelRecycler = view.findViewById(R.id.productsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        hotelRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions hotelQueue = new FirebaseRecyclerOptions.Builder<display_hotel_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Leisure").child("Hotel").orderByValue(), display_hotel_data.class)
                .build();
        hotelAdapter = new hotelAdapter(getContext(),hotelQueue);
        hotelRecycler.setAdapter(hotelAdapter);
        hotelAdapter.startListening();

    }

    private void setresortRecycler() {
        resortRecycler = view.findViewById(R.id.resortList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        resortRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions resortQueue = new FirebaseRecyclerOptions.Builder<display_resort_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Leisure").child("Resort").orderByValue(), display_resort_data.class)
                .build();
        resortAdapter = new resortAdapter(getContext(),resortQueue);
        resortRecycler.setAdapter(resortAdapter);
        resortAdapter.startListening();

    }

    private void setrestoRecycler() {
        restoRecycler = view.findViewById(R.id.kainList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        restoRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions restoQueue = new FirebaseRecyclerOptions.Builder<display_resto_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Leisure").child("Food Hubs").orderByValue(), display_resto_data.class)
                .build();
        restoAdapter = new restoAdapter(getContext(),restoQueue);
        restoRecycler.setAdapter(restoAdapter);
        restoAdapter.startListening();

    }

    private void tapTargetSequence(){
        new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(view.findViewById(R.id.storeHeader)
                                        , "Leisure Category","Hotels, resorts, and food hubs are listed here.\nClick on a panel to view its information, or swipe left in a window to show more.")
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

    private void firstTimeChecker(){
        Boolean ranFirst = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).getBoolean("First run", true);
        if(ranFirst){
            tapTargetSequence();
        }
        getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit().putBoolean("First run", false).commit();

    }
}