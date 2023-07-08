package com.project.foodieadmin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodieadmin.Activity.IntroActivity;
import com.project.foodieadmin.DBContract;
import com.project.foodieadmin.Domain.EmployeeDomain;
import com.project.foodieadmin.Fragment.EmployeeFragment;
import com.project.foodieadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    ArrayList<EmployeeDomain> employeeDomains;

    public EmployeeAdapter(ArrayList<EmployeeDomain> employeeDomains) {
        this.employeeDomains = employeeDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_employee, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(employeeDomains.get(position).getName());
        holder.txtUsername.setText(employeeDomains.get(position).getUsername());
        holder.txtPhone.setText(employeeDomains.get(position).getPhone());
        holder.txtEmail.setText(employeeDomains.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return employeeDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtUsername, txtPhone, txtEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }
}
