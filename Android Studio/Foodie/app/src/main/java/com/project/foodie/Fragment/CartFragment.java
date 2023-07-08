package com.project.foodie.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodie.Adapter.CartListAdapter;
import com.project.foodie.DBContract;
import com.project.foodie.Domain.CartDomain;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.Helper.ManagementCart;
import com.project.foodie.Interface.ChangeNumberItemsListener;
import com.project.foodie.R;
import com.project.foodie.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class CartFragment extends Fragment {

    View view;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, txt_address;
    private Button checkOutBtn;
    private double itemTotal, total;
    private int numberOrder = 1;
    private int loop = 1;
    private double tax;
    String address;
    String order_code;
    String order_date;
    private ScrollView scrollView;

    SessionManager sessionManager;

    private static final String TAG_CART = "data";
    private static final String TAG_FOOD_NAME = "food_name";
    private static final String TAG_NUMBER_ORDER = "number_order";

    private static final String TAG_FOOD = "data";
    private static final String TAG_ID_FOOD = "id_food";
    private static final String TAG_NAME = "name";
    private static final String TAG_PIC = "pic";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ID_CATEGORY = "id_category";
    private static final String TAG_PRICE = "price";

    ArrayList<FoodDomain> arrayListFood;
    ArrayList<CartDomain> cartList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart, container, false);

        sessionManager = new SessionManager(getContext());
        managementCart = new ManagementCart(getContext());

        // initialize all view
        initView();

        // get cart from database
        GetCartById(sessionManager.getId());

        initList();
        calculateCard();

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("Check Out");
                dialog.setMessage("Are you sure to Check Out?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get address
                        address = txt_address.getText().toString();
                        // generate random order code
                        order_code = generateString(6);
                        // get user date
                        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        order_date = dateF.format(Calendar.getInstance().getTime());

                        if(address.equals("")) {
                            Toast.makeText(getContext(), "Fill in the address!", Toast.LENGTH_SHORT).show();
                        } else {
                            // set cart item based on cart
                            arrayListFood = managementCart.getListCard();
                            cartList = new ArrayList<>();
                            for(int i = 0; i < arrayListFood.size(); i++) {
                                CartDomain cartItem = new CartDomain(sessionManager.getId(), arrayListFood.get(i).getTitle(),
                                        arrayListFood.get(i).getNumInCard());
                                cartList.add(cartItem);
                            }

                            for(int i = 0; i < arrayListFood.size(); i++) {
                                // add order to database
                                AddOrder(order_code, cartList.get(i).getId_user(), cartList.get(i).getFood_name(),
                                        cartList.get(i).getNumber_order(), order_date, total, address);
                            }
                        }
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

    public void calculateCard() {
        double percentTax = 0.02;
        double delivery = 13000;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100;
        itemTotal = Math.round(managementCart.getTotalFee() * 100 / 100);
        total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100;

        totalFeeTxt.setText("Rp. " + itemTotal);
        taxTxt.setText("Rp. " + tax);
        deliveryTxt.setText("Rp. " + delivery);
        totalTxt.setText("Rp. " + total);
    }

    public void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCard(), getContext(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

        // set cart item based on cart
        arrayListFood = managementCart.getListCard();
        cartList = new ArrayList<>();

        for(int i = 0; i < arrayListFood.size(); i++) {
            CartDomain cartItem = new CartDomain(sessionManager.getId(), arrayListFood.get(i).getTitle(), arrayListFood.get(i).getNumInCard());
            cartList.add(cartItem);
        }
    }

    private void initView() {
        recyclerViewList = view.findViewById(R.id.recyclerViewList);
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        taxTxt = view.findViewById(R.id.taxTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        deliveryTxt = view.findViewById(R.id.deliveryTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        emptyTxt = view.findViewById(R.id.emptyTxt);
        scrollView = view.findViewById(R.id.scrollView2);
        checkOutBtn = view.findViewById(R.id.checkOutBtn);
        txt_address = view.findViewById(R.id.txt_address);
    }

    private String generateString(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < length; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void AddOrder(final String order_code, final int id_user, final String food_name, final int number_order,
                         final String order_date, final double total, final String address) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_ADD_ORDER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getContext(), "Successful!", Toast.LENGTH_SHORT).show();

                                    // create fragment
                                    Fragment orderFrag = new OrderFragment();

                                    // set bundle to intent
                                    Bundle bundle = new Bundle();
                                    bundle.putString("order_code", order_code);
                                    orderFrag.setArguments(bundle);

                                    // intent to OrderFragment
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, orderFrag);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    getActivity().getFragmentManager().popBackStack();
                                } else {
                                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
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
                    params.put("order_code", order_code);
                    params.put("id_user", String.valueOf(id_user));
                    params.put("food_name", food_name);
                    params.put("number_order", String.valueOf(number_order));
                    params.put("order_date", order_date);
                    params.put("total", String.valueOf(total));
                    params.put("address", address);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetCartById(final int id_user) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_CART_BY_ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_CART);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String food_name = a.getString(TAG_FOOD_NAME);
                                    int number_order = a.getInt(TAG_NUMBER_ORDER);

                                    GetFoodByName(food_name, number_order);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            DeleteCartById(sessionManager.getId());
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
                    params.put("id_user", String.valueOf(id_user));
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetFoodByName(final String name, final int number_order) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_FOOD_BY_NAME_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_FOOD);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String name = a.getString(TAG_NAME);
                                    String pic = a.getString(TAG_PIC);
                                    String description = a.getString(TAG_DESCRIPTION);
                                    int id_category = a.getInt(TAG_ID_CATEGORY);
                                    int price = a.getInt(TAG_PRICE);

                                    FoodDomain Food = new FoodDomain(name, pic, description, id_category, price);
                                    Food.setNumInCard(number_order);
                                    managementCart.insertExistingCart(Food);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initList();
                            calculateCard();
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
                    params.put("name", name);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteCartById(final int id_user) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_DELETE_CART_BY_ID, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
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
                    params.put("id_user", String.valueOf(id_user));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}