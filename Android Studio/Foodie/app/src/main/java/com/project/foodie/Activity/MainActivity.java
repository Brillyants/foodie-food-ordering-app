package com.project.foodie.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.foodie.Fragment.AccountFragment;
import com.project.foodie.Fragment.CartFragment;
import com.project.foodie.Fragment.HomeFragment;
import com.project.foodie.Fragment.MenuFragment;
import com.project.foodie.Fragment.PaymentFragment;
import com.project.foodie.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_menu:
                        fragment = new MenuFragment();
                        break;
                    case R.id.nav_cart:
                        fragment = new CartFragment();
                        break;
                    case R.id.nav_payment:
                        fragment = new PaymentFragment();
                        break;
                    case R.id.nav_account:
                        fragment = new AccountFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }
}