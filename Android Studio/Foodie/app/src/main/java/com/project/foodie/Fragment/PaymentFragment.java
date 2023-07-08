package com.project.foodie.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodie.Adapter.AllFoodAdapter;
import com.project.foodie.Adapter.CategoryAdapter;
import com.project.foodie.Adapter.OrderListAdapter;
import com.project.foodie.DBContract;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.Domain.OrderDomain;
import com.project.foodie.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentFragment extends Fragment {

    View view;
    private RecyclerView recyclerViewOrder;
    private RecyclerView.Adapter adapter;

    TextView txt_order_code;
    Button btn_next_payment;

    private static final String TAG_ORDER = "data";
    private static final String TAG_ORDER_CODE = "order_code";
    private static final String TAG_ID_USER = "id_user";
    private static final String TAG_FOOD_NAME = "food_name";
    private static final String TAG_NUMBER_ORDER= "number_order";
    private static final String TAG_ORDER_DATE = "order_date";;
    private static final String TAG_TOTAL = "total";

    ArrayList<OrderDomain> orderList;
    String order_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);

        // init required view
        initView();

        // set button next to disabled
        btn_next_payment.setEnabled(false);

        // if button next enabled and clicked
        btn_next_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create fragment
                Fragment uploadFrag = new UploadFragment();

                // set bundle to intent
                Bundle bundle = new Bundle();
                bundle.putString("order_code", order_code);
                uploadFrag.setArguments(bundle);

                // intent to OrderFragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, uploadFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        // check search query in edit text
        txt_order_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txt_order_code.getText().toString().equals("")) {
                    orderList.clear();
                    adapter = new OrderListAdapter(orderList);
                    recyclerViewOrder.setAdapter(adapter);
                } else {
                    GetOrderByCode(txt_order_code.getText().toString());
                }
            }
        });

        return view;
    }

    private void initView() {
        txt_order_code = view.findViewById(R.id.txt_order_code);
        btn_next_payment = view.findViewById(R.id.btn_next_payment);
    }

    public void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewOrder = view.findViewById(R.id.recyclerViewOrder);
        recyclerViewOrder.setLayoutManager(linearLayoutManager);
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

                                adapter = new OrderListAdapter(orderList);
                                recyclerViewOrder.setAdapter(adapter);

                                // set button next to disabled
                                btn_next_payment.setEnabled(true);
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

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}