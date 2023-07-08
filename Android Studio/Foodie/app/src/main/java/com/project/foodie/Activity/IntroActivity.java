package com.project.foodie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.foodie.R;
import com.project.foodie.SessionManager;

public class IntroActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Button btn_get_started, btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // init required view
        initView();

        // initialize session manager
        sessionManager = new SessionManager(getApplicationContext());

        // if user already logged in, intent to MainActivity
        if (sessionManager.getLogin()) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        // when user click get started, intent to SignUp
        btn_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

        // when user click sign in started, intent to SignIn
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initView() {
        btn_get_started = findViewById(R.id.btn_get_started);
        btn_sign_in = findViewById(R.id.btn_sign_in);
    }

    @Override
    public void onBackPressed() {
        // when user press back, exit app
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}