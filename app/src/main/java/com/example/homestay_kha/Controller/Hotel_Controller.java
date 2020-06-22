package com.example.homestay_kha.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homestay_kha.Adapers.Adapter_Recycler_Hotel;
import com.example.homestay_kha.Adapers.Adapter_Recycler_Hotel_daxem;
import com.example.homestay_kha.Controller.Interface.Hotel_Interface;
import com.example.homestay_kha.Model.Hotel_model;
import com.example.homestay_kha.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.homestay_kha.R.color.xam;

public class Hotel_Controller {

    Context context;
    Hotel_model hotel_model;


    public Hotel_Controller(Context context) {
        this.context = context;
        hotel_model = new Hotel_model();
    }


    public void getHotel_List_Controller(Context context1, RecyclerView recyclerView, Location location_current) {
        final List<Hotel_model> hotel_model_list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        final Adapter_Recycler_Hotel adapter_recycler_hotel =
                new Adapter_Recycler_Hotel(hotel_model_list, R.layout.hotel_item_layout, context1);
        recyclerView.setAdapter(adapter_recycler_hotel);
        Hotel_Interface hotel_interface = new Hotel_Interface() {
            @Override
            public void getHotel_List_Model(Hotel_model hotel_model) {
                hotel_model_list.add(hotel_model);
                adapter_recycler_hotel.notifyDataSetChanged();
            }
        };
        hotel_model.getHotel_List(hotel_interface, location_current);
    }

    public void getHotel_List_Controller_history(Context context1, RecyclerView recyclerView, Location location_current) {
        final List<Hotel_model> hotel_model_list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        final Adapter_Recycler_Hotel_daxem adapter_recycler_hotel =
                new Adapter_Recycler_Hotel_daxem(hotel_model_list, R.layout.hotel_item_layout, context1);
        recyclerView.setAdapter(adapter_recycler_hotel);
        Hotel_Interface hotel_interface = new Hotel_Interface() {
            @Override
            public void getHotel_List_Model(Hotel_model hotel_model) {
                hotel_model_list.add(hotel_model);
                adapter_recycler_hotel.notifyDataSetChanged();
            }
        };
        hotel_model.getHotel_List_daxem(hotel_interface, location_current);


    }
}
