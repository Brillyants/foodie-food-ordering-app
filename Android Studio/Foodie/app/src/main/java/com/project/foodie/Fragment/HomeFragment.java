package com.project.foodie.Fragment;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodie.Adapter.AllFoodAdapter;
import com.project.foodie.Adapter.BestSellerAdapter;
import com.project.foodie.Adapter.CategoryAdapter;
import com.project.foodie.DBContract;
import com.project.foodie.Domain.CategoryDomain;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.Helper.ManagementCart;
import com.project.foodie.R;
import com.project.foodie.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    View view;
    private RecyclerView.Adapter adapter, adapter2, adapter3;
    private RecyclerView recyclerViewCategoryList, recyclerViewBestSellerList, recyclerViewAllList, recyclerView3;
    TextView searchView;
    TextView helloUserTxt;
    TextView seeAllTxt;

    SessionManager sessionManager;

    private static final String TAG_FOOD = "data";
    private static final String TAG_ID_FOOD = "id_food";
    private static final String TAG_NAME = "name";
    private static final String TAG_PIC = "pic";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ID_CATEGORY = "id_category";
    private static final String TAG_PRICE = "price";

    private ArrayList<FoodDomain> allFoodList;
    ArrayList<FoodDomain> bestSellerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sessionManager = new SessionManager(getContext());

        // init view
        helloUserTxt = view.findViewById(R.id.helloUserTxt);
        searchView = view.findViewById(R.id.searchView);
        seeAllTxt = view.findViewById(R.id.seeAllTxt);
        recyclerView3 = view.findViewById(R.id.recyclerView3);

        seeAllTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment menuFrag = new MenuFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, menuFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        helloUserTxt.setText("Hello, " + sessionManager.getUsername());

        // get all food
        GetAllFood();

        // get category
        GetCategory();

        // get seller food
        GetBestSellerFood();

        // search logic
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchView.getText().equals("")) {
                    GetAllFood();
                } else {
                    GetAllFoodSearch();
                }
            }
        });

        return view;
    }

    private void recyclerViewBestSeller() {
        // set recycler view layout to linear layout horizontal
        LinearLayoutManager constraintLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBestSellerList = view.findViewById(R.id.recyclerView2);
        recyclerViewBestSellerList.setLayoutManager(constraintLayoutManager);
    }

    private void GetCategory() {
        // set recycler view layout to linear layout horizontal
        LinearLayoutManager constraintLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = view.findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(constraintLayoutManager);

        // add item to categoryList
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("Meat","meat_category", 1));
        categoryList.add(new CategoryDomain("Seafood","seafood_category", 2));
        categoryList.add(new CategoryDomain("Vegetables","vegetables_category", 3));
        categoryList.add(new CategoryDomain("Drink","drink_category", 4));

        // implement CategoryAdapter to categoryList
        adapter2 = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter2);
    }

    private void setRecyclerView() {
        LinearLayoutManager constraintLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAllList = view.findViewById(R.id.recyclerView3);
        recyclerViewAllList.setLayoutManager(constraintLayoutManager);
    }

    public void GetAllFood() {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ALL_FOOD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_FOOD);

                                ArrayList<FoodDomain> allFoodList = new ArrayList<>();

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String name = a.getString(TAG_NAME);
                                    String pic = a.getString(TAG_PIC);
                                    String description = a.getString(TAG_DESCRIPTION);
                                    int id_category = a.getInt(TAG_ID_CATEGORY);
                                    int price = a.getInt(TAG_PRICE);

                                    allFoodList.add(new FoodDomain(name, pic, description, id_category, price));
                                }

                                adapter3 = new AllFoodAdapter(allFoodList);
                                recyclerViewAllList.setAdapter(adapter3);
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
//            VolleyConnection.getInstance(getContext()).addToRequestQueue(stringRequest);
            setRecyclerView();
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetAllFoodSearch() {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ALL_FOOD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_FOOD);

                                allFoodList = new ArrayList<>();

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String name = a.getString(TAG_NAME);
                                    String pic = a.getString(TAG_PIC);
                                    String description = a.getString(TAG_DESCRIPTION);
                                    int id_category = a.getInt(TAG_ID_CATEGORY);
                                    int price = a.getInt(TAG_PRICE);

                                    FoodDomain food = new FoodDomain(name, pic, description, id_category, price);
                                    if(containsIgnoreCase(name, searchView.getText().toString())) {
                                        allFoodList.add(food);
                                    }
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new AllFoodAdapter(allFoodList);
                            recyclerView3.setAdapter(adapter);
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
//            VolleyConnection.getInstance(getContext()).addToRequestQueue(stringRequest);
            setRecyclerView();
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetBestSellerFood() {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_BEST_SELLER_FOOD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_FOOD);

                                bestSellerList = new ArrayList<>();

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    String name = a.getString(TAG_NAME);
                                    String pic = a.getString(TAG_PIC);
                                    String description = a.getString(TAG_DESCRIPTION);
                                    int id_category = a.getInt(TAG_ID_CATEGORY);
                                    int price = a.getInt(TAG_PRICE);

                                    bestSellerList.add(new FoodDomain(name, pic, description, id_category, price));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter = new BestSellerAdapter(bestSellerList);
                            recyclerViewBestSellerList.setAdapter(adapter);
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
//            VolleyConnection.getInstance(getContext()).addToRequestQueue(stringRequest);
            recyclerViewBestSeller();
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}