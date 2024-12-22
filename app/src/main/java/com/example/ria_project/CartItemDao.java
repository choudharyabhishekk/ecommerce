package com.example.ria_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CartItemDao {

    @Insert
    void insert(CartItem cartItem);

    @Delete
    void delete(CartItem cartItem);

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    void deleteAllItemsByUserId(String userId);
    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    List<CartItem> getCartItemsByUserId(String userId);
}
