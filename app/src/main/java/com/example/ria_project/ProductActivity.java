package com.example.ria_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView productsRV;
    private ProductAdapter productAdapter;
    private ProductData productData;
    private TextView greetingTextView;
    private Button logoutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productsRV = findViewById(R.id.productsRV);
        greetingTextView = findViewById(R.id.greetingTextView);
        logoutButton = findViewById(R.id.logoutButton);
        productsRV.setLayoutManager(new LinearLayoutManager(this));
        productData = new ProductData(this);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            String userEmail = mAuth.getCurrentUser().getEmail();
            greetingTextView.setText("Hello, " + (userEmail != null ? userEmail : "User"));
        }

        // Insert dummy data
        productData.insertInitialData();
        fetchAndDisplayProducts();

        // Set up logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                mAuth.signOut();

                // Redirect to login activity or other appropriate activity
                Intent intent = new Intent(ProductActivity.this, Login.class);
                startActivity(intent);
                finish(); // Finish this activity so user can't go back to it
            }
        });
    }

    private void fetchAndDisplayProducts() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Product> productList = productData.getAllProducts();

                // Update UI on the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        productAdapter = new ProductAdapter(ProductActivity.this, productList);
                        productsRV.setAdapter(productAdapter);
                    }
                });
            }
        });
    }
}