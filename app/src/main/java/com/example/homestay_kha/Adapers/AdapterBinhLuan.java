package com.example.homestay_kha.Adapers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homestay_kha.Model.BinhLuanModel;
import com.example.homestay_kha.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;


    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList){
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txtUsername,txtNoiDungBinhLuan,txtSoDiem,tv_tieude,tv_ngay;
        RecyclerView recyclerViewHinhBinhLuan_detail;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.cicleImageUser);
            txtUsername = itemView.findViewById(R.id.tv_user_name_comment);
            txtNoiDungBinhLuan =  itemView.findViewById(R.id.txtNodungbinhluan);
            txtSoDiem = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            recyclerViewHinhBinhLuan_detail = itemView.findViewById(R.id.recyclerHinhBinhLuan);
            tv_tieude = itemView.findViewById(R.id.tv_title_comment);
            tv_ngay =itemView.findViewById(R.id.textView_date);
        }
    }

    @Override
    public AdapterBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterBinhLuan.ViewHolder holder, int position) {
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.tv_ngay.setText(binhLuanModel.getDate_time());
        holder.tv_tieude.setText(binhLuanModel.getTieude());
        holder.txtUsername.setText(binhLuanModel.getThanhVienModel().getHoten());
        holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        holder.txtSoDiem.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.circleImageView,binhLuanModel.getThanhVienModel().getHinhanh());
        final List<Bitmap>  bitmapList = new ArrayList<>();
        for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);

                    if(bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()){
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(context,
                                R.layout.custom_layout_hinhbinhluan,bitmapList,binhLuanModel,false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                        holder.recyclerViewHinhBinhLuan_detail.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan_detail.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }



    }

    @Override
    public int getItemCount() {
//        int soBinhLuan = binhLuanModelList.size();
//        if(soBinhLuan > 5){
//            return 5;
//        }else{
//
//        }
        return binhLuanModelList.size();
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }


}
