package org.example.foodie;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.example.foodie.models.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.CustomViewHolder> {
    private FragmentManager f_manager;
    private Context context;
    private List<Restaurant> items;


    public RestaurantAdapter(Context context) {
        this.context = context;

        // this.items = items;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_view, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.restaurantName.setText(items.get(position).getName());


        holder.restaurantCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodsActivity.id = items.get(position).getId();
                SharedPreferences sharedPreferences = context.getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

                CartActivity.getPrefernce(sharedPreferences);

                Log.i("AFNAN", String.valueOf(items.get(position).getId()));
                if (!CartActivity.cartItems.isEmpty()) {//TODO:Build an alert dialog builder
                    if (!FoodsActivity.rest_id.equals(FoodsActivity.id)) {
                        Toast.makeText(context, "CART CONTAINS ITEMS FROM ANOTHER RESTAURANT",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                FoodsActivity.rest_id = FoodsActivity.id;
                Intent i = new Intent(context, FoodsActivity.class);
                CartActivity.saveData(sharedPreferences);
                context.startActivity(i);

                //   f_manager.popBackStack();
            }
        });
        //holder.description.setText(items.get(position).getDescription());
        //holder.rating.setText((int) items.get(position).getRating());
      //  holder.eta.setText(items.get(position).getEta());
    /*    Glide.with(context).asBitmap().load(items.get(position).getImageUrl())
                .into(holder.itemImage);
    */
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView restaurantName;
        private TextView eta, rating, description;
        private CardView restaurantCard;


        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            restaurantName = view.findViewById(R.id.restaurantName);
            rating = view.findViewById(R.id.rating);
            eta = view.findViewById(R.id.eta);
            description = view.findViewById(R.id.description);
            restaurantCard = view.findViewById(R.id.restaurant);

          /*  if (mOnItemCLickListener != null) {
                restaurantCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemCLickListener.OnItemClick(getAdapterPosition());

                    }
                });

            }*/
        }
    }
    public void setRestaurants(List<Restaurant> restaurantsList) {
        this.items = restaurantsList;
        notifyDataSetChanged();
    }


}