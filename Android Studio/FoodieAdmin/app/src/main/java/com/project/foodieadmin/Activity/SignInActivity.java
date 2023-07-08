package com.project.foodieadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodieadmin.DBContract;
import com.project.foodieadmin.Helper.SQLiteAdminData;
import com.project.foodieadmin.R;

import com.project.foodieadmin.SessionManager;
import com.project.foodieadmin.VolleyConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private SQLiteAdminData dbHandler;

    private static final String TAG_PROFILE = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // initialize session manager & SQLiteAdminData
        sessionManager = new SessionManager(getApplicationContext());
        dbHandler = new SQLiteAdminData(getApplicationContext());

        dbHandler.open();

        // if user already logged in, intent to MainActivity
        if (sessionManager.getLogin()) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        EditText edt_username = findViewById(R.id.edt_username);
        EditText edt_password = findViewById(R.id.edt_password);
        Button btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                CheckLogin(username, password);
            }
        });
    }

    public void CheckLogin(final String username, final String password) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_LOGIN_ADMIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    // store login in session
                                    sessionManager.setLogin(true);
                                    // create data to local database
                                    GetProfile(username);
                                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            VolleyConnection.getInstance(SignInActivity.this).addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetProfile(final String username) {
        if(checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_PROFILE_ADMIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray member = jsonObject.getJSONArray(TAG_PROFILE);

                                for (int i = 0; i < member.length(); i++) {
                                    JSONObject a = member.getJSONObject(i);

                                    int id = a.getInt(TAG_ID);
                                    String name = a.getString(TAG_NAME);
                                    String phone = a.getString(TAG_PHONE);
                                    String email = a.getString(TAG_EMAIL);
                                    String username = a.getString(TAG_USERNAME);

                                    dbHandler.createData(id, name, phone, email, username);
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