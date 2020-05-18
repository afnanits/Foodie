package org.example.foodie.Respositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.example.foodie.FoodsActivity;
import org.example.foodie.MainActivity;
import org.example.foodie.models.Food;
import org.example.foodie.models.ResponseRestaurant;
import org.example.foodie.models.Restaurant;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepository {

    public static FoodRepository foodRepository;
    private FoodieClient foodieClient;

    public FoodRepository() {
        foodieClient = ServiceGenerator.createService(FoodieClient.class);
    }

    public static FoodRepository getInstance() {
        if (foodRepository == null) {
            foodRepository = new FoodRepository();
        }

        return foodRepository;

    }

    public static FoodRepository getFoodRepository() {
        return foodRepository;
    }

    public MutableLiveData<List<Food>> getFoods() {

        MutableLiveData<List<Food>> restaurantData = new MutableLiveData<>();


        Call<ResponseRestaurant> call = foodieClient.getFood(FoodsActivity.id);

        call.enqueue(new Callback<ResponseRestaurant>() {
            @Override
            public void onResponse(Call<ResponseRestaurant> call, Response<ResponseRestaurant> response) {


                if (response.isSuccessful()) {
                    restaurantData.setValue(response.body().getRestaurant().getFoods());
                    Log.i("ResponseGive", String.valueOf(response.body().getRestaurant().getFoods()));

                }

            }

            @Override
            public void onFailure(Call<ResponseRestaurant> call, Throwable t) {

                Log.i("response", String.valueOf(FoodsActivity.id));
                restaurantData.setValue(null);
            }
        });


        return restaurantData;


    }

}
