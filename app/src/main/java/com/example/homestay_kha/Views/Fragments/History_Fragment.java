package com.example.homestay_kha.Views.Fragments;


import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homestay_kha.Controller.Hotel_Controller;
import com.example.homestay_kha.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class History_Fragment extends Fragment {
    RecyclerView recyclerView_history;
    Hotel_Controller hotel_controller;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipeRefreshLayout;
    Location my_location;


    public History_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView_history = view.findViewById(R.id.recyclerView_history);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("my_location", MODE_PRIVATE);
        my_location=  new Location("");
        my_location.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        my_location.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        hotel_controller = new Hotel_Controller(getContext());
        hotel_controller.getHotel_List_Controller_history(getContext(),recyclerView_history,my_location);

    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerView_history.requestLayout();
    }
}
