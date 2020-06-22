package com.example.homestay_kha.Adapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homestay_kha.Model.ChonHinhBinhLuanModel;
import com.example.homestay_kha.R;

import java.util.List;


public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {

    Context context;
    int resource;
    List<ChonHinhBinhLuanModel> listDuongDan;

    public AdapterChonHinhBinhLuan(Context context, int resource, List<ChonHinhBinhLuanModel> listDuongDan) {
        this.context = context;
        this.resource = resource;
        this.listDuongDan = listDuongDan;
    }

    @Override
    public ViewHolderChonHinh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderChonHinh viewHolderChonHinh = new ViewHolderChonHinh(view);
        return viewHolderChonHinh;
    }

    @Override
    public void onBindViewHolder(ViewHolderChonHinh holder, final int position) {
        final ChonHinhBinhLuanModel chonHinhBinhLuanModel = listDuongDan.get(position);

        Glide.with(context).load(chonHinhBinhLuanModel.getDuongdan()).into(holder.imageView);
        holder.checkBox.setChecked(chonHinhBinhLuanModel.isCheck());
        holder.checkBox.setOnClickListener(v -> {
            CheckBox checkBox = (CheckBox) v;
            listDuongDan.get(position).setCheck(checkBox.isChecked());
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }

    public class ViewHolderChonHinh extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;

        public ViewHolderChonHinh(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);

        }
    }

}
