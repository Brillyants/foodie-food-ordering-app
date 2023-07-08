package com.project.foodieadmin.Fragment;

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
import com.project.foodieadmin.Adapter.EmployeeAdapter;
import com.project.foodieadmin.DBContract;
import com.project.foodieadmin.Domain.EmployeeDomain;
import com.project.foodieadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeFragment extends Fragment {

    View view;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewAllEmployeeList;
    private ArrayList<EmployeeDomain> allEmployeeList;

    TextView searchView;
    Button btn_addEmployee;

    private static final String TAG_EMPLOYEE = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_USERNAME = "username";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employee, container, false);

        initView();

        GetAllEmployee();

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
                    recyclerViewAllEmployee();
                } else {
                    if(checkNetworkConnection()) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ALL_EMPLOYEE_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONArray member = jsonObject.getJSONArray(TAG_EMPLOYEE);

                                            allEmployeeList = new ArrayList<>();

                                            for (int i = 0; i < member.length(); i++) {
                                                JSONObject a = member.getJSONObject(i);

                                                int id = a.getInt(TAG_ID);
                                                String name = a.getString(TAG_NAME);
                                                String phone = a.getString(TAG_PHONE);
                                                String email = a.getString(TAG_EMAIL);
                                                String username = a.getString(TAG_USERNAME);

                                                EmployeeDomain employee = new EmployeeDomain(id, name, phone, email, username);
                                                if(containsIgnoreCase(name, searchView.getText().toString())) {
                                                    allEmployeeList.add(employee);
                                                }
                                            }
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        adapter = new EmployeeAdapter(allEmployeeList);
                                        recyclerViewAllEmployeeList.setAdapter(adapter);
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
                        recyclerViewAllEmployee();
                    } else {
                        Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addEmployeeFrag = new AddEmployeeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, addEmployeeFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void initView() {
        searchView = view.findViewById(R.id.searchView);
        btn_addEmployee = view.findViewById(R.id.btn_addEmployee);
    }

    private void recyclerViewAllEmployee() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerViewAllEmployeeList = view.findViewById(R.id.recyclerViewAllEmployee);
        recyclerViewAllEmployeeList.setLayoutManager(gridLayoutManager);
    }

    public void GetAllEmployee() {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_ALL_EMPLOYEE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_EMPLOYEE);

                                allEmployeeList = new ArrayList<>();

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    int id = a.getInt(TAG_ID);
                                    String name = a.getString(TAG_NAME);
                                    String phone = a.getString(TAG_PHONE);
                                    String email = a.getString(TAG_EMAIL);
                                    String username = a.getString(TAG_USERNAME);

                                    allEmployeeList.add(new EmployeeDomain(id, name, phone, email, username));
                                }

                                adapter = new EmployeeAdapter(allEmployeeList);
                                recyclerViewAllEmployeeList.setAdapter(adapter);
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
            recyclerViewAllEmployee();
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