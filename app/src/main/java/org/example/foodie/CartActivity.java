package org.example.foodie;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.example.foodie.models.Food;
import org.example.foodie.models.Order;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartActivity extends AppCompatActivity {

    public static Gson gson = new Gson();
    public static String json;
    public static int total = 0;
    public static TextView totalPrice;
    public static RelativeLayout cartView;
    public static TextView emptyCart;
    public static Button button;
    public static RecyclerView cartList;
    public static CartAdapter adapter;
    static List<Food> cartItems = new ArrayList<>();
    public SharedPreferences sharedPreferences;
    Set<String> foods;

    public static void getPrefernce(SharedPreferences sharedPreferences) {


        if (cartItems.isEmpty()) {

            json = sharedPreferences.getString("cartitems", null);
            String id = sharedPreferences.getString("rest_id", null);
            if (json != null) {
                Type type = new TypeToken<List<Food>>() {
                }.getType();
                cartItems = gson.fromJson(json, type);
            }
            if (id != null) FoodsActivity.rest_id = id;


        }
/*
        else{
            SharedPreferences.Editor editor= sharedPreferences.edit();
            json=gson.toJson(cartItems);
            editor.putString("cartitems",json);
            editor.commit();

        }*/

    }

    public static void saveData(SharedPreferences sharedPreferences) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        json = gson.toJson(cartItems);
        editor.putString("cartitems", json);
        editor.putString("rest_id", FoodsActivity.rest_id);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        button = findViewById(R.id.placeOrder);
        emptyCart = findViewById(R.id.emptyCart);
        cartView = findViewById(R.id.cartView);
        total = 0;
        totalPrice = findViewById(R.id.total);
        cartList = findViewById(R.id.cartList);
        sharedPreferences = getSharedPreferences("org.example.foodie", MODE_PRIVATE);


        cartView.setVisibility(View.GONE);
        emptyCart.setVisibility(View.VISIBLE);

        getPrefernce(sharedPreferences);
        for (int i = 0; i < CartActivity.cartItems.size(); i++) {
            CartActivity.total = CartActivity.total + Integer.parseInt(CartActivity.cartItems.get(i).getPrice()) * CartActivity.cartItems.get(i).getCount();

        }

        CartActivity.totalPrice.setText(String.valueOf(CartActivity.total));


        if (!cartItems.isEmpty()) {

            try {
                cartView.setVisibility(View.VISIBLE);
                emptyCart.setVisibility(View.GONE);
                adapter = new CartAdapter(this);

                cartList.setLayoutManager(new GridLayoutManager(this, 1));
                Log.i("Current food: ", cartItems.get(cartItems.size() - 1).getFoodid().getName());
                adapter.setFood(cartItems);
                cartList.setAdapter(adapter);
            } catch (Exception e) {

                // Toolbar toolbar=findViewById(R.id.homeAsUp);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);


            }
            //   setupRecyclerView();

//TODO:place order throuugh button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CartActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void placeOrder(Order order) {

    }


}
