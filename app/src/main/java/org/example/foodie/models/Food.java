package org.example.foodie.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("foodid")
    public Foodid foodid;
    @SerializedName("price")
    public String price;
    @SerializedName("_id")
    String _id;
    int count;


    public Food(Foodid foodid, String _id, String price) {
        this.foodid = foodid;
        this._id = _id;
        this.price = price;
        this.count = 0;
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


    public void addCount() {
        this.count = count + 1;
//setCount(this.count);
    }


    public void decreaseCount() {
        this.count = count - 1;
        setCount(this.count);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
