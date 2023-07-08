package com.project.foodie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.Helper.ManagementCart;
import com.project.foodie.R;
import com.squareup.picasso.Picasso;

public class    DetailActivity extends AppCompatActivity {
    private TextView addToCardBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView picFood;
    private Button plusBtn, minusBtn;
    private FoodDomain object;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        managementCart = new ManagementCart(this);

        initView();
        getBundle();
    }

    private void initView() {
        addToCardBtn = findViewById(R.id.addToCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        picFood = findViewById(R.id.foodPic);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
    }

    private void getBundle() {
        object = (FoodDomain) getIntent().getSerializableExtra("object");

        Picasso.with(getApplicationContext()).load(object.getPic()).into(picFood);

        titleTxt.setText(object.getTitle());
        feeTxt.setText("Rp. " + object.getPrice());
        descriptionTxt.setText(object.getDesc());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumInCard(numberOrder);
                managementCart.insertFood(object);
            }
        });
    }
}