package org.example.foodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.example.foodie.models.Order;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;
import org.example.foodie.viewmodels.OrdersViewModel;
import org.example.foodie.viewmodels.RestaurantsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    OrdersViewModel ordersViewModel;
    OrdersAdapter adapter;
    List<Order> orders;
    RecyclerView orderRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderRecView = findViewById(R.id.allOrders);


        ordersViewModel = ViewModelProviders.of(OrdersActivity.this).get(OrdersViewModel.class);

        ordersViewModel.init();


        ordersViewModel.getUserRepository().observe(this, new Observer<ResponseUser>() {
            @Override
            public void onChanged(ResponseUser responseUser) {

                if (responseUser.getUser().getOrders() != null) {

                    Log.i("Ordersss:", String.valueOf(responseUser.getUser().getOrders().get(0).getFoodList().get(0).getFoodName()));
                    adapter = new OrdersAdapter(OrdersActivity.this);
                    orders = new ArrayList<>(responseUser.getUser().getOrders().size());
                    orders = responseUser.getUser().getOrders();
                    Collections.reverse(orders);
                    adapter.setOrders(orders);
                    orderRecView.setAdapter(adapter);

                }

                adapter.notifyDataSetChanged();
            }
        });

        orderRecView.setLayoutManager(new GridLayoutManager(
                OrdersActivity.this, 1));
        //SETTING up recyclerview
        setupRecyclerView();


    }


    public void setupRecyclerView() {

        if (adapter == null) {
            orderRecView.setLayoutManager(new GridLayoutManager(OrdersActivity.this, 1));

            orderRecView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }


}
