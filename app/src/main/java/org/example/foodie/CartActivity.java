package org.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.example.foodie.models.Food;
import org.example.foodie.models.Order;
import org.example.foodie.models.OrderFood;
import org.example.foodie.models.Payment;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static Order order;
    public SharedPreferences sharedPreferences;
    Set<String> foods;
    public static List<OrderFood> orderFood = new ArrayList<>();
    public static Payment payment;


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

    public static void CreateOrder(Payment payment) {
        for (int i = 0; i < cartItems.size(); i++) {
            BigInteger decimal = new BigInteger(cartItems.get(i).getFoodid().get_id(), 16);

            Log.i("Answer", decimal.toString(16));


            orderFood.add(new OrderFood(decimal.toString(16), cartItems.get(i).getCount()));
        }


        order = new Order(FoodsActivity.rest_id, orderFood, payment);


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
            CartActivity.total = CartActivity.total + Integer.parseInt(String.valueOf(CartActivity.cartItems.get(i).getPrice())) * CartActivity.cartItems.get(i).getCount();

        }

        CartActivity.totalPrice.setText("₹ " + String.valueOf(CartActivity.total));


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

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   Toast.makeText(CartActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                /*    CreateOrder();

                    placeOrder(order);
                */
                    Intent i = new Intent(CartActivity.this, PaymentActivity.class);

                    i.putExtra("price", totalPrice.getText());

                    startActivity(i);

                }
            });

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Payment() {

    }


/*
    public static PaymentsClient createPaymentsClient(Activity activity) {
        Wallet.WalletOptions walletOptions =new Wallet.WalletOptions.Builder()
                .setEnvironment(Constants.)
                .setTheme(WalletConstants.THEME_DARK)
                .build();
        return Wallet.getPaymentsClient(activity, walletOptions);
    }
*/





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


}
