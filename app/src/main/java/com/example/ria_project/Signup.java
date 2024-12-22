package com.example.ria_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    EditText email, password, confirmPassword;
    Button submit;
    FirebaseAuth mAuth;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.signupEmail);
        password = findViewById(R.id.signupPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        submit = findViewById(R.id.signupBtn);
        mAuth = FirebaseAuth.getInstance();
        loginLink = findViewById(R.id.loginRedirect);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailData, passwordData, confirmPasswordData;
                emailData = email.getText().toString().trim();
                passwordData = password.getText().toString().trim();
                confirmPasswordData = confirmPassword.getText().toString().trim();

                // Sign up page validation
                if (emailData.length() == 0) {
                    email.setError("Email is required");
                    return;
                }
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailData).matches()) {
                    email.setError("Invalid email address");
                    return;
                }

                if (passwordData.length() == 0) {
                    password.setError("Password is required");
                    return;
                }
                if (confirmPasswordData.length() == 0) {
                    confirmPassword.setError("Confirm password is required");
                    return;
                }

                // Check if password and confirm password match
                if (!passwordData.equals(confirmPasswordData)) {
                    confirmPassword.setError("Passwords do not match");
                    return;
                }

                // Create user with email and password
                mAuth.createUserWithEmailAndPassword(emailData, passwordData)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Signup.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
