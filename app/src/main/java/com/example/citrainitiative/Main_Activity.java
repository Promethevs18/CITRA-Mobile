package com.example.citrainitiative;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.citrainitiative.Fragments.Explore_Fragment;
import com.example.citrainitiative.Fragments.Leisure_Fragment;
import com.example.citrainitiative.Fragments.News_Fragment;
import com.example.citrainitiative.Fragments.Store_Fragment;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Main_Activity extends AppCompatActivity {

    private BottomNavigationView botNav;
    private Fragment fragSelector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botNav = (BottomNavigationView) findViewById(R.id.bottom_navi);

        botNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.newsHome:
                        fragSelector = new News_Fragment();
                        break;
                    case R.id.exploreHome:
                        fragSelector = new Explore_Fragment();

                        break;
                    case R.id.unwindHome:
                        fragSelector = new Leisure_Fragment();
                        break;
                    case R.id.storeHome:
                        fragSelector = new Store_Fragment();
                        break;
                }
                if(fragSelector != null){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                            .replace(R.id.Fragment_container, fragSelector).commit();                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out).replace(R.id.Fragment_container, new News_Fragment()).commit();
    }
}