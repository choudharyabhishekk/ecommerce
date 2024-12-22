package com.example.ria_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImg, backBtn, cartBtn;
    private TextView name, price, description;
    private Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialize views
        productImg = findViewById(R.id.productImg);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        buyBtn = findViewById(R.id.buyBtn);
        backBtn = findViewById(R.id.backBtn);
        cartBtn = findViewById(R.id.cartBtnDetails);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        double productPrice = intent.getDoubleExtra("productPrice", 0.0);
        String productDescription = intent.getStringExtra("productDescription");
        String productImage = intent.getStringExtra("productImage");

        // Set the data to the views
        name.setText(productName);
        price.setText(String.format("$%.2f", productPrice));
        description.setText(productDescription);

        int imageResId = getResources().getIdentifier(productImage, "drawable", getPackageName());
        productImg.setImageResource(imageResId);

        // Back button onclick listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // cart button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the "Add to Cart" button
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new CartItem with the product details
                CartItem cartItem = new CartItem(productName, productPrice, 1, productImage);

                // Add the CartItem to the database
                CartData cartData = new CartData(ProductDetailsActivity.this);
                cartData.addItemToCart(cartItem);
                Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                // redirect to cart page
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });
    }
}
