package com.example.homestay_kha.Controller;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homestay_kha.Adapers.Adapter_tien_nghi;
import com.example.homestay_kha.Controller.Interface.Tien_nghi_Interface;
import com.example.homestay_kha.Model.Tien_nghi_model;
import com.example.homestay_kha.R;

import java.util.ArrayList;
import java.util.List;

public class Tien_nghi_Controller {
    Context context;
    Tien_nghi_model  tien_nghi_model;

    public Tien_nghi_Controller(Context context, Tien_nghi_model tien_nghi_model) {
        this.context = context;
        this.tien_nghi_model = tien_nghi_model;
    }
    public  void get_listTienNghi_Controller(Context context, RecyclerView recyclerView)
    {
        List<Tien_nghi_model> tien_nghi_models = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Adapter_tien_nghi adapter_tien_nghi = new Adapter_tien_nghi(tien_nghi_models, R.layout.tien_nghi_item,context);
        recyclerView.setAdapter(adapter_tien_nghi);
        Tien_nghi_Interface tien_nghi_interface = new Tien_nghi_Interface() {
            @Override
            public void getTiennghi(Tien_nghi_model tien_nghi_model) {
                tien_nghi_models.add(tien_nghi_model);
                adapter_tien_nghi.notifyDataSetChanged();
            }
        };
        tien_nghi_model.getTienNghiModel(tien_nghi_interface);

    }
}
