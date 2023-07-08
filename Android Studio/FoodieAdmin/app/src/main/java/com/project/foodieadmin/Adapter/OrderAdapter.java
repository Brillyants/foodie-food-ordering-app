package com.project.foodieadmin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.project.foodieadmin.Domain.PaymentDomain;
import com.project.foodieadmin.Fragment.OrderDetailFragment;
import com.project.foodieadmin.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<PaymentDomain> orderDomains;
    private Context context;

    public OrderAdapter(Context context, ArrayList<PaymentDomain> orderDomains) {
        this.context = context;
        this.orderDomains = orderDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCustomerName.setText(orderDomains.get(position).getCust_name());
        holder.txtDate.setText("Date : " + orderDomains.get(position).getDate());
        holder.txtCustTotal.setText("Total : " + NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(orderDomains.get(position).getCust_total()));
        holder.txtOrderCode.setText(orderDomains.get(position).getOrder_code());

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment orderDetailFrag = new OrderDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString("order_code", orderDomains.get(position).getOrder_code());
                bundle.putString("cust_name", orderDomains.get(position).getCust_name());
                bundle.putString("cust_phone", orderDomains.get(position).getCust_phone());
                bundle.putString("cust_location", orderDomains.get(position).getCust_location());
                bundle.putString("date", orderDomains.get(position).getDate());
                bundle.putInt("cust_total", orderDomains.get(position).getCust_total());
                bundle.putString("cust_pic", orderDomains.get(position).getCust_pic());
                orderDetailFrag.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, orderDetailFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomerName, txtCustTotal, txtOrderCode, txtDate;
        Button btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtCustTotal = itemView.findViewById(R.id.txtCustTotal);
            txtOrderCode = itemView.findViewById(R.id.txtOrderCode);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
