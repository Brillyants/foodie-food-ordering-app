package com.project.foodieadmin.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.foodieadmin.R;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment {

    View view;

    Button terrain, satellite, normal, hybrid, btnDuperIndo, btnCahaya, btnPelangi, btnSinarTerang;
    GoogleMap map;
    LatLng home;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);

        try{
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    home = new LatLng(-6.1319698768027955, 106.71065266642218);
                    map.addMarker(new MarkerOptions().position(home).title("PT Duper Indo")).showInfoWindow();
                    map.moveCamera(CameraUpdateFactory.newLatLng(home));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(home,16));
                    map.setTrafficEnabled(true);
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        terrain = view.findViewById(R.id.btnTerrainMode);
        terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
        hybrid = view.findViewById(R.id.btnHybridMode);
        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
        satellite = view.findViewById(R.id.btnSatelliteMode);
        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        normal = view.findViewById(R.id.btnNormalMode);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        btnDuperIndo = view.findViewById(R.id.btnDuperIndo);
        btnDuperIndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        home = new LatLng(-6.1319698768027955, 106.71065266642218);
                        map.addMarker(new MarkerOptions().position(home).title("PT Duper Indo")).showInfoWindow();
                        map.moveCamera(CameraUpdateFactory.newLatLng(home));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home,16));
                        map.setTrafficEnabled(true);
                    }
                });
            }
        });

        btnCahaya = view.findViewById(R.id.btnCahaya);
        btnCahaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        home = new LatLng(-6.139314491152599, 106.73171368847298);
                        map.addMarker(new MarkerOptions().position(home).title("PT Cahaya")).showInfoWindow();
                        map.moveCamera(CameraUpdateFactory.newLatLng(home));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home,16));
                        map.setTrafficEnabled(true);
                    }
                });
            }
        });

        btnPelangi = view.findViewById(R.id.btnPelangi);
        btnPelangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        home = new LatLng(-6.184535833232443, 106.75320664499138);
                        map.addMarker(new MarkerOptions().position(home).title("PT Pelangi")).showInfoWindow();
                        map.moveCamera(CameraUpdateFactory.newLatLng(home));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home,16));
                        map.setTrafficEnabled(true);
                    }
                });
            }
        });

        btnSinarTerang = view.findViewById(R.id.btnSinarTerang);
        btnSinarTerang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        home = new LatLng(-6.175097663978205, 106.79046617360098);
                        map.addMarker(new MarkerOptions().position(home).title("PT Sinar Terang")).showInfoWindow();
                        map.moveCamera(CameraUpdateFactory.newLatLng(home));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home,16));
                        map.setTrafficEnabled(true);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    }
}