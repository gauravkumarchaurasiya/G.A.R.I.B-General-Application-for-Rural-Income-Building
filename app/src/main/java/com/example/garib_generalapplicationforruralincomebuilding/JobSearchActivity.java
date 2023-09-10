package com.example.garib_generalapplicationforruralincomebuilding;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobSearchActivity extends AppCompatActivity {

    private RecyclerView jobListRecyclerView;
    private EditText jobQueryEditText;
    private Button searchButton;
    private Spinner filterSpinner;
    private JobListAdapter jobListAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        // Initialize the BottomNavigationView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_item2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_item1) {
                    // Handle Item 1 (Home) click
                    startActivity(new Intent(JobSearchActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item2) {
                    // Handle Item 2 (Jobs) click
                    startActivity(new Intent(JobSearchActivity.this, JobSearchActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item3) {
                    // Handle Item 3 (Score) click
                    startActivity(new Intent(JobSearchActivity.this, CreditScoreActivity.class));
                    return true;
                } else if (itemId == R.id.menu_item4) {
                    // Handle Item 4 (Schemes) click
                    startActivity(new Intent(JobSearchActivity.this, govtSchemesActivity.class));
                    return true;
                }

                return false;
            }
        });


        // Initialize UI elements
        jobListRecyclerView = findViewById(R.id.jobListRecyclerView);
        jobQueryEditText = findViewById(R.id.jobQueryEditText);
        searchButton = findViewById(R.id.searchButton);
        filterSpinner = findViewById(R.id.filterSpinner);

        // Initialize RecyclerView adapter
        jobListAdapter = new JobListAdapter();
        jobListRecyclerView.setAdapter(jobListAdapter);
        jobListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up Firebase reference to your Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference(); // Reference to the root of your Firebase Realtime Database

        // Set up the Spinner with filter options (you can customize this)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.job_filters_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        // Set up search functionality
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = jobQueryEditText.getText().toString();
                String filter = filterSpinner.getSelectedItem().toString();
                fetchDataFromFirebase(query, filter);
            }
        });
    }

    // Fetch data from Firebase without any filter
    private void fetchDataFromFirebase(String query, String filter) {
        // Query Firebase Realtime Database without any filter
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Job> fetchedJobs = new ArrayList<>();

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null) {
                        fetchedJobs.add(job);
                    }
                }

                // Update the RecyclerView adapter with the fetched data
                jobListAdapter.setJobs(fetchedJobs);
                Toast.makeText(JobSearchActivity.this, "Data fetched: " + fetchedJobs.size() + " items", Toast.LENGTH_SHORT).show();
            }


//    private void fetchDataFromFirebase(String query, String filter) {
//        // Query Firebase Realtime Database based on search query and filter
//        DatabaseReference jobsRef = databaseReference.child("0/Role");
//        Toast.makeText(this, "jobsRef"+ jobsRef, Toast.LENGTH_SHORT).show();
//        Query queryRef;
//        if (filter.equals("All")) {
//            queryRef = jobsRef;
//        Toast.makeText(this, "queryRef1"+ queryRef, Toast.LENGTH_SHORT).show();
//        } else {
//            queryRef = jobsRef.orderByChild("Role").equalTo(filter);
//        Toast.makeText(this, "query Ref 2 "+ queryRef, Toast.LENGTH_SHORT).show();
//        }
//
//        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Job> fetchedJobs = new ArrayList<>();
//
//                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
//                    Job job = jobSnapshot.getValue(Job.class);
//                    if (job != null) {
//                        fetchedJobs.add(job);
//                    }
//                }
//
//                // Update the RecyclerView adapter with the fetched data
//                jobListAdapter.setJobs(fetchedJobs);
//                Toast.makeText(JobSearchActivity.this, "Data fetched: " + fetchedJobs.size() + " items", Toast.LENGTH_SHORT).show();
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here with more details
                String errorMessage = databaseError.getMessage();
                Toast.makeText(JobSearchActivity.this, "Data fetch failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
