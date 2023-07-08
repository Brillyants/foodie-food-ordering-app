package com.project.foodie.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.foodie.Activity.IntroActivity;
import com.project.foodie.DBContract;
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

public class AccountFragment extends Fragment {

    View view;

    SessionManager sessionManager;

    TextView txt_username, txt_name, txt_phone, txt_email;
    Button btn_editProfile, btn_logOut;

    private static final String TAG_PROFILE = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_USERNAME = "username";

    String name, phone, email;

    ManagementCart managementCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        managementCart = new ManagementCart(getContext());

        // initialize session manager
        sessionManager = new SessionManager(getContext());

        // get username
        String username = sessionManager.getUsername();

        txt_name = view.findViewById(R.id.txt_name);
        txt_phone = view.findViewById(R.id.txt_phone);
        txt_email = view.findViewById(R.id.txt_email);
        txt_username = view.findViewById(R.id.txt_username);

        GetProfile(username);

        btn_logOut = view.findViewById(R.id.btn_logOut);

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                // set title
                dialog.setTitle("Log Out");
                // set message
                dialog.setMessage("Are you sure to Log Out?");
                // set positive button
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // set login to false
                        sessionManager.setLogin(false);
                        // set username to empty
                        sessionManager.setUsername("");

                        ArrayList<FoodDomain> foodList = managementCart.getListCard();

                        if (foodList.size() > 0) {
                            // add cart in local to database server
                            for (int i = 0; i < foodList.size(); i++) {
                                AddCart(sessionManager.getId(), foodList.get(i).getTitle(), foodList.get(i).getNumInCard());
                            }
                            // remove cart in local database
                            managementCart.removeData();
                        }

                        // intent to IntroActivity
                        Intent i = new Intent(getContext(), IntroActivity.class);
                        startActivity(i);
                    }
                });

                // set negative button
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel dialog
                        dialog.cancel();
                    }
                });
                // initialize dialog
                AlertDialog alertDialog = dialog.create();
                // show dialog
                alertDialog.show();
            }
        });

        btn_editProfile = view.findViewById(R.id.btn_editProfile);

        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                // set title
                dialog.setTitle("Edit Profile");
                // set message
                dialog.setMessage("Are you sure to edit your profile?");
                // set positive button
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        name = txt_name.getText().toString();
                        phone = txt_phone.getText().toString();
                        email = txt_email.getText().toString();

                        EditProfile(username, name, phone, email);
                    }
                });
                // set negative button
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel dialog
                        dialog.cancel();
                    }
                });
                // initialize dialog
                AlertDialog alertDialog = dialog.create();
                // show dialog
                alertDialog.show();
            }
        });

        return view;
    }

    public void GetProfile(final String username) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_GET_PROFILE_URL,
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
                                    String email = a.getString(TAG_EMAIL);
                                    String username = a.getString(TAG_USERNAME);

                                    txt_name.setText(name);
                                    txt_phone.setText(phone);
                                    txt_email.setText(email);
                                    txt_username.setText("Hi There " + username + "!");
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
                    params.put("username", username);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void EditProfile(final String username, final String name, final String phone, final String email) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_EDIT_PROFILE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                if (success == 1) {
                                    Toast.makeText(getContext(), "Profile successfully updated!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Profile failed to update!", Toast.LENGTH_SHORT).show();
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
                    params.put("username", username);
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("email", email);
                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddCart(final int id_user, final String food_name, final int number_order) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.SERVER_ADD_CART,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {

                                } else {

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
                    params.put("id_user", String.valueOf(id_user));
                    params.put("food_name", food_name);
                    params.put("number_order", String.valueOf(number_order));
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