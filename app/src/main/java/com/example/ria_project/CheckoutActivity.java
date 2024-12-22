package com.example.ria_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class CheckoutActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText creditCardEditText;
    private EditText cvvEditText;
    private Button checkoutButton;
    private CartData cartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameEditText = findViewById(R.id.nameEditText);
        creditCardEditText = findViewById(R.id.cardNumberEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        checkoutButton = findViewById(R.id.paymentButton);
        cartData = new CartData(this);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    cartData.clearCart();
                    Intent intent = new Intent(CheckoutActivity.this, ThankyouActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInputs() {
        String name = nameEditText.getText().toString().trim();
        String creditCard = creditCardEditText.getText().toString().trim();
        String cvv = cvvEditText.getText().toString().trim();

        if (name.isEmpty()) {
            showToast("Name is required");
            return false;
        }


        if (creditCard.length() != 16) {
            showToast("Credit card number must be 16 digits");
            return false;
        }

        if (cvv.length() != 3) {
            showToast("CVV must be 3 digits");
            return false;
        }

        return true;
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
