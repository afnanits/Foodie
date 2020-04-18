package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Restaurant {
    @SerializedName("rest_id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("contactNos")
    public List<String> contactNos;
    @SerializedName("foods")
    public List<Food> menu = new ArrayList<>();
    @SerializedName("address")
    String address;
    Map<Long, List<Food>> orderList = new HashMap<>();


    //Constructor

    Restaurant(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Restaurant(List<String> contactNos, String address, List<Food> menu) {
        this.contactNos = contactNos;
        this.address = address;
        this.menu = menu;
    }

    //Methods
    public void changeMenu(List<Food> Menu) {
        this.menu = Menu;
    }

    public void addFoodToMenu(Food food) {
        menu.add(food);
        food.restaurant = this;
    }

    public void deleteFoodFromMenu(Food food) {
        menu.remove(food);
    }

    public List<Food> getMenu() {
        return menu;
    }

    public void addOrder(Order order) throws Exception {
        if (orderList.containsKey(order.id)) {
            throw new Exception("Duplicate Order!");
        } else {
            orderList.put(order.id, order.foodList);
        }
        for (Food food : order.foodList) {
            food.status = "Start Cooking";
        }
    }

    public void deleteOrder(Order order) {
        orderList.remove(order.id);

    }

    public String getName() {
        return name;
    }

    /*    public int hashCode() {
        return Long.hashCode(id);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Restaurant) {
            Restaurant r = (Restaurant) o;
            return r.id == this.id;
        }
        return false;
    }

    public synchronized void trackOrder(Order order) {
        for (Food food: orderList.get(order.id)) {
            System.out.println("Food: " + food.name + " -- Status: " + food.status);
        }
    }*/
}