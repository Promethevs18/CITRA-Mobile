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

import com.example.citrainitiative.R;
import com.example.citrainitiative.adapters.storeAdapter;
import com.example.citrainitiative.models.display_store_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.database.FirebaseDatabase;


public class Store_Fragment extends Fragment {

    RecyclerView storeRecycler;
    storeAdapter storeAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store_, container, false);


        firstTimeChecker();
        setstoreRecycler();
        return view;
    }

    private void setstoreRecycler() {
        storeRecycler = view.findViewById(R.id.productsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        storeRecycler.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions storeQueue = new FirebaseRecyclerOptions.Builder<display_store_data>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Store").orderByValue(), display_store_data.class)
                .build();
        storeAdapter = new storeAdapter(getContext(),storeQueue);
        storeRecycler.setAdapter(storeAdapter);
        storeAdapter.startListening();
    }

    private void tapTargetSequence(){
        new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(view.findViewById(R.id.storeHeader)
                                        , "Store Category","Unique products of Pangantucan are posted here\nClick on a panel to view its information, or swipe down in a window to show more.")
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