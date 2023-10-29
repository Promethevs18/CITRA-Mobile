package com.example.citrainitiative.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citrainitiative.R;
import com.example.citrainitiative.adapters.landAdapter;
import com.example.citrainitiative.adapters.waterAdapter;
import com.example.citrainitiative.models.display_land_data;
import com.example.citrainitiative.models.display_water_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class Explore_Fragment extends Fragment {

    TextView landID, waterID, guide;
    ViewPager2 locationViewPager;
    landAdapter adapterLand;
    waterAdapter adapterWater;
    ProgressDialog progress;
    Query referenceLand, referenceWater;
    Boolean ranFirst;
    ConstraintLayout buongView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore_, container, false);
        locationViewPager = (ViewPager2) view.findViewById(R.id.locationViewPager);

        landID = view.findViewById(R.id.landMassID);
        waterID = view.findViewById(R.id.waterMassID);
        guide = view.findViewById(R.id.guide);
        progress = new ProgressDialog(getContext());

        referenceLand = FirebaseDatabase.getInstance().getReference().child("Explore").child("Land Masses").orderByValue();
        referenceWater = FirebaseDatabase.getInstance().getReference().child("Explore").child("Water Masses").orderByValue();

        //for tutorial
        firstTimeChecker();

        landID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landID.setTextColor(Color.MAGENTA);
                waterID.setTextColor(Color.BLACK);
                guide.setVisibility(View.INVISIBLE);
                FirebaseRecyclerOptions options =
                        new FirebaseRecyclerOptions.Builder<display_land_data>()
                                .setQuery(referenceLand, display_land_data.class)
                                .build();
                adapterLand = new landAdapter(getContext(), options);
                locationViewPager.setAdapter(adapterLand);
                adapterLand.startListening();


            }
        });

        //Water Masses event handler
        waterID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landID.setTextColor(Color.BLACK);
                waterID.setTextColor(Color.MAGENTA);
                guide.setVisibility(View.INVISIBLE);
                FirebaseRecyclerOptions options =
                        new FirebaseRecyclerOptions.Builder<display_water_data>()
                                .setQuery(referenceWater, display_water_data.class)
                                .build();
                adapterWater = new waterAdapter(getContext(),options);
                locationViewPager.setAdapter(adapterWater);
                adapterWater.startListening();

            }
        });

        //formatter method for locationViewPager
        locationViewPageFormatter();
        return view;
    }

    private void locationViewPageFormatter() {
        locationViewPager.setClipToPadding(false);
        locationViewPager.setClipChildren(false);
        locationViewPager.setOffscreenPageLimit(3);
        locationViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        locationViewPager.setPageTransformer(compositePageTransformer);
    }

    private void tapTargetSequence(){
        new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(landID
                                , "Land Mass","Click to see the mountains and other bodies of land in Pangantucan")
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
                                .cancelable(false),
                        TapTarget.forView(waterID
                                        , "Water Mass","Click to see the falls, lakes, and other bodies of water in Pangantucan")
                                .outerCircleColor(android.R.color.holo_orange_dark)
                                .outerCircleAlpha(0.90f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(30)
                                .titleTextColor(R.color.white)
                                .descriptionTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .drawShadow(true)
                                .dimColor(R.color.black)
                                .tintTarget(true)
                                .targetRadius(60)
                                .cancelable(false)
                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(getContext(), "Explore Tutorial Completed!", Toast.LENGTH_SHORT).show();

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
        ranFirst = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).getBoolean("First run", true);
        if(ranFirst){
            tapTargetSequence();
        }
        getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit().putBoolean("First run", false).commit();

    }

}