
package com.project.foodieadmin.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodieadmin.Adapter.ConfirmedAdapter;
import com.project.foodieadmin.DBContract;
import com.project.foodieadmin.Domain.OrderDataDomain;
import com.project.foodieadmin.Domain.PaymentDomain;
import com.project.foodieadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmedFragment extends Fragment {

    View view;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewAllOrderList;
    private ArrayList<OrderDataDomain> allOrderList;
    private ArrayList<PaymentDomain> allPaymentList;

    private static final String TAG_ORDER = "data";
    private static final String TAG_ID_USER = "id_user";
    private static final String TAG_ORDER_CODE = "order_code";
    private static final String TAG_PAYMENT_PIC = "payment_pic";
    private static final String TAG_ORDER_DATE = "order_date";
    private static final String TAG_TOTAL = "total";
    private static final String TAG_ADDRESS = "address";

    private static final String TAG_PROFILE = "data";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE = "phone";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirmed, container, false);

        allPaymentList = new ArrayList<>();

        initView();

        GetAllConfirmedOrder();

        return view;
    }

    private void initView() {
        recyclerViewAllOrderList = view.findViewById(R.id.recyclerViewAllOrder);
    }

    private void recyclerViewAllOrder() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerViewAllOrderList = view.findViewById(R.id.recyclerViewAllOrder);
        recyclerViewAllOrderList.setLayoutManager(gridLayoutManager);
    }

    public void GetAllConfirmedOrder() {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ALL_CONFIRMED_PAYMENT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_ORDER);

                                allOrderList = new ArrayList<>();

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    int id_user = a.getInt(TAG_ID_USER);
                                    String order_code = a.getString(TAG_ORDER_CODE);
                                    String payment_pic = a.getString(TAG_PAYMENT_PIC);

                                    allOrderList.add(new OrderDataDomain(id_user, order_code, payment_pic));

                                    // get name & phone
                                    GetCustById(id_user, order_code, payment_pic);
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
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetCustById(final int id, final String order_code, String payment_pic) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_CUST_BY_ID_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_PROFILE);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String name = a.getString(TAG_NAME);
                                    String phone = a.getString(TAG_PHONE);

                                    // get order date, total, address
                                    GetOrderByOrderCode(order_code, name, phone, payment_pic);
                                }
                            } catch (JSONException e) {
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
                    params.put("id", String.valueOf(id));
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetOrderByOrderCode(final String order_code, final String name, final String phone, final String payment_pic) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ORDER_BY_ORDER_CODE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_ORDER);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String order_date = a.getString(TAG_ORDER_DATE);
                                    int total = a.getInt(TAG_TOTAL);
                                    String address = a.getString(TAG_ADDRESS);

                                    allPaymentList.add(new PaymentDomain(order_code, name, phone, address, order_date, total, payment_pic));
                                }

                                adapter = new ConfirmedAdapter(getContext(), allPaymentList);
                                recyclerViewAllOrderList.setAdapter(adapter);

                            } catch (JSONException e) {
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
                    params.put("order_code", order_code);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
            recyclerViewAllOrder();
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