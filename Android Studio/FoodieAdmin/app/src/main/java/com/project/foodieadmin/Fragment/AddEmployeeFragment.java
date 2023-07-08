package com.project.foodieadmin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.project.foodieadmin.DBContract;
import com.project.foodieadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEmployeeFragment extends Fragment {

    View view;
    // email pattern checker
    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    // initialize EditText, TextInputLayout, Button
    EditText edt_name, edt_phone, edt_email, edt_username, edt_password, edt_confirm_password;
    TextInputLayout til_name, til_phone, til_email, til_username, til_password, til_confirm_password;
    Button btn_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_employee, container, false);

        initView();

        btn_next.setEnabled(false);

        edt_name.addTextChangedListener(textWatcher);
        edt_phone.addTextChangedListener(textWatcher);
        edt_email.addTextChangedListener(textWatcher);
        edt_username.addTextChangedListener(textWatcher);
        edt_password.addTextChangedListener(textWatcher);
        edt_confirm_password.addTextChangedListener(textWatcher);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String phone = edt_phone.getText().toString();
                String email = edt_email.getText().toString();
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                CreateDataToServer(name, phone, email, username, password);
            }
        });

        return view;
    }

    private void initView() {
        edt_name = view.findViewById(R.id.edt_name);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_email = view.findViewById(R.id.edt_email);
        edt_username = view.findViewById(R.id.edt_username);
        edt_password = view.findViewById(R.id.edt_password);
        edt_confirm_password = view.findViewById(R.id.edt_confirm_password);

        til_name = view.findViewById(R.id.til_name);
        til_phone = view.findViewById(R.id.til_phone);
        til_email = view.findViewById(R.id.til_email);
        til_username = view.findViewById(R.id.til_username);
        til_password = view.findViewById(R.id.til_password);
        til_confirm_password = view.findViewById(R.id.til_confirm_password);
        btn_next = view.findViewById(R.id.btn_next);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Boolean bool_name = false;
            Boolean bool_phone = false;
            Boolean bool_email = false;
            Boolean bool_username = false;
            Boolean bool_password = false;
            Boolean bool_confirm_password = false;

            String name = edt_name.getText().toString();
            String phone = edt_phone.getText().toString();
            String email = edt_email.getText().toString();
            String username = edt_username.getText().toString();
            String password = edt_password.getText().toString();
            String confirm_password = edt_confirm_password.getText().toString();

            // name rules
            if(name.toString().equals("")) {

            } else {
                til_name.setError(null);
                bool_name = true;
            }

            // phone rules
            if(phone.toString().equals("")) {

            } else if(phone.length() > 12) {
                til_phone.setError("Maximum 12 digits!");
            } else if(phone.length() <= 12) {
                til_phone.setError(null);
                bool_phone = true;
            }

            // email rules
            if(email.toString().equals("")) {

            } else if(!email.toString().matches(email_pattern)) {
                til_email.setError("Fill in the valid email!");
            } else {
                til_email.setError(null);
                bool_email = true;
            }

            // username rules
            if(username.toString().equals("")) {

            } else if(username.length() > 30) {
                til_username.setError("Maximum 30 characters!");
            } else if(username.length() <= 30) {
                til_username.setError(null);
                bool_username = true;
            }

            // password rules
            if(password.toString().equals("")) {

            } else if(password.length() < 8) {
                til_password.setError("Minimum 8 characters!");
            } else if(password.length() >= 8) {
                til_password.setError(null);
                bool_password = true;
            }

            // confirm password rules
            if(confirm_password.toString().equals("")) {

            } else if(!confirm_password.toString().equals(edt_password.getText().toString())) {
                til_confirm_password.setError("Confirmation password should match with password!");
            } else if(confirm_password.toString().equals(edt_password.getText().toString())) {
                til_confirm_password.setError(null);
                bool_confirm_password = true;
            }

            // set next btn based on input field rules
            btn_next.setEnabled(bool_name && bool_phone && bool_email && bool_password && bool_confirm_password);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void CreateDataToServer(final String name, final String phone, final String email, final String username, final String password) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_CREATE_DATA_ADMIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                                    Fragment employeeFrag = new EmployeeFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, employeeFrag);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                } else if(resp.equals("[{\"status\":\"Username Already Exists\"}]")) {
                                    Toast.makeText(getContext(), "Username Already Exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
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
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("email", email);
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

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