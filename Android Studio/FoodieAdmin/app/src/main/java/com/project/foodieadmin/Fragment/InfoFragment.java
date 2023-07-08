package com.project.foodieadmin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.foodieadmin.R;

public class InfoFragment extends Fragment {

    View view;

    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);

        mViewPager = view.findViewById(R.id.pager);
        mViewPager.setAdapter(new myAdapter(getActivity().getSupportFragmentManager()));

        return view;
    }

    public class myAdapter extends FragmentPagerAdapter {
        public myAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new WebFragment();
            } else {
                return new MapsFragment();
            }
        }
    }
}