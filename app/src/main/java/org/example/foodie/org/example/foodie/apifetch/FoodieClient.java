package org.example.foodie.org.example.foodie.apifetch;


import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.models.RestaurantCreate.RestaurantCreate;
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
    @POST("restaurant")
    Call<RestaurantCreate> createRestaurant(@Body RestaurantCreate restaurantCreate);


}