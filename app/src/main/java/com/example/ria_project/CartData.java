package com.example.ria_project;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CartData {

    private final CartItemDao cartItemDao;
    private final ExecutorService executorService;
    private final FirebaseAuth mAuth;

    public CartData(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        cartItemDao = db.cartItemDao();
        executorService = Executors.newSingleThreadExecutor();
        mAuth = FirebaseAuth.getInstance();
    }

    // Firebase method to get current user ID
    private String getCurrentUserId() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null ? currentUser.getUid() : null;
    }

    // Cart item methods
    public List<CartItem> getCartItemsForCurrentUser() {
        String userId = getCurrentUserId();
        Future<List<CartItem>> future = executorService.submit(new Callable<List<CartItem>>() {
            @Override
            public List<CartItem> call() {
                return cartItemDao.getCartItemsByUserId(userId);
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void clearCart() {
        String userId = getCurrentUserId();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cartItemDao.deleteAllItemsByUserId(userId);
            }
        });
    }
    public void addItemToCart(final CartItem cartItem) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cartItem.setUserId(getCurrentUserId());
                cartItemDao.insert(cartItem);
            }
        });
    }

    public void removeItemFromCart(final CartItem cartItem) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cartItemDao.delete(cartItem);
            }
        });
    }
}
