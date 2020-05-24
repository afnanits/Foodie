package org.example.foodie;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.example.foodie.models.Order;
import org.example.foodie.models.OrderFood;
import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.CustomViewHolder> {
    private Context context;

    FoodListAdapter adapter;
    private List<Order> items;
    //  private ArrayList<NEWS> subjects;

    public OrdersAdapter(Context context) {
        this.context = context;
        // this.items = items;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.orderview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.total.setText(String.valueOf(updateTotal(items.get(position).getFoodList())));

        holder.restaurantName.setText(items.get(position).getRestaurant().getName());


        adapter = new FoodListAdapter(context);
        adapter.setFood(items.get(position).getFoodList());

        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        holder.recyclerView.setAdapter(adapter);


        if (items.get(position).getAssign()) {

            holder.deliveryBoyName.setText(items.get(position).getDeliveryGuy().getName());
            holder.deliveryBoyContact.setText(items.get(position).getDeliveryGuy().phone);


        } else {
            holder.deliveryBoyName.setText("Not Assigned");
            holder.deliveryBoyContact.setVisibility(View.GONE);


        }
        holder.orderStatus.setText(items.get(position).getStatus());


        //set Elements here

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOrders(List<Order> orders) {
        this.items = orders;
        notifyDataSetChanged();
    }

    public int updateTotal(List<OrderFood> foods) {
        int total = 0;
        for (int i = 0; i < foods.size(); i++) {
            total = total + foods.get(i).getCount() * foods.get(i).getPrice();
        }
        return total;

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantName;
        private TextView total;
        private RecyclerView recyclerView;
        private TextView deliveryBoyName;
        private TextView deliveryBoyContact;
        private TextView orderStatus;

        public CustomViewHolder(View view) {
            super(view);

            restaurantName = view.findViewById(R.id.restaurantName);
            total = view.findViewById(R.id.total);
            recyclerView = view.findViewById(R.id.foodList);
            deliveryBoyName = view.findViewById(R.id.deliveryBoyName);
            deliveryBoyContact = view.findViewById(R.id.deliveryBoyContact);
            orderStatus = view.findViewById(R.id.orderStatus);

        }
    }


}