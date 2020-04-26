package org.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantFoodAdd extends AppCompatActivity {
    private TextView restaurantName, restaurantInfo;
    private ImageView restaurantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_add);
        initWidget();
        Intent intent = getIntent();
        restaurantName.setText(intent.getStringExtra("name"));
        restaurantInfo.setText(intent.getStringExtra("restId")+"\n"+intent.getStringExtra("address"));



    }

    public void initWidget() {
        restaurantName = (TextView) findViewById(R.id.restaurantName);
        restaurantInfo = (TextView) findViewById(R.id.restaurantInfo);
        restaurantImage = (ImageView) findViewById(R.id.restaurantProfile);
    }
}