package org.example.foodie.org.example.foodie.apifetch;


import com.google.gson.JsonObject;

import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.models.RestaurantCreate.RestaurantCreate;
import org.example.foodie.models.User;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("restaurant/{id}")
    Call<Restaurant> getFood(@Path("id") String id);

    @POST("restaurant")
    Call<RestaurantCreate> createRestaurant(@Body RestaurantCreate restaurantCreate);


}