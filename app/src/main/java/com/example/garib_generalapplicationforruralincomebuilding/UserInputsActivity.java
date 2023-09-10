package com.example.garib_generalapplicationforruralincomebuilding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class UserInputsActivity extends AppCompatActivity {

    private EditText etCity;
    private EditText etAge;
    private RadioGroup rgSex;
    private EditText etPrimaryBusiness;
    private EditText etAnnualIncome;
    private EditText etYoungDependents;
    private Spinner spinnerHomeOwnership;
    private EditText etHouseArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinputs);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize UI elements
        etCity = findViewById(R.id.etCity);
        etAge = findViewById(R.id.etAge);
        rgSex = findViewById(R.id.rgSex);
        etPrimaryBusiness = findViewById(R.id.etPrimaryBusiness);
        etAnnualIncome = findViewById(R.id.etAnnualIncome);
        etYoungDependents = findViewById(R.id.etYoungDependents);
        spinnerHomeOwnership = findViewById(R.id.spinnerHomeOwnership);
        etHouseArea = findViewById(R.id.etHouseArea);
        Button btnSave = findViewById(R.id.btnSave);
        TextView skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInputsActivity.this,HomeActivity.class));
            }
        });
        // Set an OnClickListener for the "Save" button
        btnSave.setOnClickListener(view -> {
            // Get values from input fields
            String city = etCity.getText().toString();
            int age = Integer.parseInt(etAge.getText().toString());

            int selectedSexId = rgSex.getCheckedRadioButtonId();
            RadioButton selectedSexRadioButton = findViewById(selectedSexId);
            String sex = selectedSexRadioButton.getText().toString();

            String primaryBusiness = etPrimaryBusiness.getText().toString();
            double annualIncome = Double.parseDouble(etAnnualIncome.getText().toString());

            String[] homeOwnershipOptions = {"1", "2", "3"};

            // Create an ArrayAdapter to populate the Spinner with values
            ArrayAdapter<String> adapter = new ArrayAdapter<>(UserInputsActivity.this, android.R.layout.simple_spinner_item, homeOwnershipOptions);

            // Specify the layout for the dropdown items (optional)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Set the ArrayAdapter as the Spinner's adapter
            spinnerHomeOwnership.setAdapter(adapter);
            int youngDependents = Integer.parseInt(etYoungDependents.getText().toString());
            String homeOwnership = spinnerHomeOwnership.getSelectedItem().toString();
            String houseArea = etHouseArea.getText().toString();

            // Initialize Firebase and create a new child reference
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference rootReference = firebaseDatabase.getReference();
            DatabaseReference newTableReference = rootReference.child("userinputstable");

// Set the values you want to save
            UserData userData = new UserData(city, age, sex, primaryBusiness,
                    annualIncome, youngDependents, homeOwnership, houseArea);

// Save the data to the new table (child reference)
            newTableReference.push().setValue(userData);

// Show a success message
            Toast.makeText(UserInputsActivity.this, "Your input has been received with gratitude. Thank you!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserInputsActivity.this,HomeActivity.class));
        });
    }
}