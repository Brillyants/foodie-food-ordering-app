package com.project.foodie.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.foodie.Activity.DetailActivity;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ViewHolder> {
    ArrayList<FoodDomain> foodDomains;

    public BestSellerAdapter(ArrayList<FoodDomain> foodDomains) {
        this.foodDomains = foodDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set viewholder to best seller viewholder
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_best_seller,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // set title based on title in certain position
        holder.txt_title.setText(foodDomains.get(position).getTitle());

        // set price based on price in certain position
        holder.txt_price.setText(String.valueOf(foodDomains.get(position).getPrice()));

        // set pic based on pic in certain position
        Picasso.with(holder.itemView.getContext()).load(foodDomains.get(position).getPic()).into(holder.img_pic);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailActivity.class);
                i.putExtra("object", foodDomains.get(position));
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // return number of elements in foodDomains list
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_title, txt_price;
        ImageView img_pic;
        Button btn_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // get txt_title, txt_price, img_pic, and btn_add
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            img_pic = itemView.findViewById(R.id.img_pic);
            btn_add = itemView.findViewById(R.id.btn_add);
        }
    }
}
