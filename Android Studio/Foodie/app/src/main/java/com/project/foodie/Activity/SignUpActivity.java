package com.project.foodie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.project.foodie.DBContract;
import com.project.foodie.R;
import com.project.foodie.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    // email pattern checker
    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    // initialize EditText, TextInputLayout, Button
    EditText edt_name, edt_phone, edt_email, edt_username, edt_password, edt_confirm_password;
    TextInputLayout til_name, til_phone, til_email, til_username, til_password, til_confirm_password;
    Button btn_next;

    SessionManager sessionManager;
    private static final String TAG_PROFILE = "data";
    private static final String TAG_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initialize session manager
        sessionManager = new SessionManager(getApplicationContext());

        // findViewbyId all elements
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);

        til_name = findViewById(R.id.til_name);
        til_phone = findViewById(R.id.til_phone);
        til_email = findViewById(R.id.til_email);
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        til_confirm_password = findViewById(R.id.til_confirm_password);
        btn_next = findViewById(R.id.btn_next);

        // set next btn to disabled
        btn_next.setEnabled(false);

        // add addTextChangedListener to every edit text
        edt_name.addTextChangedListener(textWatcher);
        edt_phone.addTextChangedListener(textWatcher);
        edt_email.addTextChangedListener(textWatcher);
        edt_username.addTextChangedListener(textWatcher);
        edt_password.addTextChangedListener(textWatcher);
        edt_confirm_password.addTextChangedListener(textWatcher);

        // button on click
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String phone = edt_phone.getText().toString();
                String email = edt_email.getText().toString();
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                String confirm_password = edt_confirm_password.getText().toString();
                CreateDataToServer(name, phone, email, username, password);
            }
        });
    }

    // create TextWatcher object
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    // store login in session
                                    sessionManager.setLogin(true);
                                    // store username in session
                                    sessionManager.setUsername(username);
                                    // store id in session
                                    GetID(sessionManager.getUsername());
                                    Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                                    startActivity(i);
                                } else if(resp.equals("[{\"status\":\"Username Already Exists\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Username Already Exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetID(final String username) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_PROFILE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_PROFILE);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    int id = a.getInt(TAG_ID);

                                    sessionManager.setId(id);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    return params;
                }
            };
            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}