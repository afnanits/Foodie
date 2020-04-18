package org.example.foodie.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Order {

    @SerializedName("_id")
    public long id;
    @SerializedName("user")
    public User user;

    public double totalPrice;
    @SerializedName("foods")
    List<Food> foodList;
    List<Restaurant> restaurantList;

/*
    foods	[...]
    restaurant	{...}
    user	{...}
    deliveryGuy	{...}
    status	string
    Status of the order- RECIEVED/ LEFT/ DELIVERED/ CANCELLED

    payment*	{
        description:
        Details of payment

        method	string
        Mode of payment- COD/ UPI/ CARD

        status	string
        Payment status- PAID/ UNPAID

        total	string
        Total amount to be paid

    }
}*/
}


class Payment {

    @SerializedName("method")
    String method;

    @SerializedName("status")
    String status;

    @SerializedName("total")
    String total;

    public Payment(String method, String status, String total) {
        this.method = method;
        this.status = status;
        this.total = total;
    }
}