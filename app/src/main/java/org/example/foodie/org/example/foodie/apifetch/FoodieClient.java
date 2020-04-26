package org.example.foodie.org.example.foodie.apifetch;


import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;
import org.example.foodie.models.RestaurantLogIn.ResponseRestaurantUser;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.models.RestaurantCreate.RestaurantCreate;
import org.example.foodie.models.RestaurantLogIn.RestaurantUser;
import org.example.foodie.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FoodieClient {
    //Create user endpoint
    @POST("user")
    Call<ResponseUser> Register(@Body User user);

    //Login user endpoint
    @POST("user/login")
    Call<ResponseUser> Login(@Body User user);

    //Logout user endpoint
    @POST("user/logout")
    Call<Void> Logout(@Header("Authorization") String token);

    //get all user info
    @GET("user/me")
    Call<ResponseUser> getData(@Header("Authorization") String token);


    //Connecting to endpoint to see all restaurants available
    @GET("restaurant")
    Call<List<Restaurant>> getRestaurant();

    //Restaturant create
    @POST("restaurant")
    Call<ResponseUser> createRestaurant(@Body RestaurantCreate restaurantCreate);

    //Get all foods of restaurant
    @GET("food")
    Call<Restaurant> getFood(String id);
    //adding food to particular restaurant
    @POST("food")
    Call<Foodid> postFood(@Header("Authorization") String token, @Body Food food);
    //log in restautant
    @POST("restaurant/login")
    Call<ResponseRestaurantUser> logInRestaurant(@Body RestaurantUser restaurantUser);
}