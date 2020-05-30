package org.example.foodie.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Order {


    public double totalPrice;
    @SerializedName("user")
    public User user;
    @SerializedName("restaurant")
    Restaurant restaurant;

    @SerializedName("address")
    String address;
    @SerializedName("assign")
    public boolean assign;

    @SerializedName("payment")
    Payment payment;
    @SerializedName("_id")
    String _id;
    @SerializedName("foods")
    List<OrderFood> foodList = new ArrayList<>();

    @SerializedName("restaurantId")
    String restaurantId;
    @SerializedName("deliveryGuy")
    DeliveryGuy deliveryGuy;
    @SerializedName("status")
    String status;
    @SerializedName("createdAt")
    String createdAt;

    public boolean getAssign() {
        return assign;
    }

    public boolean isAssign() {
        return assign;
    }

    public DeliveryGuy getDeliveryGuy() {
        return deliveryGuy;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    List<Restaurant> restaurantList;

    public Order(String restaurantId, List<OrderFood> foodList, Payment payment) {
        this.payment = payment;
        this.foodList = foodList;
        this.restaurantId = restaurantId;
    }

    public Order(String restaurantId, String address, List<OrderFood> foodList, Payment payment) {
        this.payment = payment;
        this.address = address;
        this.foodList = foodList;
        this.restaurantId = restaurantId;
    }


    public Payment getPayment() {
        return payment;
    }


    public User getUser() {
        return user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderFood> getFoodList() {
        return foodList;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String get_id() {
        return _id;
    }
}


