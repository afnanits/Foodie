package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class DeliveryGuy {


    @SerializedName("_id")
    public String _id;
    @SerializedName("name")
    public String name;
    @SerializedName("phone")
    public String phone;


    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
