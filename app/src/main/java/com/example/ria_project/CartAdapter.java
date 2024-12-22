package com.example.ria_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<CartItem> cartItemList;
    private final OnQuantityChangeListener quantityChangeListener;
    private final CartData cartData;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(Context context, List<CartItem> cartItemList, OnQuantityChangeListener listener) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.quantityChangeListener = listener;
        this.cartData = new CartData(context);
    }



    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the correct layout for individual cart items
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);

        holder.cartName.setText(item.getName());
        holder.cartPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.cartQuantity.setText(String.valueOf(item.getQuantity()));

        int imageResId = context.getResources().getIdentifier(item.getImageUrl(), "drawable", context.getPackageName());
        holder.cartImage.setImageResource(imageResId);

        holder.deleteButton.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                // Decrease quantity if it's more than 1
                item.setQuantity(item.getQuantity() - 1);
                holder.cartQuantity.setText(String.valueOf(item.getQuantity()));
                quantityChangeListener.onQuantityChanged();
            } else {
                // Remove item from cart if quantity is 1
                cartData.removeItemFromCart(item);
                cartItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItemList.size());
                quantityChangeListener.onQuantityChanged();
            }
        });

        holder.increaseQuantity.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.cartQuantity.setText(String.valueOf(item.getQuantity()));
            quantityChangeListener.onQuantityChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView cartName, cartPrice, cartQuantity;
        ImageView cartImage;
        Button deleteButton, increaseQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartName = itemView.findViewById(R.id.cartName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            cartImage = itemView.findViewById(R.id.cartImage);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
        }
    }
}
