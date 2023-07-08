package com.project.foodie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.project.foodie.Domain.CategoryDomain;
import com.project.foodie.Fragment.DrinkFragment;
import com.project.foodie.Fragment.MeatFragment;
import com.project.foodie.Fragment.SeafoodFragment;
import com.project.foodie.Fragment.VegetablesFragment;
import com.project.foodie.R;

public class CategoryDetailActivity extends AppCompatActivity {
    private CategoryDomain object;

    FragmentManager fragManager;
    MeatFragment meatFrag;
    DrinkFragment drinkFrag;
    SeafoodFragment seafoodFrag;
    VegetablesFragment vegetablesFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        meatFrag = new MeatFragment();
        meatFrag.setArguments(getIntent().getExtras());

        drinkFrag = new DrinkFragment();
        drinkFrag.setArguments(getIntent().getExtras());

        seafoodFrag = new SeafoodFragment();
        seafoodFrag.setArguments(getIntent().getExtras());

        vegetablesFrag = new VegetablesFragment();
        vegetablesFrag.setArguments(getIntent().getExtras());

        fragManager = getSupportFragmentManager();

        getBundle();
    }

    private void getBundle() {
        object = (CategoryDomain) getIntent().getSerializableExtra("object");

        if(object.getCategoryID() == 1) {
            final FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.replace(R.id.frame,meatFrag);
            fragTransaction.commit();

        } else if(object.getCategoryID() == 2){
            final FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.replace(R.id.frame,seafoodFrag);
            fragTransaction.commit();

        } else if(object.getCategoryID() == 3){
            final FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.replace(R.id.frame,vegetablesFrag);
            fragTransaction.commit();

        } else if(object.getCategoryID() == 4) {
            final FragmentTransaction fragTransaction = fragManager.beginTransaction();
            fragTransaction.replace(R.id.frame,drinkFrag);
            fragTransaction.commit();
        }
    }
}