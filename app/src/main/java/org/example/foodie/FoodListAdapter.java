package org.example.foodie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;
import org.example.foodie.models.OrderFood;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.CustomViewHolder> {

    public static int pos;

    List<OrderFood> items;
    private OnItemCLickListener mOnItemCLickListener;
    private FragmentManager f_manager;
    private Context context;

    public FoodListAdapter(Context context) {
        this.context = context;

        // this.items = items;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodListAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.food_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getFoodName());
        holder.itemQuantity.setText(String.valueOf(items.get(position).getCount()));
        holder.itemPrice.setText(items.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFood(List<OrderFood> foods) {
        this.items = foods;
        notifyDataSetChanged();
    }


    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private ImageView removeFromCart;
        private TextView itemQuantity;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);

        }
    }


}
