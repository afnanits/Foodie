package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("name")
    public String foodName;
    @SerializedName("foodid")
    public Foodid foodid;
    @SerializedName("price")
    public String price;
    @SerializedName("_id")
    String _id;

    public Food(String foodName , String price) {
        this.foodName = foodName;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Food(Foodid foodid, String _id, String price) {
        this.foodid = foodid;
        this._id = _id;
        this.price = price;
    }

    public Food(String price) {
        //      this.name = name;
        this.price = price;
    }


    public Food(Foodid foodid) {
        this.foodid = foodid;
    }

    public String getPrice() {
        return price;
    }

    public Foodid getFoodid() {
        return foodid;
    }
}
