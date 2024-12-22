package com.example.ria_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> productList;
    private final CartData cartData;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.cartData = new CartData(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$" + product.getPrice());
        holder.productDescription.setText(product.getDescription());
        int imageResId = context.getResources().getIdentifier(product.getImageResourceName(), "drawable", context.getPackageName());
        holder.productImage.setImageResource(imageResId);
        // product card click
        holder.productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productName", product.getName());
                intent.putExtra("productPrice", product.getPrice());
                intent.putExtra("productDescription", product.getDescription());
                intent.putExtra("productImage", product.getImageResourceName());
                context.startActivity(intent);
            }
        });

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem cartItem = new CartItem(product.getName(), product.getPrice(), 1, product.getImageResourceName());

                // Add the CartItem to the database
                cartData.addItemToCart(cartItem);
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        CardView productCardView;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
            productCardView = itemView.findViewById(R.id.productCardView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
