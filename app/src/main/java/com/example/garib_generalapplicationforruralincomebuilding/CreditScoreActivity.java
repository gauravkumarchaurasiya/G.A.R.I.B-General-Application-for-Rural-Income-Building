
package com.example.garib_generalapplicationforruralincomebuilding;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.Random;

public class CreditScoreActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView creditScoreTextView;
    private Interpreter tflite;
    private ImageView needle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score);
        // Initialize Firebase Realtime Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("userinputstable");

        // Initialize TensorFlow Lite Interpreter
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize UI elements
        Button refresh = findViewById(R.id.refreshbtn);
        creditScoreTextView = findViewById(R.id.creditScoreTextView);
        ImageView needle = findViewById(R.id.needle);

        // Get the target credit score (e.g., 750)
        int targetCreditScore;
        // Retrieve and display user data from Firebase Realtime Database

        targetCreditScore = (int) retrieveUserDataFromDatabase();
//        Toast.makeText(this, "target"+targetCreditScore, Toast.LENGTH_SHORT).show();
        // Create a ValueAnimator to animate the credit score text changed
        // Use int values for animation
        int initialCreditScore = 0;
        ValueAnimator animator = ValueAnimator.ofInt(initialCreditScore, targetCreditScore);
        animator.setDuration(1000);  // Animation duration in milliseconds
        animator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            creditScoreTextView.setText(String.valueOf(animatedValue));
        });
        animator.start();

        // Rotate the meter image based on the credit score
        double rotationDegree = calculateRotation(targetCreditScore);
        needle.setRotation((float) rotationDegree);

        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_item3);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_item1) {
                // Handle Item 1 (Home) click
                startActivity(new Intent(CreditScoreActivity.this, HomeActivity.class));
            } else if (itemId == R.id.menu_item2) {
                // Handle Item 2 (Jobs) click
                startActivity(new Intent(CreditScoreActivity.this, JobSearchActivity.class));
            } else if (itemId == R.id.menu_item3) {
                // Handle Item 3 (Score) click (current activity)
            } else if (itemId == R.id.menu_item4) {
                // Handle Item 4 (Schemes) click
                startActivity(new Intent(CreditScoreActivity.this, govtSchemesActivity.class));
            }
            return true;
        });

        // Set a click listener for the refresh button
        refresh.setOnClickListener(view -> {
            // Restart the current activity
            Intent intent = getIntent();
            finish(); // Close the current activity
            startActivity(intent); // Start the activity again
        });



    }

    private double calculateRotation(double creditScore) {
        // Calculate the rotation degree based on the credit score as before
        float maxRotation = 180.0f; // Maximum rotation in degrees
        float minCreditScore = 300; // Minimum possible credit score
        float maxCreditScore = 850; // Maximum possible credit score

        // Calculate the percentage of credit score within the range
        double percentage = (creditScore - minCreditScore) / (maxCreditScore - minCreditScore);

        // Calculate the rotation angle based on the percentage
        double rotation = maxRotation * percentage;

        // Adjust the rotation to make 300 points to the west (left)
        return rotation - 90.0f;
    }

    private float retrieveUserDataFromDatabase() {

        final float[] creditScoreResult = {0};
        // Listen for changes in the database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Query to get the last item in the "userinputstable"
                Query lastItemQuery = databaseReference.orderByKey().limitToLast(1);

                // Listen for changes in the query result
                lastItemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Get the last user input
                            DataSnapshot lastUserInputSnapshot = dataSnapshot.getChildren().iterator().next();

                            // Retrieve and display user data
                            Integer age = lastUserInputSnapshot.child("age").getValue(Integer.class);
                            Integer annualIncome = lastUserInputSnapshot.child("annualIncome").getValue(Integer.class);
                            String city = lastUserInputSnapshot.child("city").getValue(String.class);
                            String homeOwnership = lastUserInputSnapshot.child("homeOwnership").getValue(String.class);
                            String houseArea = lastUserInputSnapshot.child("houseArea").getValue(String.class);
                            String primaryBusiness = lastUserInputSnapshot.child("primaryBusiness").getValue(String.class);
                            String sex = lastUserInputSnapshot.child("sex").getValue(String.class);
                            Integer youngDependents = lastUserInputSnapshot.child("youngDependents").getValue(Integer.class);

                            // Display the last user's data using Toast messages
                           /* Toast.makeText(CreditScoreActivity.this, "User Age: " + age, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User Annual Income: " + annualIncome, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User City: " + city, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User Home Ownership: " + homeOwnership, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User House Area: " + houseArea, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User Primary Business: " + primaryBusiness, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User Sex: " + sex, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreditScoreActivity.this, "User Young Dependents: " + youngDependents, Toast.LENGTH_SHORT).show();

                            */
//                            Toast.makeText(CreditScoreActivity.this, "User HOme ownership: " + Float.parseFloat(homeOwnership), Toast.LENGTH_SHORT).show();
                            int inputSex = "male".equals(sex) ? 1 : 0;
                            // Perform inference with retrieved data
                            assert houseArea != null;
                            assert homeOwnership != null;
                            float[] inputValues = new float[]{age, inputSex, annualIncome, youngDependents, Float.parseFloat(homeOwnership), Float.parseFloat(houseArea)};
//                            Toast.makeText(CreditScoreActivity.this, "inputfields"+inputValues, Toast.LENGTH_SHORT).show();
                            Random random = new Random();

                            // Generate a random integer between 1 and 100 (inclusive)
                            int rInt = random.nextInt(401) + 400;

                            creditScoreResult[0] = performInference(inputValues) + rInt;
//                            Toast.makeText(CreditScoreActivity.this, "credit"+creditScoreResult[0], Toast.LENGTH_SHORT).show();
                            // Display the credit score result in the TextView
//                            creditScoreTextView.setText(String.valueOf(creditScoreResult));
                            float targetCreditScore;
                            targetCreditScore = creditScoreResult[0];
                            int initialCreditScore = 0;
                            ValueAnimator animator = ValueAnimator.ofInt(initialCreditScore, (int)targetCreditScore);
                            animator.setDuration(1000);  // Animation duration in milliseconds
                            animator.addUpdateListener(animation -> {
                                int animatedValue = (int) animation.getAnimatedValue();
                                creditScoreTextView.setText(String.valueOf(animatedValue));
                            });
                            animator.start();

                            // Rotate the meter image based on the credit score
                            double rotationDegree = calculateRotation(targetCreditScore);
                            needle = findViewById(R.id.needle);
                            needle.setRotation((float) rotationDegree);
                        } else {
                            // No data exists under "userinputstable"
                            Toast.makeText(CreditScoreActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(CreditScoreActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(CreditScoreActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Toast.makeText(this, "score"+creditScoreResult[0], Toast.LENGTH_SHORT).show();
        return creditScoreResult[0];
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("regression_model.tflite"); // Replace with your model's file name
        int modelSize = inputStream.available();
        byte[] modelBuffer = new byte[modelSize];
        inputStream.read(modelBuffer);
        ByteBuffer buffer = ByteBuffer.allocateDirect(modelSize)
                .order(ByteOrder.nativeOrder());
        buffer.put(modelBuffer);
        buffer.flip(); // Prepare the buffer for reading
        return (MappedByteBuffer) buffer;
    }

    private float performInference(float[] inputValues) {
        try {
            // Initialize output array
            float[][] outputValues = new float[1][1]; // One output for regression

            // Perform inference
            tflite.run(inputValues, outputValues);

            // Check if the output array contains valid results
            if (outputValues[0].length > 0) {
                return outputValues[0][0];
            } else {
                // Handle the case where the output is invalid
                Toast.makeText(CreditScoreActivity.this, "Invalid model output", Toast.LENGTH_SHORT).show();
                return 0.0f; // Return a default value or handle the error accordingly
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during inferencec

            Toast.makeText(CreditScoreActivity.this, " inference delaying model: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

            return 0.0f; // Return a default value or handle the error accordingly
        }
    }

}