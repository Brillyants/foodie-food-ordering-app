package com.project.foodie.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.foodie.Activity.CategoryDetailActivity;
import com.project.foodie.Activity.DetailActivity;
import com.project.foodie.Domain.CategoryDomain;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.Fragment.MeatFragment;
import com.project.foodie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set viewholder to category viewholder
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // set categoryName based on title in certain position
        holder.categoryName.setText(categoryDomains.get(position).getTitle());

        // set categoryImg based on imgUrl
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(categoryDomains.get(position).getPic(),
                "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.categoryImg);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), CategoryDetailActivity.class);
                i.putExtra("object", categoryDomains.get(position));
                i.putExtra("category", categoryDomains.get(position).getCategoryID());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // return number of elements in categoryDomains list
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryImg;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // get categoryName, categoryImg, and mainLayout
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImg = itemView.findViewById(R.id.categoryImg);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
