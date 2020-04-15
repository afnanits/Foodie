package org.example.foodie.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;


public class Order {
    public long id;
    public User user;
    public double totalPrice;
    List<Food> foodList;
    List<Restaurant> restaurantList;

    Order(long id, User user, List<Food> foodList) {
        this.foodList = foodList;
        this.id = id;
        this.user = user;
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Long.hashCode(id);
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Order) {
            Order e = (Order) o;
            return e.id == this.id;
        }
        return false;
    }*/
}