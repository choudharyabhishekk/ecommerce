package com.example.ria_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private TextView totalShipping, totalTax, totalPrice;
    private CartAdapter cartAdapter;
    private CartData cartData;
    private List<CartItem> cartItemList;
    private Button addItemsBtn, checkoutBtn;

    private static final double TAX_RATE = 0.07; // 7% tax rate
    private static final double SHIPPING_COST = 10.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalShipping = findViewById(R.id.totalShipping);
        totalTax = findViewById(R.id.totalTax);
        totalPrice = findViewById(R.id.totalPrice);
        addItemsBtn = findViewById(R.id.addItemsBtn);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartData = new CartData(this);
        cartItemList = cartData.getCartItemsForCurrentUser(); // Retrieve cart items for the current user
        // add new items in cart
        addItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
        // checkout button
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        cartAdapter = new CartAdapter(this, cartItemList, new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                updateTotals();
            }
        });

        cartRecyclerView.setAdapter(cartAdapter);

        updateTotals();
    }

    private void updateTotals() {
        double subtotal = 0.0;
        for (CartItem item : cartItemList) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double taxes = subtotal * TAX_RATE;
        double total = subtotal + taxes + SHIPPING_COST;

        totalShipping.setText(String.format("Shipping: $%.2f", SHIPPING_COST));
        totalTax.setText(String.format("Taxes: $%.2f", taxes));
        totalPrice.setText(String.format("Estimated Total: $%.2f", total));
    }
}
