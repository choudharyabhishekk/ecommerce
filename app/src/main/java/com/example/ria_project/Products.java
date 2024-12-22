package com.example.ria_project;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_db")
public class Products {
    @PrimaryKey(autoGenerate = true)
    private int pid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @ColumnInfo(name = "product_name")
    public String product_name;

    @ColumnInfo(name = "product_description")
    public String product_description;

    @ColumnInfo(name = "product_price")
    public String product_price;

    @ColumnInfo(name = "product_image")
    public String product_image;

}