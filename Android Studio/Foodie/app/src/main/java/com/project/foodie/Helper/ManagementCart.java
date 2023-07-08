package com.project.foodie.Helper;

import android.content.Context;
import android.widget.Toast;

import com.project.foodie.Domain.FoodDomain;
import com.project.foodie.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        // retrieve all food list
        ArrayList<FoodDomain> listFood = getListCard();

        boolean existAlready = false;
        int n = 0;

        // check if the food is already exists in food list
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        // if already exists, only set the number in cart
        // if not exists, add to item
        if (existAlready) {
            listFood.get(n).setNumInCard(item.getNumInCard());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", listFood);

        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();

    }

    public void insertExistingCart(FoodDomain item) {
        // retrieve all food list
        ArrayList<FoodDomain> listFood = getListCard();

        boolean existAlready = false;
        int n = 0;

        // check if the food is already exists in food list
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        // if already exists, only set the number in cart
        // if not exists, add to item
        if (existAlready) {
            listFood.get(n).setNumInCard(item.getNumInCard());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", listFood);

    }

    public ArrayList<FoodDomain> getListCard() {
        // create new object with key = CardList
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        // add num in cart for food in certain poisition
        listfood.get(position).setNumInCard(listfood.get(position).getNumInCard() + 1);

        // put all food to CardList
        tinyDB.putListObject("CardList", listfood);

        // change the number
        changeNumberItemsListener.changed();
    }

    public void MinusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        // if there's only 1 food, then remove the food, else minus the number in cart
        if (listfood.get(position).getNumInCard() == 1) {
            listfood.remove(position);

        } else {
            listfood.get(position).setNumInCard(listfood.get(position).getNumInCard() - 1);
        }

        // put all food to CardList
        tinyDB.putListObject("CardList", listfood);

        // change the number
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listFood2 = getListCard();
        double fee = 0;

        // loop for all food in list food and calculate the fee
        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (listFood2.get(i).getPrice() * listFood2.get(i).getNumInCard());
        }

        // return the fee (double)
        return fee;
    }

    public void removeData() {
        // remove all local data CardList
        ArrayList<FoodDomain> listFood = getListCard();
        listFood.clear();
        tinyDB.putListObject("CardList", listFood);
    }
}
