package com.example.homestay_kha.Adapers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.example.homestay_kha.Hotel_detail_Activity;
import com.example.homestay_kha.Model.BinhLuanModel;
import com.example.homestay_kha.Model.Hotel_Address_Model;
import com.example.homestay_kha.Model.Hotel_model;
import com.example.homestay_kha.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;



public class Adapter_Recycler_Hotel extends RecyclerView.Adapter<Adapter_Recycler_Hotel.ViewHolder> {
    List<Hotel_model> hotel_modelList;
    int resource;
    Context context;

    public Adapter_Recycler_Hotel(List<Hotel_model> hotel_modelList, int resource, Context context) {
        this.hotel_modelList = hotel_modelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_hotel, tv_address_hotel, tv_distance_to, tv_price,tv_diemtrungbinh,tv_dembinhluan,tv_demhinh;
        ImageView imageView_hotel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_hotel = itemView.findViewById(R.id.name_hotel);
            imageView_hotel = itemView.findViewById(R.id.img_hotel);
            tv_address_hotel = itemView.findViewById(R.id.adress_hotel);
            tv_distance_to = itemView.findViewById(R.id.tv_distanceTo);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_diemtrungbinh = itemView.findViewById(R.id.tv_trungbinhdiem);
            tv_dembinhluan =itemView.findViewById(R.id.tv_so_comment);
            tv_demhinh = itemView.findViewById(R.id.tv_sohinhbinhluan);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Hotel_model hotelModel = hotel_modelList.get(position);
        holder.tv_name_hotel.setText(hotelModel.getName_hotel());
        holder.tv_price.setText(hotelModel.getPrice_min() +" VNĐ");
        //lay hnh anh
        if (hotelModel.getImg_hotel().size() > 0) {
            StorageReference storageReference =
                    FirebaseStorage.getInstance().getReference().child("img_hotel")
                            .child(hotelModel.getImg_hotel().get(0));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                   holder.imageView_hotel.setImageBitmap(bitmap);
                }
            });
        }
        //lay dia chi
        if (hotelModel.getHotel_address_modelList().size() > 0) {
            Hotel_Address_Model hotel_address_model_tam = hotelModel.getHotel_address_modelList().get(0);
            for (Hotel_Address_Model hotel_address_model : hotelModel.getHotel_address_modelList()) {
                if (hotel_address_model_tam.getDistance_to() > hotel_address_model.getDistance_to()) {
                    hotel_address_model_tam = hotel_address_model;
                }
                holder.tv_address_hotel.setText(hotel_address_model_tam.getAddress());
                holder.tv_distance_to.setText(String.format("%.1f", hotel_address_model_tam.getDistance_to()) + " km");
            }
            //dem so binh luan va hinh
            int tongsohinhbinhluan = 0;
            double tongdiem = 0;

            for (BinhLuanModel binhLuanModel1 : hotelModel.getBinhLuanModelList()){
                tongsohinhbinhluan += binhLuanModel1.getHinhanhBinhLuanList().size();
                tongdiem += binhLuanModel1.getChamdiem();
            }
            if(tongsohinhbinhluan > 0){
                holder.tv_demhinh.setText(tongsohinhbinhluan + "");
            }
            double diemtrungbinh = tongdiem / hotelModel.getBinhLuanModelList().size();

            if (hotelModel.getBinhLuanModelList().size() <= 0){
                holder.tv_diemtrungbinh.setText("0.0"+"/5.0");
            }
            else {
                holder.tv_diemtrungbinh.setText(String.format("%.1f",diemtrungbinh)+"/5.0");
            }




            holder.tv_dembinhluan.setText(hotelModel.getBinhLuanModelList().size()+" ");
        }
        else {
            holder.tv_diemtrungbinh.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Hotel_detail_Activity.class);
                intent.putExtra("hotel", hotelModel);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return hotel_modelList.size();
    }


}
