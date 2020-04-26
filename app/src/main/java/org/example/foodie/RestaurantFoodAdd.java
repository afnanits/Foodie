package org.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantFoodAdd extends AppCompatActivity {
    TextView restaurantName, restaurantInfo;
    ImageView restaurantImage;
    EditText foodName,foodprice;
    Button addFood;
    String token;
    View progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_add);
        progressBar = findViewById(R.id.progressBarAddFood);
        progressBar.setVisibility(View.GONE);
        initWidget();
        Intent intent = getIntent();
        token=intent.getStringExtra("token");
        Toast.makeText(RestaurantFoodAdd.this , token , Toast.LENGTH_LONG).show();
        restaurantName.setText(intent.getStringExtra("name"));
        restaurantInfo.setText(intent.getStringExtra("restId")+"\n"+intent.getStringExtra("address"));
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFoodToRestaurantList();
            }
        });
    }

    public void initWidget() {
        restaurantName = (TextView) findViewById(R.id.restaurantName);
        restaurantInfo = (TextView) findViewById(R.id.restaurantInfo);
        restaurantImage = (ImageView) findViewById(R.id.restaurantProfile);
        foodName=(EditText)findViewById(R.id.foodName);
        foodprice=(EditText)findViewById(R.id.foodPrice);
        addFood=(Button)findViewById(R.id.addFood);
    }
    public void PostFoodToRestaurantList()
    {
        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        Food food=new Food(foodName.getText().toString(),foodprice.getText().toString());
        Call<Foodid> foodCall=foodieClient.postFood(token,food);
        progressBar.setVisibility(View.VISIBLE);
        foodCall.enqueue(new Callback<Foodid>() {
            @Override
            public void onResponse(Call<Foodid> call , Response<Foodid> response) {
                if(response.code()==201){
                    Toast.makeText(RestaurantFoodAdd.this ,response.body().getName() +" added Successfully.", Toast.LENGTH_SHORT).show();
                    foodName.setText("");
                    foodprice.setText("");
                    addFood.setText("Add More To Food List");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Foodid> call , Throwable t) {
                Toast.makeText(RestaurantFoodAdd.this ,t.getMessage() , Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}