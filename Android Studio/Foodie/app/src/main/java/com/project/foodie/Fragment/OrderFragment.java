package com.project.foodie.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.foodie.Activity.MainActivity;
import com.project.foodie.R;

import org.w3c.dom.Text;

public class OrderFragment extends Fragment {

    View view;
    Button btn_home;
    TextView order_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);
        btn_home = view.findViewById(R.id.btn_home);
        order_code = view.findViewById(R.id.order_code);

        Bundle args = getArguments();
        if (args != null) {
            order_code.setText(args.getString("order_code"));
        }

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}