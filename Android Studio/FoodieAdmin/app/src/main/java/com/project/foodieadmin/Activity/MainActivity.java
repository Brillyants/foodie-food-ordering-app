package com.project.foodieadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.foodieadmin.Fragment.AccountFragment;
import com.project.foodieadmin.Fragment.ConfirmedFragment;
import com.project.foodieadmin.Fragment.EmployeeFragment;
import com.project.foodieadmin.Fragment.InfoFragment;
import com.project.foodieadmin.Fragment.OrderFragment;
import com.project.foodieadmin.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new OrderFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.nav_order);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_order:
                        fragment = new OrderFragment();
                        break;
                    case R.id.nav_confirmed:
                        fragment = new ConfirmedFragment();
                        break;
                    case R.id.nav_employee:
                        fragment = new EmployeeFragment();
                        break;
                    case R.id.nav_info:
                        fragment = new InfoFragment();
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