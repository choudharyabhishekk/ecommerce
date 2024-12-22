package com.example.ria_project;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Product.class, CartItem.class}, version = 2)  // Updated to include CartItem and version number
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ProductDao productDao();
    public abstract CartItemDao cartItemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()  // Handle migration if needed
                    .build();
        }
        return instance;
    }
}
