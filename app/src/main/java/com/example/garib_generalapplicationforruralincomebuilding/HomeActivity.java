package com.example.garib_generalapplicationforruralincomebuilding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.garib_generalapplicationforruralincomebuilding.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private CardView card1;
    private CardView card2;
    private CardView card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        card1 = findViewById(R.id.cardview1);
        card2 = findViewById(R.id.cardview2);
        card3 = findViewById(R.id.cardview3);

        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_item1) {
                    // Handle Item 1 (Home) click
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item2) {
                    // Handle Item 2 (Jobs) click
                    startActivity(new Intent(HomeActivity.this, JobSearchActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item3) {
                    // Handle Item 3 (Score) click
                    startActivity(new Intent(HomeActivity.this, CreditScoreActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item4) {
                    // Handle Item 4 (Schemes) click
                    startActivity(new Intent(HomeActivity.this, govtSchemesActivity.class));
                    return true;
                }

                return false;
            }
        });


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, JobSearchActivity.class));
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreditScoreActivity.class));
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, govtSchemesActivity.class));
            }
        });
    }
}
