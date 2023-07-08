package com.project.foodie.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodie.Adapter.AllFoodAdapter;
import com.project.foodie.DBContract;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuFragment extends Fragment {

    View view;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewAllFoodList;

    TextView searchView;
    Button buttonMeat, buttonSeafood, buttonVegetables, buttonDrink;

    private static final String TAG_FOOD = "data";
    private static final String TAG_ID_FOOD = "id_food";
    private static final String TAG_NAME = "name";
    private static final String TAG_PIC = "pic";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ID_CATEGORY = "id_category";
    private static final String TAG_PRICE = "price";

    private ArrayList<FoodDomain> allFoodList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        initView();

        buttonMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment meatFrag = new MeatFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, meatFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        buttonSeafood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment seafoodFrag = new SeafoodFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, seafoodFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        buttonVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment vegetablesFrag = new VegetablesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, vegetablesFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        buttonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment drinkFrag = new DrinkFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, drinkFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        // get all food from database and set to recyclerView
        GetAllFood();

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
                    recyclerViewAllFood();
                } else {
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
                                        recyclerViewAllFoodList.setAdapter(adapter);
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
                        recyclerViewAllFood();
                    } else {
                        Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void initView() {
        buttonMeat = view.findViewById(R.id.buttonMeat);
        buttonSeafood = view.findViewById(R.id.buttonSeafood);
        buttonVegetables = view.findViewById(R.id.buttonVegetables);
        buttonDrink = view.findViewById(R.id.buttonDrink);
        searchView = view.findViewById(R.id.searchView);
    }

    private void recyclerViewAllFood() {
        // set recycler view layout to grid layout with spanCount = 2
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewAllFoodList = view.findViewById(R.id.recyclerViewAllFood);
        recyclerViewAllFoodList.setLayoutManager(gridLayoutManager);
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

                                adapter = new AllFoodAdapter(allFoodList);
                                recyclerViewAllFoodList.setAdapter(adapter);
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
            recyclerViewAllFood();
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