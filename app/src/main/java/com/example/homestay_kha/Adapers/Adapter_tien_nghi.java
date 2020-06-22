package com.example.homestay_kha.Adapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homestay_kha.Model.Tien_nghi_model;
import com.example.homestay_kha.R;

import java.util.List;

public class Adapter_tien_nghi extends RecyclerView.Adapter<Adapter_tien_nghi.ViewHolder> {
    List<Tien_nghi_model> Tien_nghi_models;
    int resource;
    Context context;

    public Adapter_tien_nghi(List<Tien_nghi_model> Tien_nghi_models, int resource, Context context) {
        this.Tien_nghi_models = Tien_nghi_models;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tien_nghi_model tienNghiModel  = Tien_nghi_models.get(position);
        holder.tv_tien_nghi.setText(tienNghiModel.getTentienich());
        if (holder.img_tien_nghi == null){
            holder.img_tien_nghi.setImageResource(R.drawable.ic_location_on_black_24dp);
        }
        else {
            holder.img_tien_nghi.setImageResource(Integer.parseInt(tienNghiModel.getHinhtienich()));
        }



    }

    @Override
    public int getItemCount() {
        return Tien_nghi_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_tien_nghi;
        TextView tv_tien_nghi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_tien_nghi = itemView.findViewById(R.id.img_tien_nghi);
            tv_tien_nghi = img_tien_nghi.findViewById(R.id.tv_tien_nghi);
        }
    }
}
