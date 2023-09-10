package com.example.garib_generalapplicationforruralincomebuilding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText fullname;
    private EditText email;
    private EditText mobile;
    private EditText password;
    private EditText confirmpassword;
    private Button register;
    private TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        register = findViewById(R.id.register);
        signin = findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname1 = fullname.getText().toString();
                String email1 = email.getText().toString();
                String mobile1 = mobile.getText().toString();
                String password1 = password.getText().toString();
                String confirmpassword1 = confirmpassword.getText().toString();
                if (fullname1.length() == 0 || email1.length() == 0 || mobile1.length() == 0 || password1.length() == 0 || confirmpassword1.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email1)) {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (!isValidMobile(mobile1)) {
                    Toast.makeText(getApplicationContext(), "Invalid mobile number", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password1)) {
                    Toast.makeText(getApplicationContext(), "Invalid password. Password should be at least 8 characters long and meet other criteria.", Toast.LENGTH_SHORT).show();
                } else if (!password1.equals(confirmpassword1)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Registration is successful
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();

                                        // Start the LoginActivity
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish(); // Finish the current activity
                                    } else {
                                        // Registration failed
                                        Toast.makeText(getApplicationContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String mobile) {
        return mobile.length() == 10 && mobile.matches("\\d{10}");
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches(".*[@#$%^&+=!].*")) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one special character (@#$%^&+=!)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
