package com.example.garib_generalapplicationforruralincomebuilding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class govtSchemesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CSVDataAdapter adapter;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_schemes);

        // Initialize the BottomNavigationView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_item4);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_item1) {
                    // Handle Item 1 (Home) click
                    startActivity(new Intent(govtSchemesActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item2) {
                    // Handle Item 2 (Jobs) click
                    startActivity(new Intent(govtSchemesActivity.this, JobSearchActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item3) {
                    // Handle Item 3 (Score) click
                    startActivity(new Intent(govtSchemesActivity.this, CreditScoreActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item4) {
                    // Handle Item 4 (Schemes) click
                    startActivity(new Intent(govtSchemesActivity.this, govtSchemesActivity.class));
                    return true;
                }

                return false;
            }
        });


        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.schemesListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the Search button
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the "Search" button click event here
                List<String[]> csvData = readCSVFile(R.raw.nationalgovernmentservicesportal);

                if (csvData != null && csvData.size() > 0) {
                    // Create an adapter and set it to the RecyclerView
                    adapter = new CSVDataAdapter(govtSchemesActivity.this, csvData);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(govtSchemesActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Implement the method to read and parse your CSV file here
    private List<String[]> readCSVFile(int resourceId) {
        List<String[]> data = new ArrayList<>();

        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(inputStreamReader);

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                // Add each row to the data list
                data.add(nextLine);
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}
