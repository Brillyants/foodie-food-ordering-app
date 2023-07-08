package com.project.foodieadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.foodieadmin.Domain.OrderDomain;
import com.project.foodieadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    ArrayList<OrderDomain> orderDomains;

    public OrderDetailAdapter(ArrayList<OrderDomain> orderList) {
        this.orderDomains = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_detail, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(orderDomains.get(position).getFood_name());
        holder.num.setText(String.valueOf(orderDomains.get(position).getNumber_order()) + " item(s)");
        Picasso.with(holder.itemView.getContext()).load(orderDomains.get(position).getFood_pic()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return orderDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;
        TextView num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleOrder);
            pic = itemView.findViewById(R.id.picOrder);
            num = itemView.findViewById(R.id.numOrder);
        }
    }
}
