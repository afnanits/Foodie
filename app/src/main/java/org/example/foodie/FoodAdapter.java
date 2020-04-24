package org.example.foodie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.CustomViewHolder> {


    List<Food> items;
    private OnItemCLickListener mOnItemCLickListener;
    private FragmentManager f_manager;
    private Context context;

    public FoodAdapter(Context context) {
        this.context = context;

        // this.items = items;
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.mOnItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.foods_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Log.i("afnan", String.valueOf(items.get(position).getFoodid()));
        Foodid foodid = items.get(position).getFoodid();
        holder.foodName.setText(foodid.getName());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFood(List<Food> foods) {
        this.items = foods;
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView foodName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.food_name);

        }
    }
}
