package com.project.foodieadmin.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodieadmin.Adapter.OrderDetailAdapter;
import com.project.foodieadmin.DBContract;
import com.project.foodieadmin.Domain.OrderDomain;
import com.project.foodieadmin.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OrderDetailFragment extends Fragment {

    View view;
    String order_code, cust_name, cust_phone, cust_location, date, cust_pic;
    int cust_total;
    TextView txt_ordercode, txt_custname, txt_custphone, txt_custlocation, txt_dateorder, txt_total;
    ImageView img_transfer;
    Button btn_accept, btn_decline;

    RecyclerView recyclerViewAllOrder;
    private RecyclerView.Adapter adapter;
    ArrayList<OrderDomain> orderList;

    private static final String TAG_ORDER = "data";
    private static final String TAG_ORDER_CODE = "order_code";
    private static final String TAG_ID_USER = "id_user";
    private static final String TAG_FOOD_NAME = "food_name";
    private static final String TAG_NUMBER_ORDER= "number_order";
    private static final String TAG_ORDER_DATE = "order_date";;
    private static final String TAG_TOTAL = "total";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_detail, container, false);

        initView();

        Bundle args = getArguments();
        order_code = args.getString("order_code");
        cust_name = args.getString("cust_name");
        cust_phone = args.getString("cust_phone");
        cust_location = args.getString("cust_location");
        date = args.getString("date");
        cust_total = args.getInt("cust_total");
        cust_pic = args.getString("cust_pic");

        txt_ordercode.setText(order_code);
        txt_custname.setText("Name : " + cust_name);
        txt_custphone.setText("Phone Number : " + cust_phone);
        txt_custlocation.setText("Location : " + cust_location);
        txt_dateorder.setText("Date Order : " + date);
        txt_total.setText("Total : " + NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(cust_total));
        Picasso.with(getContext()).load(cust_pic).into(img_transfer);

        GetOrderByCode(order_code);

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("Decline Order");
                dialog.setMessage("Are you sure to Decline Order?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeletePaymentByOrderCode(order_code);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("Accept Order");
                dialog.setMessage("Are you sure to Accept Order?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditPaidPaymentByOrderCode(order_code);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void initView() {
        txt_ordercode = view.findViewById(R.id.txt_ordercode);
        txt_custname = view.findViewById(R.id.txt_custname);
        txt_custphone = view.findViewById(R.id.txt_custphone);
        txt_custlocation = view.findViewById(R.id.txt_custlocation);
        txt_dateorder = view.findViewById(R.id.txt_dateorder);
        txt_total = view.findViewById(R.id.txt_total);
        recyclerViewAllOrder = view.findViewById(R.id.recyclerViewAllOrder);
        img_transfer = view.findViewById(R.id.img_transfer);
        btn_accept = view.findViewById(R.id.btn_accept);
        btn_decline = view.findViewById(R.id.btn_decline);
    }

    public void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAllOrder = view.findViewById(R.id.recyclerViewAllOrder);
        recyclerViewAllOrder.setLayoutManager(linearLayoutManager);
    }

    public void GetOrderByCode(final String query) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ORDER_BY_CODE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_ORDER);

                                orderList = new ArrayList<>();

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    order_code = a.getString(TAG_ORDER_CODE);
                                    int id_user = a.getInt(TAG_ID_USER);
                                    String food_name = a.getString(TAG_FOOD_NAME);
                                    int number_order = a.getInt(TAG_NUMBER_ORDER);
                                    String order_date = a.getString(TAG_ORDER_DATE);
                                    double total = a.getDouble(TAG_TOTAL);

                                    GetPicByFood(order_code, id_user, food_name, number_order, order_date, total);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_code", query);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetPicByFood(final String order_code, final int id_user, final String food_name, final int number_order,
                             final String order_date, final double total) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_PIC_BY_NAME_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_ORDER);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String food_pic = a.getString("pic");

                                    orderList.add(new OrderDomain(order_code, id_user, food_name, food_pic,
                                            number_order, order_date, total));
                                }

                                adapter = new OrderDetailAdapter(orderList);
                                recyclerViewAllOrder.setAdapter(adapter);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("food_name", food_name);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
            setRecyclerView();
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeletePaymentByOrderCode(final String order_code) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_DELETE_PAYMENT_BY_ORDER_CODE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        DeleteOrderByOrderCode(order_code);
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.getMessage());
                    Toast.makeText(getContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_code", String.valueOf(order_code));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }

    public void DeleteOrderByOrderCode(final String order_code) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_DELETE_ORDER_BY_ORDER_CODE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        Toast.makeText(getContext(), "Order Rejected!", Toast.LENGTH_SHORT).show();
                        Fragment orderFragment = new OrderFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, orderFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.getMessage());
                    Toast.makeText(getContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_code", String.valueOf(order_code));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }

    public void EditPaidPaymentByOrderCode(final String order_code) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_EDIT_PAID_PAYMENT_BY_ORDER_CODE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                if (success == 1) {
                                    Toast.makeText(getContext(), "Order Confirmed!", Toast.LENGTH_SHORT).show();
                                    Fragment orderFragment = new OrderFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, orderFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                } else {
                                    Toast.makeText(getContext(), "Order failed to confirmed!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {
                                Log.e("Error", ex.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_code", order_code);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}