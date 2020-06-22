package com.example.homestay_kha.Views.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homestay_kha.Controller.Hotel_Controller;
import com.example.homestay_kha.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Hotel_Fragment extends Fragment {
    RecyclerView recyclerView_hotel;
    Hotel_Controller hotel_controller;
    SharedPreferences sharedPreferences;
    Location my_location;
    public Hotel_Fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        recyclerView_hotel= view.findViewById(R.id.recyclerView_hotel);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("my_location", MODE_PRIVATE);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        my_location=  new Location("");
        my_location.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        my_location.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        hotel_controller = new Hotel_Controller(getContext());
        hotel_controller.getHotel_List_Controller(getContext(),recyclerView_hotel,my_location);
    }

}
